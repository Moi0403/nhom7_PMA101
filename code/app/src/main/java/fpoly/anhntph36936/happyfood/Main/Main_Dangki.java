package fpoly.anhntph36936.happyfood.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_Dangki extends AppCompatActivity {
    ImageView img_back;
    EditText ed_sdt, ed_user, ed_pass;
    Button btn_Dangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dangki);
        img_back = findViewById(R.id.imv_back);
        ed_sdt = findViewById(R.id.edSDT_DK);
        ed_user = findViewById(R.id.edUser_DK);
        ed_pass = findViewById(R.id.edPass_DK);
        btn_Dangki = findViewById(R.id.btnDangKi);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Dangki.this, Main_Login.class));
                finish();
            }
        });

        btn_Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = ed_sdt.getText().toString();
                String user = ed_user.getText().toString();
                String pass = ed_pass.getText().toString();

                if (sdt.isEmpty() || user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Main_Dangki.this, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel model = new UserModel();
                    model.setPhone(Integer.parseInt(sdt));
                    model.setUsername(user);
                    model.setPassword(pass);
                    model.setAddress("");
                    model.setRole("user");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Host.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    API_Host apiService = retrofit.create(API_Host.class);
                    Call<ArrayList<UserModel>> call =apiService.DangKi(model);
                    call.enqueue(new Callback<ArrayList<UserModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                            if(response.isSuccessful()){
                                ArrayList<UserModel> list = response.body();
                                Toast.makeText(Main_Dangki.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Main_Dangki.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}