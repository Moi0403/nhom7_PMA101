package fpoly.anhntph36936.happyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import fpoly.anhntph36936.happyfood.Frag_User.Frag_Home;
import fpoly.anhntph36936.happyfood.Frag_ADM.QL_SP_ADM;
import fpoly.anhntph36936.happyfood.Main.Main_Login;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_adm;
    Toolbar toolbar_adm;
    NavigationView navi_adm;
    ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_adm = findViewById(R.id.drawerLayout_frame4);
        toolbar_adm = findViewById(R.id.toolbar_frame4);
        navi_adm = findViewById(R.id.navigationView_frame4);
        View headerLayout = navi_adm.getHeaderView(0);

        setSupportActionBar(toolbar_adm);
        getSupportActionBar().setTitle("Happy Food");

        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer_adm, toolbar_adm, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawer_adm.addDrawerListener(drawerToggle);

        fragmentManager = getSupportFragmentManager();
        fragment = new Frag_Home();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_frame4, fragment).commit();

        navi_adm.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                showMenu(itemId);
                return true;
            }
        });
    }

    private boolean showMenu(int itemId){
        Fragment fragment = null;
        String title = "";
        try {
            if (itemId == R.id.menu_home){
                fragment = new Frag_Home();
                title = "Trang chủ";
            } else if (itemId == R.id.menu_ql_sanpham){
                fragment = new QL_SP_ADM();
                title = "Quản lý sản phẩm";
            } else if (itemId == R.id.menu_nd_exit) {
                showExit();
                return true;
            }

            if (fragment != null) {
                drawer_adm.close();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_frame4, fragment).commit();
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    private void showExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit !");
        builder.setIcon(R.drawable.thongbao);
        builder.setMessage("Bạn muốn đăng xuất ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, Main_Login.class));
                finish();
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
