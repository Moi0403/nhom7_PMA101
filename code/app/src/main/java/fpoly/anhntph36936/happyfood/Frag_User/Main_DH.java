package fpoly.anhntph36936.happyfood.Frag_User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import fpoly.anhntph36936.happyfood.R;

public class Main_DH extends AppCompatActivity {
    ImageView img_back;
    RecyclerView recyclerView;
    String maUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dh);
        img_back = findViewById(R.id.imgBack_dh);
        recyclerView = findViewById(R.id.rc_dh);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        maUser = getMaUser();

        if (TextUtils.isEmpty(maUser)) {
            Toast.makeText(Main_DH.this, "Vui lòng đăng nhập để xem đơn hàng", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
    private String getMaUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyUser", this.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        Log.d("User ID", "Current User ID: " + userId);
        return userId;
    }
}