package fpoly.anhntph36936.happyfood.Frag_User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Adapter.SanPhamHome_ADT;
import fpoly.anhntph36936.happyfood.Adapter.SanPham_ADT;
import fpoly.anhntph36936.happyfood.Interface.ItemClickListener;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frag_Home extends Fragment {
    RecyclerView recyclerView;
    ArrayList<SanPhamModel> list_sp = new ArrayList<>();
    SanPhamHome_ADT sanPhamAdt;
    EditText edt_tim;
    ImageView imv_tim;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_frame4);
        edt_tim = view.findViewById(R.id.edTimKiem);
        imv_tim = view.findViewById(R.id.imgTimKiem);
        LoadSP();

        imv_tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tim();
            }
        });
        return view;
    }
    private void LoadSP(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiService = retrofit.create(API_Host.class);
        Call<ArrayList<SanPhamModel>> call = apiService.ListSP();
        call.enqueue(new Callback<ArrayList<SanPhamModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPhamModel>> call, Response<ArrayList<SanPhamModel>> response) {
                if (response.isSuccessful()){
                    list_sp = response.body();
                    sanPhamAdt = new SanPhamHome_ADT(getContext(), list_sp);
                    int numColumns = 2;
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));
                    recyclerView.setAdapter(sanPhamAdt);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {

            }
        });
    }

    private void tim(){
        String tenSP = edt_tim.getText().toString();
        if (tenSP.isEmpty()){
            Toast.makeText(getContext(),"Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            LoadSP();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiService = retrofit.create(API_Host.class);
        Call<ArrayList<SanPhamModel>> call = apiService.tim(tenSP);
        call.enqueue(new Callback<ArrayList<SanPhamModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPhamModel>> call, Response<ArrayList<SanPhamModel>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    list_sp.clear();
                    list_sp.addAll(response.body());
                    sanPhamAdt.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
