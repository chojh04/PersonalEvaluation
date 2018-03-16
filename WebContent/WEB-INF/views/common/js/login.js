//로그인  처리
var login = function(){
	if($("#employeId").val()==""){
		alert("아이디를 입력해주세요.");
		return;
	}else if($("#employePw").val()==""){
		alert("비밀번호를 입력해주세요.");
		return;
	}
	$.ajax({
		url : '/KpcPersonalEvaluation/loginProc',
		type : 'post',
		dataType : 'text',
		data : {"employeId" : $("#employeId").val(), "employePw":$("#employePw").val()},
		success:function(data){
			if(data!="fail"){
				alert("'"+data+"'님 로그인 되었습니다.");
				document.location.href='/KpcPersonalEvaluation/main';
			}else{
				alert("로그인 실패");
			}
		},error:function(e){			
			//console.log(e);
		}			
	});
}

//Enter Submit
$(function(){
	$("text").keydown(function(e){
		if(e.keyCode == 13){
			login();
			return false;
		}
	});		
});