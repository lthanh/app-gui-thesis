package Architecture_Posting;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thanh Le Quoc
 */
public class Friends extends UserLoginObject {

    public Friends() {
    }
    IPAddress ip;
    private int countOffline;
    private String checkFriendsGroup;

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

    public String getCheckFriendsGroup() {
        return checkFriendsGroup;
    }

    public void setCheckFriendsGroup(String checkFriendsGroup) {
        this.checkFriendsGroup = checkFriendsGroup;
    }
}
