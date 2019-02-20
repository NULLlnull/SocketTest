package Test;

import java.io.*;
import java.net.Socket;

public class ServerThreadDemo extends Thread {
    Socket socket = null;
    byte[] sendByte;
    int len = 0;
    DataOutputStream dos;
    InputStream is = null;

    public ServerThreadDemo(Socket socket) {
        this.socket = socket;
        System.out.println("客户端已经连接，客户IP为：" + socket.getInetAddress().getHostAddress());
    }

    @Override
    public void run() {
//        ObjectInputStream ois = null;
        try {
//            is = socket.getInputStream();
//            ois = new ObjectInputStream(is);
            System.out.println("=====传输准备中=====");

            File file = new File("D:\\Splash.bmp");

            FileInputStream fis = new FileInputStream(file);
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(file.getName());
//            dos.flush();
//            dos.writeLong(file.length());
//            dos.flush();

            System.out.println("-----开始传输文件-----");
            sendByte = new byte[1024];
            while ((len = fis.read(sendByte, 0, sendByte.length)) != -1) {
                dos.write(sendByte, 0, len);//向BufferedInputStream中写入数据
                dos.flush();
            }
            System.out.println("-----文件传输成功-----");
//            BeanDemo bean = (BeanDemo) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (is != null) {
                    is.close();
                }
//                if (ois != null) {
//                    ois.close();
//                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
