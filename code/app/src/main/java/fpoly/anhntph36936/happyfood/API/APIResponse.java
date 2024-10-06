package fpoly.anhntph36936.happyfood.API;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.Model.GioHangModel;

public class APIResponse {

    private String message;
    private String role;
    private String maUser;
    private ArrayList<GioHangModel> data;

    public ArrayList<GioHangModel> getData() {
        return data;
    }

    public void setData(ArrayList<GioHangModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }
}
