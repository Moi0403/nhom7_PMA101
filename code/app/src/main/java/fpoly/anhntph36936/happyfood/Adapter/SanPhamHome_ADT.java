package fpoly.anhntph36936.happyfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.Main.Main_ChiTietSP;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;

public class SanPhamHome_ADT extends RecyclerView.Adapter<SanPhamHome_ADT.MyViewHolder> {
    Context context;
    ArrayList<SanPhamModel> list;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
}
