<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<div id="loginModal" class="modal fade">
          <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                  <div class="modal-header">
                  <h4 class="modal-title"></h4>
                      <button id = "loginclosebnt"type="button" class="btn-close" data-bs-dismiss="modal"></button>
                  </div>
                  <div class="modal-body">
                      <form id="loginForm" name="loginForm" action="/product/trip?action=login" method="POST">
                      	  <div class="form-group">
                              <label for="userId">아이디</label>
                              <input type="text" class="form-control" id="id" name = "id" placeholder="아이디 입력">
                          </div>
                          <div class="form-group">
                              <label for="password">비밀번호</label>
                              <input type="password" class="form-control" id="pw" name = "pw" placeholder="비밀번호 입력">
                          </div>  
                          <div class = "mt-3 d-flex justify-content-end ">
	                          <button type="button" class="btn btn-outline-info" onclick="logIn()" value="로그인">로그인</button>
		                      <button type="button" class="btn btn-outline-secondary" href="#findPassModal" data-bs-toggle="modal">비밀번호찾기</button>            
	                   
                          </div>
                          </form>
                  </div>
                  <div class="modal-footer">
                      
                  </div>
                  
                  
              </div>
          </div>
	</div>




            <div id="findPassModal" class="modal fade">
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h4 class="modal-title">비밀번호 찾기</h4>
                    <button id="findpclosebnt" type="button" class="btn-close" data-bs-dismiss="modal"></button>
                  </div>
                  <form id="findpassform"action="/product/trip?action=findpass" method="POST">
                  <div class="modal-body">
                    
                      <div class="form-group mb-2">
                        <label for="newuserId">아이디</label>
                        <input type="text" class="form-control signInput" name="id" placeholder="아이디를 입력하세요">
                      </div>
                      <div class="form-group row align-items-center">
                        <label for="email" class="col-sm col-form-label">이메일</label>
                        <div class="col-sm-12">
                          <div class="input-group col-sm-5">
                            <input type="text" class="form-control signInput" placeholder="이메일을 입력하세요" 
                            aria-label="Recipient's email" aria-describedby="basic-addon2" name = 'email'>

                          </div>
                        </div>
                      </div>
                  
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-outline-info" onclick ="findpass()">찾기</button>
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">닫기</button>
                  </div>
                    </form>
                </div>
              </div>
            </div>



      <div id="signupModal" class="modal fade">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">회원가입</h4>
              <button id="newclosebnt" type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">

                <div class="form-group mb-2">
                  <label for="userName">이름</label>
                  <input type="text" class="form-control signInput" id="newName" placeholder="이름을 입력하세요">
                </div>
                <div class="form-group mb-2">
                  <label for="newuserId">아이디</label>
                  <input type="text" class="form-control signInput" id="newId" name="newId"  placeholder="아이디를 입력하세요">
                </div>
                <div class="form-group mb-2">
                  <label for="newPassword">비밀번호</label>
                  <input type="password" class="form-control signInput" id="newPassword" name = "newPassword" placeholder="비밀번호를 입력하세요">
                </div>
                <div class="form-group mb-2">
                  <label for="confirmPassword">비밀번호 확인</label>
                  <input type="password" class="form-control signInput" id="newconfirmPassword" placeholder="비밀번호를 다시 입력하세요">
                </div>
                <div class="form-group row align-items-center">
                  <label for="email" class="col-sm col-form-label">이메일</label>
                  <div class="col-sm-12">
                    <div class="input-group col-sm-5">
                      <input type="text" class="form-control signInput" placeholder="이메일 아이디" 
                      aria-label="Recipient's email" aria-describedby="basic-addon2" id = 'newEmail' name ="newEmail">
                      </div>
                    </div>
                    
         
                  </div>
                  <div class = "mt-3 d-flex justify-content-end ">
                  	  <button type="button" class="btn btn-outline-info" onclick="signUp() ">회원가입</button>
		              <button type="button" class="btn btn-outline-success" onclick="resetForm()">초기화</button>
		              <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">닫기</button>
                  </div>
                  
                     
                  
                  
                </div>

            </div>
            <div class="modal-footer">

            </div>
          </div>
        </div>
      </div>