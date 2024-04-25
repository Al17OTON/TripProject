var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(36.35535495701599, 127.29840379609664), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
    // 클릭한 위치에 마커를 표시합니다 
    addMarker(mouseEvent.latLng);
    searchAddrFromCoords(mouseEvent.latLng,displayCenterInfo);
    displayDistance();         
});

var distanceOverlay;


//출발및 도착 마커이미지 불러오기
var startSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png', // 출발 마커이미지의 주소입니다    
    startSize = new kakao.maps.Size(50, 45), // 출발 마커이미지의 크기입니다 
    startOption = { 
        offset: new kakao.maps.Point(15, 43) // 출발 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
    };

//도착
var arriveSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png', // 도착 마커이미지 주소입니다    
arriveSize = new kakao.maps.Size(50, 45), // 도착 마커이미지의 크기입니다 
arriveOption = { 
    offset: new kakao.maps.Point(15, 43) // 도착 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
};

//중간길 마커이미지
var midleSrc = '/product/asset/img/flag_icon.png', 
midleSize = new kakao.maps.Size(50, 45), 
midleOption = { 
    offset: new kakao.maps.Point(3, 43) 
};

// 도착과 시작 중간길 마커이미지를 생성합니다
var startImage = new kakao.maps.MarkerImage(startSrc, startSize, startOption);
var arriveImage = new kakao.maps.MarkerImage(arriveSrc, arriveSize, arriveOption);
var midleImage = new kakao.maps.MarkerImage(midleSrc, midleSize, midleOption);


var bounds = new kakao.maps.LatLngBounds();  
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var markers = [];
//마커별로 좌표를 얻고 이어주면 될듯
// 지도에 표시할 선을 생성합니다
var linePath = getLinePath();
var polyline = new kakao.maps.Polyline({
	path: linePath, // 선을 구성하는 좌표배열 입니다
	strokeWeight: 3, // 선의 두께 입니다
	strokeColor: '#db4040', // 선의 색깔입니다
	strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	strokeStyle: 'solid' // 선의 스타일입니다
});


function getLinePath(){
	var list = [];
	for (var i = 0; i < markers.length; i++) {
			//getPosition 반환타입 > Latlng
			// linePath 에 필요한타입 > Lat lng 
			//바로넣으면 됩니다
		console.log("폴리라인" + markers[i].getPosition());
		list.push(markers[i].getPosition());
	}
	return list; 
}
		
polyline.setMap(map);  	

//위도 경도차로 직선거리를 반환해주는 함수
function haversine(lat1, lon1, lat2, lon2) {
    const R = 6371.0;

    const lat1Rad = toRadians(lat1);
    const lon1Rad = toRadians(lon1);
    const lat2Rad = toRadians(lat2);
    const lon2Rad = toRadians(lon2);

    const dlat = lat2Rad - lat1Rad;
    const dlon = lon2Rad - lon1Rad;

    const a = Math.sin(dlat / 2) ** 2 + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(dlon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const distance = R * c;
    console.log(distance * 1000);console.log(distance);
    return distance * 1000; 
}

function toRadians(degrees) {
    return degrees * (Math.PI / 180);
}



// 마커를 생성하고 지도위에 표시하는 함수입니다
function addMarker(position) {
    
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        position: position
    });	

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
    marker.setDraggable(true);
    kakao.maps.event.addListener(marker, 'click', function() {
		console.log("마커클릭");
		console.log(markers);
		marker.setMap(null);
		markers = markers.filter(isView);
		setAllImage();
		polyline.setPath(getLinePath());
		displayDistance();  
		
	});
	kakao.maps.event.addListener(marker, 'dragend', function() {
    	polyline.setPath(getLinePath());
    	displayDistance();  
 
	});
    
    // 생성된 마커를 배열에 추가합니다
    markers.push(marker);
    setAllImage();
   	polyline.setPath(getLinePath());
   	displayDistance();  
   	
}

//마커가 삭제될때 마커배열에서 삭제하기위한 함수 작성
var isView = function(value){
	return value.getMap();
};
//마커가 삭제될 시 시작과 끝 이미지를 설정하기 위한 함수 작성
function setAllImage(){
    for (var i = 0; i < markers.length; i++) {
        markers[i].setImage(midleImage);
    }  		
	markers[markers.length-1].setImage(arriveImage);
	markers[0].setImage(startImage);
}

let viewdis =[];


