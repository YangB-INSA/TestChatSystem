package Model.reseau;

import Model.User;
import Model.messages.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class testSender {
	static int port = 52425;
	static DatagramSocket socket;
	
    public static void main(String[] args) throws IOException {
    	 //serialize the message
    	User user = new User("pierre","127.0.0.1");
        Message co = new Connected(user);
        Message deco = new Disconnected(user);
        Message chg = new NameChanged(user,"cobra");
        Message m=chg;
        
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