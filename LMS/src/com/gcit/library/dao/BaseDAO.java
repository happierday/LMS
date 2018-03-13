/**
 * 
 */
package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author gcit
 *
 */
public class BaseDAO {
	protected static Connection cnn = null;
	
	public BaseDAO(Connection cnn) {
		this.cnn = cnn;
	}
	
	public ResultSet showTables(String sql, Object[]vals) throws SQLException {
		PreparedStatement pstmt = cnn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o:vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		return pstmt.executeQuery();
	}
	
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = cnn.prepareStatement(sql);
		if(vals!=null){
			int count = 1;
			for(Object o:vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
	}
	
	public Integer getCount(String sql, Object[]values) throws SQLException {
		PreparedStatement pstmt = cnn.prepareStatement(sql);
		if(values!=null){
			int count = 1;
			for(Object o:values){
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}
	
	public Integer addGetPK(String sql, Object[]values) throws SQLException {
		PreparedStatement pstmt = cnn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		if(values!=null){
			int count = 1;
			for(Object o:values){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}
}
