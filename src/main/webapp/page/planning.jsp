<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var = "root" value='${pageContext.request.contextPath }'></c:set>
<c:set var="id" value="${login}" />

<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${root }/style/main.css" />
    <link rel="stylesheet" href="${root }/style/style.css" />
    <link rel="stylesheet" href="${root }/style/header.css" />
    
    <script id="kakaoSrc" type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=67e3e393e4e107a6962ea565cd1a99ea&libraries=services"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
<meta charset="EUC-KR">
<title>Insert title here</title>
    <style>
		.mundane-button {
		  display: inline-block;
		  padding: 10px 20px;
		  font-size: 16px;
		  font-weight: bold;
		  text-align: center;
		  text-decoration: none;
		  border: 2px solid #4CAF50;
		  color: #4CAF50;
		  background-color: #fff;
		  border-radius: 5px;
		  cursor: pointer;
		  transition: background-color 0.3s, color 0.3s, border-color 0.3s;
		}
		
		.mundane-button:hover {
		  background-color: #4CAF50;
		  color: #fff;
		}
		.dot {overflow:hidden;float:left;width:12px;height:12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png');}    
		.dotOverlay {position:relative;bottom:10px;border-radius:6px;top: 35px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;font-size:12px;padding:5px;background:#fff;}
		.dotOverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}    
		.number {font-weight:bold;color:#ee6152;}
		.dotOverlay:after {content:'';position:absolute;margin-left:-6px;
			left:50%;top:-7px;width:11px;height:8px;
			background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png');
			transform: rotate(180deg);
			}
		.distanceInfo {position:relative;top:5px;left:5px;list-style:none;margin:0;}
		.distanceInfo .label {display:inline-block;width:50px;}
		.distanceInfo:after {content:none;}
		.map_wrap {position:relative;overflow:hidden;width:100%;height:80%;}
		.custom_typecontrol {
		  position: absolute;
		  top: 10px;
		  left: 10px; /* 왼쪽 상단으로 이동 */
		  overflow: hidden;
		  max-width: 130px; /* 최대 너비 제한 */
		  height: auto; /* 자동으로 높이 조정 */
		  margin: 0;
		  padding: 0;
		  z-index: 1;
		  font-size: 12px;
		  font-family: 'Malgun Gothic', '맑은 고딕', sans-serif;
		}	
	</style>
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="map_wrap">
	    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div> 
		    <div class="custom_typecontrol radius_border" >
		         <button type="button" class="mundane-button" onclick="recomendPath()">경로추천</button>
		         <button type="button" class="mundane-button" data-toggle="modal" data-target="#saveModal">저장</button>
		         <button type="button" class="mundane-button" data-toggle="modal" data-target="#loadModal">불러오기</button>
		         
		    </div>
		     <span id="centerAddr"></span>
	
	</div>

	<!-- 모달 -->
	<div class="modal" id="saveModal">
	  <div class="modal-dialog">
	    <div class="modal-content">   
	      <!-- 모달 본문 -->
	      <div class="modal-body">
	        <div class="form-group">
	          <label for="textbox">해당 여행의 이름을 입력해주세요! </label>
	          <input type="text" class="form-control" id="inputPlaneName">
	        </div>
	      </div>

	      <div class="d-flex justify-content-end">
	        <button type="button" class="btn btn-primary mb-1 mr-1" style="margin-right: 5px;" onclick="savePlanning()">Save changes</button>
	        <button type="button" class="btn btn-secondary mb-1 mr-1" style="margin-right: 5px;" data-dismiss="modal">Close</button>
	        
	      </div>
	    </div>
	  </div>
	</div>
	
		<!-- 불러오기 모달 -->
	<div class="modal" id="loadModal">
	  <div class="modal-dialog">
	    <div class="modal-content">   
	      <!-- 모달 본문 -->
	      <div class="modal-body">
	        <div class="form-group">
	          <div>${login }님의 여행 목록</div>
	          <ul id="travelList" class="list-group"></ul> <!-- 여행 목록을 표시할 위치 -->
	        </div>
	      </div>
	
	      <div class="d-flex justify-content-end">
	        <button type="button" class="btn btn-primary mb-1 mr-1" style="margin-right: 5px;" onclick ="choiceRoute()">경로 선택</button>
	        <button type="button" class="btn btn-secondary mb-1 mr-1" style="margin-right: 5px;" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>

	<!-- 부트스트랩 JavaScript 추가 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
<script src="${root }/js/planning.js"></script>

</html>




