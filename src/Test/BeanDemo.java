package Test;

import java.io.Serializable;

public class BeanDemo implements Serializable {
    public static final String CUT = "**************************************************************";
    private static final long serialVersionUID = -5809782578272943999L;//序列化ID

    private int type;
    private String userName;
    private String passWord;

    public BeanDemo(int type, String userName, String passWord) {
        this.type = type;
        this.userName = userName;
        this.passWord = passWord;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
