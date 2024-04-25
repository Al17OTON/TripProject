let container = document.getElementById('map');;
let overlay;
let map;
let markers = [];
let infowindow = new kakao.maps.InfoWindow({zIndex:1});

loadMap();


function loadMap() {
	var options = {
		center: new kakao.maps.LatLng(36.35535495701599 , 127.29840379609664 ),
		level: 3
	};

	map = new kakao.maps.Map(container, options);
}

function drawMarker() {
	var listEl = document.getElementById('placesList'), 
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(), 
    bounds = new kakao.maps.LatLngBounds(), 
    listStr = '';
    
    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);
	removeMarker();

	var positions = [];
	idx = 0;
	midX = 0.0;
	midY = 0.0;

	searchList.forEach(element => {
		x = parseFloat(element.longitude);
		y = parseFloat(element.latitude);

		positions.push({
			title : element.title,
			latlng : new kakao.maps.LatLng(y, x)
		});

		midX += x;
		midY += y;
		idx++;
	});

	midX /= idx;
	midY /= idx;

	//container.innerHTML = "";
	setCenter(midX, midY);
	map.setLevel(8);
	var imageSrc = '/product/asset/img/markerimg.png', // 마커이미지의 주소입니다    
    	imageSize = new kakao.maps.Size(54, 60), // 마커이미지의 크기입니다
    	imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)

	for(var i = 0; i < positions.length; i++) {

		var marker = new kakao.maps.Marker({
			map : map,
			position: positions[i].latlng,
			title : positions[i].title,
			image: markerImage
		}); 
		itemEl = getListItem(i);

		markers.push(marker);

		bounds.extend(positions[i].latlng);

		var infowindow = new kakao.maps.InfoWindow({
			content: searchList[i].title
			
		});

		//kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    	//kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
		
		// 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function() {
				map.panTo(marker.getPosition());	//부드럽게 해당 마커로 이동
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            itemEl.onmouseover =  function () {
				map.panTo(marker.getPosition());	//부드럽게 해당 마커로 이동
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, searchList[i].title);


		fragment.appendChild(itemEl);
	}

	// 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}



