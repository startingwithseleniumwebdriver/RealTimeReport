package report.realtime.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import report.realtime.result.ExMethodResultDTO;
import report.realtime.result.MethodResultDTO;

public class DataPreparator {

	synchronized public static String prepareSuiteName(ISuite suite) {
		return suite.getName();
	}

	synchronized public static Set<String> prepareSuiteNameList(ISuite suite) {
		XmlSuite xSuite = suite.getXmlSuite();
		listSuitesName(xSuite);
		return suiteNames;
	}

	private static Set<String> suiteNames = new ConcurrentSkipListSet<>();

	private static void listSuitesName(XmlSuite suite) {
		if (suite.getParentSuite() != null) {
			for (XmlSuite childSuite : suite.getParentSuite().getChildSuites()) {
				if (childSuite.getChildSuites().size() != 0) {
					listSuitesName(childSuite);
				} else {
					suiteNames.add(childSuite.getName());
				}
			}
		}
	}

	synchronized public static String prepareRemoteHost(ISuite suite) {
		return suite.getHost();
	}

	synchronized public static List<String> prepareExcludedMethodList(ISuite suite) {
		List<String> exMethods = new ArrayList<String>();
		for (ITestNGMethod testngMethod : suite.getExcludedMethods()) {
			exMethods.add(testngMethod.getMethodName());
		}
		return (exMethods.isEmpty()) ? null : exMethods;
	}

	synchronized public static String pepareSuiteRunState(ISuite suite) {
		return (suite.getSuiteState().isFailed()) ? "Fail" : "Pass";
	}

	synchronized public static String prepareReportOutputDirectory(ISuite suite) {
		return suite.getOutputDirectory();
	}

	synchronized public static String prepareParallelTestFor(ISuite suite) {
		return suite.getParallel();
	}

	synchronized public static List<String> prepareTestMethodList(ISuite suite) {
		List<String> testMethods = new ArrayList<String>();
		for (ITestNGMethod testNGMethod : suite.getAllMethods()) {
			testMethods.add(testNGMethod.getMethodName());
		}
		return (testMethods.isEmpty()) ? null : testMethods;
	}

	synchronized public static List<String> prepareInvokedMethodList(ISuite suite) {
		List<String> invokedMethods = new ArrayList<String>();
		for (IInvokedMethod invokedMethod : suite.getAllInvokedMethods()) {
			invokedMethods.add(invokedMethod.getTestMethod().getMethodName());
		}
		return (invokedMethods.isEmpty()) ? null : invokedMethods;
	}

