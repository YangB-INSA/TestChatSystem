package test;


import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import Controller.Controller_reseau;

public class UDPReceiver {
	private static DatagramSocket servSock ;
    private Controller_reseau control;
    private static DatagramPacket packet;
    private static InetAddress remoteAddr;
    public static int port;
	
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        port = 15559;
    	servSock = new DatagramSocket(port);
    	while (true) {
	    	 byte[] recvBuf = new byte[5000];
	         packet = new DatagramPacket(recvBuf,recvBuf.length);
	         System.out.println("Server en écoute sur le port " + port + "\n");
	         servSock.receive(packet);
	         System.out.println("Packet received from " + packet.getAddress().getHostName());
	         remoteAddr = packet.getAddress();
	         
	         //déserialisation de la data reçu
	         System.out.println("Message en cours de déserialisation");
	         ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf); 
	         ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
	         Message m = (Message)is.readObject();  
	         System.out.println("---Message déserialisé---");
	         is.close();
	         
	         if (m instanceof Connected) {
	         	System.out.println(((Connected) m).getNickname() + ": " + m.getText()+ "\n");
	           }
	         
	 		  if (m instanceof Disconnected) {
	 			System.out.println(((Disconnected) m).getNickname() + " vient de se deconnecter"+ "\n");
	 		  }
	           
	 		  if (m instanceof NameChanged) {
	 			System.out.println(( "Cet utilisateur à changer son nom en '"+ ((NameChanged) m).getNickname()) + "'" +"\n");
	 	      }
         
    	} 
          
    }
}