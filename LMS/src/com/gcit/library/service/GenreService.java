package com.gcit.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.library.dao.GenreDAO;
import com.gcit.library.model.Genre;
import com.gcit.library.service.ConnectionUtil;

public class GenreService {
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Genre> getAllGenres(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO bdao = new GenreDAO(conn);
			return bdao.getAllGenres();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
//	public Genre getGenreByPK(Integer genreId) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			GenreDAO bdao = new GenreDAO(conn);
//			return bdao.getByPK(genreId).get(0);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
	
//	public Integer getGenreCount(String search) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			GenreDAO bdao = new GenreDAO(conn);
//			return bdao.getGenreCount(search);
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
