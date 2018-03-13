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
		ResultSet rs = showTables("select publisher.publisherId, publisher.publisherName from tbl_publisher publisher\n" + 
				"join tbl_book book on publisher.publisherId = book.publisherId\n" + 
				"where book.bookId = ?", new Object[] {bookId});
		return extractPublisherOnly(rs);
	}

	private List<Publisher> extractPublisherOnly(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new LinkedList<Publisher>();
		Publisher publisher = null;
		while(rs.next()) {
			publisher = new Publisher();
			publisher.setId(rs.getInt(1));
			publisher.setName(rs.getString(2));
			publishers.add(publisher);
		}
		return publishers;
	}
}
