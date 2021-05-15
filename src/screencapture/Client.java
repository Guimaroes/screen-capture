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

/**
 *
 * @author Gabriel
 */
public class Client {
    private static final int PORT = 5000;
    private static final String IP = "127.0.0.1";
    
    private static boolean b = true;
    
    public static void main( String[] args ) {
        try {
            Socket socket = new Socket( IP, PORT );
            
            ObjectInputStream input = new ObjectInputStream( socket.getInputStream() );

            byte[] bytes = input.readAllBytes();
            
            BufferedImage bi = ImageUtils.toBufferedImage( bytes );
            
            ImageUtils.showImage( bi );
            
            socket.close();
            
        } catch ( IOException e ) {
            System.out.println( e );
        }
    }
}
