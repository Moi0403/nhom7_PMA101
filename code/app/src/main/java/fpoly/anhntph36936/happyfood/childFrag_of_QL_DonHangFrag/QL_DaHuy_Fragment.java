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


public class QL_DaHuy_Fragment extends Fragment {
    private RecyclerView ryc_ql_daHuy;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_da_huy, container, false);
        ryc_ql_daHuy = view.findViewById(R.id.ryc_ql_daHuy);

        return view;
    }

}
