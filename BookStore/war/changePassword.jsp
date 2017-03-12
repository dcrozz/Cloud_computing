<%-- 
    Document   : changePassword
    Created on : Mar 10, 2013, 10:18:54 AM
    Author     : srg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Password Change</title>
        <link rel = "stylesheet" href = "styles.css" 
        type = "text/css" />
    </head>
    <body>
        <p class = "right bold green">
            <a href = "books.jsp">Continue Shopping</a>
        </p>
            <%
                String changePWD = (String)session.getAttribute("changePWD");
                if (changePWD == null || changePWD.equals("ERROR") ){
            %><p style = "font-weight: bold" class="center"> Please input the your account information. </p>
        <form method = "post" action = "ChangePWD">
            <!-- table of form elements -->
            <table>
                <tr>
                    <td class = "right bold">Old Password:</td>
                    <td>
                       <input type = "password" name = "oldPWD" 
                          size = "25" />
                    </td>
                </tr>
                <tr>
                    <td class = "right bold">New Password:</td>
                    <td>
                       <input type = "password" name = "newPWD" 
                          size = "25" />
                    </td>
                </tr>
            </table>
            <!-- enable user to submit the form  -->
            <p><input type = "submit" value = "Submit" /></p>
        </form>
        <% } else { session.removeAttribute("changePWD"); %>
         <p style = "font-weight: bold" class="center">Change success. </p>
        <% }%>
    </body>
</html>
