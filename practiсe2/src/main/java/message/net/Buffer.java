package message.net;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer<T> {
    private Queue<T> queue = new LinkedList<>();

    private int MAX_SIZE = 10;

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= MAX_SIZE) {
            wait();
        }

        queue.add(item);

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        T item = queue.poll();

        notifyAll();

        return item;
    }
}