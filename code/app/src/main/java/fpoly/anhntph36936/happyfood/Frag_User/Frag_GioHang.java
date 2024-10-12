package fpoly.anhntph36936.happyfood.Frag_User;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Adapter.GioHang_ADT;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.HoaDonModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frag_GioHang extends Fragment {
    RecyclerView recyclerView;
    String maUser;
    GioHang_ADT gioHangAdt;
    ImageView imgBack;
    public TextView tvTotal;
    int totalPrice = 0;
    public Button btnMua_hang;

    ArrayList<GioHangModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_gio_hang);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnMua_hang = view.findViewById(R.id.btnMua_hang);

        maUser = getMaUser();

        if (TextUtils.isEmpty(maUser)) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để xem giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            getGH(maUser);
        }

        btnMua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MuaHang(maUser);
            }
        });
        return view;
    }
    private String getMaUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUser", getActivity().MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }
    private void getGH(String maUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);
        Call<ArrayList<GioHangModel>> call = apiService.getGioHang(maUser);
        call.enqueue(new Callback<ArrayList<GioHangModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHangModel>> call, Response<ArrayList<GioHangModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = response.body();
                    if (list.isEmpty()) {
                        Toast.makeText(getContext(), "Giỏ hàng của bạn hiện tại đang trống.", Toast.LENGTH_SHORT).show();
                    } else {
                        gioHangAdt = new GioHang_ADT(getContext(), list, Frag_GioHang.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(gioHangAdt);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GioHang", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    private void getUserInfo(String userId, EditText edt_sdt, EditText edt_ten, EditText edt_diachi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);
        Call<UserModel> call = apiService.getUser(userId);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserModel user = response.body();
                    edt_ten.setText(user.getUsername());
                    edt_sdt.setText(user.getPhone()+"");
                    edt_diachi.setText(user.getAddress());
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void MuaHang(String maUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_muahang, null);
        builder.setView(view1);
        AlertDialog dialog = builder.create();

        EditText edt_sdt = view1.findViewById(R.id.edt_sdtTT);
        EditText edt_ten = view1.findViewById(R.id.edt_tenTT);
        EditText edt_diachi = view1.findViewById(R.id.edt_diachi);
        TextView tv_ttien = view1.findViewById(R.id.tv_ttien);
        TextView tv_ngay = view1.findViewById(R.id.tv_ngaymua);
        edt_sdt.setEnabled(false);
        edt_ten.setEnabled(false);
        edt_diachi.setEnabled(false);
        totalPrice = 0;
        String maGH = "";  // Mã giỏ hàng
        ArrayList<String> listMaSP = new ArrayList<>();  // Danh sách các ID sản phẩm

        for (GioHangModel gioHangModel : list) {
            if (gioHangModel != null && gioHangModel.getTrangThaiMua() == 1) {
                totalPrice += gioHangModel.getGiaGH();
                // Trích xuất chỉ ID sản phẩm từ GioHangModel
                listMaSP.add(gioHangModel.getMaSP().get_id());  // Giả sử `getId()` là phương thức lấy ID của SanPhamModel
                maGH = gioHangModel.get_id();  // Giỏ hàng
            }
        }

        if (totalPrice == 0) {
            Toast.makeText(getContext(), "Vui lòng chọn ít nhất một sản phẩm để mua.", Toast.LENGTH_SHORT).show();
            return;
        }

        tv_ttien.setText(totalPrice + ".000");
        tv_ngay.setText(getCurrentDate());
        getUserInfo(maUser, edt_sdt, edt_ten, edt_diachi);

        String finalMaGH = maGH;
        view1.findViewById(R.id.btnThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonModel hoaDon = new HoaDonModel();
                hoaDon.setMaUser(maUser);
                hoaDon.setNgayMua(getCurrentDate());
                hoaDon.setTongTien(totalPrice);
                hoaDon.setDiaChi(edt_diachi.getText().toString());
                hoaDon.setTrangThaiDH(1);
                hoaDon.setMaSP(listMaSP);
                hoaDon.setMaGH(finalMaGH);

                // Debug thông tin gửi đi
                Log.d("HoaDon", "MaUser: " + maUser);
                Log.d("HoaDon", "TongTien: " + totalPrice);
                Log.d("HoaDon", "NgayMua: " + getCurrentDate());
                Log.d("HoaDon", "DiaChi: " + edt_diachi.getText().toString());
                Log.d("HoaDon", "MaSP: " + listMaSP.toString());
                Log.d("HoaDon", "MaGH: " + finalMaGH);

                // Gửi API tạo hóa đơn
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_Host.DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API_Host apiService = retrofit.create(API_Host.class);
                Call<HoaDonModel> call = apiService.addHoaDon(hoaDon);
                call.enqueue(new Callback<HoaDonModel>() {
                    @Override
                    public void onResponse(Call<HoaDonModel> call, Response<HoaDonModel> response) {
                        if (response.isSuccessful()) {
                            HoaDonModel order = response.body();
                            Toast.makeText(getContext(), "Mua hàng thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Log.e("Order Error", "Error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<HoaDonModel> call, Throwable t) {
                        Log.e("Request Failed", t.getMessage());
                    }
                });
            }
        });

        view1.findViewById(R.id.btnHuy_TT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }



    private void showXoa(String maGH){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiService = retrofit.create(API_Host.class);
        Call<GioHangModel> call = apiService.del_gh(maGH);
        call.enqueue(new Callback<GioHangModel>() {
            @Override
            public void onResponse(Call<GioHangModel> call, Response<GioHangModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                } else {

                }
            }

            @Override
            public void onFailure(Call<GioHangModel> call, Throwable t) {

            }
        });
    }
}
