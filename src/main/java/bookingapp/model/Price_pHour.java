package bookingapp.model;

public class Price_pHour {
    private int day_in_week;
    private int hour;
    private double price;

    public Price_pHour(int day_in_week, int hour, double price) {
        this.day_in_week = day_in_week;
        this.hour = hour;
        this.price = price;
    }

    public double getPrice(){
        return price;
    }
}
