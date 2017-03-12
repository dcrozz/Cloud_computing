/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

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
        processRequest(request, response);
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
        
        Customer newUsr = new Customer();
        String addUsrErrorInfo;
        if (email == null){
            session.setAttribute("addUsrErrorInfo", "Please input your email again."); 
            dispatcher = 
                    request.getRequestDispatcher( "/login.jsp" );
            dispatcher.forward( request, response );
            dispatcher.forward( request, response );
        } else session.setAttribute("userEmail", email);
        if (passward == null){
            session.setAttribute("addUsrErrorInfo", "Please input your password again.");
            dispatcher = 
                    request.getRequestDispatcher( "/login.jsp" );
            dispatcher.forward( request, response ); 
            dispatcher.forward( request, response );
        } 
        if (!newUsr.initialUser(email, passward)){
            session.setAttribute("addUsrErrorInfo", "Information Wrong.");
            dispatcher = 
                    request.getRequestDispatcher( "/login.jsp" );
            dispatcher.forward( request, response );
            dispatcher.forward( request, response );
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
