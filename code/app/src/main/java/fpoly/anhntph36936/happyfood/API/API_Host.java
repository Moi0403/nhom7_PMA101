package fpoly.anhntph36936.happyfood.API;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.Main.APIResponse;
import fpoly.anhntph36936.happyfood.Main.DangNhapRequest;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Host {
    String DOMAIN = "http://192.168.101.16:3000/";

    @POST("/api/dangnhap")
    Call<APIResponse> DangNhap(@Body DangNhapRequest response);

    @POST("/api/dangki")
    Call<ArrayList<UserModel>> DangKi(@Body UserModel model);

    @GET("/api/list_user")
    Call<ArrayList<UserModel>> ListUser();
}
