<%@page import="ch.qos.logback.core.pattern.parser.Parser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.dev.tools.api.personal.service.PerformanceValue" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="/WEB-INF/views/sub/login/session.jsp" %>
<%@ include file="/WEB-INF/views/common/function.jsp" %>
<%
	List<PerformanceValue> PerformanceValueList = (List<PerformanceValue>)request.getAttribute("performanceValueList");

	String confirmUrl = ""; //버튼URL	
	String btnText = "평가 확인"; //버튼Text
	String status = "미등록"; //평가상태
	
	String yyyyMM = getBeforeMonth(new SimpleDateFormat("yyyyMMdd").format(new Date()));
	int limitDay = 16;
	
	int useAuthLevel = 0;
	double valueScoreTot1 = 0, valueScoreTot2 = 0;
	
	boolean useConfirm = useConfirmChk(yyyyMM, PerformanceValueList.get(0).getMonth(), limitDay);
	
	
	
%>

<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 성과평가 산정기준: Sales Meeting, weekly Meeting, WIP 기반하여 점수 부여(만점 10점) </label></span>
<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 성과평과 + 가치평가 환산비율 : (성과평가 <%= (int)(PerformanceValueList.get(0).getPerformRatio()*100) %>%), (가치평가 <%= (int)(PerformanceValueList.get(0).getValueRatio()*100) %>%) </label></span>
<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 가치평가 기호표 : ○=> 1명 선택, ◎=> 2명선택 </label></span>
<table class="table">
	<thead>		
		<tr>		
			<td width="2%" rowspan="2"><b>No</b></td>
			<td width="6%" rowspan="2" valign="middle"><b>이름</b></td>
			<td width="9%" rowspan="2"><b>부서</b></td>							  
			<td width="8%" rowspan="2"><b>팀</b></td>
			<td width="6%"rowspan="2"><b>상태</b></td>
			<td rowspan="2"><b>성과평가</br>총점</b></td>			
			<td width="6%" colspan="8"><b>가치평가</b></td>
			<td rowspan="2"><b>가치평가</br>총점</b></td>
			<td width="7%" rowspan="2"><b>최종점수</b></td>	
			<td width="7%" rowspan="2"><b></b></td>
			
		</tr>
		<tr>
			<td width="6%"><b>판단력</b></td>
			<td width="6%"><b>소통</b></td>		
			<td width="6%"><b>임팩트</b></td>		
			<td width="6%"><b>호기심</b></td>		
			<td width="6%"><b>혁신</b></td>		
			<td width="6%"><b>열정</b></td>
			<td width="6%"><b>정직</b></td>
			<td width="7%"><b>이타적 행동</b></td>		
		</tr>
	</thead>
	<tbody>
		<%
		int i=0;
		for(PerformanceValue performanceValue : PerformanceValueList){ 
			useAuthLevel = (sessionAuthLevel-performanceValue.getAuthLevel());
			valueScoreTot1=0; valueScoreTot2=0;
			if(useAuthLevel==1){
				confirmUrl = "/performanceConfirm1";
				btnText = "평가하기";
			}else if(useAuthLevel==2 || sessionAuthLevel==3){
				confirmUrl = "/performanceConfirm2";
				btnText = "평가하기";
			}else{
				confirmUrl = "";
				btnText = "";
			}
		%>
		<tr>
			<input type="hidden" name="employeId" id="employeId_<%=i%>" value="<%=performanceValue.getEmployeId()%>"/>
			<td><%=(i+1)%></td>
			<td><%=performanceValue.getEmployeName()%></td>
			<td><%=performanceValue.getDivisionName()%></td>
			<td><%=performanceValue.getTeamName()%></td>
			<td>
				<%					
					if(performanceValue.getPerfomScore1()>0 && performanceValue.getPerfomScore2()>0){
						status = "<font color='blue'>평가완료</font>";	
						if(performanceValue.getDissent() != null
						&& performanceValue.getDissentComments1() != null
						&& performanceValue.getDissentComments2() != null){
							status = "<font color='blue'>이의제기</br>(평가완료)</font>";	
						}else if(performanceValue.getDissent() != null &&(
								performanceValue.getDissentComments1() == null
								|| performanceValue.getDissentComments2() == null)){
							status = "<font color='green'>이의제기</br>(재평가중)</font>";	
						}
					}else if(performanceValue.getPerfomScore1()>0 || performanceValue.getPerfomScore2()>0){
						status = "<font color='green'>평가중</font>";
					}else{
						status = "<font color='red'>미등록</font>";
					}		
				
					out.print(status);
				%>
			</td>
		<!-- 점수출력 (S)-->	
			
			<td width="7%">
				<!-- 성과평가 -->
				<%= (double)(performanceValue.getPerfomScore1()+performanceValue.getPerfomScore2()) %>
			</td>			
			
			<%	for(int j=0; j<performanceValue.getValueScore1().size(); j++){%>
			<td width="6%">
				<!-- 가치평가 -->
				<%	
					if(performanceValue.getValueScore1().get(j) && performanceValue.getValueScore2().get(j)){						
						out.println("◎");						
					}else if(	(performanceValue.getValueScore1().get(j) && !performanceValue.getValueScore2().get(j)) 
							 || (!performanceValue.getValueScore1().get(j) && performanceValue.getValueScore2().get(j))
							){
						out.println("○");
					}	
					
					if(performanceValue.getValueScore1().get(j)){ valueScoreTot1++;}
					if(performanceValue.getValueScore2().get(j)){ valueScoreTot2++;}
					
				%>
			</td>
			<% } %>																	
		
			<td width="7%">
				<!-- 가치평가 총합 -->
				<% 
					valueScoreTot1 = (valueScoreTot1>0)? valueScoreTot1+2 : valueScoreTot1;
					valueScoreTot2 = (valueScoreTot2>0)? valueScoreTot2+2 : valueScoreTot2;
					
					out.print((double)(valueScoreTot1+valueScoreTot2));	
				%>
			</td>
			
			<td width="7%">
				<!-- 성과평가 + 가치평가-->
				<%= (double)(performanceValue.getPerfomScore1()+performanceValue.getPerfomScore2()) * (performanceValue.getPerformRatio()) +  ((double)(valueScoreTot1+valueScoreTot2) * performanceValue.getValueRatio())
				%>
			</td>			
		<!-- 점수출력 (E)-->
		
			
			<td width="7%">				
				<%if((performanceValue.getPerfomScore1() > 0 && performanceValue.getPerfomScore2() > 0 && useAuthLevel>0) || (useAuthLevel==0 && performanceValue.getEmployeId().equals(sessionEmployeId))){ %>
					<%if(!performanceValue.getEmployeId().equals("990101")){ %>
						<button type="button" class="btn btn-success btn-sm btn-block" onClick="moveConfirm('/performanceData','<%=performanceValue.getEmployeId()%>');">평가확인</button>
					<%}else{ %>
						<%if(performanceValue.getPerfomScore1() > 0 && performanceValue.getPerfomScore2() > 0){ %>
							<button type="button" class="btn btn-success btn-sm btn-block" onClick="moveConfirm('/performanceData','<%=performanceValue.getEmployeId()%>');">평가확인</button>
						<%}else{ %>
							<button type="button" class="btn btn-danger btn-sm btn-block" onClick="moveConfirm('<%=confirmUrl%>','<%=performanceValue.getEmployeId()%>','2');"><%=btnText%></button>	
						<%} %>
					<%} %>
					
				<%}else if(confirmUrl != "" && useAuthLevel<3 && useAuthLevel>0 && useConfirm){%>
					<button type="button" class="btn btn-danger btn-sm btn-block" onClick="moveConfirm('<%=confirmUrl%>','<%=performanceValue.getEmployeId()%>','<%=useAuthLevel%>');"><%=btnText%></button>									
				<%}%>
			</td>
			
		</tr>			
		<% i++; } %>
	</tbody>					
</table>
<%!
	public boolean useConfirmChk(String yyyymm,  String month, int limitDay){
		boolean res = false;
		int dd = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		
		if(month.equals(yyyymm) &&  dd <= limitDay ){
			res = true;
		}
		
		return res;
	}
%>
