package se.dse.pulsarbyexample.chatter.shellui.cliche;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConsoleInputStream extends InputStream {

    private final BlockingQueue<Integer> queue;
    private final InputStream delegate;
    private boolean interrupt;
    private Thread thread;
    private Thread feeder;
    volatile private boolean running;

    public ConsoleInputStream(InputStream i_delegate) {
        super();
        delegate = i_delegate;
        queue = new ArrayBlockingQueue<>(1024);
        thread = Thread.currentThread();
        feeder = new Thread(new Feeder());
        running = true;
        feeder.start();
    }

    private class Feeder implements Runnable {
        public void run() {
            try {
                while (running)  {
                    // since we want this thread to be able to interrupt we spin with a small sleep period
                    // unfortunately it isn't possible to unblock a blocked read() operation
                    try {
                        if (delegate.available() > 0) {
                            int c = delegate.read();
                            if (c == -1) {
                                queue.put(c);
                                return;
                            }
                            queue.put(c);
                        } else {
                            Thread.sleep(100);
                        }
                    }
                    catch (Throwable t) {
                        return;
                    }
                }
            } finally {
                close();
            }
        }
    }



    @Override
    public int read() throws IOException {
        return read(true);
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int nb = 1;
        int i = read(true);
        if (i < 0) {
            return -1;
        }
        b[off++] = (byte) i;
        while (nb < len) {
            i = read(false);
            if (i < 0) {
                return nb;
            }
            b[off++] = (byte) i;
            nb++;
        }
        return nb;
    }

    @Override
    public void close() {
        running = false;
        thread.interrupt();
        feeder.interrupt();
    }


    private int read(boolean i_wait) throws IOException {
        if (!running) {
            return -1;
        }
        checkInterrupt();
        Integer i;
        if (i_wait) {
            try {
                i = queue.take();
            } catch (InterruptedException e) {
                return -1;
            }
            checkInterrupt();
        } else {
            i = queue.poll();
        }
        if (i == null) {
            return -1;
        }
        return i;
    }

    private void checkInterrupt() throws IOException {
        if (Thread.interrupted() || interrupt) {
            interrupt = false;
            throw new InterruptedIOException();
        }
    }


}
