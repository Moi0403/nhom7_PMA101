package fpoly.anhntph36936.happyfood.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_ChiTietSP extends AppCompatActivity {
    private String idSP;
    ImageView imgAnh_sanpham_chitiet, imgBack;
    TextView tvTen_sanpham_chitiet, tvGia_sanpham_chitiet, tv_PL, tv_TL,  tv_MT;
    ArrayList<SanPhamModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chi_tiet_sp);
        imgAnh_sanpham_chitiet = findViewById(R.id.imgAnh_sanpham_chitiet);
        imgBack = findViewById(R.id.imgBack);
        tvTen_sanpham_chitiet = findViewById(R.id.tvTen_sanpham_chitiet);
        tvGia_sanpham_chitiet = findViewById(R.id.tvGia_sanpham_chitiet);
        tv_PL = findViewById(R.id.tvPL_sanpham_chitiet);
        tv_TL = findViewById(R.id.tvTL_sanpham_chitiet);
        tv_MT = findViewById(R.id.tvMT_sanpham_chitiet);



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSP(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiService = retrofit.create(API_Host.class);
        Call<SanPhamModel> call = apiService.getSP(id); // Gọi API trả về 1 SanPhamModel
        call.enqueue(new Callback<SanPhamModel>() {
            @Override
            public void onResponse(Call<SanPhamModel> call, Response<SanPhamModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPhamModel model = response.body(); // Lấy thông tin sản phẩm từ phản hồi
                    String srcImg = model.getAnhSP(); // Đường dẫn ảnh
                    Picasso.get().load(srcImg).into(imgAnh_sanpham_chitiet); // Hiển thị ảnh

                    // Hiển thị các thông tin khác
                    tvTen_sanpham_chitiet.setText(model.getTenSP());
                    tvGia_sanpham_chitiet.setText(String.valueOf(model.getGiaSP())+".000đ");
                    tv_PL.setText(model.getPhanloaiSP());
                    tv_TL.setText(String.valueOf(model.getTrongluongSP()));
                    tv_MT.setText(model.getMotaSP());
                }
            }

            @Override
            public void onFailure(Call<SanPhamModel> call, Throwable t) {
                // Xử lý lỗi nếu cần
                Toast.makeText(Main_ChiTietSP.this, "Lỗi khi lấy thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

}