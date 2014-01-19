package postService;
import architecture.*;
import postService.Post;
import GUI.AppGUI;
import PeerAction.peerReceivePost;
import architecture.*;
import static architecture.Preferences.friendList;
import static architecture.Preferences.peerManageList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
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
    public static Vector<Post> recieveListPost = new Vector<Post>(); // receive listPost from friends
    public static Vector<String> showListPost = new Vector<String>();

    public PostHandler(IPAddress queryIP, Post postMessage) {
        this.postMessage = postMessage;
        postMessage.setIP(queryIP);  //set IPAddress of query
        recieveListPost.add(0, postMessage);
        showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate()));
    }

    public void run() {
      

        boolean isFriends = serverCheckListFriendorPeer(postMessage, Preferences.idFriendsListString);
        boolean isPeer = serverCheckListFriendorPeer(postMessage, peerManageList);

        if (isFriends) { // if friend,show on news feed.
            peerReceivePost peerReceivePost = new peerReceivePost();
            peerReceivePost.receivePost(postMessage);
        }

        if (isPeer) {
            Preferences.statusWriteToFileSuperPeer(postMessage.getPostTypeString(), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), postMessage.getGroupFriendID(), postMessage.getPostStatusContent(), postMessage.getCreatedDate());
        }

        NetworkManager.writeButOne(postMessage.getIP(), postMessage);  // Query is forwarded to all connected nodes except one from which query came.




        // AppGUI.inform(myIP, showListPost);
        // qt.put((Packet) postMessage, postMessage);
//        searchResult = SharedDirectory.search(postMessage.getSearchString());  //check shared directory for query match
//        queryID = postMessage.getMessageID();
//        port = Mine.getPort();
//        myIP = Mine.getIPAddress();
//        speed = Mine.getSpeed();
//        serventID = Mine.getServentIdentifier();
//        queryHit = new QueryHit(numHits, port, myIP, speed, searchResult, serventID, queryID);
        //  NetworkManager.writeToOne(postMessage.getIP(), queryHit);  //send qHit back to node that sent original query


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
}
