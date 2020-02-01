package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import Controller.Controller_Interface;
import Model.messages.*;

public class History {
	
    private static Path historyPath = FileSystems.getDefault().getPath(".appData", ".History");

    private static void createDirectory() {
        File historyFile = historyPath.toFile();
        if (!historyFile.exists()) {
        	historyFile.mkdirs();
        }
    }

    public void createHistory(String senderAddr) {
        createDirectory();
        File historyFile = historyPath.resolve(senderAddr).toFile();
        if (!historyFile.exists()) {
            try {
            	historyFile.createNewFile();
                System.out.println("fichier historique crée a l'addresse " + historyPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> generateHistory(String senderAddr) {
        createHistory(senderAddr);
        ArrayList<String> messageList = new ArrayList<>();
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(historyPath.resolve(senderAddr), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("#", 3);
                messageList.add(split[0]);
                messageList.add(split[1]);
                messageList.add(split[2]);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return messageList;
    }

    public static void addToHistory(String addr, MsgNormal message) {
        Charset charset = StandardCharsets.UTF_8;       
        try (BufferedWriter writer = Files.newBufferedWriter(historyPath.resolve(addr), charset, StandardOpenOption.APPEND)) {
        	String msg;       	
            if (message.getSender().getAddr().equals(Controller_Interface.getAddress())) {            	
            	msg = "Moi#"+message.getMessage()+"#"+message.getDate();
            }
            else {            	
            	msg = message.getSender().getNom()+"#"+message.getMessage()+"#"+message.getDate();
            }            
            writer.write(msg, 0, msg.length());
            writer.newLine();
            System.out.println("Ajouté l'historique : " +  msg );
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}