import java.io.*;
import java.net.*;

public class FTPServer {
    public static void main(String[] args) {
        int port = 12345; // Server port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("FTP Server is running on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {

            String fileName = dis.readUTF(); // Get the requested file name
            File file = new File(fileName);

            if (file.exists() && !file.isDirectory()) {
                dos.writeBoolean(true); // File exists
                dos.writeLong(file.length()); // Send file length
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, bytesRead); // Send file data
                    }
                    System.out.println("File sent: " + fileName);
                }
            } else {
                dos.writeBoolean(false); // File does not exist
                System.out.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}