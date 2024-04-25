<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value='${pageContext.request.contextPath }'></c:set>
 <c:set var="id" value="${login}" />
 <c:set var="u" value="${requestScope.u}" />
 
 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>

    <!-- Custom Css -->
    <link rel="stylesheet" href="${root }/style/profile.css">
    <link rel="stylesheet" href="${root }/style/header.css">
    <link rel="stylesheet" href="${root }/style/style.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css">
    <!-- js script -->

    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <script type="text/javascript" src="${root }/js/header.js"></script>
</head>
<body>
  <header>
    <nav id="navbar">
      <div class="logoBox">
        <a href="${root }/index.jsp" class="navbar-logo">
          <svg style="width: 40px;" alt="logo" id="logo" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><defs><style>.a{fill:none;stroke:rgb(0 87 217/1);;stroke-linecap:round;stroke-linejoin:round;stroke-width:1.5px;}</style></defs><title/><path class="a" d="M20.776,12.79,11.89,15.777,10.7,14.594l-1.766.593a.75.75,0,0,0-.32,1.211L11,19.071a1.5,1.5,0,0,0,1.6.423l2.617-.88-.53,3.781a.75.75,0,0,0,.982.815l1.36-.457a.748.748,0,0,0,.46-.44l1.994-5.132,2.488-.837a1.875,1.875,0,1,0-1.2-3.554Z"/><path class="a" d="M18.288,13.626l-2.78-1.914a.749.749,0,0,0-.664-.093l-1.338.449a.751.751,0,0,0-.29,1.243l1.517,1.51"/><path class="a" d="M6,17.932A9,9,0,1,1,18.749,9.75"/><path class="a" d="M4.024,2.805l5.281,2a1.5,1.5,0,0,1,.944,1.7L9.935,8.069A1.5,1.5,0,0,1,8.828,9.23l-2.079.52L5.6,12.635a3,3,0,0,1-1.121,1.382l-1.83,1.257"/><path class="a" d="M15.994,3.27,14.527,6.194a1.5,1.5,0,0,0-.081,1.145L15,9"/></svg>
          <span style="color: rgb(0 87 217 / 1);"> HAPPLE.NET</span> 
        </a>
      </div>
      <div class="container">
        <div class="navbarLi collapse" id="navBox">
          <ul class="navbar-nav">
            <li class="nav-item"><a href="${root }/page/search.jsp" class="nav-link">Destinations</a></li>
            <li class="nav-item"><a class="nav-link">Planning</a></li>
            <li class="nav-item"><a href="${root }/page/hotplace.jsp" class="nav-link">My Place</a></li>
            <li class="nav-item"><a class="nav-link" onclick="javascript:onClickProfileBtn()">Profile</a></li>
            <li class="nav-item">
              <a class="nav-link signInBtn" onclick="javascript:onClickSignInBtn()"></a>
            </li>
          </ul>
        </div>
      <button type="button" class="navbar-toggler">
        <img src="../asset/img/menuDropDownBtn.svg" alt="menuDropDownBtn" />
      </button>
    </div>
  </nav>
  </header>

    <!-- Main -->
    <div class="main">
    	
        <h2>내정보 확인 및 수정</h2>
        <div class="card">
            <div class="card-body">
                <form name="loginForm" action="/product/trip?action=modify" method="POST">
				    <table>
				        <tbody>
				            <tr>
				                <td>ID :</td>
				                <td id="nameTd" class="editValue">
				                    <input type="text" name="id" value="${u.id}" readonly>
				                </td>
				            </tr>
				            <tr>
				                <td>Password :</td>
				                <td id="pwTd" class="editValue">
				                    <input type="text" name="pw" value="${u.pw}">
				                </td>
				            </tr>
				            <tr>
				                <td>이메일 :</td>
				                <td id="emailTd" class="editValue">
				                    <input type="text"  name="email" value="${u.email}">
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2" style="text-align: right;">
				                    <button type="button" onclick ="doSubmit()">수정</button>
				                    <button type="button" style="color: red;" onclick="disUser('${u.id}','${u.pw}')">회원 탈퇴</button>
				                </td>
				            </tr>
				        </tbody>
				    </table>
				</form>

            </div>
        </div>
    </div>
    <script type="text/javascript">
		function doSubmit() {
			
		    $.ajax({
		        type: "POST",
		        url: "/product/trip?action=modify",
		        data: $('#loginForm').serialize(),
		        success: function (response) {
					alert("회원 정보 수정 성공")
		            
		        },
		        error: function () {
		            alert("수정실패"); 
		        }
		    });
			
			
		}
	    function disUser(id,pw) {	
	    	console.log(id);
	    	var url = "/product/trip?action=deleteuser&id=" + id + "&pw=" + pw;
	    	fetch(url)
	        .then(response => {
	            // 서버로부터의 응답 처리
	            if (!response.ok) {
	                throw new Error('Network response was not ok');
	            }
	            alert("성공적으로 회원탈퇴 되었습니다.");
	            location.href="/product/index.jsp";
	            
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            // 오류 처리
	        });
	    }

    </script>
    <!-- End -->
</body>
</html>