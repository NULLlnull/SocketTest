package Test;

import TCP.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("******多线程服务器启动******");
            while (true) {
//                socket = serverSocket.accept();
                ServerThreadDemo thread = new ServerThreadDemo(socket);
                thread.start();
//                new ServerThreadDemo(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
