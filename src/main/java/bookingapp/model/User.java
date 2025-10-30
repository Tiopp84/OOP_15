package bookingapp.model;

/**
 * Lớp Model (POJO) đại diện cho một đối tượng User.
 * Nó tương ứng với bảng 'users' trong CSDL.
 */
public class User {

    private int id;
    private String username;
    // Chúng ta không lưu password ở đây vì lý do bảo mật,
    // trừ khi thực sự cần thiết cho việc gì đó khác ngoài đăng nhập.

    /**
     * Constructor (hàm dựng)
     */
    public User(){

    }
    public User(int id, String username) {
        this.id = id;
        this.username = username;
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

    // Hàm toString() để debug (tùy chọn)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}

