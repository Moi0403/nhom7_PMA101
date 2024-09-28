package fpoly.anhntph36936.happyfood.Frag_ADM;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.APIResponse;
import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Adapter.SanPham_ADT;
import fpoly.anhntph36936.happyfood.Interface.ItemClickListener;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class QL_SP_ADM extends Fragment {
    RecyclerView rc_ql_sp_adm;
    SanPham_ADT sanPhamAdt;
    ArrayList<SanPhamModel> list_sp = new ArrayList<>();
    EditText edt_masp, edt_anhsp, edt_tensp, edt_trongluong, edt_giasp, edt_phanloai, edt_mota;
    public static final String TAG = "ql_sp";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_sp_adm, container, false);
        rc_ql_sp_adm = view.findViewById(R.id.rc_ql_sp_adm);

        LoadSP();

        view.findViewById(R.id.fab_SanPham).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThemorSua(getContext(),0, null);
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
                    sanPhamAdt = new SanPham_ADT(getContext(), list_sp);
                    rc_ql_sp_adm.setLayoutManager(new LinearLayoutManager(getContext()));
                    rc_ql_sp_adm.setAdapter(sanPhamAdt);

                    sanPhamAdt.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void UpdateItem(int position) {
                            SanPhamModel sanPham = list_sp.get(position);
                            showThemorSua(getContext(), 1, sanPham);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {

            }
        });
    }

    private void showThemorSua(Context context, int type, SanPhamModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_add_sp, null);
        builder.setView(view1);
        AlertDialog dialog = builder.create();

        edt_masp = view1.findViewById(R.id.edt_masp);
        edt_masp.setEnabled(false);
        edt_anhsp = view1.findViewById(R.id.edt_anhsp);
        edt_tensp = view1.findViewById(R.id.edt_tensp);
        edt_phanloai = view1.findViewById(R.id.edt_phanloai);
        edt_trongluong = view1.findViewById(R.id.edt_trongluong);
        edt_giasp = view1.findViewById(R.id.edt_giasp);
        edt_mota = view1.findViewById(R.id.edt_mota);

        edt_phanloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] phanloai = {"Food", "Water"};
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setIcon(R.drawable.thongbao);
                builder1.setTitle("Chọn phân loại sản phẩm: ");
                builder1.setItems(phanloai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_phanloai.setText(phanloai[which]);
                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });
        if (type != 0 ){
            edt_masp.setText(model.get_id()+"");
            edt_anhsp.setText(model.getAnhSP());
            edt_tensp.setText(model.getTenSP());
            edt_phanloai.setText(model.getPhanloaiSP());
            edt_trongluong.setText(model.getTrongluongSP()+"");
            edt_giasp.setText(model.getGiaSP()+"");
            edt_mota.setText(model.getMotaSP());
        }

        view1.findViewById(R.id.btnThem_themsp).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String id = edt_masp.getText().toString();
                String anh = edt_anhsp.getText().toString();
                String ten = edt_tensp.getText().toString();
                String ploai = edt_phanloai.getText().toString();
                String tluong = edt_trongluong.getText().toString();
                String gia = edt_giasp.getText().toString();
                String mota = edt_mota.getText().toString();

                if (anh.isEmpty() || ten.isEmpty() || ploai.isEmpty() || tluong.isEmpty() || gia.isEmpty() || mota.isEmpty() ){
                    Toast.makeText(getContext(), "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (type == 0) {
                        SanPhamModel model1 = new SanPhamModel();
                        model1 = new SanPhamModel();
                        model1.setAnhSP(anh);
                        model1.setTenSP(ten);
                        model1.setPhanloaiSP(ploai);
                        model1.setTrongluongSP(Integer.parseInt(tluong));
                        model1.setGiaSP(Integer.parseInt(gia));
                        model1.setMotaSP(mota);
                        try {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(API_Host.DOMAIN)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            API_Host apiService = retrofit.create(API_Host.class);
                            Call<ArrayList<SanPhamModel>> call = apiService.addPro(model1);
                            call.enqueue(new Callback<ArrayList<SanPhamModel>>() {
                                @Override
                                public void onResponse(Call<ArrayList<SanPhamModel>> call, Response<ArrayList<SanPhamModel>> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(getContext(), "Thêm SP thành công", Toast.LENGTH_SHORT).show();
                                        LoadSP();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {
                                    Toast.makeText(getContext(), "Thêm SP không thành công", Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        model.set_id(id);
                        model.setAnhSP(anh);
                        model.setTenSP(ten);
                        model.setPhanloaiSP(ploai);
                        model.setTrongluongSP(Integer.parseInt(tluong));
                        model.setGiaSP(Integer.parseInt(gia));
                        model.setMotaSP(mota);
                        try {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(API_Host.DOMAIN)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            API_Host apiService = retrofit.create(API_Host.class);
                            Call<ArrayList<SanPhamModel>> call = apiService.up_sp(id, model);
                            call.enqueue(new Callback<ArrayList<SanPhamModel>>() {
                                @Override
                                public void onResponse(Call<ArrayList<SanPhamModel>> call, Response<ArrayList<SanPhamModel>> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Sửa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                        LoadSP();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {
                                    Toast.makeText(context, "Sửa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Lỗi khi thao tác với cơ sở dũ liệu", e);
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        view1.findViewById(R.id.btnHuy_themsp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
