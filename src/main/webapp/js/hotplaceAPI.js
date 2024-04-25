let newMarker;

//getLocalData();


function getMyPlace(id) {
	console.log(id);
	
	var url = "/product/trip?action=showmyplace&id=" + id;
	fetch(url)
	.then(response => {
		// 서버로부터의 응답 처리
	  if (!response.ok) {
	    throw new Error('Network response was not ok');
	  }
	  return response.json(); // 응답 내용을 JSON 형식으로 변환하여 반환
	})
	.then(data => {
	  // 응답 데이터 처리
	  console.log(data); // 응답 내용 확인 (예: console 출력)
	  console.log("11111111111111");
	  setList(data); // JSON 데이터를 사용하여 데이터 설정
	  // 여기에 응답에 따른 추가적인 작업을 수행할 수 있습니다.
	})
    .catch(error => {
      // 오류 처리
      console.log("2222222222222222");
      console.error('There was a problem with your fetch operation:', error);
    });
    
    function setList(json) {
		console.log(json);
		makeHotPlace();
		if(json.length == 0) searchList = [];
		else {
			searchList = json;
			drawMarker();
		}
        view = document.getElementById("listview");
    }
}

function getLocalData() {
    searchList = JSON.parse(window.localStorage.getItem("hotplace"));
    if(!searchList) searchList = [];
    else drawMarker();
    makeHotPlace();
}

function makeHotPlace() {
    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
        
        if(newMarker) newMarker.setMap(null);

        // 클릭한 위도, 경도 정보를 가져옵니다 
        var latlng = mouseEvent.latLng; 
        
        newMarker = new kakao.maps.Marker({ 
            // 지도 중심좌표에 마커를 생성합니다 
            position: latlng
        });

        newMarker.setMap(map);

        // 마커 위치를 클릭한 위치로 옮깁니다
        //newMarker.setPosition(latlng);
    
        var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
        message += '경도는 ' + latlng.getLng() + ' 입니다';
    
        //var resultDiv = document.getElementById('clickLatlng'); 
        //resultDiv.innerHTML = message;
        console.log(message);
    
    });
}

function setHotPlace(id) {
    if(!newMarker) {
        console.log("마커가 설정되지 않음");
        alert("마커가 설정되지 않았습니다!")
        return;
    }

    title = document.getElementById("titleInput").value;
    desc = document.getElementById("descInput").value;
    cat = document.getElementById("catInput").value;

    if(!title || !desc || !cat) {
        alert("모든 항목을 입력해주세요.")
        return;
    }

    var dic = {}
    dic['title'] = title + "";
    dic['addr1'] = desc + "";
    dic['addr2'] = id;
    dic['tel'] = cat;
    pos = newMarker.getPosition();
    dic['latitude'] = pos.getLat() + "";
    dic['longitude'] = pos.getLng() + "";

    searchList.push(dic);

    drawMarker();
    //saveLocalData();
    
    addMyPlace(dic);

    //초기화
    newMarker.setMap(null);
    newMarker = null;
    document.getElementById("titleInput").value = "";
    document.getElementById("descInput").value = "";
    document.getElementById("catInput").value = "";
}

function addMyPlace(data) {
	var url = "/product/trip?action=addmyplace";
	
	fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
	.then(response => {
	  // 서버로부터의 응답 처리
	  if (!response.ok) {
	    throw new Error('Network response was not ok');
	  }
	  return response.json(); // 응답 내용을 JSON 형식으로 변환하여 반환
	})
	.then(data => {
	  // 응답 데이터 처리
	  console.log(data); // 응답 내용 확인 (예: console 출력)
	  setList(data); // JSON 데이터를 사용하여 데이터 설정
	  // 여기에 응답에 따른 추가적인 작업을 수행할 수 있습니다.
	})
    .catch(error => {
      // 오류 처리
      console.error('There was a problem with your fetch operation:', error);
    });
	
}

function saveLocalData() {
    window.localStorage.setItem("hotplace", JSON.stringify(searchList));
}