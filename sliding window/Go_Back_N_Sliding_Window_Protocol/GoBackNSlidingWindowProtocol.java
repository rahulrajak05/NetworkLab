import java.util.Scanner;
import java.util.Random;

class SenderGoBackN {
    private int windowSize = 4;
    private boolean[] ackReceived = new boolean[windowSize];

    public void sendFrames(int[] frames) {
        for (int i = 0; i < frames.length; i++) {
            System.out.println("Sender: Sending frame: " + frames[i]);
            if (isFrameCorrupted(frames[i])) {
                System.out.println("Sender: Frame " + frames[i] + " corrupted during transmission.");
            }
        }
    }

    public void acknowledgeFrame(int frame) {
        ackReceived[frame % windowSize] = true;
        System.out.println("Sender: Acknowledgment received for frame: " + frame);
    }

    private boolean isFrameCorrupted(int frame) {
        Random rand = new Random();
        return rand.nextInt(100) < 20; 
    }
}

class ReceiverGoBackN {
    private Random rand = new Random();

    public void receiveFrames(int[] frames, SenderGoBackN sender) {
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

    private boolean isFrameCorrupted(int frame) {
        return rand.nextInt(100) < 20; 
    }
}

public class GoBackNSlidingWindowProtocol {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SenderGoBackN sender = new SenderGoBackN();
        ReceiverGoBackN receiver = new ReceiverGoBackN();

        System.out.println("Go-Back-N Sliding Window Protocol Simulation");

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

                sender.sendFrames(frames);

                receiver.receiveFrames(frames, sender);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integers only.");
            }
        }
        scanner.close();
    }
}
