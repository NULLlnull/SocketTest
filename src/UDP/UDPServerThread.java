package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServerThread extends Thread {
    DatagramPacket packet = null;
    DatagramSocket socket = null;
    byte[] data = null;

    public UDPServerThread(byte[] bytes, DatagramSocket socket, DatagramPacket packet) {
        this.packet = packet;
        this.socket = socket;
        data = bytes;
    }

    @Override
    public void run() {
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
        try {
            socket.send(packet2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
