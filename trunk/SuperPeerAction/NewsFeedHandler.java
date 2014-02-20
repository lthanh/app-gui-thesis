/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.PeerReceivePost;
import Architecture_Posting.IPAddress;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.Preferences;
import Architecture_Posting.SharedDirectory;
import Architecture_Posting.Utils;
import java.util.List;
import java.util.Vector;
import static postService.PostHandler.recieveListPost;
import static postService.PostHandler.showListPost;

/**
 *
 * @author admin
 */
public class NewsFeedHandler extends Thread {

    IPAddress requestIP;
    Request_NewsFeed request_NewsFeed;
    Respond_NewsFeed respond_NewsFeed;
    public static Vector<PostObject> listPostVector;
    public static Vector<String> listPostStringShow;

    public NewsFeedHandler(Request_NewsFeed request_NewsFeed, Respond_NewsFeed respond_NewsFeed) {
        if (request_NewsFeed != null) {
            this.request_NewsFeed = request_NewsFeed;
            this.requestIP = request_NewsFeed.getIP();

        }
        if (respond_NewsFeed != null) {
            this.respond_NewsFeed = respond_NewsFeed;
        }
    }

    public void run() {
        if (request_NewsFeed != null) {
            if (SharedDirectory.listFileIDSaving.contains(request_NewsFeed.getIdUserIDReq() + "_NewsFeed.txt")) {
                System.out.println("## REQUEST listFileIDSaving.contains OK ");
                String listFeed = Preferences.readUserPOSTorNEWSFEED(Preferences.NEWSFEED, request_NewsFeed.getIdUserIDReq(), request_NewsFeed.getIndexPost());
                Respond_NewsFeed respond = new Respond_NewsFeed(request_NewsFeed.getIndexPost(), request_NewsFeed.getIdUserIDReq(), listFeed);
                NetworkManager.writeToOne(requestIP, respond);
            }
        }
        if (respond_NewsFeed != null) {
           // PeerReceivePost prc = new PeerReceivePost();
            System.out.println("######## RESPOND");

            String userIDInRes = respond_NewsFeed.getUserIDReq();//respond_ProfileMsg.getListPost().split("\n\n")[0].split("~~")[2].substring(9);
            if (userIDInRes.equals(LoginForm.currentUser.getIdUserLogin())) {

                String listFeedRespond = respond_NewsFeed.getListPost();

                System.out.println("######## RESPOND listPostRespond: " + listFeedRespond);
                if (listFeedRespond.trim().length() != 0) {
                    String[] tempListPost = listFeedRespond.split("\n\n");

                    for (int i = 0; i < tempListPost.length; i++) {
                        PostObject newsFeedObject = new PostObject();
                        String[] line = tempListPost[i].split("~~");
                        newsFeedObject.setPostID(Long.parseLong(line[1].substring(8)));
                        newsFeedObject.setUserIDPost(line[2].substring(11));
                        newsFeedObject.setNamePost(line[3].substring(14));
                        newsFeedObject.setContentPost(line[4].substring(14));
                        newsFeedObject.setCreatedDate(line[5].substring(12));

                        newsFeedObject.setGroupID("");
//                        if (respond_NewsFeed.getIndexRequested() != -1) {
                        recieveListPost.add(newsFeedObject);
                        showListPost.add(Utils.formSHOWSTATUS(newsFeedObject.getNamePost(), newsFeedObject.getContentPost(), newsFeedObject.getCreatedDate()));
                        AppGUI.inform(showListPost);
//                        } else {
//                            prc.receivePost(newsFeedObject);
//                        }
                    }
                }
            }
        }
    }
}