///마커별 거리를 표시해주는 함수
function displayDistance() {
	var position =[];
	var distance= [];
	
    for (var i = 0; i < viewdis.length; i++) {
		viewdis[i].setMap(null);
		console.log("viewdis = ",viewdis[i])
    }

    viewdis = [];
    
    for (var i = 1; i < markers.length; i++) {
		var apoint = markers[i].getPosition();
		var bpoint = markers[i-1].getPosition();
        position.push(apoint) ;
        distance.push( Math.round(haversine(apoint.getLat(),apoint.getLng(),bpoint.getLat(),bpoint.getLng() ))) ;
        console.log(distance[i]);
    }	
	
	
    for (var i = 0; i < position.length; i++) {
		if (distance[i] > 0) {
			if(i== position.length-1){//도착지점일때 (도보 자동차 자전거 거리 보여주기)
				var path= polyline.getPath();
				var sums = Math.round(polyline.getLength()), // 선의 총 거리를 계산합니다
                content = getTimeHTML(sums); // 커스텀오버레이에 추가될 내용입니다
                
            	// 그려진 선의 거리정보를 지도에 표시합니다
            	showDistance(content, path[path.length-1]);
				
			}else{
		        // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
		        var distanceOverlay = new kakao.maps.CustomOverlay({
		            content: '<div class="dotOverlay">거리 <span class="number">' + distance[i] + '</span>m</div>',
		            position: position[i],
		            yAnchor: 1,
		            zIndex: 2
		        });
		
		        // 지도에 표시합니다
		        viewdis.push(distanceOverlay);
		        distanceOverlay.setMap(map);
	        				
			}

	        
    	}
   
    }
    
    	
    
}
function showDistance(content, position) {
    
    if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면
        console.log("이미있음");
        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        distanceOverlay.setPosition(position);
        distanceOverlay.setContent(content);
        
    } else { // 커스텀 오버레이가 생성되지 않은 상태이면
        console.log("distanceOver !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        // 커스텀 오버레이를 생성하고 지도에 표시합니다
        distanceOverlay = new kakao.maps.CustomOverlay({
            map: map, // 커스텀오버레이를 표시할 지도입니다
            content: content,  // 커스텀오버레이에 표시할 내용입니다
            position: position, // 커스텀오버레이를 표시할 위치입니다.
            xAnchor: 0,
            yAnchor: 0,
            zIndex: 3  
        }); 
    }
}

function getTimeHTML(distance) {

    // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
    var walkkTime = distance / 67 | 0;
    var walkHour = '', walkMin = '';

    // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
    if (walkkTime > 60) {
        walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + '</span>시간 '
    }
    walkMin = '<span class="number">' + walkkTime % 60 + '</span>분'

    // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
    var bycicleTime = distance / 227 | 0;
    var bycicleHour = '', bycicleMin = '';

    // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
    if (bycicleTime > 60) {
        bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + '</span>시간 '
    }
    bycicleMin = '<span class="number">' + bycicleTime % 60 + '</span>분'

    // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
    var content = '<ul class="dotOverlay distanceInfo">';
    content += '    <li>';
    content += '        <span class="label">총거리</span><span class="number" id="totaldis">' + distance + '</span>m';
    content += '    </li>';
    content += '    <li>';
    content += '        <span class="label">도보</span>' + walkHour + walkMin;
    content += '    </li>';
    content += '    <li>';
    content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
    content += '    </li>';
    content += '</ul>'

    return content;
}
    





// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map); 

    }            
}


//TSP를활용해 경로중 가장 짦은 경로로 바꿔준다 최대마커는 8개 라고 가정한다.
//TSP(여행하는 외판원 문제) 완탐
//시작점은 고정되있다고 가정한다.
function recomendPath(){
	//거리를 담을 배열 dist배열
	var dist = new Array(markers.length);
	for (var i = 0; i < markers.length; i++) {
	    dist[i] = new Array(markers.length);
	}
	
	// 노드별 거리저장
	for (var i = 0; i < markers.length; i++) {
		var apoint = markers[i].getPosition();
        for (var j = i; j < markers.length; j++) {
        	if(i==j)continue;
        	var bpoint = markers[j].getPosition();
        	var m = haversine(apoint.getLat(),apoint.getLng(),bpoint.getLat(),bpoint.getLng());
        	dist[i][j] = m;
        	dist[j][i] = m;
    	} 
    }
    var path = TSP(dist);
    var tmp = [];
    console.log("findfath pth: ",path);
   	for (var i = 0; i < path.length; i++) {
	    tmp.push(markers[path[i]]);
	}
	markers = tmp;
	setAllImage();
	polyline.setPath(getLinePath());
    displayDistance();  
    
    
    
       
}

function TSP(dist){	
	let path = [];
	let mindist = Number.MAX_SAFE_INTEGER;
	
	var visit = []
	for (var i = 0; i < markers.length; i++) {visit.push(false);}
	//모든 경로의 거리 합산 후 가장짦은 경우를 path에 저장
	function recu(now, order,sum){
		if(sum > mindist)return;
		if(order.length ==markers.length){
			mindist = sum;
			path = order.slice();
			console.log("order :"+ order);
			return;
		}
		for(var i = 0; i < markers.length; i++){
			if(visit[i])continue;
			visit[i] = true;
			order.push(i);
			recu(i,order, sum+ dist[now][i]);
			order.pop();	
			visit[i] = false;	
		}
		
	}
	visit[0] = true;
	recu(0,[0],0);
	console.log("TSP :", path);
	return path;
}

