package bookingapp.model;

public class User {

    private int id;
    private String username;
    private String full_name;
    private String phone_number;
    private String password;
    private String role;

    public User(int id, String username, String full_name, String phone_number, String password, String role) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getFull_name() { return full_name; }
    public String getPhone_number() { return phone_number; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setters (if needed)
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setFull_name(String full_name) { this.full_name = full_name; }
    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
