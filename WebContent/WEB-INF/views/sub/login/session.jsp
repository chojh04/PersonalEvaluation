<%
	String sessionEmployeId = (String)session.getAttribute("employeId");
	int sessionAuthLevel = (Integer)session.getAttribute("authLevel");
	int sessionTeamCode = (Integer)session.getAttribute("teamCode");
%>
<input type="hidden" id="authLevel" value="<%=sessionAuthLevel%>"/>