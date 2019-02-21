package NewTest;

import java.io.*;
import java.net.Socket;

public class NewClient extends Socket {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8899;
    private DataInputStream dis;
    private FileOutputStream fos;

    private Socket client;
    private FileInputStream fis;
    private DataOutputStream dos;

    public NewClient() throws IOException {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println(client.getLocalPort() + "成功连接到服务器");
    }

    public void sendFile() throws Exception {
        File file = new File("D:\\test.txt");
        try {
            fis = new FileInputStream(file);
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(file.getName());
            dos.flush();

            System.out.println("开始传输文件");
            byte buffer[] = new byte[1024];
            int length = 0;
            while ((length = fis.read(buffer, 0, buffer.length)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.flush();
            System.out.println("文件传输成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
            if (dos != null)
                dos.close();
            client.close();
        }
    }

    private void saveFile() {
        try {
            dis = new DataInputStream(this.getInputStream());
            String fileName = dis.readUTF();
            File file = new File("G:\\test1.txt" + fileName);
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
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            NewClient newClient = new NewClient();
            System.out.println("服务器连接成功");
            newClient.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
