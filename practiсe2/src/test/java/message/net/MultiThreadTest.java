package message.net;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiThreadTest {
    class MyWorker implements Runnable {
        private Store store;
        private MessagePOJO message;

        public MyWorker(Store store, MessagePOJO message) {
            this.store = store;
            this.message = message;
        }

        @Override
        public void run() {
            store.proccessCommand(message);
        }
    }

    @Test
    public void multiThreadTestAddQuantity() throws InterruptedException {
        Store store = new Store();

        MessagePOJO addMessage = new MessagePOJO(
                (byte) 1, 1L,
                MessagePOJO.ADD_QUANTITY,
                1,
                "Гречка,10"
        );

        Thread[] threads = new Thread[100];

        for (int i = 0; i < 100; i++) {
            MyWorker worker = new MyWorker(store, addMessage);

            threads[i] = new Thread(worker);
            threads[i].start();
        }

        for (int i = 0; i < 100; i++) {
            threads[i].join();
        }

        MessagePOJO checkMessage = new MessagePOJO(
                (byte) 1, 2L,
                MessagePOJO.GET_QUANTITY,
                1,
                "Гречка"
        );

        String finalQuantity = store.proccessCommand(checkMessage);

        assertEquals("1000", finalQuantity);
    }
}