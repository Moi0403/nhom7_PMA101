package fpoly.anhntph36936.happyfood.Model;

import java.io.Serializable;

public class GioHangModel implements Serializable {
    private String _id;
    private String maUser;
    private String maSP;
    private int soLuong;
    private int trangThaiMua;

    public GioHangModel() {
    }

    public GioHangModel(String _id, String maUser, String maSP, int soLuong, int trangThaiMua) {
        this._id = _id;
        this.maUser = maUser;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.trangThaiMua = trangThaiMua;
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

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
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

