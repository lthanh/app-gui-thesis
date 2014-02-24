package PostingService;

import Architecture_Posting.Friends;
import Architecture_Posting.QueryHit;
import Architecture_Posting.IPAddress;
import Architecture_Posting.Pong;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.ResultSet;
import Architecture_Posting.Preferences;
import GUI.LoginForm;
import PeerAction.*;
import SuperPeerAction.PostObject;
import Architecture_Posting.Utils;
import java.util.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class PostHandler extends Thread {

    public static Map postTable;  //post table     
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
    Utils utils = new Utils();
    boolean isServerID;
    boolean isServerName = utils.checkServerName(LoginForm.currentUser.getUserName());

    public PostHandler(IPAddress ip, Post postMessage) {
        this.postMessage = postMessage;
        postMessage.setPostIP(ip);  //set IPAddress of query
    }

    public static void initPostTable() {
        postTable = new Hashtable(5000);
    }

    public void run() {
        if (!postTable.containsKey(postMessage.getMessageID())) //check that postMessage is not already in table
        {
            postTable.put(postMessage.getMessageID(), postMessage);
            String listFriendID = postMessage.getGroupFriendID();
            String[] tempListFriendID = listFriendID.split(":");

            boolean isFriends = checkGroupFriendIDPost(LoginForm.currentUser.getIdUserLogin(), tempListFriendID);
            boolean isPeer = serverCheckListFriendorPeer(postMessage, Preferences.peerManageList);

            if (isServerName) {

                // check list Friend in Post to save post to friends News feed
                for (int i = 0; i < tempListFriendID.length; i++) {
                    boolean isNewsFeedOfPeer = checkNewsFeedForPeer(tempListFriendID[i], Preferences.peerManageList);
                    if (isNewsFeedOfPeer) {
                        Preferences.writeNewsFeed(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getMessageID(), tempListFriendID[i], postMessage.getUserID(), postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate());
                    }
                }

                String listServerID = postMessage.getGroupSuperPeerID();
                /*
                 if list server peer in post message contain id of server logging in 
                 and the user request are the peer that SP are managing, then it will store the message
                 */
                isServerID = utils.checkServerID(LoginForm.currentUser.getIdUserLogin(), listServerID);
                if (isPeer && isServerID) { // if id of user post is managing by SP and the post is requiring SP store this message based on List Super Peer in Post 
                    Preferences.statusWriteToFileSuperPeer(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), postMessage.getGroupFriendID(), postMessage.getPostStatusContent(), postMessage.getCreatedDate());
                }
            }

            if (isFriends) { // if friend,show on news feed.
                PostObject post = new PostObject();
                post.setNamePost(postMessage.getUserName());
                post.setPostID(postMessage.getMessageID());
                post.setContentPost(postMessage.getPostStatusContent());
                post.setGroupID(postMessage.getGroupFriendID());
                post.setCreatedDate(postMessage.getCreatedDate());
                post.setUserIDPost(postMessage.getUserID());
                (new PeerReceivePost()).receivePost(post);
            }

            NetworkManager.writeButOne(postMessage.getPostIP(), postMessage);  // Query is forwarded to all connected nodes except one from which query came.
        }
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

//        for (int i = 0; i < listPeerOrFriend.size(); i++) {
        if (listPeerOrFriend.contains(userPostID)) {
//            if (userPostID.equals(listPeerOrFriend.get(i))) {
            return true;
//            }
        }
        return false;
    }

    public static boolean checkListFriendORPeerInPong(Pong pong, Vector<Friends> listPeerOrFriend) {
        String userPongID = pong.getUserIDOnline();
        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (userPongID.equals(listPeerOrFriend.get(i).getIdUserLogin())) {
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
