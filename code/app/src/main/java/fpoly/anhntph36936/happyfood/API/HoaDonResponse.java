package fpoly.anhntph36936.happyfood.API;

import java.util.List;

import fpoly.anhntph36936.happyfood.Model.HoaDonModel;

public class HoaDonResponse {
    private boolean success;  // Trạng thái trả về
    private String message;   // Thông báo lỗi hoặc thành công
    private List<HoaDonModel> data;

    public HoaDonResponse(boolean success, String message, List<HoaDonModel> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HoaDonModel> getData() {
        return data;
    }

    public void setData(List<HoaDonModel> data) {
        this.data = data;
    }
}
