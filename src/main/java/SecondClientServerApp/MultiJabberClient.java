package SecondClientServerApp;

import java.net.*;

import java.io.*;



public class MultiJabberClient {
    static final int MAX_THREADS = 40;

    public static void main(String[] args) throws IOException,
            InterruptedException {
        InetAddress addr = InetAddress.getByName(null);
        while (true) {
            if (JabberClientThread.threadCount() < MAX_THREADS)
                new JabberClientThread(addr);
            Thread.currentThread().sleep(10);
        }

    }
}