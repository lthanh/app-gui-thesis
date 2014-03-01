package Architecture_Posting;

import GUI.*;
import java.util.*;

/**
 * Similar to QHitHandler, PongHandler will process pongs that match pings
 * forwarded by us, not pings that originated with Pinger.
 */
public class PongHandler extends Thread {

    public static Map pongTable;  //pong table     
    public static Map listPongTable;  //pong table     
    Pong pong;
    IPAddress pingIP;
    IPAddress ip;
    Ping pingMatch;
    Utils utils = new Utils();
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

    public static void listPongTable() {
        listPongTable = new Hashtable();
    }

    public void run() {
        if (!pongTable.containsKey(pong.getMessageID())) //check that postMessage is not already in table
        {
            pongTable.put(pong.getMessageID(), pong);
            NetworkManager.writeButOne(pong.getPongRespondIP(), pong);

            if (!listPongTable.containsKey(pong.getUserIDOnline())) {
                System.out.println("\n ### Server : Packet == PONG -- " + pong.getUserIDOnline());
                listPongTable.put(pong.getUserIDOnline(), pong);
                listPong.add(pong);
            }


//            boolean isPeerFriends = checkListFriendORPeerInPong(pong);

            // if friends pong, then it will add to listPong to check online connection
//            if (isPeerFriends) {
//                System.out.println("\n ### ADD == PONG -- " + pong.getUserNameOnline());
////                listPong.add(pong);
////
//                Friends userPongObject = new Friends();
//                userPongObject.setIdUserLogin(pong.getUserIDOnline());
//                userPongObject.setUserName(pong.getUserNameOnline());
//                userPongObject.setStatus(Preferences.ONLINE);
//                userPongObject.setCountOffline(Preferences.COUNTER_ONLINE);
//                CheckUserOnlineAction.userInPong.add(userPongObject);
//
//                // showUserNameFriend.add(userPongObject.getUserName() + userPongObject.getStatus());
//                //   AppGUI.listFriends.setListData(showUserNameFriend);
//            }

            boolean isSuperPeer = utils.checkSuperPeer(pong.getIP(), pong.getPort());
            if (isSuperPeer) {
//                String ipname = pong.getIP().toString();
//                int port = pong.getPort();

                String ipname = pong.getPongRespondIP().toString();
                int port = pong.getPongRespondIP().getPort();

                Host newhost = new Host(ipname, port);
                HostCache.addHost(newhost);
            }
//            NetworkManager.writeToAll(pong);

//            if (PingHandler.pingTable.containsKey(pong.getMessageID())) {
//                pingMatch = (Ping) PingHandler.pingTable.get(pong.getMessageID());
//                /**
//                 * Matching pong is used as key to obtain original ping.
//                 */
//                pingIP = pingMatch.getIP();
//                NetworkManager.writeToOne(pingIP, pong);
//            } else {
//                NetworkManager.writeButOne(pong.getPongRespondIP(), pong);
//            }

        }
    }
}
