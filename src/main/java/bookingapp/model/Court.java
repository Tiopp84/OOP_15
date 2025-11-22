package bookingapp.model;


public class Court {
    private int id;
    private String name;
    private String status;


    public Court(int id, String name, String status){
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus(){
        return status;
    }
}

