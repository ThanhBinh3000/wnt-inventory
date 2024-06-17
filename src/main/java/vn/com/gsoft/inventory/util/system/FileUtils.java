package vn.com.gsoft.inventory.util.system;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.micrometer.common.util.StringUtils;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vn.com.gsoft.inventory.entity.ReportTemplateResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Component
public class FileUtils {

    public static final String InKhachQuen = "1";
    public static final String InKhachLeA5 = "2";
    public static final String InKhachLe80mm = "3";
    public static final String InKhachLe58mm = "4";
    public static final String InLieuDung = "5";
    public static final String InBuonA4 = "6";
    public static final String InBuonA5 = "7";
    public static final String InBuon80mm = "8";
    public static final String InCatLieu80mm = "9";
    public static final String InCatLieu58mm = "10";
    private static final String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    private static final String[] tens = {"", "", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
    private static final String[] thousands = {"", "nghìn", "triệu", "tỷ"};
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static ReportTemplateResponse convertDocxToPdf(InputStream inputFile, Object data, String barcode, Object... detail) throws Exception {
        try (ByteArrayOutputStream outputStreamPdf = new ByteArrayOutputStream();
             ByteArrayOutputStream outputStreamWord = new ByteArrayOutputStream()) {
            ReportTemplateResponse reportTemplateResponse = new ReportTemplateResponse();
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(inputFile, TemplateEngineKind.Velocity);
            if (barcode != null) {
                FieldsMetadata metadata = new FieldsMetadata();
                metadata.addFieldAsImage("imageBarcode");
                report.setFieldsMetadata(metadata);
            }
            IContext context = report.createContext();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("data", data);
            hashMap.put("numberTool", new NumberTool());
            hashMap.put("dateTool", new DateTool());
            hashMap.put("mathTool", new MathTool());
            hashMap.put("locale", new Locale("vi", "VN"));
            for (int i = 0; i < detail.length; i++) {
                hashMap.put("detail" + i, detail[i]);
            }
            if (barcode != null) {
                String barcodeBase64 = generateBarcodeBase64(barcode, 300, 100);
                byte[] imageBytes = Base64.getDecoder().decode(barcodeBase64);
                try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                     ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream()) {
                    BufferedImage image = ImageIO.read(bis);
                    ImageIO.write(image, "png", imageOutputStream);
                    hashMap.put("imageBarcode", imageOutputStream.toByteArray());
                }
            }
            context.putMap(hashMap);
            report.process(context, outputStreamWord);
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, outputStreamPdf);
            byte[] pdfBytes = outputStreamPdf.toByteArray();
            byte[] wordBytes = outputStreamWord.toByteArray();
            reportTemplateResponse.setPdfSrc(convertToBase64(pdfBytes));
            reportTemplateResponse.setWordSrc(convertToBase64(wordBytes));
            return reportTemplateResponse;
        }
    }

    public static String generateBarcodeBase64(String text, int width, int height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);
        byte[] barcodeBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(barcodeBytes);
    }

    public static String convertToBase64(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static InputStream getInputStreamByFileName(String fileName) throws FileNotFoundException {
        if (StringUtils.isNotEmpty(fileName) && fileName.contains(".")) {
            log.info("Find template {}", fileName);
            ClassLoader cl = Component.class.getClassLoader();
            InputStream is = cl.getResourceAsStream("\\template\\" + fileName);
            if (is == null) {
                log.info("Find template in template folder {}", fileName);
                String f = new File("").getAbsolutePath() + File.separator + "template" + File.separator + fileName;
                return new FileInputStream(f);
            }
            return is;
        } else {
            return null;
        }
    }

    public static Long safeToLong(Object o) {
        if (o == null) return 0L;
        if (o instanceof BigDecimal) return ((BigDecimal) o).longValue();
        if (o instanceof BigInteger) return ((BigInteger) o).longValue();
        try {
            return Long.parseLong(o.toString());
        } catch (NumberFormatException ignored) {
            return 0L;
        }
    }

    public static String safeToString(Object o) {
        return (o != null) ? o.toString() : null;
    }

    public static String convertToWords(Object amount) {
        BigDecimal number = toBigDecimal(amount);
        if (number == null)
            throw new IllegalArgumentException("Invalid input type");
        long num = number.longValue();
        if (num == 0) return "Không đồng";
        StringBuilder words = new StringBuilder();
        int place = 0;
        do {
            int n = (int) (num % 1000);
            if (n != 0) {
                words.insert(0, convertLessThanOneThousand(n) + " " + thousands[place] + " ");
            }
            place++;
            num /= 1000;
        } while (num > 0);
        String result = words.toString().trim() + " đồng ";
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    private static String convertLessThanOneThousand(int number) {
        StringBuilder current = new StringBuilder();
        if (number % 100 < 20) {
            current.append(units[number % 100]);
            number /= 100;
        } else {
            current.append(units[number % 10]);
            number /= 10;
            current.insert(0, tens[number % 10] + " ");
            number /= 10;
        }
        if (number > 0) {
            current.insert(0, units[number] + " trăm ");
        }
        return current.toString().trim();
    }

    private static BigDecimal toBigDecimal(Object amount) {
        if (amount instanceof BigDecimal) return (BigDecimal) amount;
        else if (amount instanceof Number) return BigDecimal.valueOf(((Number) amount).doubleValue());
        else if (amount instanceof String) {
            try {
                return new BigDecimal((String) amount);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}