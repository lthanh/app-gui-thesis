
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
    public static Map qt;  //query table
    //instance variables
    Post postMessage;
    QueryHit queryHit;
    IPAddress myIP;
    IPAddress queryIP;
    ResultSet searchResult;
    int numHits;
    int port;
    int speed;
    ResultSet result;
    byte[] serventID;
    byte[] queryID;

    public PostHandler(IPAddress queryIP, Post postMessage) {
        this.postMessage = postMessage;
        postMessage.setIP(queryIP);  //set IPAddress of query
    }

    /////////////////////////////////////////////////////////////// **
    public static void initQueryTable() {
        qt = new Hashtable(5000);
    }
    /////////////////////////////////////////////////////////////// **

    //QHandler handles incoming queries.  
    public void run() {
        //hmmm.. this seems like potential bug.  I want to check that query is not in table.  But even if query table contains key,
        //that does not necessarily mean it is in table, b/c two queries can have SAME HASHCODE VALUE.  I need to have some other means.
        //Will talk to Rusty @ this on Monday.

           AppGUI.inform(myIP, postMessage);
        
        
        /*
        
        if (!qt.containsKey(postMessage)) //check that query is not already in table
        {
            AppGUI.inform(postMessage); // Give information to the Search Monitor panel
            NetworkManager.writeButOne(postMessage.getIP(), postMessage);  // Query is forwarded to all connected nodes except one from which query came.
             
            qt.put((Packet) postMessage, postMessage);    //add query to table, indexed by its unique MessageID
            //searchResult = SharedDirectory.search(postMessage.getSearchString());  //check shared directory for query match
            //numHits = searchResult.getSize();


            queryID = postMessage.getMessageID();
            port = Mine.getPort();
            myIP = Mine.getIPAddress();
            speed = Mine.getSpeed();
            serventID = Mine.getServentIdentifier();
            queryHit = new QueryHit(numHits, port, myIP, speed, searchResult, serventID, queryID);
            NetworkManager.writeToOne(postMessage.getIP(), queryHit);  //send qHit back to node that sent original query

        }
        * */
    }
}
