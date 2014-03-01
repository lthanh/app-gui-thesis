package PeerAction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import GUI.AppGUI;
import Architecture_Posting.*;
import Architecture_Posting.PongHandler;
import static Architecture_Posting.PongHandler.listPongTable;
import Architecture_Posting.Preferences;
import java.util.*;

/**
 *
 * @author Thanh Le Quoc
 */
public class CheckUserOnlineAction {

    public CheckUserOnlineAction() {
    }
    public static Vector<String> showUserNameFriend = new Vector<String>(); // show friends in JList
    public static Vector<Friends> userInPong = new Vector<Friends>();
    public static Vector<Friends> friendObjectCheckOnline; // list friend object currently online
    Vector<Friends> tempListFriend = AppGUI.tempListFriend;

    public void checkUserOnline() {
        if (!PongHandler.listPong.isEmpty()) {
            for (int i = 0; i < PongHandler.listPong.size(); i++) {
                Friends userPongObject = new Friends();
                userPongObject.setIdUserLogin(PongHandler.listPong.get(i).getUserIDOnline());
                userPongObject.setUserName(PongHandler.listPong.get(i).getUserNameOnline());
                userPongObject.setStatus(Preferences.ONLINE);
                userPongObject.setCountOffline(Preferences.COUNTER_ONLINE);
                userInPong.add(userPongObject);
            }
        }

        PongHandler.listPong.removeAllElements();

        Vector<Friends> newFriendList = checkFriendOnline(userInPong, tempListFriend); // check user in pong with list of friends
        showUserNameFriend.removeAllElements();

        for (int i = 0; i < newFriendList.size(); i++) {
            showUserNameFriend.add(newFriendList.get(i).getUserName() + newFriendList.get(i).getStatus());
        }

        userInPong.removeAllElements(); // loai bo list user in pong sau khi check voi list friends
        listPongTable.clear();
        AppGUI.listFriends.setListData(showUserNameFriend);
    }

    // check friend online in list pong with list friends file user
    public Vector<Friends> checkFriendOnline(Vector<Friends> userInPong, Vector<Friends> friendList) {

        if (userInPong.isEmpty()) {
            for (int j = 0; j < friendList.size(); j++) {
                editStatusFriendList(friendList, j);
            }
        } else {

            for (int j = 0; j < friendList.size(); j++) {
                boolean isOnline = checkListFriendORPeerInPong(userInPong, friendList.get(j));
                if (isOnline) {
                    friendList.get(j).setStatus(Preferences.ONLINE);
                    friendList.get(j).setCountOffline(Preferences.COUNTER_ONLINE);
                } else {
                    editStatusFriendList(friendList, j);
                }
            }
        }
        return friendList;
    }

    public Vector<Friends> editStatusFriendList(Vector<Friends> friendList, int index) {
        if (friendList.get(index).getStatus().equals(Preferences.ONLINE)) {
            friendList.get(index).setCountOffline(friendList.get(index).getCountOffline() + 1);
        }

        if (friendList.get(index).getCountOffline() >= 3) {
            friendList.get(index).setStatus(Preferences.OFFLINE);
            friendList.get(index).setCountOffline(Preferences.COUNTER_OFFLINE);
        }
        return friendList;
    }

    public boolean checkListFriendORPeerInPong(Vector<Friends> userInPong, Friends friendList) {
        for (int i = 0; i < userInPong.size(); i++) {
            if (userInPong.get(i).getIdUserLogin().equals(friendList.getIdUserLogin())) {
                return true;
            }
        }
        return false;
    }
}
