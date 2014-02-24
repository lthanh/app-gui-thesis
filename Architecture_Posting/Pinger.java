package Architecture_Posting;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.CheckUserOnlineAction;
import SuperPeerAction.PostObject;
import java.util.Date;
import PostingService.Post;
import PostingService.PostHandler;

public class Pinger extends Thread {

    static int hosts = 0;
    static int totalkb = 0;
    static int totalfiles = 0;
    static Ping myping;
    IPAddress ip;
    public static int pingCounter = 0;
    public static int numberTimesCounter = 4;
    public static int i = 550;
    CheckUserOnlineAction check = new CheckUserOnlineAction();
    /*
     int port = 6346;
     byte[] ipbytes = socket.getInetAddress().getAddress();
    
     // Cat chuoi IP byte ra dang decimal *****************************************************
     int[] ipints = new int[4];
     for (int i = 0; i < 4; i++)
     ipints[i] = ((int)(ipbytes[i]) & 0xff);
    
     // end cat chuoi
    
    
     ip = new IPAddress(ipints[0], ipints[1], ipints[2], ipints[3], port);
    
    
     */

    public void run() {
        while (true) {
            try {
                sleep(Preferences.PINGER_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Searcher.updateInfo(hosts, totalkb, totalfiles);
            myping = new Ping();
            pingCounter++;

            System.out.println("\n pingCounter times: " + pingCounter);
            System.out.println("\n numberTimesCounter times: " + numberTimesCounter);


            if (pingCounter == numberTimesCounter) {
                check.checkUserOnline();
            }
            NetworkManager.writeToAll(myping);


            /*
            
             String userID = LoginForm.currentUser.getIdUserLogin();
             String userNamePost = LoginForm.currentUser.getUserName();
             String createdate = Utils.formatDate(new Date());
             String friend = "9999999999999999:0000000000000000:1111111111111111:5555555555555555:8888888888888888:4444444444444444:3333333333333333:2222222222222222:6666666666666666";
             String groupdSuperPeerID = "9999999999999999:0000000000000000"; // ignore

             Post postMessage = new Post(userID, createdate.length(), friend.length(), groupdSuperPeerID.length(), userNamePost.length(), userNamePost, createdate, friend, groupdSuperPeerID, String.valueOf(i));
             i++;
             PostObject postWrite = new PostObject();
             postWrite.setNamePost(postMessage.getUserName());
             postWrite.setPostID(postMessage.getMessageID());
             postWrite.setContentPost(postMessage.getPostStatusContent());
             postWrite.setGroupID(postMessage.getGroupFriendID());
             postWrite.setCreatedDate(postMessage.getCreatedDate());
             postWrite.setUserIDPost(postMessage.getUserID());

             PostHandler.recieveListPost.add(0, postWrite);
             PostHandler.showListPost.add(0, Utils.formSHOWSTATUS(postWrite.getNamePost(), postWrite.getContentPost(), postWrite.getCreatedDate()));
             AppGUI.inform(PostHandler.showListPost);


             NetworkManager.writeToAll(postMessage);
             */
        }
    }
}