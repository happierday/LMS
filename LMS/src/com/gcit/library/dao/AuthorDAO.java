package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gcit.library.model.Author;

public class AuthorDAO extends BaseDAO{

	public AuthorDAO(Connection cnn) {
		super(cnn);
	}

	public List<Author> getAuthorForBook(Integer bookId) throws SQLException{
		ResultSet rs = showTables("select author.authorId, author.authorName from tbl_author author\n" + 
				"join tbl_book_authors authors on author.authorId = authors.authorId\n" + 
				"where authors.bookId = ?", new Object[] {bookId});
		return extractAuthorOnly(rs);
	}

	private List<Author> extractAuthorOnly(ResultSet rs) throws SQLException {
		List<Author> authors = new LinkedList<Author>();
		Author author = null;
		while(rs.next()) {
			author = new Author();
			author.setId(rs.getInt(1));
			author.setName(rs.getString(2));
			authors.add(author);
		}
		return authors;
	}
	
}
