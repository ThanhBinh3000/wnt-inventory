package vn.com.gsoft.inventory.common;

import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.com.gsoft.inventory.entity.ReportTemplateResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@Component
public class DocxToPdfConverter {


    @Transactional
    public ReportTemplateResponse convertDocxToPdf(InputStream inputFile, Object data, Object... detail) {
        try {
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
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertToBase64(byte[] byteArray) throws Exception {
        String base64String = Base64.getEncoder().encodeToString(byteArray);
        return base64String;
    }
}