
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
    Friends friends = new Friends();
   // public static Vector<String> showUserNameFriend = new Vector<String>();
    public static Vector<Friends> friendObject = new Vector<Friends>();

    public PongHandler(IPAddress ip, Pong pong) {
        this.pong = pong;
        this.ip = ip;
    }

    public void run() {
        String ipname = pong.getIP().toString();
        int port = pong.getPort();

        Host newhost = new Host(ipname, port);
        HostCache.addHost(newhost);

        // showFriend

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

    public void showFriendInform(Pong pong) {
        boolean checkFriend = checkFriendList(friendObject, pong.getUserNameOnline());
        if (checkFriend) {
            friends.setIdUserLogin(pong.getUserIDOnline());
            friends.setUserName(pong.getUserNameOnline());
            friends.setIp(pong.getIP());
            friendObject.add(0, friends);
            System.out.println("\n\n PONGHANDLER: SHOW FRIEND Name: " + friendObject.get(0).getUserName());
            System.out.println("\n\n PONGHANDLER: SHOW FRIEND ID : " + friendObject.get(0).getIdUserLogin());
            HostArray.showUserNameFriend.add(0, friendObject.get(0).getUserName());
        }
        AppGUI.listFriends.setListData(HostArray.showUserNameFriend);
    }

    public boolean checkFriendList(Vector<Friends> objectFriend, String pongFriend) {
        for (int i = 0; i < objectFriend.size(); i++) {
            if (objectFriend.get(i).getUserName().equals(pongFriend)) {
                return false;
            }
        }
        return true;
    }
}
