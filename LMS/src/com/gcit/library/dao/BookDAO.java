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

	public void updateBook(Book book, Object[] genres, Object[] authors, Integer pubId, Object[] branches,
			Object[] copies) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title = ? where bookId = ?", new Object[] {book.getTitle(),book.getId()});
		save("delete from tbl_book_genres where bookId = ?", new Object[] {book.getId()});
		if(genres != null) {
			for(Object i : genres) {
				save("insert into tbl_book_genres values(?,?)", new Object[] {i,book.getId()});
			}
		}
		save("delete from tbl_book_authors where bookId = ?", new Object[] {book.getId()});
		if(authors != null) {
			for(Object i : authors) {
				save("insert into tbl_book_authors values(?,?)", new Object[] {book.getId(),i});
			}
		}
		save("update tbl_book set pubId = ? where bookId = ?", new Object[] {pubId,book.getId()});
		save("delete from tbl_book_copies where bookId = ?", new Object[] {book.getId()});
		if(branches != null) {
			for(Object i : branches) {
				save("insert into tbl_book_copies values(?,?,10)", new Object[] {book.getId(),i});
			}
		}
	}


}
