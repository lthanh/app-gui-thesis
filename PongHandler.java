
import java.util.List;
import java.util.Vector;

/**
 * Similar to QHitHandler, PongHandler will process pongs that match pings
 * forwarded by us, not pings that originated with Pinger.
 */
class PongHandler extends Thread {

    Pong pong;
    IPAddress pingIP;
    IPAddress ip;
    Ping pingMatch;
    byte[] useIDOnlineToByte = LoginForm.currentUser.getIdUserLogin().getBytes();
    String userNameOnlineString = LoginForm.currentUser.getUserName();
    public static Vector<Pong> listPong = new Vector<Pong>();

    public PongHandler(IPAddress ip, Pong pong) {
        this.pong = pong;
        this.ip = ip;
    }

    public void run() {
        String ipname = pong.getIP().toString();
        int port = pong.getPort();
        Host newhost = new Host(ipname, port);
        HostCache.addHost(newhost);
        listPong.add(pong);

        if (PingHandler.pt.containsKey(pong)) {
            pingMatch = (Ping) PingHandler.pt.get((Packet) pong);
            /**
             * Matching pong is used as key to obtain original ping.
             */
            pingIP = pingMatch.getIP();
            NetworkManager.writeToOne(pingIP, pong);
            //System.out.println("PONG LENGHTH: " + po);

//            Vector<IPAddress> check = HostArray.cacheConnection;
//            String ipRePing = pingIP.toString();
//            for (int i = 0; i < HostArray.cacheConnection.size(); i++) {
//                if (!ipRePing.equals(check.get(i).toString())) {
//                    NetworkManager.writeToOne(pingIP, pong);
//                }
//            }

        }
    }
//    public boolean checkFriendListToShow(Vector<Friends> objectFriend, String pongFriend) {
//        for (int i = 0; i < objectFriend.size(); i++) {
//            if (objectFriend.get(i).getUserName().equals(pongFriend)) {
//                return false;
//            }
//        }
//        return true;
//    }
}