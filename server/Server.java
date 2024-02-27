import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {
    public static void main(String[] args) {
        int port = 6969; // port muss 6969 sein, aufgrund der port forwardings des dev-servers

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("running server on :6969");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                // new thread for a client
                EchoThread e = new EchoThread(socket, nextID);
                clients.add(e);
                e.start();
                System.out.printf("added client %d\n", nextID);
                nextID++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
        }
    }

    public static void notify(String cmd, int from) {
        for (EchoThread t : clients) {
            // skip sender
            if (t.id != from) {
                try {
                    t.notify(from + " " + cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void rmClient(EchoThread e) {
        clients.remove(e);
        notify("UNREGISTER\r\n", e.id);
        System.out.printf("removed client %d\n", e.id);
    }

    private static int nextID = 1;

    private static final ArrayList<EchoThread> clients = new ArrayList<>();

}
