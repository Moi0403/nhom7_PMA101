package fpoly.anhntph36936.happyfood.Frag_User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Main.Main_Login;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frag_TTUser extends Fragment {
    LinearLayout item_tt, item_exit, item_dh;
    TextView tv_tt_user;
    String maUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ttuser, container, false);
        item_exit = view.findViewById(R.id.item_exit);
        item_tt = view.findViewById(R.id.item_tt);
        item_dh =  view.findViewById(R.id.item_hd);
        tv_tt_user = view.findViewById(R.id.tv_tt_user);

        maUser = getMaUser();
        item_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExit();
            }
        });
        item_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Main_DH.class));
            }
        });
        item_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTin(maUser);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");

        tv_tt_user.setText("Xin chào, "+user);
        return view;
    }
    private String getMaUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", getActivity().MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }
    private void showExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Exit !");
        builder.setIcon(R.drawable.thongbao);
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), Main_Login.class));
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getTT(String maUser){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);
        Call<UserModel> call = apiService.getUser(maUser);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
    private void ThongTin(String maUser){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ttuser, null);
        builder.setView(view1);
        android.app.AlertDialog dialog = builder.create();

        EditText edt_phone = view1.findViewById(R.id.edt_sdttt);
        EditText edt_user = view1.findViewById(R.id.edt_tentt);
        EditText edit_pass = view1.findViewById(R.id.edt_passtt);
        EditText edt_dc = view1.findViewById(R.id.edt_diachi);
        String phone = edt_phone.getText().toString();
        String user = edt_user.getText().toString();
        String pass = edit_pass.getText().toString();
        String dc = edt_dc.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);
        Call<UserModel> call = apiService.getUser(maUser);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserModel user = response.body();

                    // Populate the dialog with current user data
                    edt_user.setText(user.getUsername());
                    edt_phone.setText(user.getPhone());
                    edit_pass.setText(user.getPassword());
                    edt_dc.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                // Handle failure
            }
        });

        view1.findViewById(R.id.btnDoi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edt_phone.getText().toString();
                String user = edt_user.getText().toString();
                String pass = edit_pass.getText().toString();
                String dc = edt_dc.getText().toString();
                UserModel model = new UserModel();
                model.setPhone(phone);
                model.setUsername(user);
                model.setPassword(pass);
                model.setAddress(dc);

                Call<ArrayList<UserModel>> call1 = apiService.up_user(maUser, model);
                call1.enqueue(new Callback<ArrayList<UserModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

                    }
                });
            }
        });

        view1.findViewById(R.id.btnHuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
