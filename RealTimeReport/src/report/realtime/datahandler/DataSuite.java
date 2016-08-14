package report.realtime.datahandler;

public class DataSuite implements Comparable<DataSuite> {

	private int suiteIndex = 0;
	private String suiteName = null;
	private String suiteHTMLPath = null;

	public DataSuite(int suiteIndex, String suiteName, String suiteHTMLPath) {
		this.suiteIndex = suiteIndex;
		this.suiteName = suiteName;
		this.suiteHTMLPath = suiteHTMLPath;
	}

	public int getSuiteIndex() {
		return suiteIndex;
	}

	public void setSuiteIndex(int suiteIndex) {
		this.suiteIndex = suiteIndex;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getSuiteHTMLPath() {
		return suiteHTMLPath;
	}

	public void setSuiteHTMLPath(String suiteHTMLPath) {
		this.suiteHTMLPath = suiteHTMLPath;
	}

	@Override
	public int compareTo(DataSuite ds) {
		return this.getSuiteHTMLPath().compareTo(ds.getSuiteHTMLPath());
	}

}
