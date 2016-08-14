package report.realtime.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testResults")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsolidatedResult {

	@XmlElement(name = "testResult")
	public List<TestResultDTO> testResults = new ArrayList<>();

	@XmlElement(name = "configurationMethod")
	public List<TestResultDTO> configurationMethods = new ArrayList<>();

	@XmlElement(name = "suites")
	public Set<SuiteResultDTO> suites = new ConcurrentSkipListSet<>();

	public List<TestResultDTO> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<TestResultDTO> testResults) {
		this.testResults = testResults;
	}

	public List<TestResultDTO> getConfigurationMethods() {
		return configurationMethods;
	}

	public void setConfigurationMethods(List<TestResultDTO> configurationMethods) {
		this.configurationMethods = configurationMethods;
	}

	public Set<SuiteResultDTO> getSuites() {
		return suites;
	}

	public void setSuites(Set<SuiteResultDTO> suites) {
		this.suites = suites;
	}

}
