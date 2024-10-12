package fpoly.anhntph36936.happyfood.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.API.HoaDonResponse;
import fpoly.anhntph36936.happyfood.Adapter.HoaDon_ADT;
import fpoly.anhntph36936.happyfood.Model.HoaDonModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_DH extends AppCompatActivity {
    ImageView img_back;
    RecyclerView recyclerView;
    String maUser;
    ArrayList<HoaDonModel> list;
    HoaDon_ADT hoaDonAdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dh);
        img_back = findViewById(R.id.imgBack_dh);
        recyclerView = findViewById(R.id.rc_dh);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        maUser = getMaUser();

        if (TextUtils.isEmpty(maUser)) {
            Toast.makeText(Main_DH.this, "Vui lòng đăng nhập để xem đơn hàng", Toast.LENGTH_SHORT).show();
        } else {
            getHoaDonByUser(maUser);
        }
    }
    private String getMaUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyUser", this.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        Log.d("User ID", "Current User ID: " + userId);
        return userId;
    }
    private void getHoaDonByUser(String maUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiHost = retrofit.create(API_Host.class);
        Call<HoaDonResponse> call = apiHost.getHoaDonByUser(maUser);
        call.enqueue(new Callback<HoaDonResponse>() {
            @Override
            public void onResponse(Call<HoaDonResponse> call, Response<HoaDonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HoaDonResponse hoaDonResponse = response.body();
                    Log.d("DEBUG", "Dữ liệu hoaDonResponse: " + hoaDonResponse.toString());

                    if (hoaDonResponse.isSuccess() && hoaDonResponse.getData() != null) {
                        list = new ArrayList<>(hoaDonResponse.getData());
                        Log.d("DEBUG", "Danh sách hóa đơn: " + list.size());

                        hoaDonAdt = new HoaDon_ADT(Main_DH.this, list);
                        recyclerView.setAdapter(hoaDonAdt);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Main_DH.this));
                    } else {
                        Log.d("DEBUG", "Không có dữ liệu");
                        Toast.makeText(Main_DH.this, "Không có hóa đơn nào", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("DEBUG", "API gọi không thành công hoặc body null");
                    Toast.makeText(Main_DH.this, "Lỗi server, không thể lấy hóa đơn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HoaDonResponse> call, Throwable t) {
                Log.e("DEBUG", "Lỗi khi gọi API: " + t.getMessage(), t);
                Toast.makeText(Main_DH.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }




}