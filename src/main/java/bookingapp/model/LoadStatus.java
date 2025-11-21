package bookingapp.model;


public class LoadStatus {
    private int id;
    private int MaSan;
    private String picked_date;
    private String status;

    public LoadStatus(int id, int maSan, String picked_date, String status) {
        this.id = id;
        MaSan = maSan;
        this.picked_date = picked_date;
        this.status = status;
    }
}
