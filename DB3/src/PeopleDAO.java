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
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/testdb"
  			          + "user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
    public List<People> listAllPeople() throws SQLException {
        List<People> listPeople = new ArrayList<People>();        
        String sql = "SELECT * FROM student";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");");
             
            People people = new People(username,password, firstName, lastName, email);
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
		String sql = "insert into people (username, password, first name, last name, email) values (?, ?, ?, ?, ?)";;"
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.username);
        preparedStatement.setString(2, users.password);
        preparedStatement.setString(3, users.firstName);
        preparedStatement.setString(4, users.lastName);
        preparedStatement.setString(5, users.email);
        preparedStatement.executeUpdate();

//		preparedStatement.executeUpdate();
		
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowInserted;
    }     
     
    public boolean delete(int username) throws SQLException {
        String sql = "DELETE FROM people WHERE username = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, username);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowDeleted;     
    }
     
    public boolean update(People people) throws SQLException {
        String sql = "update people set password=?, firstName=?, lastName=?, email=? where username=?";
        connect_func();

        preparedStatement=(PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.username);
        preparedStatement.setString(2, users.password);
        preparedStatement.setString(3, users.firstName);
        preparedStatement.setString(4, users.lastName);
        preparedStatement.setString(5, users.email);
         
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
	
    public People getPeople(int username) throws SQLException {
    	People people = null;
        String sql = "SELECT * FROM people WHERE username = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if(resultSet.next()){// do we need to include password????
            String password=resultSet.getString("password");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            people = new People(username, password, firstName, lastName, email);
        }
         
        resultSet.close();
        statement.close();
         
        return people;
    }
}
