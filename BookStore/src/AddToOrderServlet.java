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
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.me.webapps.bookstore.BookBean;
import org.me.webapps.bookstore.CartItemBean;
import org.me.webapps.bookstore.Customer;

/**
 *
 * @author srg
 */
public class AddToOrderServlet extends HttpServlet {

 


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

        Customer newUsr = (Customer) session.getAttribute("user");
        Map cart = ( Map ) session.getAttribute( "cart" );
        if ( cart == null || cart.isEmpty() ) {
            dispatcher = request.getRequestDispatcher( "/books.jsp" );
            dispatcher.forward( request, response );
            return;
        } else {
            String email;
            if (newUsr == null){
                email = (String) request.getParameter("firstname");
                if (email == null){
                    dispatcher = request.getRequestDispatcher( "/order.jsp" );
                    dispatcher.forward( request, response );
                    return;
                }
            } else email = newUsr.getUseremail();
            // create variables used in display of cart
            Set cartItems = cart.keySet();
            Iterator iterator = cartItems.iterator();
            BookBean book;
            CartItemBean cartItem;
            int quantity;
            double price;
            StringBuilder emailMsg = new StringBuilder();

            
            emailMsg.append("Dear ");
            emailMsg.append(email);
            emailMsg.append(",\n Thanks for your purchasing.\nOrder Detail:\n");
            while ( iterator.hasNext() ) {
                // get book data; calculate subtotal and total
                cartItem = ( CartItemBean ) cart.get( iterator.next() );
                book = cartItem.getBook();
                quantity = cartItem.getQuantity();
                price = book.getPrice();
                if (newUsr != null)newUsr.addToOrder(book.getISBN(), quantity);
                emailMsg.append(book.getTitle());
                emailMsg.append("    ");
                emailMsg.append(price);
                emailMsg.append("    ");
                emailMsg.append(quantity);
                emailMsg.append("    ");
                price = quantity * price;
                emailMsg.append(price);
                emailMsg.append("\n");
            }
            emailMsg.append("TOTAL: ");
            emailMsg.append(session.getAttribute("total"));
            emailMsg.append("\n********************************\nPlease do not reply.");
            // Assuming you are sending email from localhost
            
            final String emailLogin = "alexukeyim@gmail.com";
            final String emailPassword = "DCRozz1200";
            
            String emailHost = "smtp.gmail.com";
            String emailAddr = emailLogin;
            String displayName = "BookStore EC2";
            String emailPort = "465";
            
            // Get system properties
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", emailHost);
            properties.put("mail.smtp.socketFactory.port", emailPort);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.debug", "true");
            Session sessionMail = Session.getInstance(properties,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailLogin, emailPassword);
			}
		  });    
            sessionMail.setDebug(true);

            try{
                
               // Create a default MimeMessage object.
               Message message = new MimeMessage(sessionMail);

               // Set From: header field of the header.
               message.setFrom(new InternetAddress(emailAddr,displayName));

               // Set To: header field of the header.
               message.addRecipient(Message.RecipientType.TO,
                                        new InternetAddress(email));

               // Set Subject: header field
               message.setSubject("Order List!");

               message.setSentDate(new Date());
               // Now set the actual message
               message.setText(emailMsg.toString());
               uploadFile(emailMsg.toString());
       
               // Send message
               Transport.send(message);
               System.out.println("Sent message successfully....");
            }catch (MessagingException mex) {
            }
        }
        dispatcher = request.getRequestDispatcher( "/process.jsp" );
        dispatcher.forward( request, response );
    }
    
    //uploadFile to S3 bucket
    public static void uploadFile(String msg) {
        AWSCredentials credentials = null;
   
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);

        String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
        String key = "MyObjectKey";
        
        try {
            s3.createBucket(bucketName);
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName,key,createFile(msg)));
            
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static File createFile(String msg) throws IOException {
        File file = File.createTempFile("email_msg_copy", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(msg);
       
        writer.close();

        return file;
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
