package org.tensorflow.lite.examples.detection.storage;

import java.util.ArrayList;

public class Detection {
    private ArrayList<Disease> diseases;
    private int count = 0;

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(ArrayList<Disease> diseases) {
        this.diseases = diseases;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
