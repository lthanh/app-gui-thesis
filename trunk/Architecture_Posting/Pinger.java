package Architecture_Posting;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.CheckUserOnlineAction;
import SuperPeerAction.PostObject;
import java.util.Date;
import postService.Post;
import postService.PostHandler;

public class Pinger extends Thread {

    static int hosts = 0;
    static int totalkb = 0;
    static int totalfiles = 0;
    static Ping myping;
    public static int pingCounter = 0;
    public static int i = 1;
    CheckUserOnlineAction check = new CheckUserOnlineAction();

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

            System.out.println("\n PINGer BEFORE times:  " + pingCounter);

            if (pingCounter == 2) {
                System.out.println("\n PINGer 3 times: " + pingCounter);
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