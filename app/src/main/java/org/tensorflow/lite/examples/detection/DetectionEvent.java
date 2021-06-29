package org.tensorflow.lite.examples.detection;

import org.tensorflow.lite.examples.detection.storage.Disease;

import java.util.ArrayList;

public class DetectionEvent {

    public ArrayList<Disease> diseases;

    public DetectionEvent(ArrayList<Disease> diseases) {
        this.diseases = diseases;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }
}
