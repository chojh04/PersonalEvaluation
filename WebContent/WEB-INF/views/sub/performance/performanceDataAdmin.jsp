<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.co.dev.tools.api.personal.database.dao.ResultPerformanceScore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/WEB-INF/views/include/header.html" %>
<%@ include file="/WEB-INF/views/sub/login/session.jsp" %>
<%
	ResultPerformanceScore performanceScore = (ResultPerformanceScore)request.getAttribute("performanceScore");
	List<Boolean> valueScore1 = (List<Boolean>)request.getAttribute("valueScore1");
	List<Boolean> valueScore2 = (List<Boolean>)request.getAttribute("valueScore2");
	
	double performScoreHistory1 = (Double)(request.getAttribute("performScoreHistory1"));
	double performScoreHistory2 = (Double)(request.getAttribute("performScoreHistory2"));
	double valueScoreHistory1 = (Double)(request.getAttribute("valueScoreHistory1"));
	double valueScoreHistory2 = (Double)(request.getAttribute("valueScoreHistory2"));
	
	double valueRatio = (Double)(request.getAttribute("valueRatio"));
	double performRatio = (Double)(request.getAttribute("performRatio"));
	
	String statusText;
	String readonly = "readonly";
	
	//오늘날짜(day)
	int todaydd = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
	double valueScoreTot1=0, valueScoreTot2=0;
	boolean useAuth0=false, useAuth1=false, useAuth2=false;
	
	int useAuthLevel = (sessionAuthLevel - performanceScore.getAuthLevel());

	
	

	for(int j=0; j<valueScore1.size(); j++){													
		if(valueScore1.get(j)){ valueScoreTot1++;}
		if(valueScore2.get(j)){ valueScoreTot2++;}
	}
	valueScoreTot1 = (valueScoreTot1>0)? valueScoreTot1+2 : valueScoreTot1;
	valueScoreTot2 = (valueScoreTot2>0)? valueScoreTot2+2 : valueScoreTot2;

	
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
					<div class="panel-body  centerText">	
						<form id="frm" name="frm">	
						<table class="table" id="dataTables-example">
							<tbody>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-14">
										<div class="col-xs-1"><label class="form-inline">이름</label></div>						            	
						            	<div class="col-xs-2">
							            	<input type="text" value="<%=performanceScore.getEmployeName()%>" readonly/>
							            	<input type="hidden" id="employeId" name="employeId" value="<%=performanceScore.getEmployeId()%>"/>
											<input type="hidden" id="month" name="month" value="<%=request.getAttribute("month")%>"/>
						            	</div>
						            	
						            	<div class="col-xs-1"><label class="form-inline">부서</label></div>						            	
						            	<div class="col-xs-2">
							            	<input type="text" value="<%=performanceScore.getDivisionName()%>" readonly/>
						            	</div>
						            	
						            	<div class="col-xs-1"><label class="form-inline">팀</label></div>						            	
						            	<div class="col-xs-2">
							            	<input type="text" value="<%=performanceScore.getTeamName()%>" readonly/>
						            	</div>
						            	
						            	<div class="col-xs-1"><label class="form-inline">상태</label></div>						            	
						            	<div class="col-xs-2">
						            		<%					
												if(performanceScore.getScore1()>0 && performanceScore.getScore2()>0){
													statusText = "평가완료";
													if(performanceScore.getDissent() != null
													&& performanceScore.getDissentComments1() != null
													&& performanceScore.getDissentComments2() != null){
														statusText = "이의제기(재평가완료)";	
													}else if(performanceScore.getDissent() != null &&(
															 performanceScore.getDissentComments1() == null
															|| performanceScore.getDissentComments2() == null)){
														statusText = "이의제기(재평가중)";	
													}
												}else if(performanceScore.getScore1()>0 || performanceScore.getScore2()>0){
													statusText = "평가중";
												}else{
													statusText = "미등록";
												}	
													
																			
											%>
							            	<input type="text" value="<%=statusText%>" readonly/>
						            	</div>																									
									</div>																															   							   
								</td>
							</tr>											
				<!-- 성과평가 (S) -->
							<tr>
								<td class="col-lg-10" >									
									<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 성과평가 산정기준: Sales Meeting, weekly Meeting, WIP 기반하여 점수 부여(만점 10점) </label></span>
									<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 성과평과 + 가치평가 환산비율 : (성과평가 <%= (int)(performRatio*100) %>%), (가치평가 <%= (int)(valueRatio*100) %>%) </label></span>
									<div class="form-group col-lg-4"  style="background-color:#d9534f;color:#FFFFFF;">
										<div class="col-xs-6 pull-left"><label class="form-inline">성과평가</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<div class="col-xs-2 ">
											<label class="form-inline">총점</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text"  class="form-control" value="<%=(double)(performanceScore.getScore1()+performanceScore.getScore2())%>" readonly/>
										</div>
										<div class="col-xs-2 ">
											<label class="form-inline">최종점수</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text" class="form-control" value="<%=((double)(performanceScore.getScore1()+performanceScore.getScore2())*performRatio) + ((valueScoreTot1 + valueScoreTot2) * valueRatio) %>" readonly/>
										</div>
										
									</div>
								</td>							
							</tr>
							<tr>
								<td class="col-lg-10" >									
									<div class="form-group col-lg-4  pull-left">
										<div class="col-xs-6"><label class="form-inline">성과평가 1차</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<div class="col-xs-2 ">
											<label class="">점수</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text" id="performScore1" name="performScore[]" maxlength="2" value="<%=performanceScore.getScore1()%>" onFocus="onFocusBlank(this);" onBlur="outFocusValue(this);"/>
										</div>
										<div class="col-xs-2 ">
											<label class="form-inline">이전 점수</label>
										</div>
										<div class="col-xs-2">
											<span><input type="text"  class="form-control" value="<%=performScoreHistory1%>" readonly/></span>
											<span><%=getPointText(performanceScore.getScore1(),performScoreHistory1)%></span>
										</div>
									</div>
								</td>							
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">성과평가 1차 Comment</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<textarea class="form-control" rows="3" id="performComments1" name="performComments[]" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false" ><%= (performanceScore.getComments1()!=null)? performanceScore.getComments1() : "" %></textarea>					
									</div>
								</td>					
							</tr>
							<tr>
								<td class="col-lg-10" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">성과평가 2차</label></div>						            														
									</div>									
									<div class="form-group col-lg-8">
										<div class="col-xs-2 ">
											<label class="">점수</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text" id="performScore2" name="performScore[]" maxlength="2" value="<%=performanceScore.getScore2()%>" onFocus="onFocusBlank(this);" onBlur="outFocusValue(this);"/>
										</div>
										<div class="col-xs-2 ">
											<label class="form-inline">이전 점수</label>
										</div>
										<div class="col-xs-2">
											<span><input type="text" class="form-control" value="<%=performScoreHistory2%>" readonly/></span>
											<span><%=getPointText(performanceScore.getScore2(),performScoreHistory2)%></span>
										</div>
									</div>							
								</td>							
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">성과평가 2차 Comment</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<textarea class="form-control" rows="3" id="performComments2" name="performComments[]" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false" ><%= (performanceScore.getComments2()!=null)? performanceScore.getComments2() : "" %></textarea>					
									</div>
								</td>					
							</tr>
				<!-- 성과평가 (E) -->
					
				<!-- 가치평가 (S) -->
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4"  style="background-color:#d9534f;color:#FFFFFF;">
										<div class="col-xs-6 pull-left"><label class="form-inline">성과평가</label></div>						            														
									</div>
									<div class="form-group col-lg-8">										
										<div class="col-xs-2 ">
											<label class="form-inline">총점</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text"  class="form-control" value="<%=(double)(valueScoreTot1 + valueScoreTot2)%>" readonly/>
										</div>
									</div>
									
								</td>							
							</tr>
							<tr>
								<td class="col-lg-10" >		
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">가치평가 1차</label></div>						            														
									</div>
									<div class="form-group col-lg-8">												
										<div class="col-xs-2 ">
											<label class="">점수</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text" class="form-control" id="valueScore1" name="valueScore1" value="<%=valueScoreTot1%>" readonly/>
										</div>
										<div class="col-xs-2 ">
											<label class="form-inline">이전 점수</label>
										</div>
										<div class="col-xs-2">
											<span><input type="text" class="form-control" value="<%=valueScoreHistory1%>" readonly/></span>
											<span><%=getPointText(valueScoreTot1,valueScoreHistory1)%></span>
										</div>								
									</div>
								</td>					
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<div class="col-xs-1 ">
											<label class="">판단력</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(0))? "checked" : "") %> />  
										</div>
										<div class="col-xs-1 ">
											<label class="">소통</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(1))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">임팩트</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(2))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">호기심</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(3))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">혁신</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(4))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">열정</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(5))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">정직</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(6))? "checked" : "") %> />
										</div>
										<div class="col-xs-1 ">
											<label class="">이타심</label>
											<input type="checkbox" class="form-control" name="valueScores1[]" <%=((valueScore1.get(7))? "checked" : "") %> />
										</div>									
									</div>
								</td>					
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">가치평가 1차 Comment</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<textarea class="form-control" rows="3" id="valueComments1" name="valueComments[]" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false" ><%= (request.getAttribute("comments1")!=null)? request.getAttribute("comments1") : "" %></textarea>					
									</div>
								</td>					
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">가치평가 2차</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<div class="col-xs-2 ">
											<label class="">점수</label>
										</div>
										<div class="col-xs-2 ">
											<input type="text" class="form-control" id="valueScore1" name="valueScore1" value="<%=valueScoreTot2%>" readonly/>
										</div>
										<div class="col-xs-2 ">
											<label class="form-inline">이전 점수</label>
										</div>
										<div class="col-xs-2">
											<span><input type="text" class="form-control" value="<%=valueScoreHistory2%>" readonly/></span>
											<span><%=getPointText(valueScoreTot2,valueScoreHistory2)%></span>
										</div>												
									</div>
								</td>							
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4 danger">
										<div class="col-xs-6"></div>						            														
									</div>
									<div class="form-group col-lg-8">										
										<div class="col-xs-1 ">
											<label class="">판단력</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(0))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">소통</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(1))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">임팩트</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(2))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">호기심</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(3))? "checked" : "") %>/>
										</div> 
										<div class="col-xs-1 ">
											<label class="">혁신</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(4))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">열정</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(5))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">정직</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(6))? "checked" : "") %>/>
										</div>
										<div class="col-xs-1 ">
											<label class="">이타심</label>
											<input type="checkbox" class="form-control" name="valueScores2[]" <%=((valueScore2.get(7))? "checked" : "") %>/>
										</div>											
									</div>
								</td>							
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4">
										<div class="col-xs-6"><label class="form-inline">가치평가 2차 Comment</label></div>						            														
									</div>
									<div class="form-group col-lg-8">
										<textarea class="form-control" rows="3" id="valueComments2" name="valueComments[]" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false"><%= (request.getAttribute("comments2")!=null)? request.getAttribute("comments2") : "" %></textarea>					
									</div>
								</td>					
							</tr>
							<tr>
								<td class="col-lg-12" >									
									<div class="form-group col-lg-4" style="background-color:#d9534f;color:#FFFFFF;">
										<div class="col-xs-6 pull-left"><label class="form-inline">이의사항</label></div>						            														
									</div>
									<div class="form-group col-lg-12">
										<div class="col-xs-2 ">
											<label class="">이의사항</label>
										</div>
										<div class="col-xs-10 ">											
											<textarea class="form-control" id="dissent" name="dissent" rows="3" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false"><%= (performanceScore.getDissent()!=null)? performanceScore.getDissent() : "" %></textarea>
										</div>										
									</div>
									<div class="form-group col-lg-12">
										<div class="col-xs-2 ">
											<label class="">Comment1</label>
										</div>
										<div class="col-xs-10 ">											
											<textarea class="form-control" id="dissentComments1" name="dissentComments[]" rows="3" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false" ><%= (performanceScore.getDissentComments1()!=null)? performanceScore.getDissentComments1() : "" %></textarea>
										</div>										
									</div>
									<div class="form-group col-lg-12">
										<div class="col-xs-2 ">
											<label class="">Comment2</label>
										</div>
										<div class="col-xs-10 ">
											
											<textarea class="form-control" id="dissentComments2" name="dissentComments[]" rows="3" style="margin-top: 0px; margin-bottom: 0px; height: 127px;" resizeable="false" ><%= (performanceScore.getDissentComments2()!=null)? performanceScore.getDissentComments2() : "" %></textarea>
										</div>										
									</div>  
								</td>
							</tr>
				<!-- 가치평가 (E) -->
							</tbody>
						</table> 	
					</form>					
					</div>		
						
						<div class="panel-body" align="right"><div class="col-xs-2 pull-right"><button type="button" class="btn btn-danger btn-sm btn-block" id="searchBtn" name="searchBtn" onClick="savePerformAdmin();">등록</button></div>
													   
					   	<!-- <div class="col-xs-2 pull-right"><button type="button" class="btn btn-danger btn-outline btn-sm btn-block" id="searchBtn" name="searchBtn" onClick="">저장</button></div> -->					   	
					   	<div class="col-xs-2 pull-right"><button type="button" class="btn btn-danger btn-outline btn-sm btn-block" id="searchBtn" name="searchBtn" onClick="javascript:history.go(-1);">목록</button></div>
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
function savePerformAdmin(){	
	var data = $("#frm").serialize();
	var values1 = [];
	var values2 = [];
	
	for(var i=0; i<$("[name='performScore[]']").length; i++){		
		if($("[name='performScore[]']").eq(i).val()>10){
			alert('평가점수는 10점을 초과 할 수 없습니다.');
			$("[name='performScore[]']").eq(i).focus();
			return;
		}
	}

	for(var i=0; i<$("[name='valueScores1[]']").length; i++){
		values1.push($("[name='valueScores1[]']").eq(i).is(":Checked"));
	}
	for(var i=0; i<$("[name='valueScores2[]']").length; i++){
		values2.push($("[name='valueScores2[]']").eq(i).is(":Checked"));
	}
	$.ajax({
		url : '/KpcPersonalEvaluation/savePerformScoreAdmin',
		type : 'post',
		dataType : 'text',
		data : data+"&values1[]="+values1+"&values2[]="+values2,
		success : function(res){
			if(res=="S"){
				alert('등록이 완료되었습니다.');
				document.location.href='/KpcPersonalEvaluation/performanceListAdmin?yyyyMM='+$("#month").val();
			}else{
				alert('등록중 에러가 발생하였습니다. \n에러가 계속될경우 기술개발팀에 문의해주세요.');
			}	
		},error : function(e){
			alert('등록실패');
			console.log(e);
		}
	});
}
</script>
<%!
	public String getPointText(double score1,  double score2){
		String retunText;
		if(score1 > score2){
			retunText = "<font color='red'>▲</font>";
		}else if(score1 < score2){
			retunText = "<font color='blue'>▼</font>";
		}else{
			retunText = "<font color='green'>■</font>";
		}
		
		return retunText;
	}

%>
</html>
