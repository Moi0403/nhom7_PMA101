package fpoly.anhntph36936.happyfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Main.Main_ChiTietSP;
import fpoly.anhntph36936.happyfood.Model.GioHangModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPhamHome_ADT extends RecyclerView.Adapter<SanPhamHome_ADT.MyViewHolder> {
    Context context;
    ArrayList<SanPhamModel> list;
    ArrayList<UserModel> list_user;

    public SanPhamHome_ADT(Context context, ArrayList<SanPhamModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPhamModel model = list.get(position);
        Picasso.get().load(model.getAnhSP()).into(holder.imgSanPham_itemGrid);
        holder.tv_tensp.setText(model.getTenSP());
        holder.tvGiaSanPham_itemGrid.setText(""+model.getGiaSP()+ ".000đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main_ChiTietSP.class);
                intent.putExtra("idSP", model.get_id());
                context.startActivity(intent);
            }
        });

        holder.imgGioHang_itemGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maUser = getMaUser();
                if (maUser.isEmpty()) {
                    Toast.makeText(context, "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng GioHangModel
                GioHangModel gioHangModel = new GioHangModel();
                gioHangModel.setMaUser(maUser);  // Đặt ID người dùng từ SharedPreferences
                gioHangModel.setMaSP(model);  // Đặt ID sản phẩm từ model
                gioHangModel.setSoLuong(1);  // Thiết lập số lượng sản phẩm mặc định là 1
                gioHangModel.setTrangThaiMua(0);  // Thiết lập trạng thái mua (0: chưa mua)

               checkAndAddToCart(gioHangModel);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSanPham_itemGrid, imgGioHang_itemGrid;
        TextView tvGiaSanPham_itemGrid, tv_tensp;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham_itemGrid = itemView.findViewById(R.id.imgSanPham_itemGrid);
            imgGioHang_itemGrid = itemView.findViewById(R.id.imgGioHang_itemGrid);
            tvGiaSanPham_itemGrid = itemView.findViewById(R.id.tvGiaSanPham_itemGrid);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }
    private String getMaUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");  // Lấy ID người dùng từ SharedPreferences
    }

    public void checkAndAddToCart(GioHangModel gioHangModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);

        // Lấy giỏ hàng hiện tại của người dùng
        Call<ArrayList<GioHangModel>> call = apiService.getGioHang(gioHangModel.getMaUser());
        call.enqueue(new Callback<ArrayList<GioHangModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHangModel>> call, Response<ArrayList<GioHangModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<GioHangModel> gioHangList = response.body();
                    boolean productExists = false;
                    int existingProductIndex = -1;
                    for (int i = 0; i < gioHangList.size(); i++) {
                        GioHangModel item = gioHangList.get(i);
                        if (item.getMaSP() != null && item.getMaSP().get_id() != null &&
                                item.getMaSP().get_id().equals(gioHangModel.getMaSP().get_id())) {
                            productExists = true;
                            existingProductIndex = i;
                            break;
                        }
                    }

                    if (productExists) {
                        GioHangModel existingItem = gioHangList.get(existingProductIndex);
                        int newQuantity = existingItem.getSoLuong() + gioHangModel.getSoLuong();
                        existingItem.setSoLuong(newQuantity);

                        updateGH(existingItem);
                    } else {
                        addGH(gioHangModel);
                    }
                } else {
                    Toast.makeText(context, "Không thể lấy giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addGH(GioHangModel gioHangModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);
        Call<GioHangModel> call = apiService.addGH(gioHangModel);

        call.enqueue(new Callback<GioHangModel>() {
            @Override
            public void onResponse(Call<GioHangModel> call, Response<GioHangModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("AddToCartError", "Response code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(context, "Thêm giỏ hàng không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GioHangModel> call, Throwable t) {
                Log.e("AddToCartError", t.getMessage(), t);
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateGH(GioHangModel gioHangModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Host apiService = retrofit.create(API_Host.class);

        Call<GioHangModel> call = apiService.updateGH(gioHangModel);
        call.enqueue(new Callback<GioHangModel>() {
            @Override
            public void onResponse(Call<GioHangModel> call, Response<GioHangModel> response) {
                if (response.isSuccessful()) {
                    String message = "Cập nhật giỏ hàng thành công";
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "Cập nhật giỏ hàng không thành công. Response code: " + response.code() + ", Message: " + response.message();
                    Log.e("UpdateCartError", errorMessage);

                   Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GioHangModel> call, Throwable t) {
                // Xử lý lỗi kết nối khi không thể gửi yêu cầu
                String errorMessage = "Lỗi kết nối: " + t.getMessage();
                Log.e("UpdateCartError", errorMessage, t);
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }



}