function savePlanning(){
	var pName = document.getElementById("inputPlaneName").value;
	var totaldis = 0;
	var lat = [];
	var lng = [];
	for (var i = 0; i < markers.length; i++) {
            lat.push(markers[i].getPosition().getLat());
            lng.push(markers[i].getPosition().getLng()); 
	}
	   
    for (var i = 1; i < markers.length; i++) {
		var apoint = markers[i].getPosition();
		var bpoint = markers[i-1].getPosition();

        totaldis += Math.round(haversine(apoint.getLat(),apoint.getLng(),bpoint.getLat(),bpoint.getLng() )) ;

    }	
	console.log("!!!!!!!!!!!!!!!!!!!totaldis :",totaldis);
	

    $.ajax({
		type: "POST",
		url :"/product/plan?action=addplan",
		data: {
	        pName: pName,
	        distance: totaldis,
	        lat: JSON.stringify(lat),
	        lng: JSON.stringify(lng)
		},
		success: function(res){
			alert("저장성공!");
		},
		error: function(){
			alert("저장실패!");
		} 
		
	})

	
}
var planlist =[];
$('#loadModal').on('shown.bs.modal', function () {
    console.log("loadModal 실행!");
    loadModalOpenData(); 
});


function loadModalOpenData() {
    console.log("loadModal 데이터 불러오기");
    planlist = [];
    $.ajax({
        type: "POST",
        url: "/product/plan?action=listplan",
        success: function (res) {
            var travelList = $('#travelList');
            travelList.empty(); 
            for (var i = 0; i < res.length; i++) {
                var plan = res[i];
                planlist.push(plan);
                var listItem = $('<li>').addClass('list-group-item d-flex justify-content-between align-items-center')
                    .append(
                        $('<div>').addClass('form-check')
                            .append(
                                $('<input>').addClass('form-check-input').attr({
                                    type: 'radio', 
                                    name: 'selectedTravel',
                                    value: plan.pName, 
                                    id: 'travelCheckbox' + i
                                })
                            )
                            .append(
                                $('<label>').addClass('form-check-label').attr('for', 'travelCheckbox' + i).text(plan.pName + ' (총 거리: ' + plan.distance + ' m)')
                            )
                    );
                travelList.append(listItem); 
            }
        },
        error: function () {
            alert("로드 실패");
        }
    });
}

// 라디오 버튼이 클릭될 때 다른 모든 라디오 버튼의 선택 상태를 해제
$(document).on('change', 'input[name="selectedTravel"]', function() {
    $('input[name="selectedTravel"]').not(this).prop('checked', false);
});

function choiceRoute(){
    var selectedPlan = $('input[name="selectedTravel"]:checked').val();
    if(selectedPlan) {
        console.log("선택된 여행 항목: " + selectedPlan);
        // 선택된 여행 항목에 해당하는 데이터가져오기
        var selectedData = null;
        for(var i = 0; i < planlist.length; i++) {
            if(planlist[i].pName === selectedPlan) {
                selectedData = planlist[i];
                break;
            }
        }
        if(selectedData) {
        	//선택된 데이터를 불러온 후 마커 추가하기
            console.log("선택된 여행 항목의 데이터: ", selectedData);
            
            var latitudes = selectedData.lat;
			var longitudes = selectedData.lon;
			
			console.log("Latitude 배열:", latitudes);
			console.log("Longitude 배열:", longitudes);
			
			setCenter(new kakao.maps.LatLng(latitudes[0],longitudes[0]));
			for(var i=0; i<markers.length;i++){
				markers[i].setMap(null);
			}
			
			markers = [];
			for(var i=0; i<latitudes.length;i++){
				var coord = new kakao.maps.LatLng(latitudes[i],longitudes[i]);
				addMarker(coord);
				// LatLngBounds 객체에 좌표를 추가합니다
    			bounds.extend(coord);
			}
			map.setBounds(bounds);
            
        } else {
            console.log("데이터를 찾을 수 없습니다");
        }
    } else {
        // 선택된 여행 항목이 없는 경우
        alert("여행 항목을 선택해주세요!");
    }
}

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var infoDiv = document.getElementById('centerAddr');
		
        for(var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === 'H') {
				console.log(result[i].address_name);
                infoDiv.innerHTML = result[i].address_name;
                break;
            }
        }
    }    
}
function setCenter(abc) {
	map.setCenter(abc);
}
