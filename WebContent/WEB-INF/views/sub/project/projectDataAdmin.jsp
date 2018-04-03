<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.dev.tools.api.personal.database.dao.ResultProjectScore" %>
<%@ page import="kr.co.dev.tools.api.personal.database.dao.ProjectScore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/WEB-INF/views/sub/login/session.jsp" %>
<%
	//프로젝트 점수(Request)
	List<ResultProjectScore> projectScoreList = (List<ResultProjectScore>)request.getAttribute("projectScoreList");
	List<ProjectScore> scoreHistoryList  = (List<ProjectScore>)request.getAttribute("scoreHistoryList");
	
	//오늘날짜(day)
	int todaydd = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
	int limitDay = 24;
	//합계점수, 항목별점수, 상태
	int tot, score, myScore, status, useAuthLevel = 0;
	
	//상태 text
	String statusText="";
	
	boolean useAuth = true, useDissent = true;
	
	
%>
<span class="col-lg-10 pull-left" style="text-align:left;"><label> * 관리입력 : PJ업무를 제외한 회사의 공통업무 또는 해피머니와 관련된 업무</span>
<table class="table" id="dataTables-example">
	<thead>						
		<tr>
			<td width="3%" rowspan="2"><b>No</b></td>							
			<td width="3%" rowspan="2"><input type="checkbox" name="allSeq" onClick="allCheck('idx[]');"/></td>
			<td width="6%" rowspan="2"><b>이름</b></td>
			<td width="*" rowspan="2"><b>부서</b></td>							  
			<td width="8%" rowspan="2"><b>팀</b></td>
			<td rowspan="2"><b>상태</b></td>			
			<td width="7%" colspan="6"><b>직접비</b></td>
			<td width="7%" colspan="2"><b>공통비</b></td>
			<td width="7%"><b>합계</b></td>
		</tr>
		<tr>
			<td width="7%"><b>캐시박스</b></td>
			<td width="7%"><b>POSA</b></td>		
			<td width="7%"><b>POP</b></td>		
			<td width="7%"><b>Palago</b></td>		
			<td width="7%"><b>T-Grid</b></td>
			<td width="7%"><b>Topping</b></td>		
			<td width="7%"><b>관리(입력)</b></td>		
			<td width="7%"><b>관리(기본)</b></td>
		</tr>
		<% for(int i=0; i<projectScoreList.size(); i++){
				useAuthLevel = (sessionAuthLevel - projectScoreList.get(i).getEmployeLevel());
				
				
		
				status = projectScoreList.get(i).getStatus();
		%>
		<tr <%= ((projectScoreList.get(i).getStatus()==4 || projectScoreList.get(i).getStatus()==5) && useDissent)? "style=\"background-color:#FF9090\"" : "" %>>
			<td><%=(i+1)%></td>
			<td>				
				<%if(useAuth && status<3){%><input type="checkbox" name="idx[]" value="<%=i%>"/><%}%>
				<input type="hidden" name="seq" id="seq_<%=i%>" value="<%=projectScoreList.get(i).getSeq()%>" />
				<input type="hidden" name="employeId" id="employeId_<%=i%>" value="<%=projectScoreList.get(i).getEmployeId()%>" />
				<input type="hidden" name="confirmNumber" id="confirmNumber_<%=i%>" value="<%= useAuthLevel %>" />
			</td>
			<td><%=projectScoreList.get(i).getEmployeName()%></td>
			<td><%=projectScoreList.get(i).getDivisionName()%></td>
			<td><%=projectScoreList.get(i).getTeamName()%></td>
			<td>
				<%
					switch(projectScoreList.get(i).getStatus()){
						case 0: statusText = "<font color='red'>미등록</font>"; break;
						case 1: statusText = "<font color='green'>상신</font>"; break;
						case 2: statusText = "<font color='green'>1차승인</font>"; break;
						case 3: statusText = "<font color='blue'>설정 완료</font>"; break;
						case 4: statusText = "<font color='red'>이의 제기</font>"; break;
						case 5: statusText = "<font color='green'>이의 제기(1차)</font>"; break;
						case 6: statusText = "<font color='blue'>설정 완료</font>"; break;
					}
					out.print(statusText);					
				%>				
				<% if( projectScoreList.get(i).getStatus()>=4){ %>
					</br><button type="button" class="btn btn-danger btn-xs" id="searchBtn" name="searchBtn" onClick="openDissentPop('<%=projectScoreList.get(i).getEmployeId()%>','<%=projectScoreList.get(i).getMonth()%>');">이의제기<%=(projectScoreList.get(i).getStatus()>=4)? "확인":"" %> </button>
				<%}%>						
			</td>
			<!-- 점수출력 (S)-->		
			<%	
				tot=0;				
				for(int k=1; k<=8; k++)
				{
					score = 0;
					myScore = 0;
					//평가자 등록 점수
					switch(k){
						case 1 : 
							score = projectScoreList.get(i).getScore1();
							break;
						case 2 : 
							score = projectScoreList.get(i).getScore2(); 
							break;
						case 3 : 
							score = projectScoreList.get(i).getScore3();
							break;
						case 4 : 
							score = projectScoreList.get(i).getScore4();
							break;
						case 5 : 
							score = projectScoreList.get(i).getScore5();
							break;
						case 6 : 
							score = projectScoreList.get(i).getScore6();							
							break;
						case 7 : 
							score = projectScoreList.get(i).getScore7();							
							break;						
						case 8 : 
							score = projectScoreList.get(i).getScore8();							
							break;
					}					
					tot += score;	
					
					//피평가자 등록점수
					for(ProjectScore scoreHistory : scoreHistoryList)
					{
						if(projectScoreList.get(i).getEmployeId().equals(scoreHistory.getEmployeId())){
							myScore = 0;
							switch(k){
								case 1 : 
									myScore = scoreHistory.getScore1();
									break;
								case 2 : 
									myScore = scoreHistory.getScore2(); 
									break;
								case 3 : 
									myScore = scoreHistory.getScore3();
									break;
								case 4 : 
									myScore = scoreHistory.getScore4();
									break;
								case 5 : 
									myScore = scoreHistory.getScore5();
									break;
								case 6 : 
									myScore = scoreHistory.getScore6();							
									break;
								case 7 : 
									myScore = scoreHistory.getScore7();							
									break;
								case 8 : 
									myScore = scoreHistory.getScore8();							
									break;
							}	
						}
					}
			%>	
				<td width="7%">
					
					<%if(k<8){%>
						<input type="text" size="2" name="projectScoreList[<%=i%>].score<%=k%>" id="score<%=i%>_<%=k%>" maxlength="1" value="<%=score%>" onKeyDown="numberChk(event)" onKeyUp="checkLimit(this)" onFocus="onFocusBlank(this);" onBlur="outFocusValue(this);">
					<%}else{%>
						<%=score%>							
						<input type="hidden" size="2" name="projectScoreList[<%=i%>].score<%=k%>" id="score<%=i%>_<%=k%>" maxlength="1" value="<%=score%>">														
					<%}%>		
					
				</td>												
			<% } %>
			<!-- 점수출력 (S)-->
			<td width="7%"><b><input type="text" size="2" id="totalscore<%=i%>" value="<%=tot%>" readonly /></b></td>			
		</tr>	
	<% } %>						
	</thead>
	<tbody>														  
		<!-- tr class="even gradeA">
			<td colspan="12" align="center"><strong>검색 결과가 존재하지 않습니다.</strong></td>								
		</tr-->
	</tbody>
</table>
