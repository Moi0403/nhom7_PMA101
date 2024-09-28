package fpoly.anhntph36936.happyfood.Adapter;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Interface.ItemClickListener;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPham_ADT extends RecyclerView.Adapter<SanPham_ADT.SanPhamViewHolder> {

    Context context;
    ArrayList<SanPhamModel> list_sp;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }

    public SanPham_ADT(Context context, ArrayList<SanPhamModel> list_sp) {
        this.context = context;
        this.list_sp = list_sp;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sp_adm, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPhamModel model = list_sp.get(position);
        Picasso.get().load(model.getAnhSP()).into(holder.imv_anhsp);
        holder.tv_tensp.setText("Tên sản phẩm: " +model.getTenSP());
        holder.tv_phanloai.setText("Phân loại: " +model.getPhanloaiSP());
        String trongluong;
        if ("Food".equals(model.getPhanloaiSP())) {
            trongluong = model.getTrongluongSP() + " gram";
        } else if ("Water".equals(model.getPhanloaiSP())) {
            trongluong = model.getTrongluongSP() + " ml";
        } else {
            trongluong = String.valueOf(model.getTrongluongSP());
        }
        holder.tv_trongluong.setText("Trọng lượng: " + trongluong);
        holder.tv_giasp.setText("Giá: " +model.getGiaSP()+ ".000đ");
        holder.tv_mota.setText("Mô tả: " +model.getMotaSP());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (itemClickListener != null){
                        itemClickListener.UpdateItem(position);
                    }
                } catch (Exception e){
                    Log.e("SanPham_ADT", "Error in long click", e);
                }
                return true;
            }
        });


        holder.imv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDE(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_sp.size();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_anhsp, imv_delete;
        TextView tv_idsp, tv_tensp, tv_phanloai, tv_trongluong, tv_giasp, tv_mota;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_anhsp = itemView.findViewById(R.id.imv_anhsp);
            imv_delete = itemView.findViewById(R.id.imv_delete);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_phanloai = itemView.findViewById(R.id.tv_phanloai);
            tv_trongluong = itemView.findViewById(R.id.tv_trongluong);
            tv_giasp = itemView.findViewById(R.id.tv_giasp);
            tv_mota = itemView.findViewById(R.id.tv_mota);
        }
    }
    public void showDE(int position){
        SanPhamModel sanPham = list_sp.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.thongbao);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getTenSP() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Host.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    API_Host apiService = retrofit.create(API_Host.class);
                    Call<ArrayList<SanPhamModel>> call = apiService.del_sp(sanPham.get_id());
                    call.enqueue(new Callback<ArrayList<SanPhamModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<SanPhamModel>> call, Response<ArrayList<SanPhamModel>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list_sp.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list_sp.size());
                            } else {

                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<SanPhamModel>> call, Throwable t) {

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
