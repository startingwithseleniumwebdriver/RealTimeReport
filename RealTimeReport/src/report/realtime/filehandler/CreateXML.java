package report.realtime.filehandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.testng.ISuite;
import org.testng.ITestResult;


import report.realtime.datahandler.DataMap;
import report.realtime.result.ConsolidatedResult;

public class CreateXML {

	@SuppressWarnings("resource")
	synchronized public static void writeXML(ISuite iSuite, ConsolidatedResult result) {
		if (!new File("./" + FileNameConstants.ROOT_FOLDER).exists()) {
			new File("./" + FileNameConstants.ROOT_FOLDER).mkdir();
		}
		int suiteIndex = 0;
		if (DataMap.suiteMap.containsKey(iSuite)) {
			suiteIndex = DataMap.suiteMap.get(iSuite);
		}		
		String fileName = "./" + FileNameConstants.ROOT_FOLDER + "/" + FileNameConstants.XML_FILE_NAME + "-" + suiteIndex + ".xml";
		if (new File(fileName).exists()) {
			new File(fileName).delete();
		}
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ConsolidatedResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();			
			CDATAContentHandler cDataContentHandler = new CDATAContentHandler(sw, "UTF-8");			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(result, cDataContentHandler);						
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false), "UTF8") ) ;
			bw.write(sw.toString());			
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

	synchronized public static void writeXML(ITestResult tr, ConsolidatedResult result) {
		ISuite iSuite = tr.getTestContext().getSuite();
		writeXML(iSuite, result);
	}
}
