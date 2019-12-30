package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import Model.messages.*;

public class Historique {
    private static Path historyPath = FileSystems.getDefault().getPath(".dataApp", ".convHistory");

    private static void createAppDirectory() {
        File f = historyPath.toFile();
        if (!f.exists()) f.mkdirs();
    }

    public void createConvHistoryFile(String addr) {
        createAppDirectory();
        File f = historyPath.resolve(addr).toFile();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getHistory(String addr) {
        createConvHistoryFile(addr);
        ArrayList<String> messageList = new ArrayList<>();
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(historyPath.resolve(addr), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_split = line.split(":", 3);
                messageList.add(line_split[2]);
                messageList.add(line_split[1]);
                messageList.add(line_split[0]);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return messageList;
    }

    public static void addToHistory(String addr, Message message) {
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedWriter writer = Files.newBufferedWriter(historyPath.resolve(addr), charset, StandardOpenOption.APPEND)) {
            String m = message.toString();
            writer.write(m, 0, m.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}