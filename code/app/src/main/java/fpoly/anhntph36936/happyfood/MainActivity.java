package fpoly.anhntph36936.happyfood;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer_adm;
    Toolbar toolbar_adm;
    NavigationView navi_adm;
    ActionBarDrawerToggle drawerToggle;
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
    }
}