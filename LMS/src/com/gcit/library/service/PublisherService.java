package com.gcit.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.library.dao.PublisherDAO;
import com.gcit.library.model.Publisher;
import com.gcit.library.service.ConnectionUtil;

public class PublisherService {
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Publisher> getAllPublishers(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO bdao = new PublisherDAO(conn);
			return bdao.getAllPublishers();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
//	public Publisher getPublisherByPK(Integer publisherId) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			PublisherDAO bdao = new PublisherDAO(conn);
//			return bdao.getByPK(publisherId).get(0);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
	
//	public Integer getPublisherCount(String search) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			PublisherDAO bdao = new PublisherDAO(conn);
//			return bdao.getPublisherCount(search);
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
