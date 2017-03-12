/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.webapps.bookstore;

import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author srg
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByUseremail", query = "SELECT c FROM Customer c WHERE c.useremail = :useremail"),
    @NamedQuery(name = "Customer.findByUserpwd", query = "SELECT c FROM Customer c WHERE c.userpwd = :userpwd"),
    @NamedQuery(name = "Customer.findByOrderid", query = "SELECT c FROM Customer c WHERE c.orderid = :orderid")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USEREMAIL")
    private String useremail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERPWD")
    private String userpwd;
    @Column(name = "ORDERID")
    private Integer orderid;


    public Customer() {
    }
    
        
    public Customer(String useremail) {
        this.useremail = useremail;
    }

    public Customer(String useremail, int id, String userpwd) {
        this.useremail = useremail;
        this.id = id;
        this.userpwd = userpwd;
    }

    public boolean addNewUser(String useremail, String userpwd){

   	try {
            Connection connection = DB.getConnection();
            PreparedStatement userCreate;

            userCreate = connection.prepareStatement("SELECT id FROM customer WHERE userEmail = ?");
            userCreate.setString(1, useremail);
            ResultSet results = userCreate.executeQuery();
            if (results.next()) return false;
            //insert into authorISBN (authorID,isbn) values (3,'0130284181')
            userCreate = connection
                    .prepareStatement("INSERT INTO customer (userEmail, userPwd, orderID) values (?,?,0)");
            userCreate.setString(1, useremail);
            userCreate.setString(2, userpwd);
            userCreate.execute();
            userCreate = connection.prepareStatement("SELECT id FROM customer WHERE userEmail = ?");
            userCreate.setString(1, useremail);
            results = userCreate.executeQuery();
            // get row data
            if (results.next()){
                id = results.getInt("id");
            } else return false;
            this.useremail = useremail;
            this.userpwd = userpwd;
            this.orderid = 0;
            return true;
        }
        // process exceptions during database setup        
        catch (SQLException sqlException) {
            return false;
        }	
        // process problems locating data source		
        catch (Exception exception) {	
            return false;
        }
      
    }
    
    public boolean changePWD(String newpwd){

   	try {
            Connection connection = DB.getConnection();
            PreparedStatement userCreate;

            userCreate = connection.prepareStatement("UPDATE customer set userPwd = ? WHERE userEmail = ?");
            userCreate.setString(1, newpwd);
            userCreate.setString(2, useremail);
            userCreate.execute();
            userpwd = newpwd;
            return true;
        }
        // process exceptions during database setup        
        catch (SQLException sqlException) {
            return false;
        }	
        // process problems locating data source		
        catch (Exception exception) {	
            return false;
        }
      
    }
    
    public boolean initialUser(String useremail, String userpwd){

   	try {
            Connection connection = DB.getConnection();
            PreparedStatement userCreate;
            
            userCreate = connection.prepareStatement("SELECT * FROM customer WHERE userEmail = ?");
            userCreate.setString(1, useremail);
            ResultSet results = userCreate.executeQuery();

            //insert into authorISBN (authorID,isbn) values (3,'0130284181')
            
            // get row data
            if (results.next()){
                this.userpwd = results.getString("userPwd");
                if ( !this.userpwd.equals(userpwd)) return false;
                id = results.getInt("id");
                this.orderid = results.getInt("orderID");
                this.useremail = useremail;
            } else return false;
            
            return true;
        }
        // process exceptions during database setup        
        catch (SQLException sqlException) {
            return false;
        }	
        // process problems locating data source		
        catch (Exception exception) {	
            return false;
        }
      
    }
    
    public boolean addToOrder(String ISBN, int quantity){
    try {
            Connection connection = DB.getConnection();
            PreparedStatement userCreate;
            
            userCreate = connection.prepareStatement("INSERT INTO orders (orderID, userID, ISBN, quantity) values (?,?,?,?)");
            userCreate.setInt(1, this.orderid);
            userCreate.setInt(2, this.id);
            userCreate.setString(3, ISBN);
            userCreate.setInt(4, quantity);
            userCreate.execute();
          
            return true;
        }
        // process exceptions during database setup        
        catch (SQLException sqlException) {
            return false;
        }	
        // process problems locating data source		
        catch (Exception exception) {	
            return false;
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (useremail != null ? useremail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.useremail == null && other.useremail != null) || (this.useremail != null && !this.useremail.equals(other.useremail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.me.webapps.bookstore.Customer[ useremail=" + useremail + " ]";
    }

    private boolean getDatabasePath(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
