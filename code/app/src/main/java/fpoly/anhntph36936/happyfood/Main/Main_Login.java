
package fpoly.anhntph36936.happyfood.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.MainActivity;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_Login extends AppCompatActivity {
    Button btn_login;
    EditText edtUser, edtPass;
    CheckBox chkNho;
    TextView tv_DK;
    private API_Host apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        btn_login = findViewById(R.id.btnLogin);
        edtUser = findViewById(R.id.edUser);
        edtPass = findViewById(R.id.edPass);
        chkNho = findViewById(R.id.chkNho);
        tv_DK = findViewById(R.id.tvDangKi);

        tv_DK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Login.this, Main_Dangki.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();


                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Main_Login.this, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("LoginInput", "Username: " + user + ", Password: " + pass + ""); // Log thông tin đầu vào

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_Host.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiService = retrofit.create(API_Host.class);
                DangNhapRequest request = new DangNhapRequest(user, pass); // Thêm role vào request
                Call<APIResponse> call = apiService.DangNhap(request);
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            APIResponse userModel = response.body();
                            String role = userModel.getRole(); // Lấy vai trò từ phản hồi

                            if (userModel.getMessage().equals("Đăng nhập thành công")) {
                                if (role.equals("adm")) {
                                    Intent intent = new Intent(Main_Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(Main_Login.this, Main_User.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(Main_Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Main_Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Log.e("LoginError", "Lỗi kết nối: " + t.getMessage());
                        Toast.makeText(Main_Login.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}