package org.tensorflow.lite.examples.detection.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.Status;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.tensorflow.lite.examples.detection.PermissionEvent;
import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.Utils;
import org.tensorflow.lite.examples.detection.storage.AppSharedPreferences;
import org.tensorflow.lite.examples.detection.storage.Constants;

public class SplashActivity extends AppCompatActivity {

    int downloadIdOne;
    String dirPath;
    Button buttonOne, buttonCancelOne, btnPermiso;
    TextView txvPermiso;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        btnPermiso = findViewById(R.id.btnPermiso);
        btnPermiso.setOnClickListener(view -> Utils.requestPermission(SplashActivity.this));
        txvPermiso = findViewById(R.id.txvPermiso);

        if (AppSharedPreferences.getModelAvailable()) {
            if (Utils.hasPermission(this)) {
                goToDetectorActivity();
            } else {
                Utils.requestPermission(this);
            }
        } else {
            if (Utils.hasPermission(this)) {
                setupDownloadModel();
            } else {
                Utils.requestPermission(this);
            }
        }
    }

    private void goToDetectorActivity() {
        Intent intent;
        intent = new Intent(this, DetectorActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupDownloadModel() {
        dirPath = Utils.getRootDirPath(getApplicationContext());

        buttonOne = findViewById(R.id.buttonOne);
        buttonCancelOne = findViewById(R.id.buttonCancelOne);

        findViewById(R.id.txv_download_model).setVisibility(View.VISIBLE);
        findViewById(R.id.download_model).setVisibility(View.VISIBLE);

        findViewById(R.id.txv_download_model).setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        buttonOne.setOnClickListener(view -> {
            if (Utils.hasPermission(this)) {
                downloadModel();
            } else {
                Utils.requestPermission(this);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PermissionEvent event) {
        if (event.isAccepted()) {
            btnPermiso.setVisibility(View.GONE);
            txvPermiso.setVisibility(View.GONE);

            if (AppSharedPreferences.getModelAvailable()) {
                goToDetectorActivity();
            } else {
                setupDownloadModel();
            }
        } else {
            btnPermiso.setVisibility(View.VISIBLE);
            txvPermiso.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        Utils.handlePermissionsResult(requestCode, grantResults, this);
    }

    void downloadModel() {
        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.pause(downloadIdOne);
            return;
        }

        downloadIdOne = PRDownloader.download("https://drive.google.com/uc?id=" + AppSharedPreferences.getGdriveFileId() + "&export=download", dirPath, Constants.modelFileName)
                .build()
                .setOnStartOrResumeListener(() -> {
                    buttonOne.setEnabled(true);
                    buttonOne.setText(getString(R.string.pause));
                    buttonCancelOne.setVisibility(View.VISIBLE);
                })
                .setOnPauseListener(() -> buttonOne.setText(getString(R.string.resume)))
                .setOnCancelListener(() -> {
                    buttonOne.setText(getString(R.string.download));
                    buttonCancelOne.setVisibility(View.GONE);
                    downloadIdOne = 0;
                })
                .setOnProgressListener(progress -> {
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        AppSharedPreferences.setModelAvailable(true);
                        goToDetectorActivity();
                    }

                    @Override
                    public void onError(Error error) {
                        buttonOne.setText(getString(R.string.download));
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        downloadIdOne = 0;
                        buttonCancelOne.setVisibility(View.GONE);
                        buttonOne.setEnabled(true);
                    }
                });
    }
}
