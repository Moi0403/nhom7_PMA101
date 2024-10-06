package fpoly.anhntph36936.happyfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
                if (list_user != null && !list_user.isEmpty()) {
                    String id_user = list_user.get(0).get_id(); // Lấy ID người dùng đầu tiên

                    // Tạo đối tượng sản phẩm từ danh sách
                    SanPhamModel sanPham = list.get(position);
                    String maSP = sanPham.get_id(); // Lấy ID sản phẩm

                    // Tạo đối tượng GioHangModel
                    GioHangModel gioHangModel = new GioHangModel();
//                    gioHangModel.setMaUser(list_user.get(0)); // Thiết lập đối tượng UserModel
//                    gioHangModel.setMaSP(sanPham); // Thiết lập đối tượng SanPhamModel
                    gioHangModel.setSoLuong(1); // Thiết lập số lượng mặc định là 1
                    gioHangModel.setTrangThaiMua(0); // Thiết lập trạng thái mua (0 = chưa mua)

                    // Gọi hàm thêm vào giỏ hàng
                    addGH(gioHangModel);
                } else {
                    Toast.makeText(context, "Danh sách người dùng không có", Toast.LENGTH_SHORT).show();
                }
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
    public void addGH(GioHangModel gioHangModel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Host.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Host apiService = retrofit.create(API_Host.class);
        Call<ArrayList<GioHangModel>> call = apiService.addGH(gioHangModel);
        call.enqueue(new Callback<ArrayList<GioHangModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHangModel>> call, Response<ArrayList<GioHangModel>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Thêm giỏ thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm giỏ không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GioHangModel>> call, Throwable t) {

            }
        });
    }

}
