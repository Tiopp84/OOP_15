package bookingapp.model;

public class LichNgoaiLe {
    private int maHoatDong;
    private int maSan;
    private String ngay;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;
    private String loaiHoatDong;
    private String ghiChu;

    public LichNgoaiLe() {}

    public LichNgoaiLe(int maHoatDong, int maSan, String ngay, String thoiGianBatDau,
                       String thoiGianKetThuc, String loaiHoatDong, String ghiChu) {
        this.maHoatDong = maHoatDong;
        this.maSan = maSan;
        this.ngay = ngay;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.loaiHoatDong = loaiHoatDong;
        this.ghiChu = ghiChu;
    }

    // Getter & Setter
    public int getMaHoatDong() { return maHoatDong; }
    public void setMaHoatDong(int maHoatDong) { this.maHoatDong = maHoatDong; }

    public int getMaSan() { return maSan; }
    public void setMaSan(int maSan) { this.maSan = maSan; }

    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }

    public String getThoiGianBatDau() { return thoiGianBatDau; }
    public void setThoiGianBatDau(String thoiGianBatDau) { this.thoiGianBatDau = thoiGianBatDau; }

    public String getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(String thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }

    public String getLoaiHoatDong() { return loaiHoatDong; }
    public void setLoaiHoatDong(String loaiHoatDong) { this.loaiHoatDong = loaiHoatDong; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
