package PeerAction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.AppGUI;
import architecture.Friends;
import architecture.Pinger;
import architecture.PongHandler;
import architecture.Preferences;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class checkUserOnlineAction {

    public checkUserOnlineAction() {
    }
    // Friends friends = new Friends();
    Friends userPongObject = new Friends();
    public static Vector<String> showUserNameFriend = new Vector<String>(); // show friends in JList
    public static Vector<Friends> userInPong = new Vector<Friends>();
    public static Vector<Friends> friendObjectCheckOnline; // list friend object currently online

    public void checkUserOnline() {
        userPongObject = new Friends();
        Pinger.pingCounter = 0;
        System.out.println("\n checkUserOnlineAction: userInPongObject Pinger.pingCounter: " + Pinger.pingCounter);
        System.out.println("\n checkUserOnlineAction: userInPongObject PongHandler.listPong: " + PongHandler.listPong.toString());

        for (int i = 0; i < PongHandler.listPong.size(); i++) {
            userPongObject.setIdUserLogin(PongHandler.listPong.get(i).getUserIDOnline());
            userPongObject.setUserName(PongHandler.listPong.get(i).getUserNameOnline());
            userPongObject.setStatus(Preferences.ONLINE);
            userPongObject.setCountOffline(Preferences.COUNTER_ONLINE);

            System.out.println("\n checkUserOnlineAction: userInPongObject ID [" + i + "] " + userPongObject.getIdUserLogin());
            System.out.println("checkUserOnlineAction: userInPongObject Name [" + i + "] " + userPongObject.getUserName());
            System.out.println("checkUserOnlineAction: userInPongObject Status [" + i + "] " + userPongObject.getStatus());

            userInPong.add(userPongObject);
        }

        System.out.println("\n checkUserOnlineAction : LIST PONG BEFORE REMOVED: " + PongHandler.listPong.toString());
        PongHandler.listPong.removeAllElements();

        System.out.println("\n checkUserOnlineAction : LIST PONG REMOVED: " + PongHandler.listPong.toString());
        System.out.println("\n checkUserOnlineAction: LIST PONG userPongObject outside for: " + userPongObject.getIdUserLogin());
        System.out.println("\n checkUserOnlineAction: LIST PONG userPongObject outside for: " + userPongObject.getUserName());
        System.out.println("\n checkUserOnlineAction: LIST PONG userPongObject outside for: " + userPongObject.getStatus());

        Vector<Friends> newFriendList = checkFriendOnline(userInPong, Preferences.friendList); // check user in pong with list of friends

        System.out.println("\n checkUserOnlineAction: newFriendList : " + newFriendList.toString());
        showUserNameFriend.removeAllElements();

        System.out.println("\n checkUserOnlineAction: showUserNameFriend REMOVED : " + showUserNameFriend.toString());

        for (int i = 0; i < newFriendList.size(); i++) {
            showUserNameFriend.add(newFriendList.get(i).getUserName() + newFriendList.get(i).getStatus());
        }
        System.out.println("\n checkUserOnlineAction: showUserNameFriend: " + showUserNameFriend.toString());

        userInPong.removeAllElements(); // loai bo list user in pong sau khi check voi list friends
        System.out.println("\n checkUserOnlineAction: AFTER REMOVED userInPong: " + userInPong.toString());

        AppGUI.listFriends.setListData(showUserNameFriend);
    }

    // check friend online in list pong with list friends file user
    public Vector<Friends> checkFriendOnline(Vector<Friends> userInPong, Vector<Friends> friendList) {

        if (userInPong.isEmpty()) {
            for (int j = 0; j < friendList.size(); j++) {
                editStatusFriendList(friendList, j);
            }
        } else {
            for (int i = 0; i < userInPong.size(); i++) {
                for (int j = 0; j < friendList.size(); j++) {
                   // System.out.println("checkUserOnlineAction GO INSIDE 2 FOR LOOP ");

                    if (friendList.get(j).getIdUserLogin().equals(userInPong.get(i).getIdUserLogin())) {
                        System.out.println("checkUserOnlineAction setStatus - Before [" + j + "]" + friendList.get(j).getStatus());
                        friendList.get(j).setStatus(Preferences.ONLINE);
                        friendList.get(j).setCountOffline(Preferences.COUNTER_ONLINE);
                        System.out.println("checkUserOnlineAction setStatus - After: [" + j + "]" + friendList.get(j).getStatus());
                    } else {
                        editStatusFriendList(friendList, j);
                    }
                }
            }
        }
        
//        System.out.println("checkUserOnlineAction Before 2 FOR LOOP checkFriendOnline: " + userInPong.size());
//
//        for (int i = 0; i < userInPong.size(); i++) {
//            for (int j = 0; j < friendList.size(); j++) {
//
//                System.out.println("checkUserOnlineAction GO INSIDE 2 FOR LOOP ");
//
//                if (friendList.get(j).getIdUserLogin().equals(userInPong.get(i).getIdUserLogin())) {
//                    System.out.println("checkUserOnlineAction setStatus - Before [" + j + "]" + friendList.get(j).getStatus());
//                    friendList.get(j).setStatus(Preferences.ONLINE);
//                    friendList.get(j).setCountOffline(Preferences.COUNTER_ONLINE);
//                    System.out.println("checkUserOnlineAction setStatus - After: [" + j + "]" + friendList.get(j).getStatus());
//                } else {
//                    if (friendList.get(j).getStatus().equals(Preferences.ONLINE)) {
//                        friendList.get(j).setCountOffline(friendList.get(j).getCountOffline() + 1);
//                        System.out.println("checkUserOnlineAction AFTER  - getCountOffline [" + j + "]" + friendList.get(j).getCountOffline());
//                    }
//
//                    if (friendList.get(j).getCountOffline() == 3) {
//                        System.out.println("checkUserOnlineAction setStatus - getCountOffline=3 :[" + j + "]" + friendList.get(j).getCountOffline());
//                        friendList.get(j).setStatus(Preferences.OFFLINE);
//                        friendList.get(j).setCountOffline(Preferences.COUNTER_OFFLINE);
//                        System.out.println("checkUserOnlineAction AFTER  getCountOffline=3: [" + j + "]" + friendList.get(j).getStatus());
//                    }
//                }
//            }
//        }
        return friendList;
    }

    public Vector<Friends> editStatusFriendList(Vector<Friends> friendList, int index) {
        //for (int j = 0; j < friendList.size(); j++) {
        if (friendList.get(index).getStatus().equals(Preferences.ONLINE)) {
            friendList.get(index).setCountOffline(friendList.get(index).getCountOffline() + 1);
            System.out.println("checkUserOnlineAction AFTER  - getCountOffline [" + index + "]" + friendList.get(index).getCountOffline());
        }

        if (friendList.get(index).getCountOffline() == 2) {
            System.out.println("checkUserOnlineAction setStatus - getCountOffline=3 :[" + index + "]" + friendList.get(index).getCountOffline());
            friendList.get(index).setStatus(Preferences.OFFLINE);
            friendList.get(index).setCountOffline(Preferences.COUNTER_OFFLINE);
            System.out.println("checkUserOnlineAction AFTER  getCountOffline=3: [" + index + "]" + friendList.get(index).getStatus());
        }
        //  }
        return friendList;
    }
}
