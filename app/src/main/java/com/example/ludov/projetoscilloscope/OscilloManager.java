package com.example.ludov.projetoscilloscope;

public final class OscilloManager implements Tranceiver.TranceiverDataListenet , Tranceiver.TranceiverEventListener{
    private static volatile OscilloManager instance = null;


    //atributs de classe

    //Constructeur
    private OscilloManager(){
        super();
    }

    public final static OscilloManager getInstance(){
        if (OscilloManager.getInstance() == null)
        {
            synchronized (OscilloManager.class){
                if (OscilloManager.instance == null)
                {
                    OscilloManager.instance = new OscilloManager();
                }
            }
        }
        return OscilloManager.instance;
    }

    @Override
    public void onTransceiverStateChanged(int state) {

    }

    @Override
    public void onTransceiverUnableToConnect() {

    }

    @Override
    public void onTranceiverConnectionLoast() {

    }

    @Override
    public void onTranceiverDataReceived(FrameProcessor data) {

    }


    //Méthodes de class
}
