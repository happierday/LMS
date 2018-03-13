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
	    				<%for(Genre g: b.getGenres()){ 
	    					out.println(g.getName() + " | ");
	    				}%>
	    			</td>
	    			<td>
	    				<%for(Author a: b.getAuthors()) {
	    					out.println(a.getName() + " | ");
	    				}%>
	    			</td>
	    			<td>
	    				<%for(Branch br: b.getBranches()) {
	    					out.println(br.getName() + " | ");
	    				}%>
	    			</td>	
	    			<td><%=b.getPublisher().getName()%></td>
	    			
				<td><button class="btn btn-info"
						href="editbook.jsp?bookId=<%=b.getId()%>"
						data-toggle="modal" data-target="#EditModal">Edit</button></td>
				<td><button class="btn btn-danger"
						onclick="javascript:location.href='deleteBook?bookId=<%=b.getId()%>'">Delete</button></td>
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