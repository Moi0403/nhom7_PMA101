package fpoly.anhntph36936.happyfood.Model;

import java.io.Serializable;

public class GioHangModel implements Serializable {
    private String _id;
    private String maUser;
    private SanPhamModel maSP;
    private int soLuong;
    private int giaGH;
    private int trangThaiMua;
    private int tongtien;

    public GioHangModel() {
    }

    public GioHangModel(String _id, String maUser, SanPhamModel maSP, int soLuong, int giaGH, int trangThaiMua, int tongtien) {
        this._id = _id;
        this.maUser = maUser;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaGH = giaGH;
        this.trangThaiMua = trangThaiMua;
        this.tongtien = tongtien;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getGiaGH() {
        return giaGH;
    }

    public void setGiaGH(int giaGH) {
        this.giaGH = giaGH;
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

    public SanPhamModel getMaSP() {
        return maSP;
    }

    public void setMaSP(SanPhamModel maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThaiMua() {
        return trangThaiMua;
    }

    public void setTrangThaiMua(int trangThaiMua) {
        this.trangThaiMua = trangThaiMua;
    }
}

