package com.example.ludov.projetoscilloscope;

public interface Tranceiver {
    interface TranceiverEventListener{
        void onTransceiverStateChanged(int state);
        void onTransceiverUnableToConnect();
        void onTranceiverConnectionLoast();
    }
    interface TranceiverDataListenet{
        void onTranceiverDataReceived(FrameProcessor data);
    }
}
