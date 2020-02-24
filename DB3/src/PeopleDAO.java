  
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/PeopleDAO")
public class PeopleDAO extends HttpServlet {     
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public PeopleDAO() {

    }
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://localhost:3306/test?"
  			          + "user=john&password=pass1234&useSSL=false");
            System.out.println(connect);
        }
    }
    
    
    public List<People> listAllPeople() throws SQLException {
        List<People> listPeople = new ArrayList<People>();        
        String sql = "SELECT * FROM users";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String email = resultSet.getString("email");
                         
            People people = new People( id, username, password, firstname, lastname, email);

            listPeople.add(people);
        }        
        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listPeople;
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
         
    public boolean insert(People people) throws SQLException {
    	connect_func();         
		String sql = "insert into  users(username, password, firstname, lastname, email) values (?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, people.username);
		preparedStatement.setString(2, people.password);
		preparedStatement.setString(3, people.firstname);
		preparedStatement.setString(4, people.lastname);
		preparedStatement.setString(5, people.email);
//		preparedStatement.executeUpdate();
		
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowInserted;
    }     
     
    public boolean delete(int peopleid) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, peopleid);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowDeleted;     
    }
     
    public boolean update(People people) throws SQLException {
        String sql = "update users set username=?, password =?, firstname = ?, lastname = ?,email = ? where id = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, people.username);
        preparedStatement.setString(2, people.password);
        preparedStatement.setString(3, people.firstname);
        preparedStatement.setString(4, people.lastname);
        preparedStatement.setString(5, people.email);
        preparedStatement.setInt(6, people.id);
         
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowUpdated;     
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
    public People getPeople(int id) throws SQLException {
    	People people = null;
        String sql = "SELECT * FROM users WHERE id = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String username = resultSet.getString("username");
             
            people = new People( id, username, password, firstname, lastname, email);
            System.out.println(people);
        }
         
        resultSet.close();
        statement.close();
         
        return people;
    }

	public void wipe() throws SQLException{
		connect_func();
		
		String deleteTable = "DROP TABLE IF EXISTS users";
		String createTable = "CREATE TABLE IF NOT EXISTS users " +
			"(id INTEGER not NULL AUTO_INCREMENT, " +
			" username VARCHAR(50) NOT NULL, " + 
			" password VARCHAR(50) NOT NULL, " + 
			" firstname VARCHAR(50) NOT NULL, " + 
			" lastname VARCHAR(50) NOT NULL, " + 
			" email varchar(50) not NULL," +
			" PRIMARY KEY ( id ))"; 
	
		statement = connect.createStatement();
	    statement.executeUpdate(deleteTable);
	    statement.executeUpdate(createTable);
	    
	    statement.close();
	}
	
	public Boolean findUser(String username, String password) throws SQLException{
		
		connect_func();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
         
        
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
         
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {

              
             return true;
        }
        
        else
        	return false;
    
		
	}
}