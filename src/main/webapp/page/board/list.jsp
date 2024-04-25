<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${login}" />
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
    <link href="${root}/asset/css/app.css" rel="stylesheet" />
    <link rel="stylesheet" href="${root }/style/header.css" />
    <title>SSAFY</title>
  </head>
  <body>
  <%@ include file="/page/header.jsp" %>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">글목록</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2">
            <div class="col-md-2 text-start">
              <button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">
                글쓰기
              </button>
            </div>
            <div class="col-md-7 offset-3">
              <form id="form-search" class="d-flex">
                <select
                  id="key"
                  name="key"
                  class="form-select form-select-sm ms-5 me-1 w-50"
                  aria-label="검색조건"
                >
                  <option selected>검색조건</option>
                  <option value="article_no">글번호</option>
                  <option value="subject">제목</option>
                  <option value="user_id">작성자</option>
                </select>
                <div class="input-group input-group-sm">
                  <input id="word" name="word" type="text" class="form-control" placeholder="검색어..." />
                  <button id="btn-search" class="btn btn-dark" type="button">검색</button>
                </div>
              </form>
            </div>
          </div>
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody>    
				<c:forEach var="article" items="${articles}">    
	              <tr class="text-center">
	                <th scope="row">${article.idx}</th>
	                <td class="text-start">
	                  <a
	                    href="#"
	                    class="article-title link-dark"
	                    data-no="${article.articleNo}"
	                    style="text-decoration: none"
	                  >
	                    ${article.subject}
	                  </a>
	                </td>
	                <td>${article.userId}</td>
	                <td>${article.hit}</td>
	                <td>${article.registerTime}</td>
	              </tr>            
				</c:forEach>   
            </tbody>
          </table>
        </div>
        <div class="row">
          <ul class="pagination justify-content-center">
          	
          	<c:if test="${page gt 1}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=1">처음으로</a></li>
            </c:if>
          	<c:if test="${page gt 1}">
            <li class="page-item">
              <a class="page-link" href="${root}/article?action=list&page=${page-1}">이전</a>
            </li>
            </c:if>
            <c:if test="${page gt 2}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${page-2}">${page - 2}</a></li>
            </c:if>
            <c:if test="${page gt 1}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${page-1}">${page - 1}</a></li>
            </c:if>
            <li class="page-item active">
              <a class="page-link" href="#">${page}</a>
            </li>
            <c:if test="${page lt endpage}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${page+1}">${page + 1}</a></li>
            </c:if>
            <c:if test="${page lt endpage - 1 }">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${page+2}">${page + 2}</a></li>
            </c:if>
            <c:if test="${page lt endpage}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${page+1}">다음</a></li>
            </c:if>
            <c:if test="${page lt endpage}">
            	<li class="page-item"><a class="page-link" href="${root}/article?action=list&page=${endpage}">마지막으로</a></li>
            </c:if>
          </ul>
        </div>
      </div>
    </div>
    
    <form id="form-param" method="get" action="">
      <input type="hidden" id="p-action" name="action" value="">
      <input type="hidden" id="p-pgno" name="pgno" value="">
      <input type="hidden" id="p-key" name="key" value="">
      <input type="hidden" id="p-word" name="word" value="">
    </form>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
    <script>
      let titles = document.querySelectorAll(".article-title");
      titles.forEach(function (title) {
        title.addEventListener("click", function () {
          console.log(this.getAttribute("data-no"));
          location.href = "${root}/article?action=view&articleno=" + this.getAttribute("data-no");
        });
      });
      
      document.querySelector("#btn-search").addEventListener("click", function () {
          // TODO : 검색 구현
      });
      
      let pages = document.querySelectorAll(".page-link");
      pages.forEach(function (page) {
        page.addEventListener("click", function () {
          console.log(this.parentNode.getAttribute("data-pg"));
          document.querySelector("#p-action").value = "list";
       	  document.querySelector("#p-pgno").value = this.parentNode.getAttribute("data-pg");
       	  document.querySelector("#p-key").value = "${param.key}";
       	  document.querySelector("#p-word").value = "${param.word}";
          document.querySelector("#form-param").submit();
        });
      });

      document.querySelector("#btn-mv-register").addEventListener("click", function () {
        location.href = "${root}/article?action=mvwrite";
      });
    </script>
  </body>
</html>
