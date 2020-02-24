import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class People {
	protected int id;
    protected String username;
	protected String password;
    protected String firstname;
    protected String lastname;
    protected String email;
 
    public People() {
    	
    }
    
    public People(int id) {
        this.id = id;
    }
 
    public People(int id, String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;
    }
    
    public People(String username, String password, String firstname, String lastname, String email) {
      this.username = username;
      this.password = password;
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
  }
 
    @Override
	public String toString() {
		return "People [id=" + id + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}
    
    public int getId() {
    	return id;
    }

	public String getUserName() {
        return username;
    }
 
    public void setUserName(String username) {
        this.username = username;
    }
 
    public String getpassword() {
        return password;
    }
 
    public void setpassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstname;
    }
 
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastName() {
        return lastname;
    }
 
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    
    public String getemail() {
        return email;
    }
 
    public void setemail(String email) {
        this.email = email;
    }
 
   
}