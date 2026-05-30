package message.net;

public class FakeSender implements Runnable {

    private Buffer<byte[]> inputBuffer;

    public FakeSender(Buffer<byte[]> inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] dataToSend = inputBuffer.take();
            }
        } catch (InterruptedException e) {
            System.out.println("FakeSender stopped");
            Thread.currentThread().interrupt();
        }
    }
}