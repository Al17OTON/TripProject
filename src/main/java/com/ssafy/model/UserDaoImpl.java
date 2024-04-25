package com.ssafy.model;

import java.sql.Connection;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssafy.dto.User;
import com.ssafy.util.DBUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean userVerify(String id, String pw) throws Exception {

		Connection conn = DBUtil.getConnection();
		String sql = "select exists(select * from users where id = ? and pw = ? )";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		stmt.setString(2, pw);
		
		ResultSet rs = stmt.executeQuery();
		boolean res = false;
		
		if(rs.next()) res = rs.getBoolean(1);
		
		conn.close();
		
		return res;
		
	}

	@Override
	public void addUser(String id, String pw, String email, String name) throws Exception {

		Connection conn = DBUtil.getConnection();
		String sql = "insert into users (id, pw, email, name) "
				+ " values(?, ?, ?, ?) ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		stmt.setString(2, pw);
		stmt.setString(3, email);
		stmt.setString(4, name);
		stmt.executeUpdate();
		conn.close();
	}

	@Override
	public void deleteUser(String id, String pw) throws Exception {

		Connection conn = DBUtil.getConnection();
		String sql = "delete from users where id = ? and pw = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		
		pstmt.executeUpdate();
		conn.close();
		
	

		
	}

	@Override
	public User userById(String id) throws Exception {
		
		Connection conn = DBUtil.getConnection();
		String sql = "select id, pw, email from users where id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		User u = new User();
		if(rs.next()) {
			u.setPw(rs.getString("pw"));
			u.setId(rs.getString("id")) ;
			u.setEmail(rs.getString("email"));
		}
		conn.close();

		return u;
	}

	@Override
	public void modifyUser(String id, String pw, String email) throws Exception {

		Connection conn = DBUtil.getConnection();
		String sql = "update users set pw=?, email=? where id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, email);
		pstmt.setString(3, id);
		pstmt.executeUpdate();
		conn.close();
		return;
		
		
	}

	@Override
	public String findPass(String id, String email) throws Exception {
		Connection conn = DBUtil.getConnection();
		String sql = "select pw from users where id = ? and email = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		stmt.setString(2, email);
		ResultSet rs = stmt.executeQuery();
		String pw = null;
		if(rs.next()) pw = rs.getString("pw");
		conn.close();
		return pw;
	}

	@Override
	public boolean userExist(String id) throws Exception {
		Connection conn = DBUtil.getConnection();
		String sql = "select exists(select * from users where id = ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, id);
		
		ResultSet rs = stmt.executeQuery();
		boolean res = false;
		
		if(rs.next()) res = rs.getBoolean(1);
		
		conn.close();
		
		return res;
	}
	@Override
	public void replaceId(String id) throws SQLException {
//		System.out.println("replaceIdreplaceIdreplaceIdreplaceId");
		
		Connection conn = DBUtil.getConnection();
		String sql = "update board set user_id=? where user_id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "탈퇴한 유저");
		stmt.setString(2, id );
		stmt.executeUpdate();
		conn.close();
		return;
	}
//	public String encrypt(String plainText) {
//		String key = "aesEncryptionKey";
//        try {
//            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public String decrypt(String encryptedText) {
//    	String key = "aesEncryptionKey";
//        try {
//            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
//            return new String(decryptedBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
	

}
