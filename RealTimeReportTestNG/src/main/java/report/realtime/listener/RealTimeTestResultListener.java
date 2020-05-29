package report.realtime.listener;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import report.realtime.datahandler.DataMap;
import report.realtime.filehandler.CreateFiles;
import report.realtime.filehandler.CreateHTML;
import report.realtime.filehandler.CreateXML;
import report.realtime.result.ConsolidatedResult;
import report.realtime.xmlhandler.XMLGenerator;

/**
 * A simple wrapper class of TestListenerAdapter which implements ISuiteListener of TestNG.
 * It helps to gather all the Test Results onTestSuccess, onTestFailure, onTestSkipped
 * and onConfigurationFailure. This class also trigger the generation of html file
 * through which the test report will be displayed.
 *
 */
public class RealTimeTestResultListener extends TestListenerAdapter implements ISuiteListener {

	private ConsolidatedResult consolidatedResult;
	
	private int suiteIndex = 0;

	@Override
	public void onFinish(ISuite arg0) {
	}

	/**
	 * Checks whether current xml-suite has any child suites, depending on
	 * which the related Result folders will be created. i.e - If there are
	 * 2 testng.xml files and they are being called by another parent xml
	 * file, only 2 subsequent result folders will be generated under
	 * "RealtimeReport" folder.
	 */
	@Override
	public void onStart(ISuite suite) {
		if (suite.getXmlSuite().getChildSuites().size() == 0) {
			if (!DataMap.suiteMap.containsKey(suite)) {
				suiteIndex++;
				DataMap.suiteMap.put(suite, suiteIndex);
				consolidatedResult = new ConsolidatedResult();
			}
			CreateFiles.createRequiredFolders(suite);
			XMLGenerator.generateResultXML(suite, consolidatedResult);
			CreateXML.writeXML(suite, consolidatedResult);
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		XMLGenerator.generateResultXML(tr, consolidatedResult);
		CreateXML.writeXML(tr, consolidatedResult);
		CreateHTML.createHtmlFiles(tr);
		super.onTestSuccess(tr);
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		XMLGenerator.generateResultXML(tr, consolidatedResult);
		CreateXML.writeXML(tr, consolidatedResult);
		CreateHTML.createHtmlFiles(tr);
		super.onTestFailure(tr);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		XMLGenerator.generateResultXML(tr, consolidatedResult);
		CreateXML.writeXML(tr, consolidatedResult);
		CreateHTML.createHtmlFiles(tr);
		super.onTestSkipped(tr);
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		XMLGenerator.generateResultXML(itr, consolidatedResult);
		CreateXML.writeXML(itr, consolidatedResult);
		CreateHTML.createHtmlFiles(itr);
		super.onConfigurationFailure(itr);
	}
}