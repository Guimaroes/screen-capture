/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencapture;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author Gabriel
 */
public class Client {
    private static final int PORT = 5000;
    private static final String IP = "177.44.248.11";
    
    public static void main( String[] args ) {
        try {
            
            ImagePanel panel = new ImagePanel();
            
            JFrame frame = new JFrame();
            
//            frame.setResizable( true );
            frame.add( panel );
            frame.pack();
            frame.setVisible( true );
            
            while ( true ) {
                
                Socket socket = new Socket( IP, PORT );
                
                ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );

                byte[] bytes = input.readAllBytes();

                BufferedImage bi = ImageUtils.toBufferedImage( bytes );

//                ImageUtils.showImage( bi );
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
