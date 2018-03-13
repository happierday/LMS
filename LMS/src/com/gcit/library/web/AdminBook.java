package com.gcit.library.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.library.model.Book;
import com.gcit.library.service.BookService;

/**
 * Servlet implementation class AdminBook
 */
@WebServlet({"/bookpage","/editbook","/addbook"})
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
				print(request.getParameterValues("branchId"));
				System.out.println(request.getParameterValues("branchId").length);
				print(request.getParameterValues("noOfCopies"));
				System.out.println(request.getParameterValues("noOfCopies").length);
//				Book book = new Book();
//				book.setId(Integer.parseInt(request.getParameter("bookId")));
//				book.setTitle(request.getParameter("title"));
//				Object [] genres = getObject(request.getParameterValues("genres"));
//				Object [] authors = getObject(request.getParameterValues("authors"));
//				Integer pubId = Integer.parseInt(request.getParameter("publisher"));
//				Object [] branches = getObject(request.getParameterValues("branches"));
//				Object [] copies = null;
//				try {
//					bookService.updateBook(book,genres,authors,pubId,branches,copies);
//					request.setAttribute("message", "Edit Book Successful");
//					request.setAttribute("messageClass", "alert alert-success");
//				} catch (SQLException e) {
//					request.setAttribute("message", "Edit Book Failed");
//					request.setAttribute("messageClass", "alert alert-danger");
//				}
				break;
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	public Object[] getObject(String [] arr) {
		Object[] object = null;
		if(arr.length!=0) {
			object = new Object[arr.length];
			int index = 0;
			for(String s: arr) {
				object[index] = Integer.parseInt(s);
				index++;
			}
		}
		return object;
	}
	
	public void print(String []arr) {
		int index = 1;
		for(String s: arr) {
			System.out.print("index " +index + ": "+ s + " ");
			index ++;
		}
		System.out.println("");
	}
}
