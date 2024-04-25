<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value='${pageContext.request.contextPath }'></c:set>   
<!DOCTYPE html>
<html lang="en">
  <head>
  	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  	<style>
		 .modal-content {
		    background-image: url('/product/asset/img/Travler.svg');
		    background-repeat: no-repeat; /* 이미지 반복 설정 해제 */
    		background-size: cover; /* 배경 이미지를 컨테이너에 맞게 조절 */
    		height: auto;
		}
		.modal-header {
		    border-bottom: none; /* 헤더의 하단 구분선 제거 */
		}
		
		.modal-body {
		    border-bottom: none; /* 바디의 하단 구분선 제거 */
		}
		
		.modal-footer {
		    border-top: none; /* 푸터의 상단 구분선 제거 */
		}
		.modal-dialog {
		    height: 80vh; /* 최대 높이를 화면의 80%로 지정합니다. */
		    margin: auto; /* 중앙 정렬을 위해 margin을 auto로 설정합니다. */
		}
		
  	</style>

    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sign</title>
    <link rel="stylesheet" href="${root }/style/login.css" />
    <link rel="stylesheet" href="${root }/style/style.css" />

    <script type="text/javascript">
      window.onload = function () {
        const isLogin = localStorage.getItem("isLogin");
        if (isLogin) location.href = "index.jsp";
      };
    </script>
    <script src="${root }/js/login.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="container">
      <div id="leftside"></div>
      <div id="rightside">
        <div id="logo">
          <img src="${root }/asset/img/logoSVG.svg" alt="" style="width: 100px" />
          <div class="logoText">HAPPLE.NET</div>
        </div>

        <div class="content">
          <p>Happle.net의 다양한 서비스를 이용하려면 로그인이 필요합니다</p>
          <div class="btnBox">
            <button class="loginBtn" onclick="">구글로 로그인</button>
             <a class="loginBtn " href="#loginModal" data-bs-toggle="modal" >로그인</a>

          </div>
          <p>---------------회원이 아니십니까? ----------------</p>
          <div class="btnBox">
            <button class="loginBtn">구글로 시작하기</button>
            <a class="loginBtn " href="#signupModal" data-bs-toggle="modal" >회원가입</a>

          </div>
        </div>
      </div>
    </div>
    
 	<div>
 	<%@ include file="loginModal_template.jsp" %>
 	</div>
      
  </body>
  <script type="text/javascript">
  	
  	
	function logIn(){
		var id = document.getElementById("id").value;
		console.log("111111111111111111111");
	    $.ajax({
	        type: "POST",
	        url: "/product/trip?action=login",
	        data: $('#loginForm').serialize(), 
	        success: function (data) {
				if(data == "false"){
					alert("일치하는 계정이 없습니다."); 
				}else{	
					alert("반갑습니다 " + id + "님");
					location.href="/product/index.jsp";
				}
	            
	        },
	        error: function () {
	            alert("error"); 
	        }
	    });
	}
  	function signUp(){
	    var nameInput = document.getElementById('newName');
	    var idInput = document.getElementById('newId');
	    var passwordInput = document.getElementById('newPassword');
	    var confirmPasswordInput = document.getElementById('newconfirmPassword');
	    var emailInput = document.getElementById('newEmail');
	    
	    //좌우 공백제거
	    var name = nameInput.value.trim();
	    var id = idInput.value.trim();
	    var password = passwordInput.value;
	    var confirmPassword = confirmPasswordInput.value;
	    var email = emailInput.value.trim();
	    
	    //유효성검사성공
	    if(validateSignupForm(name,id,password,confirmPassword,email)){
	    	$.ajax({
	    		url:'/product/trip?action=signup',
	    		type:'post',
	    		data:{"name" : name,
	    			"id": id,
	    			"password":password,
	    			"email": email
	    		},
	    		success: function(data){
	    			if(data=="IdDupl"){
	    				alert("아이디가 중복됩니다");
	    			}else{
	    				alert("회원가입성공!!");
		    			$('#signupModal').modal('hide');
	    			}
	    			
	    		},error: function(error){
	    			alert("error");
	    			$('#signupModal').modal('hide');
	    		}
	    	})
	    }
  		
  	}

	function validateSignupForm(name,id,password,confirmPassword,email) {
	    // 필요한 모든 입력 필드 가져오기

	    
	    // 이름이 비어 있는지 확인
	    if (name === '') {
	        alert('이름을 입력하세요.');
	        nameInput.focus();
	        return false;
	    }
	    
	    // 아이디가 비어 있는지 확인
	    if (id === '') {
	        alert('아이디를 입력하세요.');
	        idInput.focus();
	        return false;
	    }
	    
	    // 비밀번호가 비어 있는지 확인
	    if (password === '') {
	        alert('비밀번호를 입력하세요.');
	        passwordInput.focus();
	        return false;
	    }
	    
	    // 비밀번호 확인이 비어 있는지 확인
	    if (confirmPassword === '') {
	        alert('비밀번호 확인을 입력하세요.');
	        confirmPasswordInput.focus();
	        return false;
	    }
	    
	    // 비밀번호와 비밀번호 확인이 일치하는지 확인
	    if (password !== confirmPassword) {
	        alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
	        confirmPasswordInput.focus();
	        return false;
	    }
	    
	    // 이메일이 비어 있는지 확인
	    if (email === '') {
	        alert('이메일을 입력하세요.');
	        emailInput.focus();
	        return false;
	    }
	    
	    // 이메일 형식 유효성 검사 (간단한 형식 검사 예시)
	    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	    if (!emailPattern.test(email)) {
	        alert('유효한 이메일 주소를 입력하세요.');
	        emailInput.focus();
	        return false;
	    }
	    
	    // 유효성 검사 통과
	    return true;
	}
	

	function findpass() {
	    // 폼 데이터를 서버로 전송하여 비밀번호 찾기 동작 수행
	    $.ajax({
	        type: "POST",
	        url: "/product/trip?action=findpass",
	        data: $('#findpassform').serialize(), // 폼 데이터 직렬화
	        success: function (response) {
	            // 서버에서 받은 비밀번호를 alert 창으로 표시
	            if(response.password == null){
	            	alert("일치하는 정보가 없습니다!!!!!!!");
	            }
	            else {alert("Your password is: " + response.password);}
	            
	        },
	        error: function () {
	            alert("Failed to find password."); // 비밀번호 찾기에 실패한 경우 알림
	        }
	    });
	}
	
	function resetForm() {
	    // 이름 입력 필드 초기화
	    document.getElementById("newName").value = "";
	    // 아이디 입력 필드 초기화
	    document.getElementById("newId").value = "";
	    // 비밀번호 입력 필드 초기화
	    document.getElementById("newPassword").value = "";
	    // 비밀번호 확인 입력 필드 초기화
	    document.getElementById("newconfirmPassword").value = "";
	    // 이메일 입력 필드 초기화
	    document.getElementById("newEmail").value = "";
	}
  </script>

</html>
