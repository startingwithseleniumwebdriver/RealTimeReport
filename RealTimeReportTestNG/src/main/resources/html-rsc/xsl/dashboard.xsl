<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">



<!-- Parameters -->
<xsl:param name="pageTitle" select="'Real Time Report'" />
<xsl:param name="reportHeading" select="'Test Results'" />

<xsl:template match="testResults">
	<html>
		<head>
			<title><xsl:value-of select="$pageTitle" /></title>
			<meta name="viewport" content="width=device-width, initial-scale=1" />
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<!--<meta http-equiv="refresh" content="3">-->
				<!-- Bootstrap Core CSS -->
			<link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' />
				<!-- Custom CSS -->
			<link href="css/new-style.css" rel='stylesheet' type='text/css' />
				<!-- Font CSS -->
			<link href="css/font-awesome.min.css" rel="stylesheet" type='text/css' />
				<!-- chart -->
			<script src="js/Chart2.0.js"></script>
				<!-- jQuery JS-->
			<script src="js/jquery-1.10.2.min.js"></script>
				<!-- Bootstrap JS -->
			<script src="js/bootstrap.min.js"></script>
				<!-- Moment Js -->
			<script src="js/moment.js"></script>
		</head>
		<body class="bdy-clr">
			<xsl:call-template name="header" />
			<xsl:call-template name="bodyContent" />
			<xsl:call-template name="footer" />
		</body>
	</html>
</xsl:template>

