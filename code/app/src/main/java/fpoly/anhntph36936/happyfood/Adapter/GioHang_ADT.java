package fpoly.anhntph36936.happyfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHang_ADT extends RecyclerView.Adapter<GioHang_ADT.ViewHolder> {
    Context context;
    ArrayList<GioHangModel> list_gh;

    public GioHang_ADT(Context context, ArrayList<GioHangModel> list_gh) {
        this.context = context;
        this.list_gh = list_gh;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangModel gioHangModel = list_gh.get(position);

        // Gọi API để lấy thông tin sản phẩm
        getProductDetails(gioHangModel.getMaSP(), holder);

        holder.tvSo_luong_mua.setText(String.valueOf(gioHangModel.getSoLuong()));
        holder.ckbMua_hang.setChecked(gioHangModel.getTrangThaiMua() == 1);
    }

    @Override
    public int getItemCount() {
        return list_gh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvSo_luong_mua, tvTen_san_pham, tvGia_san_pham;
        ImageView imgAnh_san_pham, imgMinus, imgPlus, imgCancel;
        CheckBox ckbMua_hang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSo_luong_mua = itemView.findViewById(R.id.tvSo_luong_mua);
            tvTen_san_pham = itemView.findViewById(R.id.tvTen_san_pham);
            tvGia_san_pham = itemView.findViewById(R.id.tvGia_san_pham);
            imgAnh_san_pham = itemView.findViewById(R.id.imgAnh_san_pham);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgCancel = itemView.findViewById(R.id.imgCancel);
            ckbMua_hang = itemView.findViewById(R.id.ckbMua_hang);
        }
    }
    private void getProductDetails(String maSP, ViewHolder holder) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiHost = retrofit.create(API_Host.class);
        Call<SanPhamModel> call = apiHost.getSP(maSP);
        call.enqueue(new Callback<SanPhamModel>() {
            @Override
            public void onResponse(Call<SanPhamModel> call, Response<SanPhamModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPhamModel sanPham = response.body();
                    holder.tvTen_san_pham.setText(sanPham.getTenSP());
                    holder.tvGia_san_pham.setText(sanPham.getGiaSP() + ".000đ");
                    Picasso.get().load(sanPham.getAnhSP()).into(holder.imgAnh_san_pham);
                } else {
                    Log.e("GioHang_ADT", "Error fetching product data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SanPhamModel> call, Throwable t) {
                Log.e("GioHang_ADT", "Error fetching data: " + t.getMessage());
            }
        });
    }
}
