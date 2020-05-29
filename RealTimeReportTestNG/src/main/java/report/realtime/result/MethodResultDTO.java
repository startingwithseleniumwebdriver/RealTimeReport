package report.realtime.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MethodResultDTO {

	@XmlAttribute(name = "dataProvider")
	private int dataProvider = 1;

	@XmlElement(name = "testMethod")
	private String testMethod = null;

	public int getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(int dataProvider) {
		this.dataProvider = dataProvider;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
}
