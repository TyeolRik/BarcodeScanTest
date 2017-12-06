package io.github.tyeolrik.Barcodescantest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button startButton;

    private final static int CAMERA_PERMISSIONS_GRANTED = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);   // Button Boilerplate

        getCameraPermission();

        // 다음 Activity로 넘어가기 위한 onClickListener
        // 이렇게 한 이유는 Permission Check 가
        // 기본적으로 UI Thread가 아닌 다른 Thread 에서 동시에 실행되기 때문에
        // 첫 실행 때, 권한이 없어서 SurfaceView 에서 addCallback 처리를 제대로 못하는 상황이 생긴다.
        // 그래서 검은 화면이 나온다. 고로, 아예 Activity를 다르게 해줬다.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNextActivity = new Intent(getApplicationContext(), QRCodeScan.class);
                startActivity(goNextActivity);
            }
        });
    }

    private boolean getCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // 권한이 왜 필요한지 설명이 필요한가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                Toast.makeText(this, "카메라 사용을 위해 확인버튼을 눌러주세요!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        CAMERA_PERMISSIONS_GRANTED);
                return true;
            }
        }
    }
}
