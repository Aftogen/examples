package SecondClientServerApp;

// Сервер, который использует многопоточность
// для обработки любого числа клиентов.
// {RunByHand}
import java.io.*;

import java.net.*;

public class TestMultiServer {
    static final int PORT = 8080;

    public static void main(String[] args) throws IOException{
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = s.accept();
                try {
                    new ServeOneJabber(socket);
                }
                catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        }
        finally {
            s.close();
        }
    }
} // /:~