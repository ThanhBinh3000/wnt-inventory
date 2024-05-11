package vn.com.gsoft.inventory.util.system;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.gsoft.inventory.entity.ReportTemplateResponse;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;

@Component
public class FileUtils {

    public ReportTemplateResponse convertDocxToPdf(InputStream inputFile, Object data, Object... detail) throws Exception {
        ByteArrayOutputStream outputStreamPdf = new ByteArrayOutputStream();
        ByteArrayOutputStream outputStreamWord = new ByteArrayOutputStream();
        ReportTemplateResponse reportTemplateResponse = new ReportTemplateResponse();
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(inputFile, TemplateEngineKind.Velocity);
        IContext context = report.createContext();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("data", data);
        hashMap.put("numberTool", new NumberTool());
        hashMap.put("dateTool", new DateTool());
        hashMap.put("mathTool", new MathTool());
        hashMap.put("locale", new Locale("vi", "VN"));
        if (detail.length > 0) {
            Object[] details = detail.clone().clone();
            for (int i = 0; i < detail.length; i++) {
                hashMap.put("detail" + i, details[i]);
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
        outputStreamPdf.close();
        outputStreamWord.close();
        return reportTemplateResponse;
    }

    public String convertToBase64(byte[] byteArray) throws Exception {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static InputStream templateInputStream(String templateName) throws IOException {
        InputStream templateInputStream = null;
        File file = new ClassPathResource(templateName).getFile();
        if (file.exists()) {
            templateInputStream = new FileInputStream(file);
        } else {
            try {
                templateInputStream = new ClassPathResource(templateName).getInputStream();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (templateInputStream == null) {
            throw new FileNotFoundException("Không tìm thấy file template: " + templateName);
        }
        return templateInputStream;
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

    public static String safeToString(Object o, String defaultValue) {
        return (o != null) ? o.toString() : defaultValue;
    }
}
