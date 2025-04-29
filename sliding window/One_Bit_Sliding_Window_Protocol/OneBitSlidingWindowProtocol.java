import java.util.Scanner;
import java.util.Random;

class SenderOneBit {
    private boolean isFrameAcknowledged = false;

    // Method to send a frame and wait for acknowledgment
    public void sendFrame(int frame) {
        isFrameAcknowledged = false;
        System.out.println("Sender: Sending frame: " + frame);
        
        if (isFrameCorrupted(frame)) {
            System.out.println("Sender: Frame corrupted during transmission.");
        } else {
            System.out.println("Sender: Frame sent successfully.");
        }
    }

    // Method to acknowledge the frame
    public void acknowledgeFrame() {
        isFrameAcknowledged = true;
        System.out.println("Sender: Frame acknowledged by receiver.");
    }

    // Simulate frame corruption
    private boolean isFrameCorrupted(int frame) {
        Random rand = new Random();
        return rand.nextInt(100) < 20; // 20% chance of corruption
    }
}

class ReceiverOneBit {
    private Random rand = new Random();

    // Method to receive a frame and check for errors
    public void receiveFrame(int frame, SenderOneBit sender) {
        System.out.println("Receiver: Frame received: " + frame);

        boolean isCorrupted = isFrameCorrupted(frame);

        if (isCorrupted) {
            System.out.println("Receiver: Frame corrupted. Requesting retransmission.");
            return;
        }

        System.out.println("Receiver: Frame received successfully.");
        sender.acknowledgeFrame();
    }

    // Simulate frame corruption
    private boolean isFrameCorrupted(int frame) {
        return rand.nextInt(100) < 20; // 20% chance of corruption
    }
}

public class OneBitSlidingWindowProtocol {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SenderOneBit sender = new SenderOneBit();
        ReceiverOneBit receiver = new ReceiverOneBit();

        System.out.println("One-Bit Sliding Window Protocol Simulation");

        while (true) {
            System.out.print("\nEnter frame to send (0 or 1) or type 'exit' to quit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the simulation...");
                break;
            }

            try {
                int frame = Integer.parseInt(input);

                // Sender sends the frame
                sender.sendFrame(frame);

                // Receiver receives the frame
                receiver.receiveFrame(frame, sender);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 0 or 1 for the frame.");
            }
        }

        scanner.close();
    }
}
