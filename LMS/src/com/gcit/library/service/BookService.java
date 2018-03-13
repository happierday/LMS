package com.gcit.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.library.dao.BookDAO;
import com.gcit.library.model.Book;
import com.gcit.library.service.ConnectionUtil;

public class BookService {
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Book> getAllBooks(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getALlBook(pageNo);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
	public Integer getBookCount(String search) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getBookCount(search);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
}
