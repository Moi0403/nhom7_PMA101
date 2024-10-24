package chienncph29246.fpoly.example.nhom2_duan1_happyfoo.Frag_ADM.childFrag_of_QL_DonHangFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import chienncph29246.fpoly.example.nhom2_duan1_happyfoo.Adapter.QLDonHangAdapter;
import chienncph29246.fpoly.example.nhom2_duan1_happyfoo.Dao.HoaDonChiTietDAO;
import chienncph29246.fpoly.example.nhom2_duan1_happyfoo.Model.HoaDonChiTiet;
import chienncph29246.fpoly.example.nhom2_duan1_happyfoo.R;


public class QL_daXacNhan_Fragment extends Fragment {
    private RecyclerView ryc_ql_daXacNhan;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_da_xac_nhan, container, false);
        ryc_ql_daXacNhan = view.findViewById(R.id.ryc_ql_daXacNhan);
        loadData(ryc_ql_daXacNhan);
        return view;
    }
    public void loadData(RecyclerView recyclerView){
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getContext());
        ArrayList<HoaDonChiTiet> list = hoaDonChiTietDAO.getDonHangByHDCTForAdmin(1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        QLDonHangAdapter adapter = new QLDonHangAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(ryc_ql_daXacNhan);
    }
}
