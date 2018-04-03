<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/WEB-INF/views/include/header.html" %>
<%@ include file="/WEB-INF/views/sub/login/session.jsp" %>
<%@ include file="/WEB-INF/views/common/function.jsp" %>
<%
	String yyyyMM = (String)request.getAttribute("yyyyMM");	
	yyyyMM = (yyyyMM.equals(null) || yyyyMM.equals(""))? getBeforeMonth(new SimpleDateFormat("yyyyMMdd").format(new Date())) : yyyyMM;	
%>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/views/include/navigator.html" %>
		<!-- /#page-wrapper -->
		<div id="page-wrapper">			
			<div class="row">	
				<div><h2 class="page-header">성과 / 가치평가</h2></div>			
				<div class="panel panel-red">
					<div class="panel-heading">성과 / 가치평가</div>
					<div class="panel-body tabbable tabs-below" id="searchDiv">																
						<table class="table" id="formTable">
							<tr>
								<td class="col-lg-10">									
									<div class="form-group col-lg-3">
										<div class="col-xs-10">
											<label class="form-inline">조회일자 - </label>
										</div>
										
										<div class="col-xs-10">
											<input class="form-control date-picker" type="text" id="searchSDate" name="searchSDate" placeholder="조회시작일" value="<%=yyyyMM%>" readonly/>
										</div>
										
										<div class="col-xs-1">
											<button type="button" class="btn btn-danger btn-sm" id="searchBtn" name="searchBtn" onClick="searchData('performanceList');">Search</button>
										</div>																			
									</div>																											   							   
								</td>
							</tr>																															
						</table>
					</div>					
					<div class="panel-body  centerText" id="data-panel">						
						
					</div>
	  	 		</div>			
			</div>
			<!-- /.row -->			
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->  
</body>
<script>
	setDatePicker();
	
	function goUrl(url){
		document.location.href="/KpcPersonalEvaluation/"+url;
	}
	
	$(document).ready(function(){
		searchData('performanceListAdmin');
	});
</script>

</html>
