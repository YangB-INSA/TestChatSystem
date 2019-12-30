package Model.reseau;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import Controller.Controller_reseau;
import Model.User;
import Model.messages.Message;
public class UDPReceiver extends Thread {
	private DatagramSocket servSock ;
    private Controller_reseau control;
    private DatagramPacket packet;
    private InetAddress remoteAddr;
    public int port;
    
    public UDPReceiver(Controller_reseau controler) throws SocketException{ 
        this.control = controler;
        this.port = control.getPort();
        this.servSock = new DatagramSocket(port); 
    }

    private Message recvmsg()
    {
    	
        try{
          byte[] recvBuf = new byte[5000];
          packet = new DatagramPacket(recvBuf,recvBuf.length);
          
          System.out.println("Thread Receiver en écoute sur le port " + port + "\n");
          servSock.receive(packet);
          remoteAddr = packet.getAddress();
          //System.out.println("Packet received from " + remoteAddr);
         
          
          //System.out.println("Deserialisation du packet");
          ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf); 
          ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
          Message m = (Message)is.readObject();  
          //System.out.println("Packet received from User = " + m.getSender());
          is.close();
          
          return(m);
       
        }
        catch (IOException e)
        {
          System.err.println("Exception:  " + e);
          e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        { 
        	e.printStackTrace(); 
        }   
        
        return null;
    }
    
    @Override
        public void run() {
            while(true){ 
                try {
                    control.messageHandle( recvmsg(), remoteAddr.getHostAddress());
                } catch (IOException ex) {
                }
            }
        }
}
