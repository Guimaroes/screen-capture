package screencapture;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Gabriel
 */
public class MicClient 
{
    public byte[] buffer;
    static AudioInputStream ais;

    public static void main(String[] args)
    {
        TargetDataLine line;
        DatagramPacket dgp; 

        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 44100.0f;
        int channels = 2;
        int sampleSize = 16;
        boolean bigEndian = false;
        InetAddress addr;

        AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " not supported.");
            return;
        }

        try
        {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   

            byte[] data = new byte[4096];

            addr = InetAddress.getByName("127.0.0.1");
            DatagramSocket socket = new DatagramSocket();
            while (true) {
                line.read(data, 0, data.length);
                dgp = new DatagramPacket (data,data.length,addr,50005);
                socket.send(dgp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
