package bookingapp.model;

public class User {

    private int id;
    private String username, full_name, phone_number, password;
    private String role;

    public User(int id, String username, String full_name, String phone_number, String password, String role) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.password = password;
        this.role = role;
    }

    // Các hàm Getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}


    // Các hàm Setters (nếu cần)

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getRole(){
        return role;
    }
}

