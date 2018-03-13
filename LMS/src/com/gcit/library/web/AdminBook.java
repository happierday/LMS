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

import com.gcit.library.model.Author;
import com.gcit.library.model.Book;
import com.gcit.library.model.Branch;
import com.gcit.library.model.Genre;
import com.gcit.library.model.Loan;
import com.gcit.library.service.BookService;

/**
 * Servlet implementation class AdminBook
 */
@WebServlet({"/bookpage","/editbook","/addbook","/deletebook","/searchbooks"})
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
						request.setAttribute("message", "This book was loaned out from this branch and didn't returned!");
						request.setAttribute("messageClass", "alert alert-danger");
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
		Boolean isAJAX = Boolean.FALSE;
		switch(reqUrl) {
			case "/bookpage":
				Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
				try {
					List<Book> books = bookService.getAllBooks(pageNo-1);
					request.setAttribute("books", books);
				} catch (SQLException e1) {
					request.setAttribute("message", "Search Book Failed!");
					request.setAttribute("messageClass", "alert alert-danger");
				}
				break;
			case "/searchbooks":
				try {
					String search = request.getParameter("searchString");
					List<Book> books = bookService.getBookByName(search,0);
					System.out.println(books.size());
					request.setAttribute("total", books.size());
					StringBuffer strBuf = new StringBuffer();
					strBuf.append(
							"<table class='table table-striped'><tr><th>ID</th><th>Title</th><th>Genre</th><th>Author</th><th>Branch</th><th>Publisher</th><th>Edit</th><th>Delete</th></tr>");
					for (Book a : books) {
						strBuf.append("<tr><td>" + (books.indexOf(a) + 1) + "</td><td>" + a.getTitle() + "</td><td>");
						for (Genre g : a.getGenres()) {
							strBuf.append(g.getName() + "<br>");
						}
						strBuf.append("</td><td>");
						for (Author g : a.getAuthors()) {
							strBuf.append(g.getName() + "<br>");
						}
						strBuf.append("</td><td>");
						for (Branch b : a.getBranches()) {
							strBuf.append(b.getName() + " has " + b.getCopies() + "<br>");
						}
						strBuf.append("</td><td>"+a.getPublisher().getName() + "<br></td>");
						strBuf.append(
								"</td><td><button class='btn btn-warning' href='editbook.jsp?bookId=" + a.getId()
										+ "' data-toggle='modal' data-target='#editModal'>Edit</button></td>");
						strBuf.append(
								"<td><button class='btn btn-danger' onclick='javascript:location.href='deletebook?bookId="
										+ a.getId() + "''>Delete</button></td></tr>");
					}
					strBuf.append("</table>");
					strBuf.append("@");
					response.getWriter().write(strBuf.toString());
					isAJAX = Boolean.TRUE;
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		if (!isAJAX) {
			RequestDispatcher rd = request.getRequestDispatcher("/adminbook.jsp");
			rd.forward(request, response);
		}
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
