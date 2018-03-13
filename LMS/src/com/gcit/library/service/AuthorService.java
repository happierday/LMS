package com.gcit.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.library.dao.AuthorDAO;
import com.gcit.library.model.Author;
import com.gcit.library.service.ConnectionUtil;

public class AuthorService {
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Author> getAllAuthors(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO bdao = new AuthorDAO(conn);
			return bdao.getAllAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
//	public Author getAuthorByPK(Integer authorId) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			AuthorDAO bdao = new AuthorDAO(conn);
//			return bdao.getByPK(authorId).get(0);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
	
//	public Integer getAuthorCount(String search) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			AuthorDAO bdao = new AuthorDAO(conn);
//			return bdao.getAuthorCount(search);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
}