<xsl:template name="header">
	<!-- Header Start -->
	<xsl:variable name="totalSuites" select="count(/testResults/suites/suiteNames/suiteName)" />
	<xsl:variable name="currentSuiteName" select="/testResults/suites/currentSuiteName/text()" />
		<div class="navbar navbar-fixed-top container-fluid head-colr">
			<script language="JavaScript"><![CDATA[
				$(document).ready(function(){
					$('.refresh').click(function(){
						document.location.reload(true);
					});
					$('.pa_italic').click(function(){
						$(this).children('span').toggle();
					});
					$('input[name="filter"]').on("change",function(){
						$('.accordian-panel-heading').hide();
						if($(this).val() == "group"){
							$('#selGrp').show();
							$('#selGrp option:first-child').prop({ selected : true});
							$('#selCls').hide();
							$('#selVerd').hide();
							$('.accordian-panel-heading').each(function(){
								if($(this).attr('id') == 'config'){
									$(this).hide();
								}
							});
						}else if($(this).val() == "verdict"){
							$('#selVerd').show();
							$('#selVerd option:first-child').prop({ selected : true});
							$('#selGrp').hide();
							$('#selCls').hide();
							$('.accordian-panel-heading').each(function(){
								if($(this).attr('id') == 'config'){
									$(this).hide();
								}
							});
						}else if($(this).val() == "class"){
							$('#selGrp').hide();
							$('#selCls').show();
							$('#selVerd').hide();
							$('.accordian-panel-heading').each(function(){
								if($(this).attr('id') == 'config'){
									$(this).hide();
								}
							});
							$('#selCls option:first-child').prop({ selected : true});
						}else{
							$('#selGrp').hide();
							$('#selCls').hide();
							$('#selVerd').hide();
							$('.accordian-panel-heading').each(function(){
								if($(this).attr('id') == 'config'){
									$(this).show();
								}else{
									$(this).hide();
								}
							});
						}
					});
					var testClasses = [];
					var testClassValue = [];
					var testGroups = [];
						$('#selCls option').each(function(k,v){
							var currentClassName = $(this).text();
							var currentOptionVal = $(this).val();
							var currentClassVal = currentOptionVal.split('@');
							var matched = false;
							for(var i=0; i<testClasses.length; i++){
								if(currentClassName == testClasses[i]){
									matched = true;
								}
							}
							if(!matched){
								testClasses.push(currentClassName);
								testClassValue.push(currentClassVal[1]);
							}
						});
						$('#selGrp option').each(function(k,v){
							var currentGrpName = $(this).text();
							var matched = false;
							for(var i=0; i<testGroups.length; i++){
								if(currentGrpName == testGroups[i]){
									matched = true;
								}
							}
							if(!matched){
								testGroups.push(currentGrpName);
							}
						});
						$('#selCls option').remove();
						$('#selGrp option').remove();
						$('#selCls').append('<option selected="selected">--Select Test Class Name--</option>');
						$('#selGrp').append('<option selected="selected">--Select Test Group Name--</option>');
						$.each(testClasses,function(index){
							$('#selCls').append('<option value="'+testClassValue[index]+'">'+testClasses[index]+'</option>');
						});
						$.each(testGroups,function(k,v){
							$('#selGrp').append('<option>'+v+'</option>');
						});
						$('#selVerd').on("change",function(){
							var selectedVerdict = $(this).find("option:selected").val();
							$('.accordian-panel-heading').each(function(){
								var title = $(this).attr('title');
								if(selectedVerdict == title){
									$(this).show();
								}else{
									$(this).hide();
								}
							});
						});
						$('#selCls').on("change", function(){
							var selectedClassName = $(this).find("option:selected").val();
							$('.accordian-panel-heading').each(function(k,v){
								var idCls = $(this).attr('id').split('@');
								if(selectedClassName == idCls[1]){
									$(this).show();
								}else{
									$(this).hide();
								}
							});
						});
						$('#selGrp').on("change", function(){
							var selGroup = $(this).find("option:selected").text();
							var selectedGroup = selGroup.replace(new RegExp(' ','g'),'-');
							$('.accordian-panel-heading').each(function(k,v){
								var splitedID = $(this).attr('id').split('@');
								var groupArray = splitedID[0].split('_');
								if(groupArray.length > 0){
									var matched = false;
									for(var i=0; i<groupArray.length; i++){
										if(selectedGroup == groupArray[i]){
											matched = true;
										}
									}
									if(matched){
										$(this).show();
									}else{
										$(this).hide();
									}
								}
							});
						});
						setInterval(function(){
							var displayedResult = $('.accordian-panel-heading:visible').size();
							$('#show').html(displayedResult);
						},100);
				});
				function getHMS(ms,id){
					var tms = parseInt(ms);
					var tm = moment.duration(tms);
					var message = tm.hours()+":"+tm.minutes()+":"+tm.seconds();
					$('#'+id).html(message);
				}
				function getPercent(decimal,id){
					var deci = Math.round(decimal);
					$('#'+id).html(deci);
				}
				function getProgress(decimal){
					var deci = Math.round(decimal);
					$('div[role="progressbar"]').html(deci+' % Complete');
					$('div[role="progressbar"]').attr('aria-valuenow',deci);
					$('div[role="progressbar"]').width(deci+'%');
				}
			]]></script>
			<div class="col-md-9">
				<h2 class="header-title">
					<xsl:value-of select="$reportHeading" /> for <xsl:value-of select="$currentSuiteName" />
				</h2>
			</div>
			<div class="col-md-3 gap-top-small">
				<span class="refresh btn btn-success" title="Refresh"><i class="fa fa-refresh"></i> Refresh</span>
			</div>
		</div>	
	<!-- Header End -->
