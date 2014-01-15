
import java.util.*;

class PingHandler extends Thread {

    public static Map pt;  //ping table    
    Ping ping;
    byte[] useIDOnlineToByte = LoginForm.currentUser.getIdUserLogin().getBytes();
    String userNameOnlineString = LoginForm.currentUser.getUserName();

    public PingHandler(IPAddress pingIP, Ping ping) {
        this.ping = ping;
        ping.setIP(pingIP);  //set ping's IP Address
    }
/////////////////////////////////////////////////////////////// **

    public static void initPingTable() {
        pt = new Hashtable(5000);
    }
/////////////////////////////////////////////////////////////// **

    public void run() {
        if (!pt.containsKey(ping)) //check that ping is not already in table
        {
            NetworkManager.writeButOne(ping.getIP(), ping);
            pt.put((Packet) ping, ping);
            Pong response = new Pong(Mine.getPort(), Mine.getIPAddress(), useIDOnlineToByte, userNameOnlineString.length(), SharedDirectory.getListFileIDSaving().length(), SharedDirectory.getListFileIDSaving(), userNameOnlineString, ping.getMessageID());

            //  System.out.println("#### PingHandler: PONG -- " + response.toString());
            NetworkManager.writeToOne(ping.getIP(), response);
        }
    }
}
