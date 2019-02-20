package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
//        single();
        thread();
    }

    private static void thread() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = null;
            //记录客户端的数量
            int count = 0;
            System.out.println("------------多线程服务器启动---------------");
            //循环监听等待客户端的连接
            while (true) {
                //调用accept()方法开始监听,等待客户端的连接
                socket = serverSocket.accept();
                //创建一个新的线程
                ServerThread serverThread = new ServerThread(socket);
                //启动线程
                serverThread.start();

                count++;
                System.out.println("客户端的数量：" + count);
                System.out.println("客户端的IP：" + socket.getInetAddress().getHostAddress());
//                System.out.println(socket.getInetAddress().getCanonicalHostName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void single() {
        try {
            //1、创建一个服务器端的Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(8888);
            //2、调用accept()方法开始监听，等待客户端的连接
            System.out.println("-----------服务器启动-------------");
            Socket socket = serverSocket.accept();
            //3、获取输入流，用来读取客户端信息
            InputStream is = socket.getInputStream();
            //转为字符流
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);//为输入流添加缓冲
            String info = null;
            while ((info = br.readLine()) != null) {//循环读取客户端的信息
                System.out.println("我是服务器：客户端说：" + info);
            }
            socket.shutdownInput();//关闭输入流

            //4、获取输出流，相应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);//包装为打印流
            pw.write("欢迎您！");
            pw.flush();//调用flush()方法刷新，将缓冲输出

            //5、关闭相关的资源
            pw.close();
            os.close();

            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
