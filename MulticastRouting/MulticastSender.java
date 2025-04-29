import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MulticastSender {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.0"); // Multicast IP address
            int port = 5000;
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter messages to send (type 'exit' to quit):");

            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
                socket.send(packet);
                System.out.println("Message sent: " + message);
            }

            socket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace(); // <-- This line was incomplete before, now fixed
        }
    }
}
