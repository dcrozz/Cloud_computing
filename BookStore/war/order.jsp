<%-- 
    Document   : order
    Created on : Mar 10, 2013, 8:45:00 AM
    Author     : srg
--%>

<%@page import="org.me.webapps.bookstore.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
   Customer user = ( Customer ) session.getAttribute( "user" );
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
        <link rel = "stylesheet" href = "styles.css" 
              type = "text/css" />
    </head>
    <body>
   <p class = "bigFont">Shopping Cart Check Out</p>

   <!-- Form to input user information and credit card.   -->
   <!-- Note: No need to input real data in this example. -->
   <form method = "post" action = "AddToOrder">


      <p style = "font-weight: bold">
         Please input the following information.</p>

      <!-- table of form elements -->
      <table>
         <tr>
             <td class = "right bold">Email:</td>
             
             <td>
                 <%if (user == null) {%><input type = "text" name = "firstname" 
                size = "25" /><%} else {%><%= user.getUseremail() %><%}%>
      </td>
      </tr>

         <tr>
            <td class = "right bold">Name to Send:</td>

            <td>
               <input type = "text" name = "lastname" 
                  size = "25" />
            </td>
         </tr>

         <tr>
            <td class = "right bold">Street:</td>

            <td>
               <input type = "text" name = "street" size = "25" />
            </td>
         </tr>

         <tr>
            <td class = "right bold">City:</td>

            <td>
               <input type = "text" name = "city" size = "25" />
            </td>
         </tr>

         <tr>
            <td class = "right bold">State:</td>

            <td>
               <input type = "text" name = "state" size = "2" />
            </td>
         </tr>

         <tr>
            <td class = "right bold">Zip code:</td>

            <td>
               <input type = "text" name = "zipcode" 
                  size = "10" />
            </td>
         </tr>

         <tr>
            <td class = "right bold">Phone #:</td>

            <td>
               (
                  <input type = "text" name = "phone" size = "3" />
               )

               <input type = "text" name = "phone2" 
                      size = "3" /> -

               <input type = "text" name = "phone3" size = "4" />
            </td>
         </tr>  

         <tr>
            <td class = "right bold">Credit Card #:</td>

            <td>
               <input type = "text" name = "creditcard" 
                  size = "25" />  
            </td>
         </tr>

         <tr>
            <td class = "right bold">Expiration (mm/yy):</td>

            <td>
               <input type = "text" name = "expires" 
                  size = "2" /> /

               <input type = "text" name = "expires2" 
                  size = "2" />
            </td>
         </tr>        
      </table>
      
      <!-- enable user to submit the form  -->
      <p><input type = "submit" value = "Submit" /></p>
   </form>
      <p class = "right bold green">
          <a href = "viewCart.jsp">Go Back to View Cart</a>
      </p>
</html>
