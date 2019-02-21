package NewTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class FileTransferServer {
    private static final int SERVER_PORT = 8899;
    private FileInputStream fis;
    private DataOutputStream dos;
    Scanner scanner = new Scanner(System.in);

    //    private FileTransferServer() throws IOException {
//        super(SERVER_PORT);
//    }
//
    public void load(Socket socket) {
//        while (true) {
//            Socket socket = this.accept();
        new Thread(new mTask(socket)).start();
//        }
    }

    public static void main(String[] args) {
        System.out.println("服务器启动");
        try {
            FileTransferServer mserver = new FileTransferServer();
//            server.load();

            ServerSocket server = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket socket = server.accept();
                System.out.println("客户端连接：" + socket.getInetAddress() + " ：" + new Date());
//                new Thread().sleep(1000);
                mserver.load(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class mTask implements Runnable {
        private Socket socket;
        private DataInputStream dis;
        private FileOutputStream fos;

        mTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
//                files[0] = new File("D:\\test1.txt");
//                files[1] = new File("D:\\test2.txt");

//                files[2] = new File("D:\\test3.txt");
//                files[2] = new File("D:\\Splash.bmp");
                sendFile(socket);
//                sendFile("D:\\Splash.bmp");
//                sendFile("D:\\fiddlersetup.exe");
//                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void sendFile(Socket socket) {
            long totalSize = 0;
            byte buf[] = new byte[8192];
            int len;
            try {
                if (socket.isOutputShutdown()) {
                    return;
                }
                DataOutputStream dout = new DataOutputStream(
                        socket.getOutputStream());
                /**
                 * @1 图片
                 * @2 视频
                 * @3 网页
                 */
                System.out.println("请输入要发送的文件类型：1:图片、2:视频、3:网页");
                int type = Integer.parseInt(scanner.nextLine());
                dout.writeInt(type);
                dout.flush();

                if (type == 3) {
                    System.out.println("请输入要发送的网页");
                    dout.writeUTF(scanner.nextLine());
                    System.out.println("正在发送......" + new Date());
                } else {
                    List<String> FileNameSet = new ArrayList<>();
                    System.out.println("请输入要发送的文件，输入 *** 结束");
                    while (true) {
                        String name = scanner.nextLine();
                        if (name.equals("***")) {
                            break;
                        } else {
                            FileNameSet.add(name);
                        }
                    }
                    File[] files = new File[FileNameSet.size()];
                    for (int i = 0; i < FileNameSet.size(); i++) {
                        files[i] = new File(FileNameSet.get(i));
                    }
                    System.out.println("正在发送......" + new Date());
                    dout.writeInt(files.length);
                    for (int i = 0; i < files.length; i++) {
                        dout.writeUTF(files[i].getName());
                        dout.flush();
                        dout.writeLong(files[i].length());
                        dout.flush();
                        totalSize += files[i].length();
                    }
                    dout.writeLong(totalSize);

                    for (int i = 0; i < files.length; i++) {
                        BufferedInputStream din = new BufferedInputStream(
                                new FileInputStream(files[i]));
                        while ((len = din.read(buf)) != -1) {
                            //write(byte[]b,int off,int len)要求off+len<b.length且 off*len！=0否则会抛出（IndexOutOfBoundsException） 将b中len个字节按顺序写入输出流从b[off]开始，到b[off+len]Q:off为负是什么情况
                            dout.write(buf, 0, len);
//                        dout.flush();
                        }
                    }
                    System.out.println("文件传输完成：" + new Date());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        public void sendFile(String fileName) throws Exception {
            File file = new File(fileName);
            try {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF(file.getName());
                dos.flush();

                Date sTime = new Date();
                System.out.println("开始传输文件：" + sTime);
                byte buffer[] = new byte[1024];
                int length = 0;
                while ((length = fis.read(buffer, 0, buffer.length)) != -1) {
                    dos.write(buffer, 0, length);
                }
                dos.flush();
                Date eTime = new Date();
                System.out.println("文件传输成功：" + eTime);
                System.out.println("使用的时间为：" + (eTime.getTime() - sTime.getTime()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null)
                    fis.close();
                if (dos != null)
                    dos.close();
                socket.close();
            }
        }

        private void saveFile() {
            try {
                dis = new DataInputStream(socket.getInputStream());
                String fileName = dis.readUTF();
                File file = new File("D:\\test" + fileName);
                fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = dis.read(bytes, 0, bytes.length)) != -1) {
                    fos.write(bytes, 0, len);
                    fos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                    if (dis != null)
                        dis.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
