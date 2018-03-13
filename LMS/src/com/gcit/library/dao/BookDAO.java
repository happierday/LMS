package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.library.model.Book;

public class BookDAO extends BaseDAO{

	public BookDAO(Connection cnn) {
		super(cnn);
	}

	public List<Book> getALlBook(Integer pageNo) throws SQLException{
		ResultSet rs = null;
		if(pageNo == null) {
			rs = showTables("select * from tbl_book;",null);
		}else {
			rs = showTables("select * from tbl_book limit ?,?;",new Object[] {pageNo * 10, 10});
		}
		return extractBookDetail(rs);
	}
	
	public Integer getBookCount(String search) throws SQLException {
		if(search == null) {
			return getCount("select count(*) from tbl_book;");
		} 
		String s = "%" + search + "%";
		return getCount("select count(*) from tbl_book where title like " + s);
	}
	
	public List<Book> getByPK(Integer bookId) throws SQLException {
		ResultSet rs = showTables("select * from tbl_book where bookId = ?", new Object[] {bookId});
		return extractBookDetail(rs);
	}

	public List<Book> extractBookDetail(ResultSet rs) throws SQLException{
		List<Book> books = new ArrayList<Book>();
		Book book = null;
		AuthorDAO adao = new AuthorDAO(cnn);
		BranchDAO bdao = new BranchDAO(cnn);
		GenreDAO gdao = new GenreDAO(cnn);
		PublisherDAO pdao = new PublisherDAO(cnn);
		while(rs.next()) {
			book = new Book();
			book.setId(rs.getInt(1));
			book.setTitle(rs.getString(2));
			book.setAuthors(adao.getAuthorForBook(book.getId()));
			book.setBranches(bdao.getBranchForBook(book.getId()));
			book.setGenres(gdao.getGenreForBook(book.getId()));
			book.setPublisher(pdao.getPublisherForBook(book.getId()).get(0));
			books.add(book);
		}
		return books;
	}


}
