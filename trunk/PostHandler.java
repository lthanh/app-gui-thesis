
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
        recieveListPost.add(0,postMessage);
        showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.like(), postMessage.comment(), postMessage.createdDate()));
    }


    public void run() {

        AppGUI.inform(myIP, showListPost);
        NetworkManager.writeButOne(postMessage.getIP(), postMessage);  // Query is forwarded to all connected nodes except one from which query came.
        // qt.put((Packet) postMessage, postMessage);
        searchResult = SharedDirectory.search(postMessage.getSearchString());  //check shared directory for query match
        queryID = postMessage.getMessageID();
        port = Mine.getPort();
        myIP = Mine.getIPAddress();
        speed = Mine.getSpeed();
        serventID = Mine.getServentIdentifier();
        queryHit = new QueryHit(numHits, port, myIP, speed, searchResult, serventID, queryID);
        NetworkManager.writeToOne(postMessage.getIP(), queryHit);  //send qHit back to node that sent original query
        
         
    }
}
