package com.ssafy.model;

import com.ssafy.dto.User;

public interface UserService {
	public void addUser(String id, String pw, String email, String name) throws Exception;
	public void deleteUser(String id, String pw) throws Exception;
	public boolean verifyUser(String id, String pw) throws Exception;
	public User userById(String id)throws Exception;
	public void modifyUser(String id, String pw, String email) throws Exception;
	public String findPass(String id, String email) throws Exception;
	public boolean userExist(String id) throws Exception;
}
