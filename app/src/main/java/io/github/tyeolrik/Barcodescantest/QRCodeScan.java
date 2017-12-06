package io.github.tyeolrik.Barcodescantest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRCodeScan extends Activity {

    CameraSource cameraSource;
    SurfaceView cameraSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

        cameraSurface = (SurfaceView) findViewById(R.id.cameraSurface); // SurfaceView 선언 :: Boilerplate

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE) // QR_CODE로 설정하면 좀더 빠르게 인식할 수 있습니다.
                .build();
        Log.d("NowStatus", "BarcodeDetector Build Complete");

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(29.8f) // 프레임 높을 수록 리소스를 많이 먹겠죠
                .setRequestedPreviewSize(1080, 1920)    // 확실한 용도를 잘 모르겠음. 필자는 핸드폰 크기로 설정
                .setAutoFocusEnabled(true)  // AutoFocus를 안하면 초점을 못 잡아서 화질이 많이 흐립니다.
                .build();
        Log.d("NowStatus", "CameraSource Build Complete");

        // Callback을 이용해서 SurfaceView를 실시간으로 Mobile Vision API와 연결
        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {   // try-catch 문은 Camera 권한획득을 위한 권장사항
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraSurface.getHolder());  // Mobile Vision API 시작
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();    // SurfaceView가 종료되었을 때, Mobile Vision API 종료
                Log.d("NowStatus", "SurfaceView Destroyed and CameraSource Stopped");
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Log.d("NowStatus", "BarcodeDetector SetProcessor Released");
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // 바코드가 인식되었을 때 무슨 일을 할까?
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() != 0) {
                    String barcodeContents = barcodes.valueAt(0).displayValue; // 바코드 인식 결과물
                    Log.d("Detection", barcodeContents);
                }
            }
        });
    }
}
