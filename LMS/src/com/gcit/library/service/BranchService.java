package com.gcit.library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.library.dao.BranchDAO;
import com.gcit.library.model.Branch;
import com.gcit.library.service.ConnectionUtil;

public class BranchService {
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public List<Branch> getAllBranchs(Integer pageNo) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			return bdao.getAllBranches();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}
	
//	public Branch getBranchByPK(Integer branchId) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			BranchDAO bdao = new BranchDAO(conn);
//			return bdao.getByPK(branchId).get(0);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return null;
//	}
	
//	public Integer getBranchCount(String search) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = connUtil.getConnection();
//			BranchDAO bdao = new BranchDAO(conn);
//			return bdao.getBranchCount(search);
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
