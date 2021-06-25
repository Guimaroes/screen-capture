/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencapture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.sound.sampled.*;

/**
 *
 * @author Gabriel
 */
public class Client {
    private static final int PORT = 5000;
    private static final String IP = "127.0.0.1";
    
    static AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        
        return format;
    }
    public static void main( String[] args ) {
        try {
            
            ImagePanel panel = new ImagePanel();
            
            JFrame frame = new JFrame();
            
            frame.add( panel );
            frame.pack();
            frame.setVisible( true );
            
            while ( true ) {
                
                Socket socket = new Socket( IP, PORT );
                
                ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );
                
                byte[] bytes = input.readAllBytes();

                BufferedImage bi = ImageUtils.toBufferedImage( bytes );

                panel.setImage( bi );
                panel.repaint();
                
                input.close();
                
                socket.close();
            }
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
}
