package fpoly.anhntph36936.happyfood.Model;

public class UserModel {
    private String _id;
    private String phone;
    private String username;
    private String password;
    private String address;
    private String role;

    public UserModel() {

    }

    public UserModel(String _id, String phone, String username, String password, String address, String role) {
        this._id = _id;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
