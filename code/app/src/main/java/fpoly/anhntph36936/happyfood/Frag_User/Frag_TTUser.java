package fpoly.anhntph36936.happyfood.Frag_User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import fpoly.anhntph36936.happyfood.Main.Main_Login;
import fpoly.anhntph36936.happyfood.MainActivity;
import fpoly.anhntph36936.happyfood.R;

public class Frag_TTUser extends Fragment {
    LinearLayout item_tt, item_exit;
    TextView tv_tt_user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ttuser, container, false);
        item_exit = view.findViewById(R.id.item_exit);
        item_tt = view.findViewById(R.id.item_tt);
        tv_tt_user = view.findViewById(R.id.tv_tt_user);
        item_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExit();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");

        tv_tt_user.setText("Xin chào, "+user);
        return view;
    }
    private void showExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Exit !");
        builder.setIcon(R.drawable.thongbao);
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), Main_Login.class));
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
