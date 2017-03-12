// AddToCartServlet.java
// Servlet to add a book to the shopping cart.
package org.me.webapps.bookstore;

// Java core packages
import java.io.*;
import java.util.*;

// Java extension packages
import javax.servlet.*;
import javax.servlet.http.*;

public class AddToCartServlet extends HttpServlet {
   private static final long serialVersionUID = 6723471178342776147L;
	
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
   protected void doPost( HttpServletRequest request,
      HttpServletResponse response )
      throws ServletException, IOException
   {
      HttpSession session = request.getSession( false );
      RequestDispatcher dispatcher;
      
      // if session does not exist, forward to index.html
      if ( session == null ) {
         dispatcher = 
            request.getRequestDispatcher( "/index.html" );
         dispatcher.forward( request, response );
      }
      
      // session exists, get cart HashMap and book to add
      Map<String,CartItemBean> cart =  (Map<String,CartItemBean>) session.getAttribute( "cart" );
      
      BookBean book = 
         ( BookBean ) session.getAttribute( "bookToAdd" );
      
      // if cart does not exist, create it
      if ( cart == null ) {
         cart = new HashMap<String,CartItemBean>();
         
         // set session attribute "cart"
         session.setAttribute( "cart", cart );
      }
      
      // determine if book is in cart
      CartItemBean cartItem = 
         ( CartItemBean ) cart.get( book.getISBN() );
      
      // If book is already in cart, update its quantity.
      // Otherwise, create an entry in the cart.
      if ( cartItem != null ) 
         cartItem.setQuantity( cartItem.getQuantity() + 1 );
      else 
         cart.put( book.getISBN(), new CartItemBean( book, 1 ) );
      
      // send the user to viewCart.jsp
      dispatcher = 
         request.getRequestDispatcher( "/viewCart.jsp" );
      dispatcher.forward( request, response );
   }   
}