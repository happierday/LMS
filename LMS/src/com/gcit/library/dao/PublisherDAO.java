package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gcit.library.model.Publisher;
import com.gcit.library.model.Publisher;

public class PublisherDAO extends BaseDAO{

	public PublisherDAO(Connection cnn) {
		super(cnn);
	}
	
	public List<Publisher> getPublisherForBook(Integer bookId) throws SQLException{
		ResultSet rs = showTables("select * from tbl_publisher publisher\n" + 
				"join tbl_book book on publisher.publisherId = book.pubId\n" + 
				"where book.bookId = ?", new Object[] {bookId});
		return extractPublisherOnly(rs);
	}

	public List<Publisher> getAllPublishers() throws SQLException{
		ResultSet rs = showTables("select * from tbl_publisher",null);
		return extractPublisherOnly(rs);
	}
	
	private List<Publisher> extractPublisherOnly(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new LinkedList<Publisher>();
		Publisher publisher = null;
		while(rs.next()) {
			publisher = new Publisher();
			publisher.setId(rs.getInt(1));
			publisher.setName(rs.getString(2));
			publisher.setAddress(rs.getString(3));
			publisher.setPhone(rs.getString(4));
			publishers.add(publisher);
		}
		return publishers;
	}
}
