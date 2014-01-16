/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class Friends extends UserLoginObject {

    public Friends() {
    }
    IPAddress ip;
    public int countOffline;

    public IPAddress getIp() {
        return ip;
    }

    public void setIp(IPAddress ip) {
        this.ip = ip;
    }

    public int getCountOffline() {
        return countOffline;
    }

    public void setCountOffline(int countOffline) {
        this.countOffline = countOffline;
    }
}
