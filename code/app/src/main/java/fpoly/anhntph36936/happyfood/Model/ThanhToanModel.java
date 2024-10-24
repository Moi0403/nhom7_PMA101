package fpoly.anhntph36936.happyfood.Model;

import java.util.List;

public class ThanhToanModel {
    private String maUser;
    private String tenSP;
    private String anhSP;
    private int giaSP;
    private int soLuong;
    private int trangthaiTT;
    private List<GioHangModel> items;

    public ThanhToanModel() {
    }

    public List<GioHangModel> getItems() {
        return items;
    }

    public void setItems(List<GioHangModel> items) {
        this.items = items;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangthaiTT() {
        return trangthaiTT;
    }

    public void setTrangthaiTT(int trangthaiTT) {
        this.trangthaiTT = trangthaiTT;
    }
}
