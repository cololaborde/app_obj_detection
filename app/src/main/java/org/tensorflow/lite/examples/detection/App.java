package org.tensorflow.lite.examples.detection;

import android.app.Application;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;

import org.tensorflow.lite.examples.detection.storage.AppSharedPreferences;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(false)
                .build();
        PRDownloader.initialize(this, config);

        if(!AppSharedPreferences.getSaved()){
            AppSharedPreferences.setShowTitle(false);
            AppSharedPreferences.setLineWidth(10.0f);
            AppSharedPreferences.setProbFrom(0.8f);
            AppSharedPreferences.setSaved(true);
        }
    }

    public static App getInstance() {
        return instance;
    }
}
