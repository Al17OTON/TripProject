package com.ssafy.model;

import com.ssafy.dto.User;

public class UserServiceImpl implements UserService {
	
	UserDaoImpl userdao = new UserDaoImpl();
	@Override
	public void addUser(String id, String pw, String email, String name) throws Exception {
		if(verifyUser(id, pw)) return;	//이미 있는 유저면 리턴
		userdao.addUser(id, pw, email, name);
	}

	@Override
	public void deleteUser(String id, String pw) throws Exception {
		if(!verifyUser(id, pw)) return; //유저가 존재하지 않는다면 리턴
		userdao.replaceId(id);
		userdao.deleteUser(id, pw);
	}

	@Override
	public boolean verifyUser(String id, String pw) throws Exception {
		return userdao.userVerify(id, pw);
	}

	@Override
	public User userById(String id) throws Exception {
		
		return userdao.userById(id);
	}

	@Override
	public void modifyUser(String id, String pw, String email) throws Exception {
		userdao.modifyUser(id, pw, email);
		
	}

	@Override
	public String findPass(String id, String email) throws Exception {
		return userdao.findPass(id, email);
	}

	@Override
	public boolean userExist(String id) throws Exception {
		return userdao.userExist(id);
	}

}
