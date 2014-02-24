package Architecture_Posting;

import GUI.*;
import java.util.*;
import PostingService.PostHandler;

/**
 * Similar to QHitHandler, PongHandler will process pongs that match pings
 * forwarded by us, not pings that originated with Pinger.
 */
public class PongHandler extends Thread {

    public static Map pongTable;  //pong table     
    Pong pong;
    IPAddress pingIP;
    IPAddress ip;
    Ping pingMatch;
    byte[] useIDOnlineToByte = LoginForm.currentUser.getIdUserLogin().getBytes();
    String userNameOnlineString = LoginForm.currentUser.getUserName();
    public static Vector<Pong> listPong = new Vector<Pong>();

    public PongHandler(IPAddress ip, Pong pong) {
        this.pong = pong;
        pong.setPongRespondIP(ip);
    }

    public static void initPongTable() {
        pongTable = new Hashtable(5000);
    }

    public void run() {
        if (!pongTable.containsKey(pong.getMessageID())) //check that postMessage is not already in table
        {
            pongTable.put(pong.getMessageID(), pong);
            boolean isPeerFriends = PostHandler.checkListFriendORPeerInPong(pong, Preferences.friendList);
            boolean isSuperPeer = checkSuperPeer(pong.getIP(), pong.getPort());


            if (isPeerFriends || isSuperPeer) {
                listPong.add(pong);
                String ipname = pong.getIP().toString();
                int port = pong.getPort();

                Host newhost = new Host(ipname, port);
                HostCache.addHost(newhost);
            }

            if (PingHandler.pingTable.containsKey(pong.getMessageID())) {
                pingMatch = (Ping) PingHandler.pingTable.get(pong.getMessageID());
                /**
                 * Matching pong is used as key to obtain original ping.
                 */
                pingIP = pingMatch.getIP();
                NetworkManager.writeToOne(pingIP, pong);
            } else {
                NetworkManager.writeButOne(pong.getPongRespondIP(), pong);
            }
        }
    }

    public boolean checkSuperPeer(IPAddress ip, int port) {
        String address = ip.toString() + ":" + port;
        if (Preferences.ipSuperPeer.contains(address)) {
            return true;
        }
        return false;
    }
}
