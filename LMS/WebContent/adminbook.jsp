<%@page import="com.gcit.library.model.Loan"%>
<%@page import="com.gcit.library.model.Branch"%>
<%@page import="com.gcit.library.model.Author"%>
<%@page import="com.gcit.library.model.Genre"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.library.model.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.library.service.BookService"%>
<%@include file="../index.html" %>
<%
BookService bookService = new BookService();
Integer total = bookService.getBookCount(null);
Integer pageSize = 0;
List<Book> books = null;
if(request.getAttribute("books") == null){
	books = bookService.getAllBooks(0);
}else {
	books = (List<Book>)request.getAttribute("books");
}

if(total % 10 == 0){
	pageSize = total / 10;
}else {
	pageSize = total / 10 + 1;
}
List<Loan> loans = null;
if(request.getAttribute("loans") != null){
	loans = (List<Loan>)request.getAttribute("loans");
}
%>
<script>
	function searchAuthors(){
		$.ajax({
			  method: "POST",
			  url: "searchBooks",
			  data: { "searchString": $('#searchString').val() 
				}
		}).done(function( data ) {
			$('#bookTable').html(data);
		});
	}
</script>
<div class = "container">
	<div class="jumbotron">
	<div class = "row  show-hide-message">
        <div class ="<%=request.getAttribute("messageClass")%>">
            ${ message }
        </div>
    </div>
    <%if(loans != null) {%>
    		<p>This book is loaned out, can't delete it until it is returned</p>
    		<table class="table able-striped">
		 	<tr>
			    <th>ID</th>
			    <th>Title</th>
			    <th>Borrower</th>
			    <th>Branch</th>
			    <th>DateOut</th>
			    <th>DueDate</th>
			    <th>DateIn</th>
		 	</tr>
	    		<%for(Loan b: loans) {%>
	    			<tr>
		    			<td><%=loans.indexOf(b)+1 %></td>
		    			<td><%=b.getBookTitle() %></td>
		    			<td><%=b.getBorrowerName() %></td>
		    			<td><%=b.getBranchName() %></td>
		    			<td><%=b.getDateOut() %></td>	
		    			<td><%=b.getDueDate()%></td>
		    			<td></td>
	    			</tr>
	    		<%} %>
		</table>
		<br>
		<hr>
    <%} %>
    <p>Book Menu</p>
	<table class="table table-striped">
	 	<tr>
		    <th>ID</th>
		    <th>Title</th>
		    <th>Genre</th>
		    <th>Author</th>
		    <th>Branch</th>
		    <th>Publisher</th>
		    <th>Edit</th>
		    <th>Delete</th>
	 	</tr>
    		<%for(Book b: books) {%>
    			<tr>
	    			<td><%=books.indexOf(b)+1 %></td>
	    			<td><%=b.getTitle() %></td>
	    			<td>
	    				<%for(Genre g: b.getGenres()){ %>
	    					<%=g.getName()%><br>
	    				<% }%>
	    			</td>
	    			<td>
	    				<%for(Author a: b.getAuthors()) {%>
	    					<%=a.getName()%><br>
	    				<%}%>
	    			</td>
	    			<td>
	    				<%for(Branch br: b.getBranches()) { %>
	    					<%=br.getName() + "has " + br.getCopies()+ " copies"%><br>
	    				<%}%>
	    			</td>	
	    			<td><%=b.getPublisher().getName()%></td>
	    			
				<td><button class="btn btn-info"
						href="editbook.jsp?bookId=<%=b.getId()%>"
						data-toggle="modal" data-target="#EditModal">Edit</button></td>
				<td><button class="btn btn-danger"
						onclick="javascript:location.href='deletebook?bookId=<%=b.getId()%>'">Delete</button></td>
    			</tr>
    		<%} %>
	</table>
	
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" id="EditModal">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content"></div>
		</div>
	</div>
	<center>
		<nav aria-label="Page navigation example">
		  <ul class="pagination">
		  	<% for(int i = 1; i<= pageSize; i++) {%>
		    		<li class="page-item"><a class="page-link" href="bookpage?pageNo=<%=i%>"><%=i %></a></li>
		    <%} %>
		  </ul>
		</nav>
	</center>
	 </div>
</div>
<script>
	$(document).ready(function() {

		$('.modal').on('hidden.bs.modal', function(e) {
			$(this).removeData();
		});
	});
</script>