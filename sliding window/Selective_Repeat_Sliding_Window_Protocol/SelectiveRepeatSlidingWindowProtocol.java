import java.util.Scanner;
import java.util.Random;

class SenderSelectiveRepeat {
    private boolean[] ackReceived;

    public SenderSelectiveRepeat(int windowSize) {
        ackReceived = new boolean[windowSize];
    }

    // Send frames to the receiver
    public void sendFrames(int[] frames) {
        for (int i = 0; i < frames.length; i++) {
            System.out.println("Sender: Sending frame: " + frames[i]);
            if (isFrameCorrupted(frames[i])) {
                System.out.println("Sender: Frame " + frames[i] + " corrupted during transmission.");
            }
        }
    }

    // Acknowledge the received frame
    public void acknowledgeFrame(int frame) {
        ackReceived[frame] = true;
        System.out.println("Sender: Acknowledgment received for frame: " + frame);
    }

    // Simulate frame corruption
    private boolean isFrameCorrupted(int frame) {
        Random rand = new Random();
        return rand.nextInt(100) < 20; // 20% chance of corruption
    }
}

class ReceiverSelectiveRepeat {
    private Random rand = new Random();

    // Method to receive frames and send acknowledgment
    public void receiveFrames(int[] frames, SenderSelectiveRepeat sender) {
        for (int frame : frames) {
            System.out.println("Receiver: Frame received: " + frame);

            if (isFrameCorrupted(frame)) {
                System.out.println("Receiver: Frame corrupted. Retransmission needed.");
                continue;
            }

            System.out.println("Receiver: Frame received successfully.");
            sender.acknowledgeFrame(frame);
        }
    }

    // Simulate frame corruption
    private boolean isFrameCorrupted(int frame) {
        return rand.nextInt(100) < 20; // 20% chance of corruption
    }
}

public class SelectiveRepeatSlidingWindowProtocol {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SenderSelectiveRepeat sender = new SenderSelectiveRepeat(4);
        ReceiverSelectiveRepeat receiver = new ReceiverSelectiveRepeat();

        System.out.println("Selective Repeat Sliding Window Protocol Simulation");

        while (true) {
            System.out.print("\nEnter frames to send (space-separated integers) or 'exit' to quit: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the simulation...");
                break;
            }

            try {
                String[] inputStrings = input.split(" ");
                int[] frames = new int[inputStrings.length];

                for (int i = 0; i < inputStrings.length; i++) {
                    frames[i] = Integer.parseInt(inputStrings[i]);
                }

                // Sender sends frames
                sender.sendFrames(frames);

                // Receiver receives frames
                receiver.receiveFrames(frames, sender);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integers only.");
            }
        }

        scanner.close();
    }
}
