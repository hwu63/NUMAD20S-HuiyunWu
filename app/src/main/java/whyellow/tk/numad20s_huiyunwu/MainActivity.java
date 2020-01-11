package whyellow.tk.numad20s_huiyunwu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout infos = findViewById(R.id.infos);
        infos.setVisibility(View.GONE);

        View coverview = findViewById(R.id.coverview);
        coverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout infos = findViewById(R.id.infos);
                infos.setVisibility(View.GONE);
            }
        });

    }

    public void display_about(android.view.View view) {
        LinearLayout infos = findViewById(R.id.infos);
        infos.setVisibility(View.VISIBLE);
    }

    public void send_email(android.view.View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","wu.hui@husky.neu.edu", null));
        startActivity(Intent.createChooser(intent, "Email"));
    }

    public void go_to_website(android.view.View view){
        Uri url = Uri.parse("https://github.com/hwu63/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

}