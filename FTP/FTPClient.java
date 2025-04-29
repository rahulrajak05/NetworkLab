import java.io.*;
import java.net.*;

public class FTPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server address
        int port = 12345; // Server port

        try (Socket socket = new Socket(serverAddress, port);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Enter the file name to download: ");
            String fileName = br.readLine(); // Read file name from user
            dos.writeUTF(fileName); // Send file name to the server

            boolean fileExists = dis.readBoolean(); // Check if file exists
            if (fileExists) {
                long fileLength = dis.readLong(); // Get file length
                try (FileOutputStream fos = new FileOutputStream("downloaded_" + fileName)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;
                    while (totalBytesRead < fileLength && (bytesRead = dis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead); // Write to file
                        totalBytesRead += bytesRead;
                    }
                    System.out.println("File downloaded: " + fileName);
                }
            } else {
                System.out.println("File not found on the server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}