package connect;

import commands.Command;
import commands.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Remote extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private final String ip;
    private final Executor executor;

    public Remote(String ip, Executor executor) {
        this.ip = ip;
        this.executor = executor;
    }

    public void connect() throws IOException {
        clientSocket = new Socket(ip, 6969);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        start();
    }

    public void sendCommand(Command c) {
        out.write(c.toString() + "\r\n");
        out.flush();
    }

    public void close() {
        executor.close();
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = in.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println("remote" + line);
                    executor.execute(Parser.parse(line.trim()));
                }
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }
}
