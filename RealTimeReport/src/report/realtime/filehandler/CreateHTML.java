package report.realtime.filehandler;

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

	private static final String DASHBOARD_XSL_PATH = FileNameConstants.RESOURCE_FOLDER + "/"
			+ FileNameConstants.XSL_FOLDER + "/" + FileNameConstants.DASHBOARD_XSL;

	private static String XML_PATH = null;

	synchronized public static void createHtmlFiles(ITestResult tr) {
		ISuite iSuite = tr.getTestContext().getSuite();
		int suiteIndex = 0;
		if (DataMap.suiteMap.containsKey(iSuite)) {
			suiteIndex = DataMap.suiteMap.get(iSuite);
			XML_PATH = FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.XML_FILE_NAME + "-" + suiteIndex
					+ ".xml";
			String dashboardHtmlPath = FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.DASHBOARD_HTML + "-"
					+ suiteIndex + ".html";
			createHTML(dashboardHtmlPath);
		} else {
			// log error
		}
	}

	synchronized private static void createHTML(String htmlPath) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Source xslDoc = new StreamSource(DASHBOARD_XSL_PATH);
		Source xmlDoc = new StreamSource("./" + XML_PATH);
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
