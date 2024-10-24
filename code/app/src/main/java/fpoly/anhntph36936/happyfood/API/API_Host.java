package fpoly.anhntph36936.happyfood.API;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.ThanhToanModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_Host {
    String DOMAIN = "http://192.168.101.16:3000/";

    @POST("/api/dangnhap")
    Call<APIResponse> DangNhap(@Body DangNhapRequest response);

    @POST("/api/dangki")
    Call<ArrayList<UserModel>> DangKi(@Body UserModel model);

    @DELETE("/api/del_user/{id}")
    Call<ArrayList<UserModel>> del_user(@Path("id") String id);

    @GET("/api/list_user")
    Call<ArrayList<UserModel>> ListUser();

    @GET("/api/list_user/{id}")
    Call<UserModel> getUser(@Path("id") String id);

    @PUT("/api/up_user/{id}")
    Call<ArrayList<UserModel>> up_user(@Path("id") String id, @Body UserModel model);

    @GET("/api/list_sp")
    Call<ArrayList<SanPhamModel>> ListSP();
    @POST("/api/add_sp")
    Call<ArrayList<SanPhamModel>> addPro(@Body SanPhamModel model);
    @PUT("/api/up_sp/{id}")
    Call<ArrayList<SanPhamModel>> up_sp(@Path("id") String id, @Body SanPhamModel model);
    @DELETE("/api/del_sp/{id}")
    Call<ArrayList<SanPhamModel>> del_sp(@Path("id") String id);

    @GET("/api/search/{tenSP}")
    Call<ArrayList<SanPhamModel>> tim(@Path("tenSP") String tenSP);

    @GET("/api/getSP/{id}")
    Call<SanPhamModel> getSP(@Path("id") String id);

    @GET("/api/list_gh/{id}")
    Call<ArrayList<GioHangModel>> getGioHang(@Path("id") String id);

    @POST("/api/addGioHang")
    Call<GioHangModel> addGH(@Body GioHangModel gioHangModel);

    @DELETE("/api/del_gh/{id}")
    Call<GioHangModel> del_gh(@Path("id") String id);

    @PUT("api/up_gh/{id}")
    Call<ArrayList<GioHangModel>> up_gh(@Path("id") String id, @Body GioHangModel gioHangModel);

    @POST("api/add_tt")
    Call<ThanhToanModel> addThanhToan(@Body ThanhToanModel thanhToan);
}