</xsl:template>
<xsl:template name="bodyContent">
	<!-- Body content Start -->
	<xsl:variable name="executionTime" select="sum(/testResults/testResult/methodExecutionTotalTime)" />
	<xsl:variable name="totalTestMethod" select="sum(/testResults/suites/testMethods/@dataProvider)" />
	<xsl:variable name="excludedMethod" select="sum(/testResults/suites/excludedMethods/@dataProvider)" />
	<xsl:variable name="executableMethod" select="($totalTestMethod - $excludedMethod)" />
	<xsl:variable name="completedTestMethod" select="count(/testResults/testResult)" />
	<xsl:variable name="percentComplete" select="(($completedTestMethod * 100) div $executableMethod)" />
	<xsl:variable name="passedMethods" select="count(/testResults/testResult/methodStatus[text() = 'PASS'])" />
	<xsl:variable name="failedMethods" select="count(/testResults/testResult/methodStatus[text() = 'FAIL'])" />
	<xsl:variable name="skippedMethods" select="count(/testResults/testResult/methodStatus[text() = 'SKIP'])" />
	<xsl:variable name="passedPercent" select="(($passedMethods * 100) div $executableMethod)" />
	<xsl:variable name="failPercent" select="(($failedMethods * 100) div $executableMethod)" />
	<xsl:variable name="skippedPercent" select="(($skippedMethods * 100) div $executableMethod)" />
	<xsl:variable name="totalSuite" select="count(/testResults/suites/suiteNames/suiteName)" />
	<xsl:variable name="parallel" select="/testResults/suites/parallel/text()" />
	<!--<xsl:variable name="testing" select="count(/testResults/testresult[methodStatus = 'PASS'][methodThreadCount = '1'])" /> -->
	
	<div class="container-fluid">
		<div class="container gap-below gap-top">
			<div class="progress">
				<div class="progress-bar progress-bar-info" role="progressbar" aria-valuemin="0" aria-valuemax="100">
				<script>getProgress('<xsl:value-of select="$percentComplete" />')</script>
				</div>
			</div>
		</div>
		<div class="container gap-below">
			<div class="col-md-4 gap-small-bottom">	
				<div class="r3_counter_box">
					<i class="large fa fa-check"></i>
					<div class="stats">
						<h5 id="passPer"><script>getPercent('<xsl:value-of select="$passedPercent" />','passPer')</script> <span>%</span></h5>
						<div class="grow">
							<p>Pass</p>
						</div>
					</div>	
				</div>			
			</div>
			<div class="col-md-4 gap-small-bottom">	
				<div class="r3_counter_box">
					<i class="large fa fa-times"></i>
					<div class="stats">
						<h5 id="failPer"><script>getPercent('<xsl:value-of select="$failPercent" />','failPer')</script> <span>%</span></h5>
						<div class="grow grow1">
							<p>Fail</p>
						</div>
					</div>				
				</div>			
			</div>
			<div class="col-md-4">	
				<div class="r3_counter_box">
					<i class="large fa fa-forward"></i>
					<div class="stats">
						<h5 id="skipPer"><script>getPercent('<xsl:value-of select="$skippedPercent" />','skipPer')</script> <span>%</span></h5>
						<div class="grow grow2">
							<p>Skip</p>
						</div>
					</div>	
				</div>			
			</div>
		</div>
		<div class="container gap-below">
			<div class="col-md-6 gap-small-bottom">
				<div class="r3_counter_box graphical">
					<div class="white-box-title">
						Result
					</div>
					<div id="canvas-holder" style="width:70%;">
						<canvas id="chart-area" width="300" height="150"></canvas>
					</div>
				</div>
			</div>
			<script>
			window.onload = function(){
				var ctx = document.getElementById("chart-area").getContext("2d");
				var myChart = new Chart(ctx, {
				    type: 'pie',
				    data: {
						labels: [
							"PASS",
							"FAIL",
							"SKIP"
						],
						datasets: [
						{
							data: ['<xsl:value-of select="$passedMethods" />', '<xsl:value-of select="$failedMethods" />', '<xsl:value-of select="$skippedMethods" />'],
							backgroundColor: [
								"#8bc34a",
								"#f44336",
								"#ffca28"
							],
							hoverBackgroundColor: [
								"#8bc75b",
								"#f44448",
								"#FFCE66"
							]
						}]
					}
				});
				}
			</script>
			<div class="col-md-6">
				<div class="col-md-6 gap-below highlight fix-height">
					<div class="r3_counter_box blue">
						<p class="title left">Suite</p>
						<p class="number right font-800">
						<xsl:if test="$totalSuite &gt; 0">
							<xsl:value-of select="$totalSuite" />
						</xsl:if>
						<xsl:if test="0 &gt;= $totalSuite">
							1
						</xsl:if>
						</p>
					</div>
				</div>
				<div class="col-md-6 gap-below highlight fix-height">
					<div class="r3_counter_box green">
						<p class="title left">Executable Test Methods</p>
						<p class="number right font-800"><xsl:value-of select="$executableMethod" /></p>
					</div>
				</div>
				<div class="col-md-6 highlight fix-height">
					<div class="r3_counter_box purple">
						<p class="title left">Running Parallel</p>
						<p class="number right font-600"><xsl:value-of select="$parallel" /></p>
					</div>
				</div>
				<div class="col-md-6 highlight fix-height">
					<div class="r3_counter_box red">
						<p class="title left">Suite Execution Time</p>
						<p class="number right font-600" id="totTm"><script>getHMS('<xsl:value-of select="$executionTime" />','totTm')</script></p>
					</div>
				</div>
			</div>
		</div>
		<div class="container gap-below">
			<div class="col-md-12">
				<div class="r3_counter_box inner-gap">
					<h3>Details</h3>
					<div id="myTabContent" class="tab-content">
						<div class="filter-area gap-below">
							<p>Filter By :
								<form>
									<input type="radio" name="filter" value="group" checked="checked"><i class="fa fa-users"></i>Group</input>
									<input type="radio" name="filter" value="class"><i class="fa fa-book"></i>Test Class</input>
									<input type="radio" name="filter" value="verdict"><i class="fa fa-balance-scale"></i>Verdict</input>
									<input type="radio" name="filter" value="config"><i class="fa fa-wrench"></i>Failed Configuration Methods</input>
								</form>
							</p>
							<select id="selGrp" class="form-control" name="action">
								<xsl:for-each select="/testResults/suites/groups/group">
									<option><xsl:value-of select="text()" /></option>
								</xsl:for-each>
							</select>
							<select id="selCls" class="form-control" name="action" style="display:none;">
								<xsl:for-each select="/testResults/testResult">
									<xsl:variable name="cid" select="id"/>
									<option value="{$cid}"><xsl:value-of select="className" /></option>
								</xsl:for-each>
							</select>
							<select id="selVerd" class="form-control" name="action" style="display:none;">
								<option>-- Select Verdict --</option>
								<option value="PASS">Pass</option>
								<option value="FAIL">Fail</option>
								<option value="SKIP">Skip</option>
							</select>
						</div>
						<div class="alert alert-info">
							<strong>Showing <i id="show"></i> results</strong>
						</div>
						<div class="panel-group accordian-top-margin" id="Accordion" role="tablist" aria-multiselectable="true">
							<xsl:for-each select="/testResults/testResult">
								<xsl:variable name="index" select="position()"/>
								<xsl:variable name="getId" select="id" />
								<xsl:variable name="verdict" select="methodStatus" />
								<xsl:variable name="name" select="methodName" />
								<xsl:variable name="instance" select="instance" />
								<xsl:variable name="start" select="methodExecutionStartTime"/>
								<xsl:variable name="end" select="methodExecutionEndTime"/>
								<xsl:variable name="duration" select="methodExecutionTotalTime"/>
								<xsl:variable name="parameter" select="methodParameter" />
								<xsl:variable name="exception" select="methodErrorCause" />
								<div class="panel accordian-panel-heading" id="{$getId}" title="{$verdict}" style="display:none;">
									<div class="panel-heading" role="tab" id="heading{$index}">
										<h4 class="nw-page-title">
											<a class="pa_italic" role="button" data-toggle="collapse" data-parent="#Accordion" href="#collapse{$index}" aria-expanded="true" aria-controls="collapseOne">
												<span class="fa fa-chevron-down"></span><span class="fa fa-chevron-up"></span>
												<label style="word-wrap: break-word; cursor: pointer;">
													<xsl:value-of select="$name" /> (
													<xsl:for-each select="methodParameters/methodParameter">
														<xsl:value-of select="text()"/>
														<xsl:if test="position() != last()">
														, 
														</xsl:if>
													</xsl:for-each> )
												</label>
												<xsl:if test="$verdict = 'PASS'">
												<i class="pass fa fa-check"></i>
												</xsl:if>
												<xsl:if test="$verdict = 'FAIL'">
												<i class="pass fa fa-times"></i>
												</xsl:if>
												<xsl:if test="$verdict = 'SKIP'">
												<i class="pass fa fa-forward"></i>
												</xsl:if>
											</a>
										</h4>
									</div>
									<div id="collapse{$index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading{$index}">
										<div class="panel-body panel_text panel-body-new">
											<p><strong>Name: </strong> <xsl:value-of select="$name" /></p>
											<p><strong>Instance: </strong> <xsl:value-of select="$instance" /></p>
											<p><strong>Parameters: </strong> 
												<xsl:for-each select="methodParameters/methodParameter">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" />
												</xsl:for-each>
											</p>
											<p><strong>Context Group Name: </strong>
												<xsl:for-each select="contextGroups/contextGroup">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" /> 
												</xsl:for-each>
											</p>
											<p><strong>Method Group Name: </strong>
												<xsl:for-each select="methodGroups/methodGroup">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" /> 
												</xsl:for-each>
											</p>
											<p><strong>Start time: </strong> <xsl:value-of select="$start" /></p>
											<p><strong>End time: </strong> <xsl:value-of select="$end" /></p>
											<p><strong>Duration: </strong> <i id="dura{$index}"><script>getHMS('<xsl:value-of select="$duration" />','dura<xsl:value-of select="position()" />')</script></i></p>
											<xsl:if test="$verdict != 'PASS'">
												<p class="breaker"><strong>Reason: </strong> <xsl:value-of select="$exception" /></p>
											</xsl:if>
										</div>
									</div>
								</div>
							</xsl:for-each>
							<xsl:for-each select="/testResults/configurationMethod">
								<xsl:variable name="index" select="position()"/>
								<xsl:variable name="getAnnotation" select="annotation" />
								<xsl:variable name="verdict" select="methodStatus" />
								<xsl:variable name="name" select="methodName" />
								<xsl:variable name="instance" select="instance" />
								<xsl:variable name="start" select="methodExecutionStartTime"/>
								<xsl:variable name="end" select="methodExecutionEndTime"/>
								<xsl:variable name="duration" select="methodExecutionTotalTime"/>
								<xsl:variable name="parameter" select="methodParameter" />
								<xsl:variable name="exception" select="methodErrorCause" />
								<div class="panel accordian-panel-heading" id="config" title="{$verdict}" style="display:none;">
									<div class="panel-heading" role="tab" id="cheading{$index}">
										<h4 class="nw-page-title">
											<a class="pa_italic" role="button" data-toggle="collapse" data-parent="#Accordion" href="#ccollapse{$index}" aria-expanded="true" aria-controls="collapseOne">
												<span class="fa fa-chevron-down"></span><span class="fa fa-chevron-up"></span>
												<label style="word-wrap: break-word; cursor: pointer;">
													<xsl:value-of select="$name" /> (
													<xsl:for-each select="methodParameters/methodParameter">
														<xsl:value-of select="text()"/>
														<xsl:if test="position() != last()">
														, 
														</xsl:if>
													</xsl:for-each> )
												</label>
												<xsl:if test="$verdict = 'PASS'">
												<i class="pass fa fa-check"></i>
												</xsl:if>
												<xsl:if test="$verdict = 'FAIL'">
												<i class="pass fa fa-times"></i>
												</xsl:if>
												<xsl:if test="$verdict = 'SKIP'">
												<i class="pass fa fa-forward"></i>
												</xsl:if>
											</a>
										</h4>
									</div>
									<div id="ccollapse{$index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="cheading{$index}">
										<div class="panel-body panel_text panel-body-new">
											<p><strong>Name: </strong> <xsl:value-of select="$name" /></p>
											<p><strong>Annotation: </strong> <xsl:value-of select="$getAnnotation" /></p>
											<p><strong>Instance: </strong> <xsl:value-of select="$instance" /></p>
											<p><strong>Parameters: </strong> 
												<xsl:for-each select="methodParameters/methodParameter">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" />
												</xsl:for-each>
											</p>
											<p><strong>Context Group Name: </strong>
												<xsl:for-each select="contextGroups/contextGroup">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" /> 
												</xsl:for-each>
											</p>
											<p><strong>Method Group Name: </strong>
												<xsl:for-each select="methodGroups/methodGroup">
													<xsl:value-of select="position()" />. <xsl:value-of select="text()" /> 
												</xsl:for-each>
											</p>
											<p><strong>Start time: </strong> <xsl:value-of select="$start" /></p>
											<p><strong>End time: </strong> <xsl:value-of select="$end" /></p>
											<p><strong>Duration: </strong> <i id="cdura{$index}"><script>getHMS('<xsl:value-of select="$duration" />','cdura<xsl:value-of select="position()" />')</script></i></p>
											<xsl:if test="$verdict != 'PASS'">
												<p class="breaker"><strong>Error: </strong> <xsl:value-of select="$exception" /></p>
											</xsl:if>
										</div>
									</div>
								</div>
							</xsl:for-each>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>		
</xsl:template>
<xsl:template name="footer">
	<!-- Footer section start -->
	<footer>
		<p>Design by 'startingwithseleniumwebdriver' team</p>
	</footer>
	<!-- Footer section end -->
</xsl:template>
</xsl:stylesheet>