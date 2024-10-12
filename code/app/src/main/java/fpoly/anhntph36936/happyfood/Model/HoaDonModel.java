package fpoly.anhntph36936.happyfood.Model;

import java.util.ArrayList;

public class HoaDonModel {
    private String _id;
    private String maUser;
    private ArrayList<String> maSP;
    private String maGH;
    private String ngayMua;
    private int tongTien;
    private String diaChi;
    private int trangThaiDH;



    public HoaDonModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public ArrayList<String> getMaSP() {
        return maSP;
    }

    public void setMaSP(ArrayList<String> maSP) {
        this.maSP = maSP;
    }

    public String getMaGH() {
        return maGH;
    }

    public void setMaGH(String maGH) {
        this.maGH = maGH;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getTrangThaiDH() {
        return trangThaiDH;
    }

    public void setTrangThaiDH(int trangThaiDH) {
        this.trangThaiDH = trangThaiDH;
    }
}
