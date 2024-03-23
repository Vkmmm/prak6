package com.example.prak6;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {
    private View view;
    private WindowManager windowManager;
    public MyService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        super.onCreate();
        String textToDisplay = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
    windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
    view = inflater.inflate(R.layout.layout, null);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
    params.gravity = Gravity.BOTTOM | Gravity.CENTER;
    Button openAppButton = view.findViewById(R.id.button3);
        openAppButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.prak6");
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
            stopSelf();
        }
    });

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "Пользователь");

        // Обновление текста на баннере
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(userName + ",");

        windowManager.addView(view, params);
        windowManager.updateViewLayout(view,params);
}

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Удаляем виджет аппаратного наложения при уничтожении сервиса
        if (view != null && view.getWindowToken() != null) {
            windowManager.removeView(view);
        }
    }
}