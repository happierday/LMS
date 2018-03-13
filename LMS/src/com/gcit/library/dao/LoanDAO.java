package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.library.model.Book;
import com.gcit.library.model.Branch;
import com.gcit.library.model.Loan;

public class LoanDAO extends BaseDAO{

	public LoanDAO(Connection cnn) {
		super(cnn);
	}

	public List<Loan> getLoanForBook(Book book) throws SQLException {
		ResultSet rs = null;
		List<Loan> loans = new ArrayList<Loan>();
		for(Branch b : book.getBranches()) {
			rs = showTables("select  \n" + 
					"loan.bookId, loan.branchId, loan.cardNo, book.title, branch.branchName, borrower.name, loan.dateOut, loan.dueDate\n" + 
					"from tbl_book_loans loan\n" + 
					"join tbl_book book on book.bookId = loan.bookId\n" + 
					"join tbl_library_branch branch on branch.branchId = loan.branchId\n" + 
					"join tbl_borrower borrower on borrower.cardNo = loan.cardNo\n" + 
					"where loan.bookId = ? and loan.branchId = ? and loan.dateIn is null; ", new Object[] {book.getId(),b.getId()});
			List<Loan> temp = extractData(rs);
			if(temp.size() != 0) {
				loans.addAll(temp);
			}
		}
		return loans;
	}
	
	public List<Loan> extractData(ResultSet rs) throws SQLException{
		List<Loan> loans = new ArrayList<Loan>();
		Loan loan = null;
		while(rs.next()) {
			loan = new Loan();
			loan.setBookId(rs.getInt(1));
			loan.setBranchId(rs.getInt(2));
			loan.setCardNo(rs.getInt(3));
			loan.setBookTitle(rs.getString(4));
			loan.setBranchName(rs.getString(5));
			loan.setBorrowerName(rs.getString(6));
			loan.setDateOut(rs.getDate(7).toLocalDate());
			loan.setDueDate(rs.getDate(8).toLocalDate());
			loans.add(loan);
		}
		return loans;
	}
}
