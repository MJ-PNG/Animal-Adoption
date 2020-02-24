import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
@WebServlet("/ControlServlet")
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Connection connect = null;
    private static Statement statement;
    private PeopleDAO peopleDAO;
 
    public void init() {
        peopleDAO = new PeopleDAO(); 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/InitializeDB":
            	clearExistingTables();
            	createNewTables();
            	addInitialPeople();
            	break;
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
            	insertPeople(request, response);
                break;
            case "/delete":
            	deletePeople(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
            	updatePeople(request, response);
                break;
            default:          	
            	listPeople(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void clearExistingTables()
            throws SQLException, IOException, ServletException
    {
        statement = connect.createStatement();
        statement.execute("DROP TABLE IF EXISTS people");
        System.out.println("CREATION");
    }
    
    private void createNewTables()
            throws SQLException, IOException, ServletException
    {
        statement = connect.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                                  "username varchar(50) NON NULL" +
                                  "password varchar(24) NON NULL" +
                                  "firstname varchar(50) NON NULL" +
                                  "lastname varchar(50) NON NULL" +
                                  "email varchar(50) NON NULL" +
                                  ")");
    }
    
  
    private void addInitialPeople()
            throws SQLException, IOException, ServletException {
        
        Scanner initialPeopleData = new Scanner(new java.io.File("initialPeople.txt"));
        String username;
        String password;
        String firstName;
        String lastName;
        String email;
        
        People newPeople;
        
        while(initialPeopleData.hasNext()) {
    
            username = initialPeopleData.next();
            password = initialPeopleData.next();
            firstName = initialPeopleData.next();
            lastName = initialPeopleData.next();
            email = initialPeopleData.next();
            
            newPeople = new People(username, password, firstName, lastName, email);
            PeopleDAO.insert(newPeople);
        }
    }
    private void listPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<People> listPeople = peopleDAO.listAllPeople();
        request.setAttribute("listPeople", listPeople);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("PeopleList.jsp");       
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("PeopleForm.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String username = request.getParameter("username");
        People existingPeople = peopleDAO.getPeople(username);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PeopleForm.jsp");
        request.setAttribute("people", existingPeople);
        dispatcher.forward(request, response);
 
    }
 
    private void insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        People newPeople = new People(username, password, firstName, lastName, email);
        peopleDAO.insert(newPeople);
        response.sendRedirect("list");
    }
 
    private void updatePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("username"));
        
        System.out.println(username);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        ;
        
        People people = new People(username, password, firstName, lastName, email);
        peopleDAO.update(people);
        response.sendRedirect("list");
    }
 
    private void deletePeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String email = request.getParameter("email");
    
        peopleDAO.delete(email);
        response.sendRedirect("list"); 
    }

}