	synchronized public static int getDataProvider(ITestNGMethod testNGMethod) {
		int dp = 1;
		Method testMethod = testNGMethod.getConstructorOrMethod().getMethod();
		if (testMethod.isAnnotationPresent(Test.class)) {// &&
			// testMethod.isAnnotationPresent(Count.class))
			// {
			Test testMethodTestAnnotation = testMethod.getAnnotation(Test.class);
			String dataProviderName = testMethodTestAnnotation.dataProvider();
			if (dataProviderName != null && !dataProviderName.isEmpty()) {
				Object instance = null;
				Class<?> dataProviderClass = testMethodTestAnnotation.dataProviderClass(); // my
				if (!dataProviderClass.isInstance(testNGMethod.getInstance())) { // check
					// if
					// dataprovider
					// class
					// is
					// test
					// class
					// itself
				} else {
					dataProviderClass = testNGMethod.getInstance().getClass();
				}
				try {
					instance = dataProviderClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					e1.getStackTrace();
				}
				Method[] allTestClassMethods = dataProviderClass.getMethods();
				for (Method m : allTestClassMethods) {
					if (m.isAnnotationPresent(DataProvider.class)) {
						DataProvider dataProviderAnnotation = m.getAnnotation(DataProvider.class);
						String dataProviderMethodName = m.getName(); // my
						String thisDataProviderName = dataProviderAnnotation.name();
						if (dataProviderName.equals(thisDataProviderName)
								|| dataProviderName.equals(dataProviderMethodName)) {
							try {
								Object[][] theData = (Object[][]) m.invoke(instance);
								Integer numberOfDataProviderRows = theData.length;
								dp = numberOfDataProviderRows;
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			} else {
			}
		} else {
		}
		return dp;
	}

	synchronized public static List<MethodResultDTO> prepareTestMethod(ISuite suite) {
		List<MethodResultDTO> resultDTOs = new ArrayList<MethodResultDTO>();
		MethodResultDTO dto;
		for (ITestNGMethod iTestNGMethod : suite.getAllMethods()) {
			dto = new MethodResultDTO();
			dto.setDataProvider(getDataProvider(iTestNGMethod));
			dto.setTestMethod(iTestNGMethod.getMethodName());
			resultDTOs.add(dto);
		}
		return resultDTOs;
	}

	synchronized public static List<ExMethodResultDTO> prepareExcludedTestMethod(ISuite suite) {
		List<ExMethodResultDTO> resultDTOs = new ArrayList<ExMethodResultDTO>();
		ExMethodResultDTO dto;
		for (ITestNGMethod iTestNGMethod : suite.getExcludedMethods()) {
			if(iTestNGMethod.isTest()){
				dto = new ExMethodResultDTO();
				dto.setDataProvider(getDataProvider(iTestNGMethod));
				dto.setExcludedMethod(iTestNGMethod.getMethodName());
				resultDTOs.add(dto);
			}
		}
		return resultDTOs;
	}

	synchronized public static List<String> prepareMethodGroupSet(ISuite suite) {
		List<String> groupSet = new ArrayList<String>();
		Map<String, Collection<ITestNGMethod>> methodMap = suite.getMethodsByGroups();
		if (!methodMap.isEmpty()) {
			groupSet.addAll(methodMap.keySet());
		}
		return groupSet;
	}

	synchronized public static String prepareId(ITestResult tr) {
		String id = "";
		for (String grp : prepareContextGroupList(tr)) {
			if (grp.contains(" ")) {
				grp = grp.replaceAll(" ", "-");
			}
			id = id + "_" + grp;
		}
		for (String grp : prepareMethodGroupList(tr)) {
			if (grp.contains(" ")) {
				grp = grp.replaceAll(" ", "-");
			}
			id = id + "_" + grp;
		}
		if (DataMap.testClassMap.containsKey(tr.getTestClass())) {
			id = id + "@" + DataMap.testClassMap.get(tr.getTestClass());
		} else {
			int classId = generateRandomInt();
			DataMap.testClassMap.put(tr.getTestClass(), classId);
			id = id + "@" + classId;
		}
		return id;
	}

	synchronized public static String prepareSuiteName(ITestResult tr) {
		return tr.getTestContext().getSuite().getName();
	}

	synchronized public static String prepareClassName(ITestResult tr) {
		return tr.getTestClass().getName(); // might be same as tr.getTestName()
	}

	synchronized public static String prepareContext(ITestResult tr) {
		return tr.getTestContext().toString();
	}

	synchronized public static String prepareLatestSuiteState(ITestResult tr) {
		return (tr.getTestContext().getSuite().getSuiteState().isFailed()) ? "Fail" : "Pass";
	}

	synchronized public static String prepareInstance(ITestResult tr) {
		return tr.getInstanceName(); // might be same as
		// tr.getInstance().toString()
	}

	synchronized public static List<String> prepareContextGroupList(ITestResult tr) {
		return Arrays.asList(tr.getTestContext().getIncludedGroups());
	}

	synchronized public static String prepareMethodName(ITestResult tr) {
		return tr.getMethod().getMethodName();
	}

	synchronized public static List<String> prepareMethodParameterList(ITestResult tr) {
		List<String> params = new ArrayList<String>();
		for (Object param : tr.getParameters()) {
			params.add(param.toString());
		}
		return (params.isEmpty()) ? null : params;
	}

	synchronized public static List<String> prepareMethodGroupList(ITestResult tr) {
		return Arrays.asList(tr.getMethod().getGroups());
	}

	synchronized public static int prepareMethodInvocationTotalCount(ITestResult tr) {
		return tr.getMethod().getInvocationCount();
	}

	synchronized public static int prepareThreadCount(ITestResult tr) {
		return tr.getMethod().getThreadPoolSize();
	}

	synchronized public static int prepareCurrentInvocationCount(ITestResult tr) {
		return tr.getMethod().getCurrentInvocationCount();
	}

	synchronized public static String prepareMethodStatus(ITestResult tr) {
		int status_int = 0;
		String status = null;
		status_int = tr.getStatus();
		switch (status_int) {
		case 1:
			status = "PASS";
			break;
		case 2:
			status = "FAIL";
			break;
		default:
			status = "SKIP";
			break;
		}
		return status;
	}

	synchronized public static String prepareMethodStartTime(ITestResult tr) {
		return convertMilisecondsToTimeString(tr.getStartMillis());
	}

	synchronized public static String prepareMethodEndTime(ITestResult tr) {
		return convertMilisecondsToTimeString(tr.getEndMillis());
	}

	synchronized public static long prepareMethodExecutionTime(ITestResult tr) {
		long time = tr.getEndMillis() - tr.getStartMillis();
		return time;
	}

	synchronized public static String prepareErrorCause(ITestResult tr) {
		String error = null;
		if (tr.getThrowable() != null) {
			error = getStackStraceInformation(tr.getThrowable());
		}
		return error;
	}

	/*
	 * synchronized public static String prepareScreenshotFile(ITestResult tr){
	 * String path = null; ITestContext key = tr.getTestContext();
	 * if(ScreenshotDataMap.dataMap.containsKey(key)){ path =
	 * ScreenshotDataMap.dataMap.get(key); } return path; }
	 */

	private static String convertMilisecondsToTimeString(long miliseconds) {
		String time = null;
		Date date = new Date(miliseconds);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		time = dateFormat.format(date);
		return time;
	}

	private static String getStackStraceInformation(Throwable th){
		String stack = null;
		StringBuilder sb = new StringBuilder();
		String message = th.getMessage();
		if(message != null){
			sb.append(message+"\n");
		}
		for (StackTraceElement iTestStackTrace : th.getStackTrace()) {
			sb.append("\t\t\t\t\t\t"+iTestStackTrace.toString()+"\n");
		}
		stack = sb.toString();
		return stack;
	}

	/*
	 * private static String formatedTimeDuration(long duration) { int hrs =
	 * (int) TimeUnit.MILLISECONDS.toHours(duration) % 24; int min = (int)
	 * TimeUnit.MILLISECONDS.toMinutes(duration) % 60; int sec = (int)
	 * TimeUnit.MILLISECONDS.toSeconds(duration) % 60; int milli = (int)
	 * TimeUnit.MILLISECONDS.toMillis(duration)%1000; return
	 * String.format("%02d:%02d:%02d.%02d", hrs, min, sec, milli); }
	 */

	public static int generateRandomInt() {
		int otp = 10000;
		Random r = new Random(System.currentTimeMillis());
		otp = (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
		return otp;
	}

	synchronized public static String prepareAnnoatationName(ITestResult tr) {
		String annoatationName = null;
		if (tr.getMethod().isAfterClassConfiguration())
			annoatationName = "AfterClassConfiguration_Method";
		else if (tr.getMethod().isAfterGroupsConfiguration())
			annoatationName = "AfterClassConfiguration_Method";
		else if (tr.getMethod().isAfterGroupsConfiguration())
			annoatationName = "AfterClassConfiguration_Method";
		else if (tr.getMethod().isAfterSuiteConfiguration())
			annoatationName = "AfterSuiteConfiguration_Method";
		else if (tr.getMethod().isAfterTestConfiguration())
			annoatationName = "AfterTestConfiguration_Method";
		else if (tr.getMethod().isBeforeClassConfiguration())
			annoatationName = "BeforeClassConfiguration_Method";
		else if (tr.getMethod().isBeforeGroupsConfiguration())
			annoatationName = "BeforeGroupsConfiguration_Method";
		else if (tr.getMethod().isBeforeMethodConfiguration())
			annoatationName = "BeforeMethodConfiguration_Method";
		else if (tr.getMethod().isBeforeSuiteConfiguration())
			annoatationName = "BeforeSuiteConfiguration_Method";
		else if (tr.getMethod().isBeforeTestConfiguration())
			annoatationName = "BeforeTestConfiguration_Method";
		else if (tr.getMethod().isTest())
			annoatationName = "Test_Method";
		else
			annoatationName = "Unknown";

		return annoatationName;
	}
}
