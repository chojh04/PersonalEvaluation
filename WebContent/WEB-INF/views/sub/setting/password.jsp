<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.dev.tools.api.personal.database.dao.Employe"%>
<%@ include file="/WEB-INF/views/include/header.html" %>
<%
	Employe employeInfo  = (Employe)(request.getAttribute("employeInfo"));
%>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/views/include/navigator.html" %>
		<div class="col-md-4 col-md-offset-4">			
			<div class="panel panel-red">
			    <div class="panel-heading">
					<h3 class="panel-title">비밀번호 변경</h3>
			    </div>
			    <input type="hidden" name="nowPwdDB" id="nowPwdDB" value="<%=employeInfo.getPassword()%>"/>
			    <div class="panel-body">
					<form class="form-signin" method="POST">
						<fieldset>
						<div><label>현재비밀번호</label></div>
						<div class="form-group">
							<input type="password" name="nowPwd"  id="nowPwd" class="form-control" placeholder="현재비밀번호" required="required" autofocus="autofocus" />
						</div>
						<div><label>새 비밀번호</label></div>
						<div class="form-group">
							<input type="password" name="pwd1" id="pwd1" class="form-control" placeholder="새 비밀번호" value="" required="required" />
						</div>
						<div><label>새 비밀번호 확인</label></div>
						<div class="form-group">
							<input type="password" name="pwd2" id="pwd2" class="form-control" placeholder="새 비밀번호 확인" value="" required="required" />
						</div>
						<button class="btn btn-lg btn-danger btn-block" type="button" onClick="checkPwd();">변경</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- /#wrapper -->  
</body>
<script>
	function checkPwd(){		
		if($("#nowPwd").val() != $("#nowPwdDB").val()){ alert("현재비밀번호를 확인해주세요."); return false;}
		
		if($("#pwd1").val().length<6){
			alert("비밀번호를 6자리 이상으로 설정해주세요.");
			return false;
		}else if($("#pwd1").val() != $("#pwd2").val()){
			alert("새로 입력한 비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		$.ajax({
			url : "/KpcPersonalEvaluation/updatePassword",
			type : "post",
			dataType : "text",
			data : {
				password : $("#pwd1").val()				
			},
			success : function(data){
				if(data=="S"){
					alert("비밀번호 변경이 완료되었습니다.");
					document.location.href="/KpcPersonalEvaluation/main";
				}else{
					alert('변경 중 에러가 발생하였습니다. \n에러가 계속될경우 기술개발팀에 문의해주세요.');
				}
			},error : function(e){
				alert("fail");
				console.log(e);
			}				
		});
		 
	}

</script>
</html>
