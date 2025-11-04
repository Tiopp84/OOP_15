package bookingapp.model;

public class Admin {
    private int id;
    private String username, password;

    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Các hàm Getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    // Các hàm Setters (nếu cần)

    public void setId(int id) {
        this.id = id;
    }

}
