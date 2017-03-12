<%-- 
    Document   : loglinVerify
    Created on : Mar 10, 2013, 9:44:02 AM
    Author     : srg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel = "stylesheet" href = "styles.css" 
              type = "text/css" />    
    </head>
    <body>
        <p class = "right bold green">
            <a href = "books.jsp">Continue Shopping</a>
        </p>
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
        <form method = "POST" action = "registerNewUser">
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
                <tr>
                    <td class = "right bold">Retype Password:</td>
                    <td>
                       <input type = "password" name = "userRePWD" 
                          size = "25" />
                    </td>
                </tr>
            </table>
            <!-- enable user to submit the form  -->
            <p><input type = "submit" value = "Submit" /></p>
        </form>
        
    </body>
</html>
