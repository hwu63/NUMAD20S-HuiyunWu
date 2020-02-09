package whyellow.tk.numad20s_huiyunwu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MathMagic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_primes);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 2;

            @Override
            public void run() {
                TextView counter = findViewById(R.id.counter);

                if (isPrime(count)){counter.setTextColor(Color.RED);}
                else {counter.setTextColor(Color.DKGRAY);}
                counter.setText(String.valueOf(count));
                count++;

                handler.postDelayed(this, 1500);
            }

            private boolean isPrime(int num){
                for (int i = 2; i <= num/2; i++){
                    if (num % i == 0){
                        return false;
                    }
                }
                return true;
            }
        };

        Button find_primes = findViewById(R.id.find_primes);
        find_primes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(runnable);
                }
        });

        View coverview = findViewById(R.id.coverview_fp);
        coverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
            }
        });

        Switch onOffSwitch = findViewById(R.id.watch_time);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) startRepeat();
                    else stopRepeat();

            }
        });


        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                TextView powerOrBattery = findViewById(R.id.powerOrBattery);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                    powerOrBattery.setText("POWER!!!");
                    powerOrBattery.setTextColor(0xFF0EA00E);
                } else {
                    powerOrBattery.setText("battery");
                    powerOrBattery.setTextColor(Color.GRAY);
                }
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

}
    NotificationManagerCompat notificationManagerCompat;
    private void createNotification() {
        String CHANNEL_ID = "MyChannel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Calendar c = Calendar.getInstance();
        Date time = c.getTime();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.my_ic_launcher)
                .setContentTitle("It's "+ hour +":"+ minute +" NOW")
                .setContentText(String.valueOf(time))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, builder.build());

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:notificationLock");
            wl.acquire();
    }

    final Handler handler_t = new Handler();
    Runnable runnable_t;

    private void startRepeat(){
        runnable_t = new Runnable(){
            public void run(){
                createNotification();
                handler_t.postDelayed(this, 60000);
            }
        };
        handler_t.post(runnable_t);
    }

    private void stopRepeat(){
        handler_t.removeCallbacks(runnable_t);
        notificationManagerCompat.cancelAll();
    }
}


