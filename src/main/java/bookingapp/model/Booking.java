package bookingapp.model;

/**
 * Lớp Model (POJO) đại diện cho một đối tượng Booking (Lượt đặt).
 * Nó tương ứng với bảng 'bookings' trong CSDL.
 */
public class Booking {

    private int id;
    private int userId;
    private int courtId;
    private String bookingDate; // Lưu dưới dạng TEXT (ví dụ: "2025-10-30")
    private String startTime;   // Lưu dưới dạng TEXT (ví dụ: "09:00")
    private String endTime;     // Lưu dưới dạng TEXT (ví dụ: "10:00")
    private double totalPrice;

    /**
     * Constructor (hàm dựng)
     */
    public Booking(int id, int userId, int courtId, String bookingDate, String startTime, String endTime, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.courtId = courtId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
    }

    // Các hàm Getters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCourtId() {
        return courtId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Các hàm Setters (nếu cần)

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Hàm toString() để debug (tùy chọn)
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", courtId=" + courtId +
                ", bookingDate='" + bookingDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

