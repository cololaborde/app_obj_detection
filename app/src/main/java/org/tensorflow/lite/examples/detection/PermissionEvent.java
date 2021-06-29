package org.tensorflow.lite.examples.detection;

public class PermissionEvent {
    private boolean accepted;

    public PermissionEvent(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
