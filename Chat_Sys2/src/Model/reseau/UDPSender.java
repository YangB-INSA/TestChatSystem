package Model.reseau;

import java.io.*;
import java.net.*;
import Model.messages.Message;

public class UDPSender {
	private DatagramSocket socket;
    
    public UDPSender() throws SocketException{
        socket = new DatagramSocket();
    }
    
    public void sendTo(Message m, String hostName, int desPort) {
        try {
            
            //serialize the message
        	//System.out.println("serialisation du message");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream os = new ObjectOutputStream(byteStream);
            os.writeObject(m);
            os.flush();
            //get the byte array of the object
            byte[] sendBuf = byteStream.toByteArray(); 
            //System.out.println("message sérialisé");
            
            InetAddress address = InetAddress.getByName(hostName);
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, desPort);
            socket.send(packet);
            //System.out.println("message envoyé vers " + address.getHostAddress());
            os.close();
        }
        catch (UnknownHostException e)
        {
          System.err.println("Exception:  " + e);
        }
        catch (IOException e)    {}
  }

}
