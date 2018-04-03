<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.dev.tools.api.personal.service.PerformanceValue" %>
<%@ page import="java.util.List" %>
<%@ include file="/WEB-INF/views/include/header.html" %>
<%@ include file="/WEB-INF/views/sub/login/session.jsp" %>
<%
	PerformanceValue performanceValue = (PerformanceValue)request.getAttribute("performanceValue");

	String display = "display:none";
	display = (Integer.parseInt(performanceValue.getMonth().substring(4))%3==1)? "" : "display:none";
%>
<%if(!performanceValue.getEmployeId().equals(sessionEmployeId) && sessionAuthLevel==0){%>
	<script>alert('잘못된 접근입니다.'); document.location.href='/KpcPersonalEvaluation/main';</script>
<%}%>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/views/include/navigator.html" %>
		<!-- /#page-wrapper -->
		<div id="page-wrapper">			
			<div class="row">	
				<div><h2 class="page-header">성과 / 가치평가</h2></div>			
				<div class="panel panel-red">
					<div class="panel-heading">평가하기</div>
					<div class="panel-body  centerText">						
					<form name="frm">
						<input type="hidden" id="employeId" name="employeName" value="<%=performanceValue.getEmployeId()%>" />
						<input type="hidden" id="month" name="month" value="<%=performanceValue.getMonth()%>" />
						<input type="hidden" id="confirmNumber" name="confirmNumber" value="2" />
						<table class="table" id="dataTables-example" width="100%">
						<thead>	
							<tr>
								<td class="col-lg-12" colspan="10">									
									<div class="form-group col-lg-6">										
										<div class="col-xs-1"><label class="form-inline">이름</label></div>						            	
						            	<div class="col-xs-2">
							            	<input class="form-control" type="text" value="<%=performanceValue.getEmployeName()%>" readonly/>
						            	</div>
						            	<div class="col-xs-1"><label class="form-inline">부서</label></div>						            	
						            	<div class="col-xs-3">
							            	<input class="form-control" type="text" value="<%=performanceValue.getDivisionName() %>" readonly/>
						            	</div>
						            	<div class="col-xs-1"><label class="form-inline">팀</label></div>						            	
						            	<div class="col-xs-3">
							            	<input class="form-control" type="text" value="<%=performanceValue.getTeamName() %>" readonly/>
						            	</div>																											
									</div>																															   							   
								</td>
							</tr>
							
							<tr>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
							</tr>	
						</thead>
						<tbody>	
						<!-- 성과평가(S)-->				
							<tr align="center">
								<td rowspan="2" colspan="2" class="body_title"><b>성과평가</b></td>
								<td colspan="2"><b>성과평가 2차</b></td>
								<td colspan="6"><b>성과평가 Comment</b></td>
							</tr>
							<tr align="center">								
								<td colspan="2">
									<input type="text" name="performScore" id="performScore2" class="form-control" value="<%=performanceValue.getPerfomScore2()%>" maxlength="2"  onKeyDown="numberChk(event)" onKeyUp="checkLimit(this)" onFocus="onFocusBlank(this);" onBlur="outFocusValue(this);"/>
								</td>
								<td colspan="6">
									<textarea class="form-control" name="performComments" id="performComments2" style="height: 50px; resize:none;"><%= (performanceValue.getPerformComments2() != null)? performanceValue.getPerformComments2() : ""  %></textarea>									
								</td>
							</tr>
						<!-- 성과평가(E)-->
						
						<!-- 가치평가(S) -->	
							<tr align="center"  style="<%=display%>">
								<td colspan="2" rowspan="20"class="body_title" ><b>가치평가</b></td>
								<td colspan="8"><b>가치평가 점수환산 (점)</b></td>
							</tr>
							<tr align="center" style="background-color:#FCD0D0; <%=display%>">									
								<td colspan="8">
									<div style="width:11%; float:left;">0개</div>
									<div style="width:11%; float:left;">1개</div>
									<div style="width:11%; float:left;">2개</div>
									<div style="width:11%; float:left;">3개</div>
									<div style="width:11%; float:left;">4개</div>
									<div style="width:11%; float:left;">5개</div>
									<div style="width:11%; float:left;">6개</div>
									<div style="width:11%; float:left;">7개</div>
									<div style="width:11%; float:left;">8개</div>
								</td>								
							</tr>
							<tr align="center" style="<%=display%>">									
								<td colspan="8">
									<div style="width:11%; float:left;">0</div>
									<div style="width:11%; float:left;">3</div>
									<div style="width:11%; float:left;">4</div>
									<div style="width:11%; float:left;">5</div>
									<div style="width:11%; float:left;">6</div>
									<div style="width:11%; float:left;">7</div>
									<div style="width:11%; float:left;">8</div>
									<div style="width:11%; float:left;">9</div>
									<div style="width:11%; float:left;">10</div>
								</td>								
							</tr>
								 
							<tr align="center" style="<%=display%>">
								
								<td colspan="4">
									<div class="centerDiv centerText"><b>평가항목</b></div>
								</td>								
								<td colspan="4">
									<div class="centerDiv centerText"><b>가치평가 Comment</b></div>
								</td>
							</tr>
						<!-- row1(S)-->						 
							<tr align="center" style="<%=display%>">								
								<td colspan="2">
									<div class="centerDiv centerText"><b>판단력</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(0))? "checked" : "") %>/></div>
								</td>
								<td colspan="4" rowspan="16" >
									<textarea class="form-control" name="valueComments" id="valueComments2" style="height: 1000px; resize:none;"><%= (performanceValue.getValueComments2() != null)? performanceValue.getValueComments2() : ""  %></textarea>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.겉으로 드러난 현상 뒤에 있는, 근본 원인을 파악해야 한다.</br>
										2.전략적으로 생각해서,자신이 할 수 있는 일과 하지 않아야 할 일을 분간하려고 노력해야 한다.</br>
									    3.지금 잘해야 하는 일과 나중에 손봐도 되는 일을 영리하게 구분해야 한다.
									</p>
								</td>
							</tr>
						<!-- row1(E) -->
							
						<!-- row2(S)-->	
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>소통</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(1))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.경거망동 하기 전에, 잘 경청함으로써 상대방을 잘 이해한다.</br>
										2.간명하게 글을 쓰고, 말한다.</br>
										3.스트레스가 많은 상황에서도 침착함을 유지한다.</br>
									</p>
								</td>
							</tr>
						<!-- row2(E)-->	
							
						<!-- row3(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>임팩트</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(2))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.엄청나게 많은, 중요한 일을 처리한다.</br>
									    2.동료들이 신뢰할 수 있을 만큼 높은 퍼포먼스를 꾸준히 유지한다.</br>
									    3.과정과 절차보다, 뛰어난 결과에 집중한다.</br>
									    4.행동주의자가 되어야 하며, 분석에만 몰두하느라 아무 일도 못하는 상황을  피한다.</br>
									</p>
								</td>
							</tr>
						<!-- row3(E)-->	
						
						<!-- row4(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>호기심</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(3))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.빨리, 열정적으로 배운다.</br>
										2.우리의 전략과 시장, 고객과 업체를 이해하려고 노력한다.</br>
										3.자신의 전문 분야를 넘어서도 효과적으로 기여한다.</br>
									</p>
								</td>
							</tr>
						<!-- row4(E)-->	
						
						<!-- row5(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>혁신</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(4))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.어려운 문제를 해결할 실용적인 방안을 찾기 위해 이슈들을 재구성한다.</br>
										2.타당하고 더 나은 가정을 세우기 위해 도전하며,더 나은 방법을 제안한다.</br>
										3.복잡성을 최소화하고 간소화하는데 시간을 들여, 회사의 민첩성에 도움을 준다</br>
									</p>
								</td>
							</tr>
						<!-- row5(E)-->	
						
						<!-- row6(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>열정</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(5))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.탁월함을 추구함으로써, 다른 사람들에게 영감을 준다.</br>
										2.한국선불카드의 성공에 지대한 관심을 갖는다.</br>
										3.성취를 축하한다.</br>
										4.끈기를 갖는다.</br>
									</p>
								</td>
							</tr>
						<!-- row6(E)-->	
						
						<!-- row7(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>정직</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(6))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.동료에 대해 이야기할 때, 동료 앞에서 이야기할 수 있는 것만 이야기한다.</br>
										2.실수를 빨리 인정한다.</br>
									</p>
								</td>
							</tr>
						<!-- row7(E)-->	
						
						<!-- row8(S)-->		
							<tr align="center" style="<%=display%>">
								<td colspan="2">
									<div class="centerDiv centerText"><b>이타적 행동</b></div>
								</td>
								<td colspan="2">									
									<div class=""><input type="checkbox" class="form-control" name="values2[]" <%=((performanceValue.getValueScore2().get(7))? "checked" : "") %>/></div>
								</td>
							</tr>
							<tr align="left" style="<%=display%>">								
								<td colspan="4">
									<p>
										1.여러분이나 여러분이 속한 부서/팀이 아닌, 한국선불카드에 최선인 것을 찾는다.</br>
										2.가장 좋은 아이디어를 찾기 위해 이기심을 버린다.</br>
										3.동료를 돕는 데 시간을 낸다.</br>
										4.정보를 활발하게 공유한다.</br>
									</p>
								</td>
							</tr>
						<!-- row8(E)-->	
						</tbody>
					</table>
					</div>
					<div class="panel-body" align="right"><div class="col-xs-2 pull-right"><button type="button" class="btn btn-danger btn-sm btn-block" id="searchBtn" name="searchBtn" onClick="SavePerformData(2);">상신</button></div>
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
</html>