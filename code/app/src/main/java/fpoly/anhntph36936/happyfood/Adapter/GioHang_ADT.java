package fpoly.anhntph36936.happyfood.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
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

        // Kiểm tra nếu maSP là đối tượng và lấy thông tin từ đó
        SanPhamModel spModel = gioHangModel.getMaSP();  // Lấy đối tượng SPModel từ GioHangModel

        if (spModel != null) {
            // Gọi API để lấy thông tin sản phẩm
            getProductDetails(spModel.get_id(), holder);  // Truyền id của sản phẩm từ SPModel

            // Hiển thị số lượng mua và trạng thái checkbox
            holder.tvSo_luong_mua.setText(String.valueOf(gioHangModel.getSoLuong()));
            holder.ckbMua_hang.setChecked(gioHangModel.getTrangThaiMua() == 1);
        } else {
            // Nếu không có thông tin sản phẩm, có thể hiển thị lỗi hoặc ẩn đi
            Log.e("GioHang_ADT", "Sản phẩm không hợp lệ hoặc không có maSP.");
        }
        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDE(position);
            }
        });
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

    public void showDE(int position){
        GioHangModel gioHangModel = list_gh.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.thongbao);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + gioHangModel.getMaSP().getTenSP() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Host.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    API_Host apiService = retrofit.create(API_Host.class);
                    Call<ArrayList<GioHangModel>> call = apiService.del_gh(gioHangModel.get_id());
                    call.enqueue(new Callback<ArrayList<GioHangModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<GioHangModel>> call, Response<ArrayList<GioHangModel>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list_gh.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list_gh.size());
                                notifyDataSetChanged();
                            } else {

                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {

                        }
                    });
                } catch (Exception e){
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }


}
