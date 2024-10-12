package fpoly.anhntph36936.happyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.Model.HoaDonModel;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.R;

public class HoaDon_ADT extends RecyclerView.Adapter<HoaDon_ADT.MyViewHolder> {
    Context context;
    ArrayList<HoaDonModel> list;

    public HoaDon_ADT(Context context, ArrayList<HoaDonModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDonModel hoaDon = list.get(position);

        holder.tvThoiGianDatHang_donhang.setText(hoaDon.getNgayMua());
        holder.tvDiaChiGiaoHang_donhang.setText(hoaDon.getDiaChi());

        // Xử lý trạng thái đơn hàng
        switch (hoaDon.getTrangThaiDH()) {
            case 1:
                holder.tvTrangThaiDonHang_donhang.setText("Đang chờ");
                break;
            case 2:
                holder.tvTrangThaiDonHang_donhang.setText("Đã giao");
                break;
            case 3:
                holder.tvTrangThaiDonHang_donhang.setText("Đã hủy");
                break;
            default:
                holder.tvTrangThaiDonHang_donhang.setText("Không xác định");
        }
        holder.tvTongTien_donhang.setText(hoaDon.getTongTien() + " .000đ");
        if (hoaDon.getMaSP() != null && hoaDon.getMaSP().size() > 0) {
//            SanPhamModel sanPham = hoaDon.getMaSP().get(0);
//            holder.tvTenSanPham_donhang.setText(sanPham.getTenSP());
//            holder.tvGiaSanPham_donhang.setText(sanPham.getGiaSP() + " .000đ");
//
//            // Hiển thị ảnh sản phẩm, nếu có URL ảnh
//            if (!sanPham.getAnhSP().isEmpty()) {
//                Picasso.get().load(sanPham.getAnhSP()).into(holder.imgAnhSanPham_donhang);
//            }
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhSanPham_donhang;
        TextView tvTenSanPham_donhang, tvSoLuongSanPham_donhang,
                tvGiaSanPham_donhang, tvThoiGianDatHang_donhang, tvDiaChiGiaoHang_donhang,
                tvTrangThaiDonHang_donhang, tvHuyDon_donhang, tvTongTien_donhang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSanPham_donhang = itemView.findViewById(R.id.imgAnhSanPham_donhang);
            tvTenSanPham_donhang = itemView.findViewById(R.id.tvTenSanPham_donhang);
            tvSoLuongSanPham_donhang = itemView.findViewById(R.id.tvSoLuongSanPham_donhang);
            tvGiaSanPham_donhang = itemView.findViewById(R.id.tvGiaSanPham_donhang);
            tvThoiGianDatHang_donhang = itemView.findViewById(R.id.tvThoiGianDatHang_donhang);
            tvDiaChiGiaoHang_donhang = itemView.findViewById(R.id.tvDiaChiGiaoHang_donhang);
            tvTrangThaiDonHang_donhang = itemView.findViewById(R.id.tvTrangThaiDonHang_donhang);
            tvHuyDon_donhang = itemView.findViewById(R.id.tvHuyDon_donhang);
            tvTongTien_donhang = itemView.findViewById(R.id.tvTongTien_donhang);
        }
    }



}
