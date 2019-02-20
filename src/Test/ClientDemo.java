package Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientDemo {

    static Socket socket = null;
    static Scanner scanner = null;
    static ObjectOutputStream oos = null;
    static ObjectInputStream ois = null;
    static OutputStream os = null;
    static InputStream is = null;
    static DataInputStream dis = null;
    static FileOutputStream fos = null;

    public static void main(String[] args) {
        try {
            socket = new Socket("127.0.0.1", 8888);
            System.out.println("服务器连接成功！\n" + BeanDemo.CUT);
//            os = socket.getOutputStream();
//            oos = new ObjectOutputStream(os);
//            is = socket.getInputStream();
            dis = new DataInputStream(socket.getInputStream());
//            BufferedInputStream bis = new BufferedInputStream(is);
            File file = new File("D:\\socket" + dis.readUTF());
            fos = new FileOutputStream(file);
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] byt = new byte[1024];
            int len = 0;
            while ((len = dis.read(byt, 0, byt.length)) != -1) {
                System.out.println(len);
                fos.write(byt, 0, len);
                fos.flush();
            }
//            bos.flush();
//            ois = new ObjectInputStream(is);
//            Start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Destory();
        }
    }

    private static void Destory() {
        try {
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (scanner != null) {
                scanner.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Start() {
        System.out.println("*****欢迎使用******\n1.登录\n2.注册\n3.退出");
        System.out.print("\n请选择：");
        scanner = new Scanner(System.in);
        switch (scanner.next()) {
            case "1":
                Login();
                break;
            case "2":
                Register();
                break;
            case "3":
                try {
                    socket.close();
                    System.out.println("谢谢使用！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Start();
                break;
        }
    }

    private static void Login() {

    }

    private static void Register() {
        System.out.print("\n请输入用户名：");
        String UserName = scanner.next();
        System.out.print("请输入密码：");
        String Pass1 = scanner.next();
        System.out.print("请再次输入密码：");
        String Pass2 = scanner.next();
        if (Pass1.equals(Pass2)) {

        } else {
            System.out.println("******两次输入的密码不一致！******");
            Login();
        }
    }
}
