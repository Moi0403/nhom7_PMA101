package fpoly.anhntph36936.happyfood.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpoly.anhntph36936.happyfood.Frag_User.Frag_Home;
import fpoly.anhntph36936.happyfood.Frag_User.Frag_GioHang;
import fpoly.anhntph36936.happyfood.Frag_User.Frag_TTUser;
import fpoly.anhntph36936.happyfood.R;

public class Main_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_navi);

        loadFragment(new Frag_Home());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_user_home) {
                    selectedFragment = new Frag_Home();
                } else if (item.getItemId() == R.id.menu_user_giohang) {
                    selectedFragment = new Frag_GioHang();
                } else if (item.getItemId() == R.id.menu_user_thongtin) {
                    selectedFragment = new Frag_TTUser();
                }
                return loadFragment(selectedFragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        // Chỉ thay đổi nếu fragment khác với fragment hiện tại
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_user, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}