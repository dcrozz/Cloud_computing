<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- process.jsp -->

<%-- JSP page settings --%>
<%@ page language = "java" session = "true" %>
<%@ page import = "java.text.*" %>
<%@ page import="org.me.webapps.bookstore.CartItemBean"%>
<%@ page import="org.me.webapps.bookstore.BookBean"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>

<html xmlns = "http://www.w3.org/1999/xhtml">

<head>
   <title>Thank You!</title>

   <link rel = "stylesheet" href = "styles.css" 
      type = "text/css" />
</head>

<% // start scriptlet
    RequestDispatcher dispatcher;
   // get total order amount
   Double d = ( Double ) session.getAttribute( "total" );
   if (d == null){
           dispatcher = request.getRequestDispatcher( "/viewCart.jsp" );
                dispatcher.forward( request, response );   
   }
   double total = d.doubleValue();
        
%> <%-- end scriptlet --%>

<body>
   <p class = "bigFont">Thank You</p>

   <p>Your order has been processed.</p>
   <p class = " bold">Order List</p>

<%-- start scriptlet to display shopping cart contents --%>
<%  
   Map cart = ( Map ) session.getAttribute( "cart" );
   total = 0;

   if ( cart == null || cart.size() == 0 ) 
      out.println( "<p>ERROR! Please retry later!</p>" );
   else {  

      // create variables used in display of cart
      Set cartItems = cart.keySet();
      Iterator iterator = cartItems.iterator();

      BookBean book;
      CartItemBean cartItem;

      int quantity;
      double price, subtotal;

%> <%-- end scriptlet for literal XHTML output --%>

   <table>
      <thead><tr>
         <th>Product</th>
         <th>Quantity</th>
         <th>Price</th>
         <th>Total</th>
      </tr></thead>

<% // continue scriptlet 

      while ( iterator.hasNext() ) {

         // get book data; calculate subtotal and total
         cartItem = ( CartItemBean ) cart.get( iterator.next() );
         book = cartItem.getBook();
         quantity = cartItem.getQuantity();
         price = book.getPrice();
         subtotal = quantity * price;
         total += subtotal;

%> <%-- end scriptlet for literal XHTML and   --%>
   <%-- JSP expressions output from this loop --%>

         <%-- display table row of book title, quantity, --%>
         <%-- price and subtotal --%>
         <tr>
            <td><%= book.getTitle() %></td>

            <td><%= quantity %></td>

            <td class = "right">
               <%= 
                  new DecimalFormat( "0.00" ).format( price )
               %>
            </td>

            <td class = "bold right">
               <%= 
                  new DecimalFormat( "0.00" ).format( subtotal ) 
               %>
            </td>
         </tr>

<% // continue scriptlet 

      }  // end of while loop

%> <%-- end scriptlet for literal XHTML and   --%>

      <%-- display table row containing shopping cart total --%>
      <tr>
         <td colspan = "4" class = "bold right">Total: 
            <%= new DecimalFormat( "0.00" ).format( total ) %>
         </td>
      </tr>
   </table>

   <p>Your credit card has been billed:
      <span class = "bold">
         $<%= new DecimalFormat( "0.00" ).format( total ) %>
      </span>
   </p>
</body>
<% // start scriptlet
   }
   // invalidate session because processing is complete
   session.invalidate();
       
%> <%-- end scriptlet --%>
</html>

<!--
 ***************************************************************
 * (C) Copyright 2001 by Deitel & Associates, Inc. and         *
 * Prentice Hall. All Rights Reserved.                         *
 *                                                             *
 * DISCLAIMER: The authors and publisher of this book have     *
 * used their best efforts in preparing the book. These        *
 * efforts include the development, research, and testing of   *
 * the theories and programs to determine their effectiveness. *
 * The authors and publisher make no warranty of any kind,     *
 * expressed or implied, with regard to these programs or to   *
 * the documentation contained in these books. The authors     *
 * and publisher shall not be liable in any event for          *
 * incidental or consequential damages in <div>connect</div>ion with, or  *
 * arising out of, the furnishing, performance, or use of      *
 * these programs.                                             *
 ***************************************************************
-->
