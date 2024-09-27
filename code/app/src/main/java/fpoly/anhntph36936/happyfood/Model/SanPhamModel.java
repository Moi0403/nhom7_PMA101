package fpoly.anhntph36936.happyfood.Model;

public class SanPhamModel {
    private String _id;
    private String anhSP;
    private String tenSP;
    private String phanloaiSP;
    private String trongluongSP;
    private String giaSP;
    private String motaSP;

    public SanPhamModel() {
    }

    public SanPhamModel(String _id, String anhSP, String tenSP, String phanloaiSP, String trongluongSP, String giaSP, String motaSP) {
        this._id = _id;
        this.anhSP = anhSP;
        this.tenSP = tenSP;
        this.phanloaiSP = phanloaiSP;
        this.trongluongSP = trongluongSP;
        this.giaSP = giaSP;
        this.motaSP = motaSP;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getPhanloaiSP() {
        return phanloaiSP;
    }

    public void setPhanloaiSP(String phanloaiSP) {
        this.phanloaiSP = phanloaiSP;
    }

    public String getTrongluongSP() {
        return trongluongSP;
    }

    public void setTrongluongSP(String trongluongSP) {
        this.trongluongSP = trongluongSP;
    }

    public String getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(String giaSP) {
        this.giaSP = giaSP;
    }

    public String getMotaSP() {
        return motaSP;
    }

    public void setMotaSP(String motaSP) {
        this.motaSP = motaSP;
    }
}
