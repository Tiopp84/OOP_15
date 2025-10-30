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

    /**
     * Hàm này được gọi bởi bất kỳ màn hình nào cần biết ai đang đăng nhập.
     * @return Đối tượng User hiện tại, hoặc null nếu chưa đăng nhập.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Hàm này được gọi bởi MainWindowController khi người dùng đăng xuất.
     */
    public static void clearSession() {
        currentUser = null;
    }

    /**
     * Kiểm tra xem có ai đang đăng nhập không.
     * @return true nếu đã đăng nhập, false nếu chưa.
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

