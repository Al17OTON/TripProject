<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value='${pageContext.request.contextPath }'></c:set>
<c:set var="id" value="${login}" />

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>HAPPLE.NET</title>
    
    <link rel="stylesheet" href="${root }/style/main.css" />
    <link rel="stylesheet" href="${root }/style/style.css" />
    <link rel="stylesheet" href="${root }/style/header.css" />
  </head>
  <body>
    <div id="page-top">
	<%@ include file="page/header.jsp" %>
      
      <main>
        <section class="page_section" id="mainCover">
            <div class="image-container">
     			 <img alt="" src="/product/asset/img/place0.jpg">
   			 </div>
          <div class="mainText">Discover your own Travel</div>
        </section>
        <section class="page-section">
          <div class="container">
            <div style="color: rgb(75 75 75); letter-spacing: .025em; text-transform: uppercase;font-weight: 500;font-size: 14px; line-height: 1.4;display: block;">
            plan your trip
            </div>
            <div style="justify-content: space-between; align-items: center; display: flex;">
              <h2 class="sectionH2">
                Where to next?
              </h2>
              <a href="${root }/page/search.jsp" class="pageMoveBtn">
                Search all destinations
              </a>
            </div>
          </div>
        </section>
        <section class="page-section"></section>
      </main>
      <!-- <footer>
        <p>
          <span id="footer">Copyright 2024.Jeon Youngju & Lim Kwon. All Rights Reserved.</span>
        </p>
      </footer> -->
    </div>
  </body>
</html>
