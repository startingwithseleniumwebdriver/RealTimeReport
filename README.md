# RealTimeReport
This project created to get the realtime test report using TestNG. This report can be used with an existing project or can be used with newly created project. Specality of this report is, user don't need to change the existing codebase.Only adding listner in testng.xml file can generate this report under RealTimeReport directory within the project structure. Index.html file holds the link of different test suite result status.

User need to add the following listner to create this realtime report of any TestNg test.

	<listeners>
		<listener class-name="report.realtime.listener.RealTimeTestResultListener" />
	</listeners>

Credits :-
--------------------------------
--------------------------------
1. Rohan Ghosh (ghoshrohan07@gmail.com)
2. Santu Saha (saha.santu@gmail.com)
3. Ananta Bose
4. Dipankar Rana (dipankar_6612@yahoo.co.in)
5. Arjun Ray (arjunray12@gmail.com)
