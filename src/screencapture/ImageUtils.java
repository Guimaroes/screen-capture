/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.JFrame;

public class ImageUtils {

    // convert BufferedImage to byte[]
    public static byte[] toByteArray( BufferedImage bi, String format ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bi, format, baos );
        byte[] bytes = baos.toByteArray();
        
        return bytes;
    }

    // convert byte[] to BufferedImage
    public static BufferedImage toBufferedImage( byte[] bytes ) throws IOException {
        InputStream is = new ByteArrayInputStream( bytes );
        BufferedImage bi = ImageIO.read( is );
        
        return bi;
    }
    
    // show BufferedImage
    public static void showImage( BufferedImage bi ) {
        JFrame frame = new JFrame();
        ImagePanel panel = new ImagePanel();
        frame.setResizable( true );
        frame.add( panel );
        frame.pack();
        
        panel.setImage( bi );
        panel.repaint();
        
        frame.setVisible( true );
    }
}
