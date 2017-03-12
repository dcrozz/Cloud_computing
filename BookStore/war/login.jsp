<%-- 
    Document   : login
    Created on : Mar 10, 2013, 9:29:57 AM
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
        <title>User</title>
        <link rel = "stylesheet" href = "styles.css" 
              type = "text/css" />
    </head>
    <body>
        <p class = "right bold green">
            <a href = "books.jsp">Continue Shopping</a>
        </p>
        <p class = "bigFont">Hello, <%if (user==null) {
        %> Please Login.
        <p style = "font-weight: bold" class="center">
            <%
            String errorinfo = (String)session.getAttribute("addUsrErrorInfo");
            if (errorinfo == null || errorinfo.equals("NoError") ){
                %>
                
                Please input the your account information.                 
        <%
            } else {%><%=errorinfo%><%}
        %>
        </p>
        <form method = "post" action = "LoginUsr">
            <!-- table of form elements -->
            <table>
                <tr>
                    <td class = "right bold">Email:</td>
                    <td>
                        <input type = "text" name = "userEmail" 
                               size = "25" />
                    </td>
                </tr>
                <tr>
                    <td class = "right bold">Password:</td>
                    <td>
                       <input type = "password" name = "userPWD" 
                          size = "25" />
                    </td>
                </tr>
            </table>
            <!-- enable user to submit the form  -->
            <p><input type = "submit" value = "Submit" /></p>
        </form>
        <p class = "bold green">
            <a href = "register.jsp">new user</a>
        </p>
        <%} else {%> <%= user.getUseremail()
        %>         
        <p class = "bold green">
            <a href = "changePassword.jsp">Change Password</a>
        </p> 
        <%}%>
        
    </body>
</html>
