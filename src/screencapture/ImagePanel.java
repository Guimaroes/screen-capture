/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencapture;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Gabriel
 */
public class ImagePanel extends JPanel {
    
    private BufferedImage image;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension( 500, 500 );
    }

    @Override
    protected void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        if ( image != null ) {
            g.drawImage( image, 0, 0, this );
        }
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage( BufferedImage image ) {
        this.image = image;
    }
}