function setCenter(x, y) {
	map.setCenter(new kakao.maps.LatLng(y, x));
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
function makeOverListener(map, marker, infowindow) {
    return function() {
		//pos = marker.getPosition();
		//setCenter(pos.longitude, pos.latitude);
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}

function removeMarker() {
	while(markers.length > 0) { 
		markers.pop().setMap(null);
	}
}

 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {   
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}


// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;text-align:center;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}


// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index) {

    var el = document.createElement('li'),
    itemStr = 
				//'<span class="markerbg marker_' + (index+1) + '"></span>' +		//이건 마커 번호가 이미지로 뜨는 것

				
				//'<img src="' + searchList[index].firstimage + '" style="max-width:80px;max-height:80px;margin:auto; float:left">' +

				//공백 제거하여 검색 쿼리에 삽입하기
                '<div class="info"' + 
				//"onclick=location.href='https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+ searchList[index].title.replace(/ /g,"") + "" + searchList[index].addr2.replace(/ /g,"") + "'; " +
				'onclick=\'javascript:clickPlace("' + searchList[index].content_id + '","' + searchList[index].title.replace(/ /g,"") + '","' + searchList[index].addr2.replace(/ /g,"") + '")\'' +
				">" +
                
				'   <h5>' + searchList[index].title + '</h5>';

    if (searchList[index].addr1) {
        itemStr += '    <span>' + searchList[index].addr2 + '</span>' 
		+ '   <span">' +  searchList[index].addr1  + '</span>';
    } else {
        itemStr += '    <span>' +  "no addr"  + '</span>'; 
    }
                 
      itemStr += '  <span class="tel">' + searchList[index].tel  + '</span>' +
                '</div>'; 
				
	// itemStr += ' <div class="form-check">' +
	// '<input type="checkbox" class="form-check-input" value="" id="flexCheckIndeterminate" style="float:right;" onclick="javaScript:openModal()">' +
  	// '</div>';


	if(searchList[index].img) itemStr = '<img src="' + searchList[index].img + '" style="max-width:80px;max-height:80px;margin:auto; float:left">' + itemStr;
	else itemStr = '<img src="../asset/img/noImage.png" style="max-width:80px;max-height:80px;margin:auto; float:left">' + itemStr;
    
	el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

function clickPlace(id, title, subtitle) {
	let url = "/product/trip?action=recommend&id=" + id;
	console.log(url);
	fetch(url, { method: "GET" }).then();
	
	window.open('https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=' + title + subtitle);
}


// function openModal() {
// 	console.log("11");
// 	modal = document.getElementById('modalContainer');
// 	modal.classList.remove('hidden');
// 	document.getElementById('map-mo').setAttribute("style", "display:none;");
// }

// function closeModal() {
// 	modal = document.getElementById('modalContainer');
// 	modal.classList.add('hidden');
// 	document.getElementById('map-mo').setAttribute("style", "display: block;");
// }


// function drawCustomMarker() {
// 	var positions = [];
// 	idx = 0;
// 	midX = 0.0;
// 	midY = 0.0;

// 	searchList.forEach(element => {
// 		x = parseFloat(element.mapx);
// 		y = parseFloat(element.mapy);

// 		positions.push({
// 			title : element.title,
// 			latlng : new kakao.maps.LatLng(y, x)
// 		});

// 		midX += x;
// 		midY += y;
// 		idx++;
// 	});

// 	midX /= idx;
// 	midY /= idx;

// 	//console.log(midX + " " + midY);

// 	var container = document.getElementById('map');
// 	container.innerHTML = "";
// 	var options = {
// 		center: new kakao.maps.LatLng(midY, midX),
// 		level: 8
// 	};
// 	var map = new kakao.maps.Map(container, options);


// 	for(var i = 0; i < positions.length; i++) {
// 		var marker = new kakao.maps.Marker({
// 			map : map,
// 			position: positions[i].latlng,
// 			title : positions[i].title
// 		}); 


// 		var content = 
// 		'<div class="wrap">' + 
// 		'    <div class="info">' + 
// 		'        <div class="title">' + 
// 						searchList[i].title	 + 
// 		'            <div class="close" onclick="javaScript:closeOverlay()" title="닫기"></div>' + 
// 		'        </div>' + 
// 		'        <div class="body">' + 
// 		'            <div class="img">' +
// 		'                <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
// 		'           </div>' + 
// 		'            <div class="desc">' + 
// 		'                <div class="ellipsis">'+ searchList[i].addr1 +'</div>' + 
// 		'                <div class="jibun ellipsis">'+ searchList[i].addr2 +'</div>' + 
// 			'                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' + 
// 		'            </div>' + 
// 		'        </div>' + 
// 		'    </div>' +    
// 		'</div>';


// 		overlay = new kakao.maps.CustomOverlay({
// 			content: content,
// 			map: map,
// 			position: marker.getPosition()       
// 		});

// 				// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
// 		kakao.maps.event.addListener(marker, 'click', function() {
// 			overlay.setMap(map);
// 		});

// 	}

// }

// function closeOverlay() {
// 	overlay.setMap(null);     
// }



// function drawMarkerTEST() {

// 	var positions = [];
// 	idx = 0;
// 	midX = 0.0;
// 	midY = 0.0;

// 	searchList.forEach(element => {
// 		x = parseFloat(element.mapx);
// 		y = parseFloat(element.mapy);

// 		positions.push({
// 			title : element.title,
// 			latlng : new kakao.maps.LatLng(y, x)
// 		});

// 		midX += x;
// 		midY += y;
// 		idx++;
// 	});

// 	midX /= idx;
// 	midY /= idx;

// 	//console.log(midX + " " + midY);

// 	var container = document.getElementById('map');
// 	//container.innerHTML = "";
// 	var options = {
// 		center: new kakao.maps.LatLng(midY, midX),
// 		level: 8
// 	};
// 	var map = new kakao.maps.Map(container, options);

// 	for(var i = 0; i < positions.length; i++) {
// 		var marker = new kakao.maps.Marker({
// 			map : map,
// 			position: positions[i].latlng,
// 			title : positions[i].title
// 		}); 
// 	}

// 	// 마커가 지도 위에 표시되도록 설정합니다
// 	//marker.setMap(map);
// }
