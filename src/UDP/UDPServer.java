package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) {
//        single();
        thread();
    }

    private static void thread() {
        //1、创建服务器端DatagramSocket,指定端口
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(8800);
            //2、创建数据报，用于接受客户端发送的数据
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            System.out.println("多线程UDP服务器启动");
            while (true) {
                //3、接受客户端发送的数据
                socket.receive(packet);//此方法在接收到数据包之前会一直堵塞
                UDPServerThread thread = new UDPServerThread(data, socket, packet);
                thread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void single() {
        DatagramSocket socket = null;
        try {
            //1、创建服务器端DatagramSocket,指定端口
            socket = new DatagramSocket(8800);
            //2、创建数据报，用于接受客户端发送的数据
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            System.out.println("UDP服务器启动");

            //3、接受客户端发送的数据
            socket.receive(packet);//此方法在接收到数据包之前会一直堵塞
            //4、读取数据
            String info = new String(data, 0, packet.getLength());
            System.out.println("我是服务器，客户端说；" + info);

            /*
             *向客户端相应数据
             */
            //1、定义客户端的地址、端口号、数据
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] data2 = "欢迎您！".getBytes();
            //2、创建数据报，包含响应的数据信息
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
            //3、响应客户端
            socket.send(packet2);
            //4、关闭资源
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
