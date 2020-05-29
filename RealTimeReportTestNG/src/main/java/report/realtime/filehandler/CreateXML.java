package report.realtime.filehandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.testng.ISuite;
import org.testng.ITestResult;


import report.realtime.datahandler.DataMap;
import report.realtime.result.ConsolidatedResult;

public class CreateXML {

	synchronized public static void writeXML(ISuite iSuite, ConsolidatedResult result) {
		String rootFolder = FileNameConstants.PROJECT_DIR + File.separator + 
							FileNameConstants.TARGET_FOLDER + File.separator + 
							FileNameConstants.ROOT_FOLDER;
		if (!new File(rootFolder).exists()) {
			new File(rootFolder).mkdir();
		}
		int suiteIndex = 0;
		if (DataMap.suiteMap.containsKey(iSuite)) {
			suiteIndex = DataMap.suiteMap.get(iSuite);
		}		
		String xmlFilePath =  FileNameConstants.PROJECT_DIR + File.separator + 
							FileNameConstants.TARGET_FOLDER + File.separator + 
							FileNameConstants.ROOT_FOLDER + File.separator + 
							FileNameConstants.XML_FILE_NAME + "-" + suiteIndex + ".xml";
		if (new File(xmlFilePath).exists()) {
			new File(xmlFilePath).delete();
		}
		JAXBContext jaxbContext;
		try(BufferedWriter bufferWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(xmlFilePath, false), StandardCharsets.UTF_8))) {
			jaxbContext = JAXBContext.newInstance(ConsolidatedResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();			
			CDATAContentHandler cDataContentHandler = new CDATAContentHandler(sw, StandardCharsets.UTF_8.name());			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(result, cDataContentHandler);						
			bufferWriter.write(sw.toString());			
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

	synchronized public static void writeXML(ITestResult tr, ConsolidatedResult result) {
		ISuite iSuite = tr.getTestContext().getSuite();
		writeXML(iSuite, result);
	}
}
