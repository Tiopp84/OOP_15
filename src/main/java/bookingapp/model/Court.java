package bookingapp.model;

/**
 * Lớp Model (POJO) đại diện cho một đối tượng Court (Sân).
 * Nó tương ứng với bảng 'courts' trong CSDL.
 */
public class Court {

    private int id;
    private String name;
    private String location;
    private double pricePerHour;

    /**
     * Constructor (hàm dựng)
     */
    public Court(){

    }
    public Court(int id, String name, String location, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerHour = pricePerHour;
    }

    // Các hàm Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    // Các hàm Setters (nếu cần)

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    // Hàm toString() để debug (tùy chọn)
    @Override
    public String toString() {
        return "Court{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}

