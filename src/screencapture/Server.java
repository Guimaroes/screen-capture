package screencapture;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Gabriel
 */
public class Server {
    
    private static final int PORT = 5000;
    
    private static boolean b = true;
    
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket( PORT );
        Socket socket = server.accept();

        ObjectOutputStream output = new ObjectOutputStream( socket.getOutputStream() );

        Robot robot = new Robot();
        BufferedImage bi = robot.createScreenCapture( new Rectangle( Toolkit.getDefaultToolkit().getScreenSize() ) );
        
        byte[] bytes = ImageUtils.toByteArray( bi, "png" );
        
        output.write( bytes );
        
        output.close();
        
        socket.close();
    }
}
