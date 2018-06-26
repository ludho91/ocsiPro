package com.example.ludov.projetoscilloscope;

public class FrameProcessor {
    byte[] toFrame(byte commande[])
    {
        int index = 3;
        int indexFinalFrame = 0;
        byte[] frame={};
        byte frameTmp[]={};
        frameTmp[0]=0x05;
        frameTmp[1]=0x00;
        frameTmp[2]=(byte)commande.length;
        for (int i = 3;i<commande.length+3;i++)
        {
            index +=1;
            frameTmp[i] = commande[i-3];
        }
        frameTmp[index++] = ctrl(commande);
        frameTmp[index] = 0x04;

        for (int i = 0; i < frameTmp.length; i++, indexFinalFrame++ )
        {
            if (i <= 1){
                frame[indexFinalFrame] = frameTmp[i];
            }
            else if (i==frameTmp.length-1){
                frame[indexFinalFrame] = frameTmp[i];
            }
            else if(i==frameTmp.length-2 && (frameTmp[i] == 0x04 || frameTmp[i] == 0x05))
            {
                frame[indexFinalFrame] = 0x06;
                indexFinalFrame++;
                frame[indexFinalFrame] = frameTmp[i];
            }
            else if (i == 2)
            {
                if (frameTmp[i] == 0x05 ||frameTmp[i] == 0x04){
                    frame[indexFinalFrame] = 0x06;
                    indexFinalFrame++;
                    frame[indexFinalFrame] = frameTmp[i];
                }
                else
                {
                    frame[indexFinalFrame] = frameTmp[i];
                }
            }
            else
            {
                if (frameTmp[i] == 0x04 ||frameTmp[i]==0x05)
                {
                    frame[indexFinalFrame] = 0x06;
                    indexFinalFrame++;
                    frame[indexFinalFrame] = frameTmp[i];
                }
                else if (frameTmp[i] == 0x06)
                {
                    frame[indexFinalFrame] = frameTmp[i];
                    indexFinalFrame++;
                    frame[indexFinalFrame] = 0x0C;
                }
                else
                {
                    frame[indexFinalFrame] = frameTmp[i];
                }
            }

        }

        return frame;
    }

    private byte ctrl(byte commande[])
    {
        int somme =  commande.length;
        for (int i = 0; i < commande.length; i++)
        {
            somme += commande[i];
        }
        somme = somme%256;
        byte somme2 = (byte)somme;
        return somme2;
    }
}
