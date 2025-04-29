import java.util.Random;
import java.util.Scanner;

public class SimplexProtocolNoisyChannel {

    static class Sender {
        private Receiver receiver;
        private int messageCount;

        public Sender(Receiver receiver, int messageCount) {
            this.receiver = receiver;
            this.messageCount = messageCount;
        }

        public void sendMessages(Scanner scanner) {
            for (int i = 1; i <= messageCount; i++) {
                System.out.print("Enter message " + i + ": ");
                String message = scanner.nextLine();
                boolean messageSent = false;

                while (!messageSent) {
                    System.out.println("Sender: Sending " + message);
                    boolean errorOccurred = receiver.receiveMessage(message);

                    // Simulate waiting for acknowledgment
                    if (!errorOccurred) {
                        System.out.println("Sender: Received ACK for Message " + i);
                        messageSent = true; // ACK received, message sent successfully
                    } else {
                        System.out.println("Sender: Error occurred, resending Message " + i);
                    }
                }
            }
        }
    }

    static class Receiver {
        private Random random = new Random();

        public boolean receiveMessage(String message) {
            // Simulate a noisy channel with a probability of error
            boolean errorOccurred = random.nextInt(10) < 3; // 30% chance of error
            if (errorOccurred) {
                System.out.println("Receiver: Received " + message + " with errors (corrupted).");
                return true; // Indicate that an error occurred
            } else {
                System.out.println("Receiver: Received " + message + " successfully.");
                sendAcknowledgment();
                return false; // No error occurred
            }
        }

        private void sendAcknowledgment() {
            System.out.println("Receiver: Sending ACK");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of messages to send: ");
        int numberOfMessages = Integer.parseInt(scanner.nextLine());

        Receiver receiver = new Receiver();
        Sender sender = new Sender(receiver, numberOfMessages);

        sender.sendMessages(scanner);
        scanner.close();
    }
}
