package fpoly.anhntph36936.happyfood.Frag_ADM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import fpoly.anhntph36936.happyfood.R;

public class QL_DonHang_ADM extends Fragment {
    private TabLayout tabLayout_ql_donhang;
    private ViewPager2 viewPager_ql_donhang;
    private TabLayoutQLDonHang_Adapter tabLayoutQLDonHang_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_don_hang, container, false);
        tabLayout_ql_donhang = view.findViewById(R.id.tabLayout_ql_donhang);
        viewPager_ql_donhang = view.findViewById(R.id.viewPager_ql_donhang);
        tabLayoutQLDonHang_adapter = new TabLayoutQLDonHang_Adapter(getActivity());
        viewPager_ql_donhang.setAdapter(tabLayoutQLDonHang_adapter);

        new TabLayoutMediator(tabLayout_ql_donhang, viewPager_ql_donhang, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Đã xác nhận");
                        break;
                    case 2:
                        tab.setText("Đang giao");
                        break;
                    case 3:
                        tab.setText("Đã giao");
                        break;
                    case 4:
                        tab.setText("Đã húy");
                }
            }
        }).attach();

        return view;
    }
}
