<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language = "java" session = "true" %>
<%@ page import = "org.me.webapps.bookstore.*" %>
<%@ page import = "java.text.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%  
   BookBean book = ( BookBean ) session.getAttribute( "bookToAdd" );
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= book.getTitle() %></title>
        
        <link rel = "stylesheet" href = "styles.css" 
        type = "text/css" />
    </head>
    <body>

        <h1><%= book.getTitle() %></h1>
    
        <table>
            <tr>
                <!-- create table cell for product image -->
                <td rowspan = "5">  <!-- cell spans 5 rows -->
                    <img style = "border: thin solid black" src =  
                    "images/<%= book.getImageFile() %>" 
                    alt = "<%= book.getTitle() %>"  />
                </td>
 
                <!-- create table cells for price in row 1 -->
                <td class = "bold">Price:</td>

                <td><%= new DecimalFormat("0.00").format( book.getPrice() ) %></td>
            </tr>

            <tr>

                <!-- create table cells for ISBN in row 2 -->
                <td class = "bold">ISBN #:</td>

                <td><%= request.getParameter("isbn")%></td>
            </tr>
         
            <tr>

                <!-- create table cells for edition in row 3 -->
                <td class = "bold">Edition:</td>

                <td><%= book.getEditionNumber() %></td>
            </tr>
         
            <tr>

                <!-- create table cells for copyright in row 4 -->
                <td class = "bold">Copyright:</td>

                <td><%= book.getCopyright() %></td>
            </tr>
         
            <tr>

                <!-- create Add to Cart button in row 5 -->
                <td>
                    <form method = "post" action="addToCart">
                        <p><input type = "submit" value = "Add to Cart" /></p>
                    </form>
                </td>

                <!-- create View Cart button in row 5 -->
                <td>
                    <form method = "get" action="viewCart.jsp">
                        <p><input type = "submit" value = "View Cart" /></p>
                    <form>
                </td>
            </tr>
        
        </table>
       <!-- link back to books.jsp to continue shopping -->
   <p class = "bold green">
      <a href = "books.jsp">Back to booklist</a>
   </p>
    </body>
</html>