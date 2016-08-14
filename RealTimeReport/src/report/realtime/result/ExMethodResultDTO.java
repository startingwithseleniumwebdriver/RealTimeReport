package report.realtime.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExMethodResultDTO {

	@XmlAttribute(name = "dataProvider")
	private int dataProvider = 1;

	@XmlElement(name = "excludedMethod")
	private String excludedMethod = null;

	public int getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(int dataProvider) {
		this.dataProvider = dataProvider;
	}

	public String getExcludedMethod() {
		return excludedMethod;
	}

	public void setExcludedMethod(String excludedMethod) {
		this.excludedMethod = excludedMethod;
	}
}
