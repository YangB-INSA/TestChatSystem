/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ross-Netbook
 */
public class TCPReceive extends Thread {
    
    private final int serverPort ;
    private final String fileOutput;
	private ServerSocket serverSocket;
	private  Socket connexionSocket;
	private boolean receive;
	
    public TCPReceive( int port, String fileRecu) throws IOException{
       super();
        serverPort=port;
        fileOutput=fileRecu;
        serverSocket = null;
        connexionSocket=null;
        receive=false;
    }

    public boolean isReceive() {
        return receive;
    }
    
    @Override
    public  void run() {
		System.out.println("----file ready to be received: "+fileOutput);  
		byte[] aByte = new byte[1];
        int bytesRead;

       InputStream is = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            connexionSocket = serverSocket.accept();
                                

            is = connexionSocket.getInputStream();
            
        } catch (IOException ex) {
            // Do exception handling
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (is != null) {

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream( fileOutput );
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(aByte, 0, aByte.length);

                do {
                        baos.write(aByte);
                        bytesRead = is.read(aByte);
                } while (bytesRead != -1);

                bos.write(baos.toByteArray());
                bos.flush();
                bos.close();
                serverSocket.close();
                System.out.println("----File received: "+fileOutput);
                System.out.println("----Thread TCP Receive closed");
                receive=true;
            } catch (IOException ex) {
                // Do exception handling
            }
        }
    }
    
    
}
