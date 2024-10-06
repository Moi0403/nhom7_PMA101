package fpoly.anhntph36936.happyfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.API.API_Host;
import fpoly.anhntph36936.happyfood.Model.SanPhamModel;
import fpoly.anhntph36936.happyfood.Model.UserModel;
import fpoly.anhntph36936.happyfood.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_ADT extends RecyclerView.Adapter<User_ADT.MyViewHover>{
    Context context;
    ArrayList<UserModel> list;
    String maUser;

    public User_ADT(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new MyViewHover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHover holder, @SuppressLint("RecyclerView") int position) {
        UserModel model = list.get(position);
        maUser = model.get_id();
        holder.tv_sdt.setText("Phone: "+model.getPhone());
        holder.tv_user.setText("Username: "+model.getUsername());
        holder.tv_pass.setText("Password: "+model.getPassword());
        holder.tv_add.setText("Address: "+model.getAddress());
        holder.tv_role.setText("Role: "+model.getRole());

        holder.imv_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDE(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHover extends RecyclerView.ViewHolder {


        TextView tv_sdt, tv_user, tv_pass, tv_add, tv_role;
        ImageView imv_xoa;


        public MyViewHover(@NonNull View itemView) {
            super(itemView);
            imv_xoa = itemView.findViewById(R.id.imv_del_nguoidung);
            tv_sdt = itemView.findViewById(R.id.tv_sdt);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_pass = itemView.findViewById(R.id.tv_pass);
            tv_add = itemView.findViewById(R.id.tv_add);
            tv_role = itemView.findViewById(R.id.tv_role);
        }
    }
    public void showDE(int position){
        UserModel userModel = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.thongbao);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + userModel.getUsername() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(API_Host.DOMAIN)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    API_Host apiService = retrofit.create(API_Host.class);
                    Call<ArrayList<UserModel>> call = apiService.del_user(userModel.get_id());
                    call.enqueue(new Callback<ArrayList<UserModel>>() {
                        @Override
                        public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                            } else {

                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {

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
