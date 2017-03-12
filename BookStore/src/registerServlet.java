/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.webapps.bookstore.Customer;

/**
 *
 * @author srg
 */
public class registerServlet extends HttpServlet {

     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        String email = request.getParameter("userEmail");
        String passward = request.getParameter("userPWD");
        String repassward = request.getParameter("userRePWD");
        
        Customer newUsr = new Customer();
        String addUsrErrorInfo;
        if (email == null){
            session.setAttribute("addUsrErrorInfo", "Please input your email again."); 
            dispatcher = 
                    request.getRequestDispatcher( "/register.jsp" );
            dispatcher.forward( request, response );
            return;
        } else session.setAttribute("userEmail", email);
        if (passward == null || repassward == null || !passward.equals(repassward)){
            session.setAttribute("addUsrErrorInfo", "Passward wrong. Please input your password again.");
            dispatcher = 
                    request.getRequestDispatcher( "/register.jsp" );
            dispatcher.forward( request, response ); 
            return;
        } 
        if (!newUsr.addNewUser(email, passward)){
            session.setAttribute("addUsrErrorInfo", "User has already existed. Pleas try another email.");
            dispatcher = 
                    request.getRequestDispatcher( "/register.jsp" );
            dispatcher.forward( request, response );
            return;
        }
        
        session.setAttribute( "user", newUsr );
        session.setAttribute("addUsrErrorInfo", "NoError");
        // send the user to viewCart.jsp
        dispatcher = 
                request.getRequestDispatcher( "/login.jsp" );
        dispatcher.forward( request, response ); 
    }
    
     
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
