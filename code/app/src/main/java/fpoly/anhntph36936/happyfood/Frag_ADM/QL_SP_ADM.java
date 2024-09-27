package fpoly.anhntph36936.happyfood.Frag_ADM;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.anhntph36936.happyfood.R;


public class QL_SP_ADM extends Fragment {
    RecyclerView rc_ql_sp_adm;

    EditText edt_masp, edt_anhsp, edt_tensp, edt_trongluong, edt_giasp, edt_phanloai, edt_mota;
    public static final String TAG = "ql_sp";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_sp_adm, container, false);
        return view;
    }


}
