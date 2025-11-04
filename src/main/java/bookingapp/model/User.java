package bookingapp.model;

public class User {

    private int id;
    private String username, password;

    public User(int id, String username, String password) {
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

    // Hàm toString() để debug (tùy chọn)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}

