package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gcit.library.model.Genre;

public class GenreDAO extends BaseDAO{

	public GenreDAO(Connection cnn) {
		super(cnn);
	}

	public List<Genre> getGenreForBook(Integer bookId) throws SQLException{
		ResultSet rs = showTables("select genre.genre_Id, genre.genreName from tbl_genre genre\n" + 
				"join tbl_book_genres genres on genre.genre_Id = genres.genre_Id\n" + 
				"where genres.bookId = ?", new Object[] {bookId});
		return extractGenreOnly(rs);
	}

	private List<Genre> extractGenreOnly(ResultSet rs) throws SQLException {
		List<Genre> genres = new LinkedList<Genre>();
		Genre genre = null;
		while(rs.next()) {
			genre = new Genre();
			genre.setId(rs.getInt(1));
			genre.setName(rs.getString(2));
			genres.add(genre);
		}
		return genres;
	}
	
}
