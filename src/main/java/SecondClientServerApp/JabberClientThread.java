package SecondClientServerApp;

import java.net.*;

import java.io.*;

class JabberClientThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static int counter = 0;
    private int id = counter++;
    private static int threadcount = 0;

    public static int threadCount() {
        return threadcount;
    }

    public JabberClientThread(InetAddress addr) {
        System.out.println("Making client " + id);
        threadcount++;
        System.out.println("threadcount: "+threadcount);
        try {
            socket = new Socket(addr, TestMultiServer.PORT);
        }
        catch (IOException e) {
            System.err.println("Socket failed");
            // Если создание сокета провалилось,
            // ничего ненужно чистить.
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            // Включаем автоматическое выталкивание:
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
            start();
        }
        catch (IOException e) {
            // Сокет должен быть закрыт при любой
            // ошибке, кроме ошибки конструктора сокета:
            try {
                socket.close();
            }
            catch (IOException e2) {
                System.err.println("Socket not closed");
            }
        }
        // В противном случае сокет будет закрыт
        // в методе run() нити.
    }

    public void run()  {
        try {
            for (int i = 0; i < 10; i++) {
                out.println("Client " + id + ": " + i+", ");
                String str = in.readLine();
                System.out.print(str);
            }
            out.println("END");
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        }
        finally {
            // Всегда закрывает:
            try {
                socket.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
            }
            threadcount--; // Завершаем эту нить
        }
    }
}