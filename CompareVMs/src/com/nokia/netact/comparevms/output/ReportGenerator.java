package com.nokia.netact.comparevms.output;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.nokia.netact.comparevms.ReadFile;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * @author dmehar
 *
 */
public class ReportGenerator {

	/**
	 * FreeMarker library is used to generate HTML report
	 * 
	 * @param output
	 * @throws Exception
	 */
	public static void generateReport(Map<String, Map<String, List<Comparison>>> output) throws Exception {

		Configuration cfg = new Configuration(new Version("2.3.30"));
		cfg.setClassForTemplateLoading(ReportGenerator.class, "templates");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "VM Data Comparison Results");
		input.put("output", output);

		Template template = cfg.getTemplate("ReportTemplate.ftl");

		String outputFile = "output.html";
		Writer fileWriter = new FileWriter(new File(outputFile));
		try {
			template.process(input, fileWriter);
			System.out.println("Report generated: " + outputFile);
		} finally {
			fileWriter.close();
		}
	}
}
