QR코드를 스캔하는 예제 앱
====================
How to use Google Mobile Vision API
---------------------
일단 Android Studio를 설치하고 실행해보세요! 블로그에 자세한 설명이 있습니다. [블로그로 가기](https://tyeolrik.github.io/android/2017/12/05/Android-Barcode-Detector.html)

### 테스트 사양

- Windows 7 (64Bits)
- Android Studio 3.0.1
- Compile SDK Version: API26: Oreo
- Google Mobile Vision API: play-services-vision:11.6.2
- Gradle Version: 4.1
- Test Device: LG V10

### 외부 라이브러리

```groovy
dependencies {
    
    // ... 중간생략
    
    // Google Mobile Vision API 사용
    implementation 'com.google.android.gms:play-services-vision:11.6.2'
}
```
