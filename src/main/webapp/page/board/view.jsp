<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="root" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${login}" />
<c:if test="${article ne null}">
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
    <title>SSAFY</title>
    <style>
        .comment-container {
            border: 1px solid #ccc;
            margin-bottom: 10px;
            padding: 10px;
        }
        .comment-author {
            font-weight: bold;
            color: #333;
        }
        .comment-date {
            color: #666;
            font-size: smaller;
        }
        .comment-content {
            margin-top: 5px;
        }
    </style>
    
  </head>
  <body>
  
    <div class="container">
      <%@ include file="/page/board/confirm.jsp" %>
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">글보기</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row my-2">
            <h2 class="text-secondary px-5">${article.articleNo}. ${article.subject}</h2>
          </div>
          <div class="row">
            <div class="col-md-8">
              <div class="clearfix align-content-center">
                <img
                  class="avatar me-2 float-md-start bg-light p-2"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                />
                <p>
                  <span class="fw-bold">${article.userId}</span> <br />
                  <span class="text-secondary fw-light"> ${article.registerTime} 조회 : ${article.hit} </span>
                </p>
              </div>
            </div>
            <div class="col-md-4 align-self-center text-end">댓글 : ${fn:length(memolist)}</div>
            <div class="divider mb-3"></div>
            <div class="text-secondary">
              ${article.getContentHTML()}
            </div>
            <div class="divider mt-3 mb-3"></div>
            <div class="d-flex justify-content-end" id="button-group">
              <button type="button" id="btn-list" class="btn btn-outline-primary mb-3">
                글목록
              </button>
              <c:if test="${ article.userId eq id }">
              <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">
                글수정
              </button>
              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">
                글삭제
              </button>
              </c:if>
           	  <button type="button" id="btn-comment" class="btn btn-outline-success mb-3 ms-1" onclick="javascript:setComment(-1, 'addcomment')">
                답글
              </button>
            </div>
            <div id="root-comment"></div>

            <div id="comment-area">
            <c:forEach items="${ memolist }" var="memo">
            	<!-- 대댓글은 최대 5단계까지만 허용됨 -->
            	<div id="memo_id_${memo.memo_no }" style="margin:0 0 0 ${memo.depth * 5 }%" >
            		<div class="comment-container">
	                	<div class="comment-author">${memo.user_id }</div>
	                	<div class="comment-date">${memo.memo_time }</div>
	                	<div class="comment-content">${memo.comment }</div>
	                	
	                	
            			<div class="d-flex justify-content-end" id="comment-button-group">
            				<c:if test="${memo.user_id eq id }">
            					<button type="button" id="btn-comment-update" class="btn btn-outline-primary mb-3" onclick="javascript:setComment(${memo.memo_no}, 'updatecomment')">
				                수정
				              	</button>
	            				<button type="button" id="btn-comment-delete" class="btn btn-outline-danger mb-3 ms-1 " onclick="javascript:deleteComment(${memo.memo_no})">
	                			삭제
	              				</button>
	              			</c:if>
	              			
	              			<c:if test="${memo.depth le 5 }">
		              			<button type="button" id="btn-comment" class="btn btn-outline-success mb-3 ms-1" onclick="javascript:setComment(${memo.memo_no}, 'addcomment')">
	                			답글
	              				</button>
              				</c:if> 
	            		</div>
            		
	                	
            		</div>
            	</div>
            </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 부트스트랩 JavaScript 추가 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
      crossorigin="anonymous"
    ></script>
    <script>
      document.querySelector("#btn-list").addEventListener("click", function () {
        location.href = "${root}/article?action=list";
      });
      document.querySelector("#btn-mv-modify").addEventListener("click", function () {
        //alert("글수정하자!!!");
        location.href = "${root}/article?action=mvmodify&articleno=${article.articleNo}";
      });
      document.querySelector("#btn-delete").addEventListener("click", function () {
        //alert("글삭제하자!!!");
        location.href = "${root}/article?action=delete&articleno=${article.articleNo}";
      });
      
      var comment = null;
      
      function setComment(id, type) {
    	if(comment == null) comment = makeComment();
    	var memo = document.getElementById("memo_id_" + id);
    	if(memo == null) memo = document.getElementById("root-comment");
    	
    	//console.log("set" + id);
    	comment.setAttribute("data-id", id);
    	comment.setAttribute("data-action", type);
    	comment.firstChild.firstChild.value = "";
    	memo.appendChild(comment);	//새로운 객체를 만들어서 추가하는게 아니라 해당 위치로 이동한다고함.
      }
      
      function makeComment() {
    	  var form = document.createElement("form");
    	  var div = document.createElement("div");
    	  div.setAttribute("id", "commentDiv");
    	  /* div.setAttribute("class", "d-flex justify-content-end") */
    	  var text = document.createElement("input");
    	  text.setAttribute("id", "commentArea");
    	  text.setAttribute("class", "form-control");
    	  text.setAttribute("type", "text");
    	  text.setAttribute("placeholder", "댓 글");
    	  text.required = true;
    	  
    	  var button = document.createElement("button");
    	  button.setAttribute("class", "float-end btn btn-outline-success mb-3 ms-1 mt-1 ");
    	  button.setAttribute("type", "button");
    	  button.onclick = addComment;
    	  button.innerHTML = "등록";
    	  
    	  var delButton = document.createElement("button");
    	  delButton.setAttribute("class", "float-end btn btn-outline-success mb-3 ms-1 mt-1 ");
    	  delButton.setAttribute("type", "button");
    	  delButton.onclick = addComment;
    	  button.innerHTML = "등록";
    	  
    	  var clearDiv = document.createElement("div");
    	  clearDiv.setAttribute("style", "clear:both;");
    	  
    	  div.appendChild(text);
    	  div.appendChild(button);
    	  div.appendChild(clearDiv);
    	  form.appendChild(div);
    	  return form;
      }
      
      function addComment() {
  		
    	var comVal = document.getElementById("commentArea").value;
    	  
    	if(!comment.checkValidity()) {
    		//alert("댓글을 입력해주세요!");
    		var tmpSubmit = document.createElement('button')
		    comment.appendChild(tmpSubmit)
		    tmpSubmit.click()
		    comment.removeChild(tmpSubmit)
    		return;
    	}
  	    $.ajax({
  	        type: "POST",
  	        url: "/product/article?action=" + comment.getAttribute("data-action"),
  	        data: {
  	        	"id" : comment.getAttribute("data-id"),
  	    		"comment" : comVal,
  	    		"num" : ${article.articleNo},
  	    		"user_id" : "${id}"
  	        }, 
  	        success: function (data) {

  	          	location.reload();
  	        },
  	        error: function () {
  	            alert("error"); 
  	        }
  	    });
      }
      
    function deleteComment(comment_id) {
    	location.href = "${root}/article?action=deletecomment&articleno=${article.articleNo}&id=" + comment_id;
    	location.reload();
	}

    </script>
  </body>
</html>
</c:if>
<c:if test="${article eq null}">
	<script>
	alert("글이 삭제되었거나 부적절한 URL 접근입니다.");
	location.href = "${root}/article?action=list";
	</script>
</c:if>
