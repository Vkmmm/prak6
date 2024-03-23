package com.example.prak6;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart(){
        super.onStart();
        stopOverlayService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopOverlayService();
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view,
                            Fragment1.class, bundle)
                    .commit();
        }
    }
    private void stopOverlayService(){
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        startOverlayService(); // Вызываем сервис при выходе из приложения
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        stopOverlayService(); // Вызываем сервис при выходе из приложения
    }

    private void startOverlayService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }
    protected void onDestroy(){
        super.onDestroy();
        stopOverlayService();
    }
}