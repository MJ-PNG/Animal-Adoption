import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class People {
	protected String id;
	protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    
    public People() {
    }
    public String getid(){
       
		return id;
    }
    public void setid(String id){
        this.id = id;
    }
    
    public People (String username, String password, String firstName, String lastName, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
 
    public String getUserName(){
        return username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
}