package com.gcit.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.gcit.library.model.Branch;

public class BranchDAO extends BaseDAO{

	public BranchDAO(Connection cnn) {
		super(cnn);
	}

	public List<Branch> getBranchForBook(Integer bookId) throws SQLException{
		ResultSet rs = showTables("select branch.branchId, branch.branchName, branch.branchAddress, copy.noOfCopies from tbl_library_branch branch\n" + 
				"join tbl_book_copies copy on branch.branchId = copy.branchId\n" + 
				"where copy.bookId = ?", new Object[] {bookId});
		return extractBranchWithCopy(rs);
	}

	public List<Branch> getAllBranches() throws SQLException{
		ResultSet rs = showTables("select * from tbl_library_branch;",null);
		return extractBranchOnly(rs);
	}
	
	private List<Branch> extractBranchOnly(ResultSet rs) throws SQLException {
		List<Branch> branchs = new LinkedList<Branch>();
		Branch branch = null;
		while(rs.next()) {
			branch = new Branch();
			branch.setId(rs.getInt(1));
			branch.setName(rs.getString(2));
			branch.setAddress(rs.getString(3));
			branchs.add(branch);
		}
		return branchs;
	}
	
	private List<Branch> extractBranchWithCopy(ResultSet rs) throws SQLException {
		List<Branch> branchs = new LinkedList<Branch>();
		Branch branch = null;
		while(rs.next()) {
			branch = new Branch();
			branch.setId(rs.getInt(1));
			branch.setName(rs.getString(2));
			branch.setAddress(rs.getString(3));
			branch.setCopies(rs.getInt(4));
			branchs.add(branch);
		}
		return branchs;
	}
}
