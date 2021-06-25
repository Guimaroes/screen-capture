package screencapture;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Gabriel
 */
public class MicServer {

    private AudioInputStream audioInputStream;
    private static AudioInputStream ais;
    private static AudioFormat format;
    private static boolean status = true;
    private static int port = 50005;
    private static int sampleRate = 44100;

    static DataLine.Info dataLineInfo;
    static SourceDataLine sourceDataLine;

    public static void main(String args[]) throws Exception 
    {
        System.out.println("Server started at port:"+port);

        DatagramSocket serverSocket = new DatagramSocket(port);

        byte[] receiveData = new byte[4096];

        format = new AudioFormat(sampleRate, 16, 2, true, false);
        dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(format);
        sourceDataLine.start();

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        ByteArrayInputStream baiss = new ByteArrayInputStream(receivePacket.getData());

        while (status == true) 
        {
            serverSocket.receive(receivePacket);
            ais = new AudioInputStream(baiss, format, receivePacket.getLength());
            toSpeaker(receivePacket.getData());
        }

        sourceDataLine.drain();
        sourceDataLine.close();
    }

    public static void toSpeaker(byte soundbytes[]) {
        try 
        {
            System.out.println("At the speaker");
            sourceDataLine.write(soundbytes, 0, soundbytes.length);
        } catch (Exception e) {
            System.out.println("Not working in speakers...");
            e.printStackTrace();
        }
    }
}
