/**
 * 
 */
package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public Integer getCount(String sql) throws SQLException {
		PreparedStatement pstmt = cnn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}
}
