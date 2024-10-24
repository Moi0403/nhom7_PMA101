package fpoly.anhntph36936.happyfood.childFrag_of_QL_DonHangFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.anhntph36936.happyfood.R;


public class QL_choXacNhan_Fragment extends Fragment {
    private RecyclerView ryc_ql_choXacNhan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_cho_xac_nhan, container, false);
        ryc_ql_choXacNhan = view.findViewById(R.id.ryc_ql_choXacNhan);

        return view;
    }

}
