package fpoly.anhntph36936.happyfood.Frag_User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.anhntph36936.happyfood.API.APIResponse;
import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Adapter.GioHang_ADT;
import fpoly.anhntph36936.happyfood.Adapter.SanPhamHome_ADT;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
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
    public Button btnMua_hang;
    ArrayList<GioHangModel> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giohang, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_gio_hang);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        maUser = sharedPreferences.getString("maUser", null);

        if (maUser == null) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để xem giỏ hàng", Toast.LENGTH_SHORT).show();
            // Có thể điều hướng đến màn hình đăng nhập hoặc xử lý phù hợp
        } else {
            getGH(); // Gọi phương thức để lấy giỏ hàng
        }
        return view;
    }
    private void getGH() {
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
                        Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                    } else {
                        gioHangAdt = new GioHang_ADT(getContext(), list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(gioHangAdt);
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GioHang", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
