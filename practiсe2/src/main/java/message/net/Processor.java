package message.net;

public class Processor implements Runnable {

    private final Buffer<MessagePOJO> inputBuffer;
    private final Buffer<MessagePOJO> outputBuffer;
    private final Store store;

    public Processor(Buffer<MessagePOJO> inputBuffer, Buffer<MessagePOJO> outputBuffer, Store store) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        this.store = store;
    }

    @Override
    public void run() {
        try {
            while (true) {
                MessagePOJO incomingMessage = inputBuffer.take();

                String resultText = store.proccessCommand(incomingMessage);

                MessagePOJO outgoingMessage = new MessagePOJO(
                        incomingMessage.getBSrc(),
                        incomingMessage.getBPktId(),
                        incomingMessage.getCType(),
                        incomingMessage.getBUserId(),
                        resultText
                );

                outputBuffer.put(outgoingMessage);
            }
        } catch (InterruptedException e) {
            System.out.println("Processor stopped");
            Thread.currentThread().interrupt();
        }
    }
}