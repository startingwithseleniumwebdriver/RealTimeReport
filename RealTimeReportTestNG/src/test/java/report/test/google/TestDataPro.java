package report.test.google;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.DataProvider;

public class TestDataPro {
	
private static Properties testMataDataProp = new Properties();
	
	static{
		try {
			testMataDataProp.load(new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/data/testdata.properties"));
		} catch (IOException e) {
			//logger.error("Error occured while loading test-metadata.properties",e);
		}
	}

	@DataProvider
	public static Object[][] getdata(){
		String data = testMataDataProp.getProperty("SEARCH");
		List<String> dataList = new ArrayList<String>(); 
		dataList = Arrays.asList(data.split(","));
		Object[][] loc = new Object[dataList.size()][1];
		for(int i=0;i<loc.length;i++){
			loc[i][0] = new String(dataList.get(i));
		}
		return loc;
	}
}
