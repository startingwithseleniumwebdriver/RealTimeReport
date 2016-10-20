# RealTimeReport
The wait is over!! Now user don't need to wait until end of the test for the report. This project is created to get the test report at **RunTime** using TestNG. This report can be used with an existing project or can be used with newly created project. Specality of this report is that - user don't need to change their existing codebase, only adding our pre-defined listner in user's **testng.xml** file can generate this report under *RealtimeReport* directory within the user's existing project structure. The *index.html* file holds the link of all test suites' results detail page.
The index.html file looks like: 

![index](https://cloud.githubusercontent.com/assets/20871486/19214672/a7616c46-8da6-11e6-9495-082eb0989002.jpg)

To get the details of any perticular suite user need to click the perticular suite button. Suite details report page looks like: 

![suiteresult](https://cloud.githubusercontent.com/assets/20871486/19214673/a7c419e0-8da6-11e6-8079-6c3b9d536b6c.png)

User need to add the **RealTimeTestResultListener** class as *listeners* to create this realtime report of any TestNg test.

	<listeners>
		<listener class-name="report.realtime.listener.RealTimeTestResultListener" />
	</listeners>

Credits :-
--------------------------------
--------------------------------
1. Santu Saha (saha.santu@gmail.com)
2. Rohan Ghosh (ghoshrohan07@gmail.com)
3. Arjun Ray (arjunray12@gmail.com)
4. Dipankar Rana (dipankar_6612@yahoo.co.in)
5. Ananta Bose (anantabose29@gmail.com)
