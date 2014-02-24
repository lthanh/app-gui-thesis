package Architecture_Posting;

import GUI.LoginForm;
import java.util.*;

public class PingHandler extends Thread {

    public static Map pingTable;  //ping table    
    Ping ping;
    String useIDOnlineString = LoginForm.currentUser.getIdUserLogin();
    String userNameOnlineString = LoginForm.currentUser.getUserName();

    public PingHandler(IPAddress pingIP, Ping ping) {
        this.ping = ping;
        ping.setIP(pingIP);  //set ping's IP Address
    }

    public static void initPingTable() {
        pingTable = new Hashtable(5000);
    }

    public void run() {
        if (!pingTable.containsKey(ping.getMessageID())) //check that ping is not already in table
        {
            NetworkManager.writeButOne(ping.getIP(), ping);
            pingTable.put(ping.getMessageID(), ping);

            Pong response = new Pong(Mine.getPort(), Mine.getIPAddress(), useIDOnlineString, userNameOnlineString.length(), SharedDirectory.getListFileIDSaving().length(), SharedDirectory.getListFileIDSaving(), userNameOnlineString, ping.getMessageID());
            NetworkManager.writeToOne(ping.getIP(), response);
        }
    }
}
