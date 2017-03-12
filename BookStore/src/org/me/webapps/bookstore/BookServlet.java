// BookServlet.java
// Servlet to return one book's information to the client.
// The servlet produces XML which is transformed with XSL to
// produce the client XHTML page.
package org.me.webapps.bookstore;

// Java core packages
import java.io.*;
import java.util.*;

// Java extension packages
import javax.servlet.*;
import javax.servlet.http.*;

public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 6723471178342776147L;
	
    protected void doGet( HttpServletRequest request,
            HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession( false );
        
        // RequestDispatcher to forward client to bookstore home
        // page if no session exists or no books are selected
        RequestDispatcher dispatcher =
                request.getRequestDispatcher( "/index.html" );
        
        // if session does not exist, forward to index.html
        if ( session == null )
            dispatcher.forward( request, response );
        
        // get books from session object
        List<BookBean> titles =
                ( List<BookBean> ) session.getAttribute( "titles" );
        
        // locate BookBean object for selected book
        Iterator<BookBean> iterator = titles.iterator();
        BookBean book = null;
        
        String isbn = request.getParameter( "isbn" );
        
        while ( iterator.hasNext() ) {
            book = ( BookBean ) iterator.next();
            
            if ( isbn.equals( book.getISBN() ) ) {
                
                // save the book in a session attribute
                session.setAttribute( "bookToAdd", book );
                dispatcher =
                        request.getRequestDispatcher( "/singleBook.jsp" );
                dispatcher.forward( request, response );
            }
        }
        
        // if book is not in list, forward to index.html
        if ( book == null )
            dispatcher.forward( request, response );
        
    }
    
}
