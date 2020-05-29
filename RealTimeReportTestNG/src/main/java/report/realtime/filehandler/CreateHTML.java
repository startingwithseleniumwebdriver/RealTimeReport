package report.realtime.filehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.ISuite;
import org.testng.ITestResult;

import report.realtime.datahandler.DataMap;

public class CreateHTML extends CreateFiles {

	private static final String DASHBOARD_XSL_PATH = FileNameConstants.PROJECT_DIR + File.separator + 
													FileNameConstants.TARGET_FOLDER + File.separator + 
													FileNameConstants.ROOT_FOLDER + File.separator + 
													FileNameConstants.XSL_FOLDER + File.separator + 
													FileNameConstants.DASHBOARD_XSL;
	private static String XML_PATH = null;
	
	synchronized public static void createHtmlFiles(ITestResult tr) {
		ISuite iSuite = tr.getTestContext().getSuite();
		int suiteIndex = 0;
		String rootfolder = System.getProperty("user.dir") + "/target/"+ FileNameConstants.ROOT_FOLDER + "/";
		if (DataMap.suiteMap.containsKey(iSuite)) {
			suiteIndex = DataMap.suiteMap.get(iSuite);
			XML_PATH = FileNameConstants.PROJECT_DIR + File.separator + 
					FileNameConstants.TARGET_FOLDER + File.separator + 
					FileNameConstants.ROOT_FOLDER + File.separator + 
					FileNameConstants.XML_FILE_NAME + "-" + suiteIndex + ".xml";
			String dashboardHtmlPath = rootfolder + FileNameConstants.DASHBOARD_HTML + "-" + suiteIndex + ".html";
			createHTML(dashboardHtmlPath);
		} else {
			// log error
		}
	}

	synchronized private static void createHTML(String htmlPath) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Source xslDoc = new StreamSource(DASHBOARD_XSL_PATH);
		Source xmlDoc = new StreamSource(XML_PATH);
		Transformer transformer = null;
		OutputStream htmlFile = null;
		try {
			htmlFile = new FileOutputStream(htmlPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			transformer = tFactory.newTransformer(xslDoc);
			transformer.transform(xmlDoc, new StreamResult(htmlFile));
		} catch (TransformerException te) {
			te.printStackTrace();
		}
	}
}
