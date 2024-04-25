//getKey();   // 키 정보 로드하기

const URL = "http://apis.data.go.kr/B551011/KorService1";
const APIKEY = "cxm37jlz8edZALZGCpImXt1gr6wT9am%2BL8GI4DkJ4R5yWjIW4ekGOJaO%2B4upx9P0B2b4m%2B3b8WTCs3mqT6S0jA%3D%3D";
let searchList; //공공 데이터를 불러온다
setCitySelect();
// function getKey() {
//     var key = fetch("../key.json")
//                 .then(res => res.json())
//                 .then(parse)
//                 .catch(()=>console.log("Fail to load key.json."));
// }

// // 로드한 json 키 정보를 변수에 할당하기
// function parse(obj) {
//     APIKEY = obj.KEYS.APIKEY;

//     setCitySelect(); //시 . 도 정보 세팅
// }

//API KEY가 발급되면 지역코드를 불러온다.
function setCitySelect() {
    areaUrl =
    "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
    APIKEY +
    "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
    
    console.log(areaUrl);

    fetch(areaUrl, { method: "GET" })
        .then((response) => response.json())
        .then((data) => makeOption(data));
    function makeOption(data) {
    let areas = data.response.body.items.item;
    // console.log(areas);
    let sel = document.getElementById("cities");
        areas.forEach((area) => {
            let opt = document.createElement("option");
            opt.setAttribute("value", area.code);
            opt.appendChild(document.createTextNode(area.name));

            sel.appendChild(opt);
        });
    }
}

//도시, 도가 선택되면 해당 지역의 하위 정보를 select에다가 넣어준다.
function setSubCity() {
    selected = document.getElementById('cities').selectedOptions[0].value;

    let sel = document.getElementById("subcity");
    if(selected == '0') {
        sel.innerHTML = "<option value=\"0\" selected>구 군</option>";
        return;
    }

    areaUrl =
    "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
    APIKEY +
    "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&areaCode=" + selected;
    
    fetch(areaUrl, { method: "GET" })
        .then((response) => response.json())
        .then((data) => makeOption(data));
    function makeOption(data) {
    let areas = data.response.body.items.item;
    // console.log(areas);
    sel.innerHTML = "";         //새로운 값을 위해 비워주기
        areas.forEach((area) => {
            let opt = document.createElement("option");
            opt.setAttribute("value", area.code);
            opt.appendChild(document.createTextNode(area.name));

            sel.appendChild(opt);
        });
    }
}


function locationBasedList1(mapX, mapY, radius) {

}

//선택된 지역코드를 기반으로 검색을 한다.

//수정드갑니다
function areaBasedList() {
    //type = document.getElementById('keywords').selectedOptions[0].value;
    //areaCode = document.getElementById('cities').selectedOptions[0].value;
    //sigunguCode = document.getElementById('subcity').selectedOptions[0].value;
    // console.log(type);
    // console.log(areaCode);
    // console.log(sigunguCode);
    
        // POST 요청을 보낼 URL 설정
    var url = "/product/trip?action=selectcity"; 
	console.log("clickckckckckkckc")
    // POST 요청에 사용할 데이터 수집
    var cityId = document.getElementById('cities').selectedOptions[0].value;
    var subCityId = document.getElementById('subcity').selectedOptions[0].value;
    var keywordId = document.getElementById('keywords').selectedOptions[0].value;


    if(cityId == '0' || subCityId == '0') {
        alert("지역을 선택해 주세요.")
        console.log("지역을 선택하지 않음");
        return;
    }
    console.log(cityId);
     console.log(subCityId);
      console.log(keywordId);
    
    var data = {
      cityId: cityId,
      subCityId: subCityId,
      keywordId: keywordId
    };

    // fetch API를 사용하여 POST 요청 보내기
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
        view = document.getElementById("listview");
        searchList = json;



        //console.log(searchList[0]["title"]);

        //drawMarkerTEST();
        //drawCustomMarker();
        drawMarker();
    }
    
}


//관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID
function searchKeyword1(keyword) {
    type = document.getElementById('keywords').selectedOptions[0].value;
    areaCode = document.getElementById('cities').selectedOptions[0].value;
    sigunguCode = document.getElementById('subcity').selectedOptions[0].value;
    // console.log(type);
    // console.log(areaCode);
    // console.log(sigunguCode);

    if(areaCode == '0' || sigunguCode == '0') {
        console.log("지역을 선택하지 않음");
        return;
    }

    url = getURL("searchKeyword1") + "&keyword=" + keyword +"&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode;
    if(type != '0') url += "&contentTypeId=" + type;

    //console.log(url);

    fetch(url)
        .then(res => res.json())
        .then(json => console.log(json));

}











// API 이름을 주면 기본으로 들어가는 쿼리들을 붙여서 반환한다.
// API 키, OS, 앱이름, json 여부
function getURL(APINAME) {
    return URL + "/" + APINAME + "?serviceKey=" + APIKEY + "&MobileOS=ETC&MobileApp=HAPPLE&_type=json";
}

// function test() {
//     var testurl = URL + "/searchKeyword1?serviceKey=" + APIKEY + "&MobileOS=ETC&MobileApp=TEST&keyword=호텔&_type=json";
//     fetch(testurl)
//         .then(res => res.json())
//         .then(j => {
//             console.log(j);
//         });
// }

// function getAreaCode() {
//     url = getURL("areaCode1");

//     fetch(url)
//         .then(res => res.json())
//         .then(setCitySelect)
//         ;
// }