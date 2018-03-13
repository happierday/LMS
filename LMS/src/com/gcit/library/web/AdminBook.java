package com.gcit.library.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.library.model.Book;
import com.gcit.library.model.Branch;
import com.gcit.library.model.Loan;
import com.gcit.library.service.BookService;

/**
 * Servlet implementation class AdminBook
 */
@WebServlet({"/bookpage","/editbook","/addbook","/deletebook"})
public class AdminBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private String forward = "adminbook.jsp";
     private BookService bookService = new BookService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch(reqUrl) {
			case "/deletebook":
				Integer bookId = Integer.parseInt(request.getParameter("bookId"));
				try {
					List<Loan> loans = bookService.isReturned(bookId);
					if(loans.size() > 0) {
						request.setAttribute("loans", loans);
						break;
					}else {
						bookService.deleteBookByPK(bookId);
						request.setAttribute("message", "Delete book successful");
						request.setAttribute("messageClass", "alert alert-success");
					}
				} catch (SQLException e) {
					request.setAttribute("message", "Delete book failed");
					request.setAttribute("messageClass", "alert alert-danger");
				}
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch(reqUrl) {
			case "/bookpage":
				break;
			case "/editbook":
				Book book = new Book();
				book.setId(Integer.parseInt(request.getParameter("bookId")));
				book.setTitle(request.getParameter("title"));
				Integer [] genres = getObject(request.getParameterValues("genres"));
				Integer [] authors = getObject(request.getParameterValues("authors"));
				Integer pubId = Integer.parseInt(request.getParameter("publisher"));
				Integer [] branchId = getObject(request.getParameterValues("branchId"));
				Integer [] copies = getObject(request.getParameterValues("noOfCopies"));
				List<Branch> branch = getBranchWithCopy(branchId, copies);
				try {
					bookService.updateBook(book,genres,authors,pubId,branch);
					request.setAttribute("message", "Edit Book Successful");
					request.setAttribute("messageClass", "alert alert-success");
				} catch (SQLException e) {
					request.setAttribute("message", "Edit Book Failed");
					request.setAttribute("messageClass", "alert alert-danger");
				}
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	public Integer[] getObject(String [] arr) {
		Integer[] object = null;
		if(arr.length!=0) {
			object = new Integer[arr.length];
			int index = 0;
			for(String s: arr) {
				if(s.length() == 0) {
					object[index] = -1;
				}else {
					object[index] = Integer.parseInt(s);
				}
					index++;
			}
		}
		return object;
	}
	
	public List<Branch> getBranchWithCopy(Integer [] branchId, Integer[]copy){
		List<Branch> branches = new ArrayList<Branch>();
		Branch branch = null;
		for(int i = 0; i< copy.length; i++) {
			if(copy[i] != -1) {
				branch = new Branch();
				branch.setId(branchId[i]);
				branch.setCopies(copy[i]);
				System.out.println(branch.toString());
				branches.add(branch);
			}
		}
		return branches;
	}
	
	public void print(String []arr) {
		int index = 1;
		for(String s: arr) {
			System.out.print("index " +index + ": "+ s.length() + " ");
			index ++;
		}
		System.out.println("");
	}
}
