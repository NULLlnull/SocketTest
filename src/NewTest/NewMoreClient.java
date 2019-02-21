package NewTest;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class NewMoreClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入服务器地址：");
            String Host = scanner.nextLine();
            Socket socket = new Socket(Host, 8899);
            System.out.println("连接服务器：" + socket.getInetAddress() + " ：" + new Date());
            FileTransferServer mserver = new FileTransferServer();
            mserver.load(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
