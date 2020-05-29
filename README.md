# RealTimeReport
The wait is over!! Now you don't need to wait until end of the test for the report. This project help you to get the test report at **RunTime** using TestNG. You can intigrate this report with an existing project or can incorporate with new testNG project. Specality of this report is that - you don't need to change your existing codebase, only adding our pre-defined listner in user's **testng.xml** file can generate this report under *target/RealtimeReport* directory within your existing project structure. The *index.html* file holds the link of all test suites' results detail page.
The index.html file looks like: 

![index](https://user-images.githubusercontent.com/20871486/83250309-8d54de00-a1c5-11ea-90c1-89f507f5a5ad.png)

To get the details of any perticular suite user need to click the perticular suite button. Suite details report page looks like: 

![suiteresult](https://user-images.githubusercontent.com/20871486/83250340-99d93680-a1c5-11ea-9991-d2fb2b1f5c9a.png)

## Getting Started

Include **realtime-report-vx.x.jar** into your project classpath. *We've released our latest package at [Releases](https://github.com/startingwithseleniumwebdriver/RealTimeReport/releases)*.

You need to add the **RealTimeTestResultListener** class as *listener* to create this realtime report for any TestNg test.

	<listeners>
		<listener class-name="report.realtime.listener.RealTimeTestResultListener" />
	</listeners>

#### So Easy !!

## Credits

1. Santu Saha (saha.santu@gmail.com)
2. Rohan Ghosh (ghoshrohan07@gmail.com)
3. Arjun Ray (arjunray12@gmail.com)
4. Dipankar Rana (dipankar_6612@yahoo.co.in)
5. Ananta Bose (anantabose29@gmail.com)
