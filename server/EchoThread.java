import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    public int id;
    private final Socket socket;
    private final PrintWriter out;
    private final String registerCmd = "REGISTER " + Config.randIntFromZero(Config.width) + " " + Config.randIntFromZero(Config.height) + "\r\n";

    public EchoThread(Socket clientSocket, int id) throws IOException {
        this.socket = clientSocket;
        this.id = id;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.notify(id + " " + registerCmd);
    }

    public void run() {
        try {
            Server.notify(registerCmd, id);
            this.notify(id + " USERS " + Server.ul.toString());
            BufferedReader brinp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = brinp.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) { // ich musste die zeile, hinzufuegen da die Zeile    if (!line.isBlank()) {    auf linux, bzw auf meiner linux maschine probleme bereitet.
                    Server.notify(line.trim() + "\r\n", id);
                }
                line = brinp.readLine();
            }
            socket.close();
        } catch (IOException ignored) {
        }
        Server.rmClient(this);
    }

    public void notify(String cmd) throws IOException {
        out.write(cmd);
        out.flush();
    }
}