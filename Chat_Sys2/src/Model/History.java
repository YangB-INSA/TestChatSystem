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

    public void createHistory(String addr) {
        createDirectory();
        File historyFile = historyPath.resolve(addr).toFile();
        if (!historyFile.exists()) {
            try {
            	historyFile.createNewFile();
                System.out.println("fichier historique cr�e a l'addresse " + historyPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> generateHistory(String addr) {
        createHistory(addr);
        ArrayList<String> messageList = new ArrayList<>();
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(historyPath.resolve(addr), charset)) {
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
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedWriter writer = Files.newBufferedWriter(historyPath.resolve(addr), charset, StandardOpenOption.APPEND)) {
        	String m;
        	
            if (message.getSender().getAddr().equals(Controller_Interface.getHostAddresses())) {
            	m = "Moi#"+message.getMessage()+"#"+message.getDate();
            }
            else {
            	m = message.getSender().getNom()+"#"+message.getMessage()+"#"+message.getDate();
            }
            writer.write(m, 0, m.length());
            writer.newLine();
            System.out.println("Ajout� � l'historique : " +  m );
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}