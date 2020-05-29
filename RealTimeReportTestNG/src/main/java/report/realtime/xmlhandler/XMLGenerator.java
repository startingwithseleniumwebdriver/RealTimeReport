package report.realtime.xmlhandler;

import org.testng.ISuite;
import org.testng.ITestResult;

import report.realtime.datahandler.DataPreparator;
import report.realtime.result.ConsolidatedResult;
import report.realtime.result.SuiteResultDTO;
import report.realtime.result.TestResultDTO;

public class XMLGenerator {

	synchronized public static void generateResultXML(ISuite iSuite, ConsolidatedResult consolidatedResult) {
		SuiteResultDTO suiteResultDTO = new SuiteResultDTO();
		suiteResultDTO.setTestMethods(DataPreparator.prepareTestMethod(iSuite));
		suiteResultDTO.setSuiteNames(DataPreparator.prepareSuiteNameList(iSuite));
		suiteResultDTO.setExcludedMethods(DataPreparator.prepareExcludedTestMethod(iSuite));
		suiteResultDTO.setGroups(DataPreparator.prepareMethodGroupSet(iSuite));
		suiteResultDTO.setInvokedMethods(DataPreparator.prepareInvokedMethodList(iSuite));
		suiteResultDTO.setParallel(DataPreparator.prepareParallelTestFor(iSuite));
		suiteResultDTO.setRemoteHost(DataPreparator.prepareRemoteHost(iSuite));
		suiteResultDTO.setCurrentSuiteName(DataPreparator.prepareSuiteName(iSuite));
		// suiteResultDTO.setSuiteState(DataPreparator.pepareSuiteRunState(iSuite));
		consolidatedResult.getSuites().add(suiteResultDTO);
	}

	synchronized public static void generateResultXML(ITestResult iTestResult, ConsolidatedResult consolidatedResult) {
		TestResultDTO testResultDTO = new TestResultDTO();
		testResultDTO.setId(DataPreparator.prepareId(iTestResult));
		testResultDTO.setClassName(DataPreparator.prepareClassName(iTestResult));
		testResultDTO.setContext(DataPreparator.prepareContext(iTestResult));
		testResultDTO.setContextGroups(DataPreparator.prepareContextGroupList(iTestResult));
		testResultDTO.setInstance(DataPreparator.prepareInstance(iTestResult));
		testResultDTO.setMethodCurrentInvocationCount(DataPreparator.prepareCurrentInvocationCount(iTestResult));
		testResultDTO.setMethodErrorCause(DataPreparator.prepareErrorCause(iTestResult));
		testResultDTO.setMethodExecutionEndTime(DataPreparator.prepareMethodEndTime(iTestResult));
		testResultDTO.setMethodExecutionStartTime(DataPreparator.prepareMethodStartTime(iTestResult));
		testResultDTO.setMethodExecutionTotalTime(DataPreparator.prepareMethodExecutionTime(iTestResult));
		testResultDTO.setMethodGroups(DataPreparator.prepareMethodGroupList(iTestResult));
		testResultDTO.setMethodInvocationTotalCount(DataPreparator.prepareMethodInvocationTotalCount(iTestResult));
		testResultDTO.setMethodName(DataPreparator.prepareMethodName(iTestResult));
		testResultDTO.setMethodParameteres(DataPreparator.prepareMethodParameterList(iTestResult));
		testResultDTO.setMethodStatus(DataPreparator.prepareMethodStatus(iTestResult));
		// testResultDTO.setSuiteState(DataPreparator.prepareLatestSuiteState(iTestResult));
		testResultDTO.setMethodThreadCount(DataPreparator.prepareThreadCount(iTestResult));
		testResultDTO.setSuiteName(DataPreparator.prepareSuiteName(iTestResult));
		// TO DO =
		// testResultDTO.setScreenshotFile(DataPreparator.prepareScreenshotFile(iTestResult));
		testResultDTO.setAnnotation(DataPreparator.prepareAnnoatationName(iTestResult));
		if (iTestResult.getMethod().isTest()) {
			consolidatedResult.getTestResults().add(testResultDTO);
		} else {
			consolidatedResult.getConfigurationMethods().add(testResultDTO);
		}
	}
}
