<%@page import="com.gcit.library.service.AuthorService"%>
<%@page import="com.gcit.library.service.PublisherService"%>
<%@page import="com.gcit.library.service.GenreService"%>
<%@page import="com.gcit.library.service.BranchService"%>
<%@page import="com.gcit.library.model.Publisher"%>
<%@page import="com.gcit.library.model.Author"%>
<%@page import="com.gcit.library.model.Genre"%>
<%@page import="com.gcit.library.model.Branch"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="com.gcit.library.service.BookService"%>
<%
BookService bookService = new BookService();
BranchService branchService = new BranchService();
GenreService genreService = new GenreService();
PublisherService publisherService = new PublisherService();
AuthorService authorService = new AuthorService();
Integer bookId = Integer.parseInt(request.getParameter("bookId"));
Book book = bookService.getBookByPK(bookId);
List<Branch> bookBranch = book.getBranches();
List<Genre> bookGenre = book.getGenres();
List<Author> bookAuthor = book.getAuthors();
Publisher pub = book.getPublisher();
List<Branch> branches = branchService.getAllBranchs(null);
List<Genre> genres = genreService.getAllGenres(null);
List<Author> authors = authorService.getAllAuthors(null);
List<Publisher> pubs = publisherService.getAllPublishers(null);
int index = 0;
%>
<div class = "container">
<div class="jumbotron">
	<h2>Edit Book</h2>
	<form action="editbook" method="post">
		<div class="panel panel-primary">
			<div class="panel-heading">Edit Author</div>
			<div class="panel-body">
				<input type = "hidden" name = "bookId" value = "<%=bookId %>">
				BookTitle : <input value = "<%=book.getTitle() %>" name = "title"><br/>
		  	</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Edit Author</div>
			<div class="panel-body">
				<select multiple name = "authors" size = "5">
			    		<%for(Author a: authors) { %>
			    			<%if(bookAuthor.contains(a)) {%>
			    				<option selected value = "<%=a.getId()%>"><%=a.getName() %></option>
			    			<%} else {%>
			    				<option value = "<%=a.getId()%>"><%=a.getName() %></option>
			    			<%} %>
			    		<%} %>
		    		</select>
		  	</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Edit Genre</div>
			<div class="panel-body">
				<select multiple name = "genres" size = "5">
			    		<%for(Genre a: genres) {%>
			    			<%if(bookGenre.contains(a)) {%>
			    				<option selected value = "<%=a.getId()%>"><%=a.getName() %></option>
			    			<%} else {%>
			    				<option value = "<%=a.getId()%>"><%=a.getName() %></option>
			    			<%} %>
			    		<%} %>
		    		</select>
		  	</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Edit Branch</div>
			<div class="panel-body">
				<table class="table table-hover">
	  				<tr>
					    <th>Branch</th>
					    <th>Number of Copies</th>
				 	</tr>
				 	<%for(Branch b: branches) {%>
				 		<tr>
				 			<th><%=b.getName() %></th>
				 			<%if(bookBranch.contains(b)) {%>
			 					<th><input name = "noOfCopies" value = "<%=bookBranch.get(bookBranch.indexOf(b)).getCopies()%>"></th>
			 				<%} else {%>
			 					<th><input name = "noOfCopies" value = ""></th>
			 				<%} %>
			 				<input type = "hidden" name = "branchId" value = "<%=b.getId()%>">
			    			</tr>
			    			<%index++; %>
			    		<%} %>
				</table>
		  	</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">Edit Publisher</div>
			<div class="panel-body">
				<select name = "publisher" size = "5">
			    		<%for(Publisher a: pubs) {%>
			    			<%if(a.equals(pub)) {%>
			    				<option selected value = "<%=a.getId()%>"><%=a.getName() %></option>
			    			<%} else {%>
			    				<option value = "<%=a.getId()%>"><%=a.getName()%></option>
			    			<%} %>
			    		<%} %>
		    		</select>
		  	</div>
		</div>
		<div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="submit" class="btn btn-primary">Save changes</button>
      	</div>
	</form>
</div>
</div>