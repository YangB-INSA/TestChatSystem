/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ross-Netbook
 */
public class TCPSend extends Thread {
    private final File fileToSend;    
    private Socket connectionSocket;
    private BufferedOutputStream outToClient;
    private final int port;
    private final String IPServer;
    //a modifier si ça marche:
    public TCPSend(File fileSend,int p,String IP){
        super();
       fileToSend=fileSend;
       port=p;
       IPServer=IP;
            connectionSocket = null;
            outToClient = null;
       
    }

     @Override   
   public void run() {

        while (true) {
          
            try {
                
                System.out.println("----File ready to send:" +fileToSend.getName());
                this.connectionSocket = new Socket(IPServer,port);
                
                this.outToClient = new BufferedOutputStream(this.connectionSocket.getOutputStream());
            } catch (IOException ex) {
                // Do exception handling
            }

            if (outToClient != null) {
                File myFile = this.fileToSend;
                if (myFile.exists()){
                System.out.println("----File exists: "+fileToSend.getName());
                }
                else{
                    System.out.println("------------\n ERROR File does not exists anymore \n ------------");
                    System.exit(1);
                }
                byte[] mybytearray = new byte[(int) myFile.length()];

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile);
                } catch (FileNotFoundException ex) {
                    // Do exception handling
                }
                BufferedInputStream bis = new BufferedInputStream(fis);

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();
                    System.out.println("----File sended: " +fileToSend.getName());
                    System.out.println("----Thread TCP Send Closed");

                    // File sent, exit the main method
                    return;
                } catch (IOException ex) {
                    // Do exception handling
                }
            }
        }
    }
    
   
    
}
