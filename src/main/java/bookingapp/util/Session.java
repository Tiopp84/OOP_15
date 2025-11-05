package bookingapp.util;


import bookingapp.model.User;

/**
 * Lớp Tiện ích (Utility) để quản lý "phiên" (Session) của người dùng.
 * Chúng ta dùng 'static' ở đây để có thể truy cập thông tin người dùng
 * từ bất kỳ đâu trong ứng dụng sau khi họ đã đăng nhập.
 */
public class Session {

    // Biến static để lưu trữ thông tin người dùng hiện tại
    private static User currentUser;

    /**
     * Hàm này được gọi bởi WelcomeController khi đăng nhập thành công.
     * @param user Đối tượng User đã được xác thực từ CSDL.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    public static User getCurrentUser() {
        return currentUser;
    }



    public static void clearSession() {
        currentUser = null;
    }

}

