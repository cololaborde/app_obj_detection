package org.tensorflow.lite.examples.detection.storage;

public class AppSharedPreferences {
    private static final String TAKE_PROB_FROM = "take_prob_from";
    private static final String LINE_WIDTH = "line_width";
    private static final String MODEL_AVAILABLE = "model_available";
    private static final String SHOW_TITLE = "show_title";
    private static final String GDRIVE_FILE_ID = "gdrive_file_id";
    private static final String SAVED = "saved";

    public static Float getProbFrom() {
        return GenericSharedPrefs.getFloat(TAKE_PROB_FROM);
    }

    public static void setProbFrom(Float takeProbFrom) {
        GenericSharedPrefs.putFloat(TAKE_PROB_FROM, takeProbFrom);
    }

    public static Boolean getModelAvailable() {
        return GenericSharedPrefs.getBoolean(MODEL_AVAILABLE);
    }

    public static void setModelAvailable(Boolean modelAvailable) {
        GenericSharedPrefs.putBoolean(MODEL_AVAILABLE, modelAvailable);
    }

    public static Boolean getShowTitle() {
        return GenericSharedPrefs.getBoolean(SHOW_TITLE, false);
    }

    public static void setShowTitle(Boolean showTitle) {
        GenericSharedPrefs.putBoolean(SHOW_TITLE, showTitle);
    }

    public static Float getLineWidth() {
        return GenericSharedPrefs.getFloat(LINE_WIDTH);
    }

    public static void setLineWidth(Float lineWidth) {
        GenericSharedPrefs.putFloat(LINE_WIDTH, lineWidth);
    }

    public static void setSaved(Boolean saved) {
        GenericSharedPrefs.putBoolean(SAVED, saved);
    }

    public static Boolean getSaved() {
        return GenericSharedPrefs.getBoolean(SAVED, false);
    }

    public static void setGdriveFileId(String id) {
        GenericSharedPrefs.putString(GDRIVE_FILE_ID, id);
    }

    public static String getGdriveFileId() {
        return GenericSharedPrefs.getString(GDRIVE_FILE_ID, Constants.modelGDrive);
    }
}
