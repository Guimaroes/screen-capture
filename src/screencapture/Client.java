/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencapture;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.sound.sampled.*;

/**
 *
 * @author Gabriel
 */
public class Client {
    private static final int PORT = 5000;
    private static final String IP = "127.0.0.1";
      
    private static TargetDataLine line;
    
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
            
//          frame.setResizable( true );
            frame.add( panel );
            frame.pack();
            frame.setVisible( true );
            
            while ( true ) {
                
                Socket socket = new Socket( IP, PORT );
                
                ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );
                
                //AUDIO---------------------------------------------------------------
                AudioFormat format = getAudioFormat();
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            
                if(!AudioSystem.isLineSupported(info)) {
                    System.out.println("Line not supported");
                    System.exit(0);
                }
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
              
                AudioInputStream in = new AudioInputStream(line);
                //---------------------------------------------------------------------

                byte[] bytes = input.readAllBytes();

                BufferedImage bi = ImageUtils.toBufferedImage( bytes );

//                ImageUtils.showImage( bi );
                panel.setImage( bi );
                panel.repaint();
                
                input.close();
                
                socket.close();
                
                line.close();
                
                
            }
        } catch ( IOException e ) {
            System.out.println( e );
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
