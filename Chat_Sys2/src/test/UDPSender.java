package test;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UDPSender {
	static int port = 52425;
	static DatagramSocket socket;
	
    public static void main(String[] args) throws IOException {
    	 //serialize the message
    	Message co = new Connected("enfin connecté","bernard");
    	Message deco = new Disconnected("déconnecté","paul");
    	Message chg = new NameChanged("pierre");
    	Message m=co;
    	socket = new DatagramSocket();
    	System.out.println("serialisation du message");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
        ObjectOutputStream os = new ObjectOutputStream(byteStream);
        os.writeObject(m);
        os.flush();
        //get the byte array of the object
        byte[] sendBuf = byteStream.toByteArray(); 
        System.out.println("message sérialisé " + m.getClass());
        
        InetAddress address = InetAddress.getByName("localhost");
        DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address,port);
        socket.send(packet);
        System.out.println("message envoyé vers " + address.getHostAddress() + " sur le port " + port);
        os.close();
    }
}