package report.realtime.datahandler;

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
			testMataDataProp.load(new FileInputStream("data/testdata.properties"));
		} catch (IOException e) {
			//logger.error("Error occured while loading test-metadata.properties",e);
		}
	}

	@DataProvider
	public static Object[][] getdata(){
		String locales = testMataDataProp.getProperty("LOCALE");
		List<String> localeList = new ArrayList<String>(); 
		localeList = Arrays.asList(locales.split(","));
		Object[][] loc = new Object[localeList.size()][1];
		for(int i=0;i<loc.length;i++){
			loc[i][0] = new String(localeList.get(i));
			//System.out.println("Remote Locale "+i+": "+loc[i][0]);
		}
		return loc;
		//return new Object[][]{{"/en_us"},{"/fr_fr"}};
	}
}
