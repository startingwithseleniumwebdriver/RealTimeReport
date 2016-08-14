package report.realtime.result;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class TestResultDTO {
	private String id = null;
	private String suiteName = null;
	private String className = null;
	private String context = null;
	private String instance = null;

	@XmlElementWrapper(name = "contextGroups")
	@XmlElement(name = "contextGroup")
	private List<String> contextGroups = null;
	private String methodName = null;

	@XmlElementWrapper(name = "methodParameters")
	@XmlElement(name = "methodParameter")
	private List<String> methodParameters = null;

	@XmlElementWrapper(name = "methodGroups")
	@XmlElement(name = "methodGroup")
	private List<String> methodGroups = null;
	private int methodInvocationTotalCount;
	private int methodThreadCount;
	private int methodCurrentInvocationCount;
	private String methodStatus = null;
	private String methodExecutionStartTime = null;
	private String methodExecutionEndTime = null;
	private long methodExecutionTotalTime;
	private String methodErrorCause = null;
	private String screenshotFile = null;
	private String annotation = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public List<String> getContextGroups() {
		return contextGroups;
	}

	public void setContextGroups(List<String> contextGroups) {
		this.contextGroups = contextGroups;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getMethodParameteres() {
		return methodParameters;
	}

	public void setMethodParameteres(List<String> methodParameters) {
		this.methodParameters = methodParameters;
	}

	public List<String> getMethodGroups() {
		return methodGroups;
	}

	public void setMethodGroups(List<String> methodGroups) {
		this.methodGroups = methodGroups;
	}

	public int getMethodInvocationTotalCount() {
		return methodInvocationTotalCount;
	}

	public void setMethodInvocationTotalCount(int methodInvocationTotalCount) {
		this.methodInvocationTotalCount = methodInvocationTotalCount;
	}

	public int getMethodThreadCount() {
		return methodThreadCount;
	}

	public void setMethodThreadCount(int methodThreadCount) {
		this.methodThreadCount = methodThreadCount;
	}

	public int getMethodCurrentInvocationCount() {
		return methodCurrentInvocationCount;
	}

	public void setMethodCurrentInvocationCount(int methodCurrentInvocationCount) {
		this.methodCurrentInvocationCount = methodCurrentInvocationCount;
	}

	public String getMethodStatus() {
		return methodStatus;
	}

	public void setMethodStatus(String methodStatus) {
		this.methodStatus = methodStatus;
	}

	public String getMethodExecutionStartTime() {
		return methodExecutionStartTime;
	}

	public void setMethodExecutionStartTime(String methodExecutionStartTime) {
		this.methodExecutionStartTime = methodExecutionStartTime;
	}

	public String getMethodExecutionEndTime() {
		return methodExecutionEndTime;
	}

	public void setMethodExecutionEndTime(String methodExecutionEndTime) {
		this.methodExecutionEndTime = methodExecutionEndTime;
	}

	public long getMethodExecutionTotalTime() {
		return methodExecutionTotalTime;
	}

	public void setMethodExecutionTotalTime(long methodExecutionTotalTime) {
		this.methodExecutionTotalTime = methodExecutionTotalTime;
	}

	public String getMethodErrorCause() {
		return methodErrorCause;
	}

	public void setMethodErrorCause(String methodErrorCause) {
		this.methodErrorCause = methodErrorCause;
	}

	public String getScreenshotFile() {
		return screenshotFile;
	}

	public void setScreenshotFile(String screenshotFile) {
		this.screenshotFile = screenshotFile;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
}
