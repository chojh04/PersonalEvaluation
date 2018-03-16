<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="navbar-default sidebar" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">						
			<li>
				<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>평가<span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">			
				<%if(!session.getAttribute("employeId").equals("kpc_admin")){%>					
					<li>
						<a href="/KpcPersonalEvaluation/projectList">프로젝트</a>
					</li>
					
					<li>
						<a href="/KpcPersonalEvaluation/performanceList">성과/가치</a>
					</li>				
				<%}else if(session.getAttribute("employeId").equals("kpc_admin")){%>					
					<li>
						<a href="/KpcPersonalEvaluation/projectListAdmin">프로젝트</a>
					</li>
					
					<li>
						<a href="/KpcPersonalEvaluation/performanceListAdmin">성과/가치</a>
					</li>
				<%}%>					
				</ul>
				<!-- /.nav-second-level -->
			</li>							
			<!-- 
			<li>
				<a href="/KpcPersonalEvaluation/statstics"><i class="fa fa-edit fa-fw"></i>통계</a>
			</li>
			 -->			
			<li>
				<a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>설정<span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li>
						<a href="/KpcPersonalEvaluation/password">비밀번호 변경</a>
					</li>					
				</ul>
				<!-- /.nav-second-level -->
			</li>				   
		</ul>
	</div>
	<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->