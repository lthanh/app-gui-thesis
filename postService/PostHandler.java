package postService;

import architecture.*;
import postService.Post;
import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.*;
import SuperPeerAction.PostObject;
import SuperPeerAction.Respond_Profile;
import architecture.*;
import static architecture.Preferences.friendList;
import static architecture.Preferences.peerManageList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class PostHandler extends Thread {

    //static variables
    // public static Map qt;  //query table
    //instance variables
    Post postMessage;
    QueryHit queryHit;
    public static IPAddress myIP;
    IPAddress queryIP;
    ResultSet searchResult;
    int numHits;
    int port;
    int speed;
    ResultSet result;
    byte[] serventID;
    byte[] queryID;
    public static Vector<PostObject> recieveListPost = new Vector<PostObject>(); // receive listPost from friends
    public static Vector<String> showListPost = new Vector<String>();

    public PostHandler(IPAddress queryIP, Post postMessage) {
        this.postMessage = postMessage;
        this.postMessage.setIP(queryIP);  //set IPAddress of query
        //  recieveListPost.add(0, postMessage);
        //  showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate()));
    }

    public void run() {

        String listFriendID = postMessage.getGroupFriendID();
        String[] tempListFriendID = listFriendID.split("~~");

        //boolean isFriends = serverCheckListFriendorPeer(postMessage, Preferences.idFriendsListString);
        boolean isFriends = checkGroupFriendIDPost(LoginForm.currentUser.getIdUserLogin(), tempListFriendID);
        boolean isPeer = serverCheckListFriendorPeer(postMessage, Preferences.peerManageList);

        // check to save post to news feed users
        for (int i = 0; i < tempListFriendID.length; i++) {
            boolean isNewsFeedOfPeer = checkNewsFeedForPeer(tempListFriendID[i], Preferences.peerManageList);
            if (isNewsFeedOfPeer) {
                Preferences.writeNewsFeed(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getMessageID(), tempListFriendID[i], postMessage.getUserID(), postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate());
            }
        }



        if (isFriends) { // if friend,show on news feed.
            // (String messageType, long postID, String userIDOnline, String idUserPost, String namePost, String statusContent, String createdDate)
            PostObject post = new PostObject();
            post.setNamePost(postMessage.getUserName());
            post.setPostID(postMessage.getMessageID());
            post.setContentPost(postMessage.getPostStatusContent());
            post.setGroupID(postMessage.getGroupFriendID());
            post.setCreatedDate(postMessage.getCreatedDate());
            post.setUserIDPost(postMessage.getUserID());
            (new peerReceivePost()).receivePost(post);
        }

        if (isPeer) {

            //  System.out.println("PostHandler: " + postMessage.getPayload() + "\n");
            Preferences.statusWriteToFileSuperPeer(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), postMessage.getGroupFriendID(), postMessage.getPostStatusContent(), postMessage.getCreatedDate());
        }

        NetworkManager.writeButOne(postMessage.getIP(), postMessage);  // Query is forwarded to all connected nodes except one from which query came.
    }

    public static boolean checkGroupFriendIDPost(String userLogin, String[] listPeerOrFriend) {
        // String userPostID = post.getUserID();
        for (int i = 0; i < listPeerOrFriend.length; i++) {
            if (userLogin.equals(listPeerOrFriend[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean serverCheckListFriendorPeer(Post post, Vector<String> listPeerOrFriend) {
        String userPostID = post.getUserID();
        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (userPostID.equals(listPeerOrFriend.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkListFriendORPeerInPong(Pong pong, Vector<String> listPeerOrFriend) {
        String userPongID = pong.getUserIDOnline();
        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (userPongID.equals(listPeerOrFriend.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkNewsFeedForPeer(String friendID, Vector<String> listPeerOrFriend) {
        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (friendID.equals(listPeerOrFriend.get(i)) || friendID.equals(LoginForm.currentUser.getIdUserLogin())) { // only server use this class
                return true;
            }
        }
        return false;
    }
}
