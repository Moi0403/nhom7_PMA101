package fpoly.anhntph36936.happyfood.API;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Host {
    String DOMAIN = "http://192.168.14.101:3000/";

    @POST("/api/dangnhap")
    Call<APIResponse> DangNhap(@Body DangNhapRequest response);

    @POST("/api/dangki")
    Call<ArrayList<UserModel>> DangKi(@Body UserModel model);

    @GET("/api/list_user")
    Call<ArrayList<UserModel>> ListUser();

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

    @GET("/api/list_gh/{maUser}")
    Call<ArrayList<GioHangModel>> getGioHang(@Path("maUser") String maUser);

    @POST("/api/addGioHang")
    Call<ArrayList<GioHangModel>> addGH(@Body GioHangModel gioHangModel);
}
