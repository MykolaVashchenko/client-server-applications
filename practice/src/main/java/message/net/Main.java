package message.net;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Store store = new Store();
        Buffer<byte[]> receiverToDecryptor = new Buffer<>();
        Buffer<MessagePOJO> decryptorToProcessor = new Buffer<>();
        Buffer<MessagePOJO> processorToEncryptor = new Buffer<>();
        Buffer<byte[]> encryptorToSender = new Buffer<>();

        List<Thread> workers = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            workers.add(new Thread(new FakeReceiver(receiverToDecryptor)));
        }
        for (int i = 0; i < 2; i++) {
            workers.add(new Thread(new Decryptor(receiverToDecryptor, decryptorToProcessor)));
        }
        for (int i = 0; i < 4; i++) {
            workers.add(new Thread(new Processor(decryptorToProcessor, processorToEncryptor, store)));
        }
        for (int i = 0; i < 3; i++) {
            workers.add(new Thread(new Encryptor(processorToEncryptor, encryptorToSender)));
        }
        for (int i = 0; i < 5; i++) {
            workers.add(new Thread(new FakeSender(encryptorToSender)));
        }

        for (Thread worker : workers) {
            worker.start();
        }

        System.out.println("System is working for 3 seconds");

        Thread.sleep(3000);

        for (Thread worker : workers) {
            worker.interrupt();
        }

        System.out.println("System stopped");
    }
}