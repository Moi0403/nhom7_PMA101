package fpoly.anhntph36936.happyfood.Adapter;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Frag_User.Frag_GioHang;
import fpoly.anhntph36936.happyfood.Interface.OnPaymentListener;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.ThanhToanModel;
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
    private int totalPrice = 0;
    private Frag_GioHang frag;
    int price;
    private OnPaymentListener paymentListener;

    public GioHang_ADT(Context context, ArrayList<GioHangModel> list_gh, Frag_GioHang frag) {
        this.context = context;
        this.list_gh = list_gh;
        this.frag = frag;
    }
    public void setOnPaymentListener(OnPaymentListener listener) {
        this.paymentListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GioHangModel gioHangModel = list_gh.get(position);
        SanPhamModel spModel = gioHangModel.getMaSP();

        if (spModel != null) {

            getProductDetails(spModel.get_id(), holder);
            holder.tvSo_luong_mua.setText(String.valueOf(gioHangModel.getSoLuong()));
            holder.tvGia_san_pham.setText(gioHangModel.getGiaGH() + ".000đ");

            holder.ckbMua_hang.setChecked(gioHangModel.getTrangThaiMua() == 1);
            updateTotalPrice();

            holder.ckbMua_hang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.ckbMua_hang.isChecked()) {
                        gioHangModel.setTrangThaiMua(1);
                        String anhSp = gioHangModel.getMaSP().getAnhSP();
                        String tenSp = gioHangModel.getMaSP().getTenSP();
                        int giaSanPham = gioHangModel.getGiaGH();
                        int sluong = gioHangModel.getSoLuong();
                        int ttTT = 0;

                    } else {
                        gioHangModel.setTrangThaiMua(0);
                    }
                    updateTotalPrice();
                    updateSoLuong(gioHangModel);
                }
            });
            holder.imgMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongHienTai = gioHangModel.getSoLuong();
                    if (soLuongHienTai > 1) {
                        gioHangModel.setSoLuong(soLuongHienTai - 1);
                        int giaSanPham = spModel.getGiaSP();
                        gioHangModel.setGiaGH(giaSanPham * gioHangModel.getSoLuong());
                        holder.tvSo_luong_mua.setText(String.valueOf(gioHangModel.getSoLuong()));
                        holder.tvGia_san_pham.setText(gioHangModel.getGiaGH() + ".000đ");
                        updateSoLuong(gioHangModel);
                        updateTotalPrice();
                    }
                }
            });

            holder.imgPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongHienTai = gioHangModel.getSoLuong();
                    gioHangModel.setSoLuong(soLuongHienTai + 1);
                    int giaSanPham = spModel.getGiaSP();
                    gioHangModel.setGiaGH(giaSanPham * gioHangModel.getSoLuong());
                    holder.tvGia_san_pham.setText(gioHangModel.getGiaGH() + ".000đ");
                    holder.tvSo_luong_mua.setText(String.valueOf(gioHangModel.getSoLuong()));
                    updateSoLuong(gioHangModel);
                    updateTotalPrice();
                }
            });
        } else {
            Log.e("GioHang_ADT", "Sản phẩm không hợp lệ hoặc không có maSP.");
        }
        // Xử lý xóa sản phẩm trong giỏ hàng
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
    private String getMaUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
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
                    Call<GioHangModel> call = apiService.del_gh(gioHangModel.get_id());
                    call.enqueue(new Callback<GioHangModel>() {
                        @Override
                        public void onResponse(Call<GioHangModel> call, Response<GioHangModel> response) {
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
                        public void onFailure(Call<GioHangModel> call, Throwable t) {

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

    private void updateSoLuong(GioHangModel gioHangModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiHost = retrofit.create(API_Host.class);
        Call<ArrayList<GioHangModel>> call = apiHost.up_gh(gioHangModel.get_id(), gioHangModel);
        call.enqueue(new Callback<ArrayList<GioHangModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHangModel>> call, Response<ArrayList<GioHangModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("GioHang_ADT", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {

            }
        });
    }

    private void updateTotalPrice() {
        int total = 0;
        for (GioHangModel item : list_gh) {
            if (item.getTrangThaiMua() == 1) {
                total += item.getGiaGH();
            }
        }
        frag.tvTotal.setText(total + ".000");
    }

    private void addThanhToan(ThanhToanModel model) {
        if (paymentListener != null) {
            paymentListener.onPaymentRequested(model); // Gọi listener
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_Host.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_Host apiHost = retrofit.create(API_Host.class);
            Call<ThanhToanModel> call = apiHost.addThanhToan(model);
            call.enqueue(new Callback<ThanhToanModel>() {
                @Override
                public void onResponse(Call<ThanhToanModel> call, Response<ThanhToanModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Thanh toán thành công ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ThanhToanModel> call, Throwable t) {
                    Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}