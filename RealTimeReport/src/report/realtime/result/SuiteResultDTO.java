package report.realtime.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class SuiteResultDTO implements Comparable<SuiteResultDTO> {

	@XmlElementWrapper(name = "suiteNames")
	@XmlElement(name = "suiteName")
	private Set<String> suiteNames = null;
	private String currentSuiteName = null;
	private String remoteHost = null;

	@XmlElement(name = "excludedMethods")
	private List<ExMethodResultDTO> excludedMethods = new ArrayList<ExMethodResultDTO>();

	@XmlElementWrapper(name = "groups")
	@XmlElement(name = "group")
	private List<String> groups = new ArrayList<String>();
	private String parallel = null;

	@XmlElement(name = "testMethods")
	private List<MethodResultDTO> testMethods = new ArrayList<MethodResultDTO>();

	@XmlElementWrapper(name = "invokedMethods")
	@XmlElement(name = "invokedMethod")
	private List<String> invokedMethods = null;

	public Set<String> getSuiteNames() {
		return suiteNames;
	}

	public void setSuiteNames(Set<String> suiteNames) {
		this.suiteNames = suiteNames;
	}

	public String getCurrentSuiteName() {
		return currentSuiteName;
	}

	public void setCurrentSuiteName(String currentSuiteName) {
		this.currentSuiteName = currentSuiteName;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public List<ExMethodResultDTO> getExcludedMethods() {
		return excludedMethods;
	}

	public void setExcludedMethods(List<ExMethodResultDTO> excludedMethods) {
		this.excludedMethods = excludedMethods;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getParallel() {
		return parallel;
	}

	public void setParallel(String parallel) {
		this.parallel = parallel;
	}

	public List<MethodResultDTO> getTestMethods() {
		return testMethods;
	}

	public void setTestMethods(List<MethodResultDTO> testMethods) {
		this.testMethods = testMethods;
	}

	public List<String> getInvokedMethods() {
		return invokedMethods;
	}

	public void setInvokedMethods(List<String> invokedMethods) {
		this.invokedMethods = invokedMethods;
	}

	@Override
	public int compareTo(SuiteResultDTO o) {
		return this.getCurrentSuiteName().compareTo(o.getCurrentSuiteName());
	}
}
