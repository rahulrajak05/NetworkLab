import java.net.*;

public class MulticastReceiver {
    public static void main(String[] args) {
        try {
            int port = 5000;
            InetAddress group = InetAddress.getByName("230.0.0.0"); // Multicast IP address
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0"); // Change for your interface (e.g., eth0)

            MulticastSocket socket = new MulticastSocket(port);
            socket.setReuseAddress(true);
            socket.joinGroup(new InetSocketAddress(group, port), networkInterface);

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Waiting for multicast messages...");
            while (true) {
                socket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received Message: " + receivedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
