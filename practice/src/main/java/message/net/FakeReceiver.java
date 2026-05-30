package message.net;

import java.util.Random;

public class FakeReceiver implements Receiver, Runnable {

    private Buffer<byte[]> outputBuffer;
    private Random random = new Random();
    private long packetIdCounter = 1;

    public FakeReceiver(Buffer<byte[]> outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    @Override
    public void run() {
        startReceiving();
    }

    @Override
    public void startReceiving() {
        try {
            Encryptor helper = new Encryptor(null, null);

            while (true) {
                int commandType = random.nextInt(6) + 1;
                String payload = generateRandomPayload(commandType);

                MessagePOJO fakeMessage = new MessagePOJO(
                        (byte) 1,
                        packetIdCounter++,
                        commandType,
                        101,
                        payload
                );

                byte[] networkBytes = helper.entry_take(fakeMessage);

                outputBuffer.put(networkBytes);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("FakeReceiver stopped");
            Thread.currentThread().interrupt();
        }
    }

    private String generateRandomPayload(int commandType) {
        switch (commandType) {
            case MessagePOJO.GET_QUANTITY:
                return "Гречка";
            case MessagePOJO.WRITE_OFF:
                return "Гречка,2";
            case MessagePOJO.ADD_QUANTITY:
                return "Гречка,10";
            case MessagePOJO.ADD_GROUP:
                return "Крупи";
            case MessagePOJO.ADD_ITEM_TO_GROUP:
                return "Гречка,Крупи";
            case MessagePOJO.SET_PRICE:
                return "Гречка,45.50";
            default:
                return "Гречка";
        }
    }
}