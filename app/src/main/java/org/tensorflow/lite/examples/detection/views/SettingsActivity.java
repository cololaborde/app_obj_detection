package org.tensorflow.lite.examples.detection.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.Status;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.Utils;
import org.tensorflow.lite.examples.detection.storage.AppSharedPreferences;
import org.tensorflow.lite.examples.detection.storage.Constants;

public class SettingsActivity extends AppCompatActivity {

    int downloadIdOne;
    String dirPath;
    Button buttonOne, buttonCancelOne;
    Switch aSwitch;
    TextView tvDownload;
    EditText percent, lineWidth, gdriveID;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_settings);

        dirPath = Utils.getRootDirPath(getApplicationContext());

        buttonOne = findViewById(R.id.buttonOne);
        buttonCancelOne = findViewById(R.id.buttonCancelOne);
        aSwitch = findViewById(R.id.switch1);
        percent = findViewById(R.id.editText);
        lineWidth = findViewById(R.id.editText1);
        gdriveID = findViewById(R.id.gdriveID);


        aSwitch.setChecked(AppSharedPreferences.getShowTitle());

        int prob = Math.round(AppSharedPreferences.getProbFrom() * 100);

        percent.setText(String.valueOf(prob));
        lineWidth.setText(String.valueOf(Math.round(AppSharedPreferences.getLineWidth())));
        gdriveID.setText(AppSharedPreferences.getGdriveFileId());

        tvDownload = findViewById(R.id.txv_download_model);
        findViewById(R.id.download_model).setVisibility(View.VISIBLE);

        buttonOne.setOnClickListener(view -> {
            downloadModel();
        });

        findViewById(R.id.buttonSave).setOnClickListener(view -> {
            if (percent.getText().toString().isEmpty()) {
                Toast.makeText(this, "Compruebe porcenaje", Toast.LENGTH_SHORT).show();
                return;
            }

            int percentN = Integer.parseInt(percent.getText().toString());
            if (percentN < 10 || percentN > 90) {
                Toast.makeText(this, "Compruebe porcenaje", Toast.LENGTH_SHORT).show();
                return;
            }

            if (lineWidth.getText().toString().isEmpty()) {
                Toast.makeText(this, "Compruebe linea", Toast.LENGTH_SHORT).show();
                return;
            }

            if (gdriveID.getText().toString().isEmpty()) {
                Toast.makeText(this, "Compruebe ID del archivo", Toast.LENGTH_SHORT).show();
                return;
            }

            int lineW = Integer.parseInt(lineWidth.getText().toString());
            if (lineW == 0) {
                Toast.makeText(this, "Compruebe linea", Toast.LENGTH_SHORT).show();
                return;
            }

            AppSharedPreferences.setProbFrom((float) percentN / 100);
            AppSharedPreferences.setLineWidth((float) lineW);
            AppSharedPreferences.setShowTitle(aSwitch.isChecked());

            if (gdriveID.getText().toString().equals(AppSharedPreferences.getGdriveFileId())) {
                goToDetectorActivity();
            } else {
                AppSharedPreferences.setGdriveFileId(gdriveID.getText().toString());
                downloadModel();
            }
        });

        findViewById(R.id.back).setOnClickListener(view -> finish());

        if (AppSharedPreferences.getModelAvailable()) {
            buttonOne.setText(getString(R.string.update));
        } else {
            buttonOne.setText(getString(R.string.download));
            tvDownload.setText(getString(R.string.model_not_found));
            tvDownload.setVisibility(View.VISIBLE);
        }
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

    private void goToDetectorActivity() {
        Intent intent;
        intent = new Intent(this, DetectorActivity.class);
        SettingsActivity.this.startActivity(intent);
        SettingsActivity.this.finishAffinity();
    }
}
