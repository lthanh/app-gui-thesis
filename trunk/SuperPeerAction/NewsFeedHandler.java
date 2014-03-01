/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import GUI.LoginForm;
import Architecture_Posting.*;
import static GUI.AppGUI.startShowLoading;
import java.util.*;
import static PostingService.PostHandler.recieveListPost;
import static PostingService.PostHandler.showListPost;
import javax.swing.JOptionPane;

/**
 *
 * @author Thanh Le Quoc
 */
public class NewsFeedHandler extends Thread {

    public static Map request_NewsFeedTable;
    public static Map respond_NewsFeedTable;
    IPAddress requestIP;
    boolean isFileStoring;
    Utils utils = new Utils();
    Request_NewsFeed request_NewsFeed;
    Respond_NewsFeed respond_NewsFeed;
    Request_NewsFeed requestMatch;
    public static Vector<PostObject> listPostVector;
    public static Vector<String> listPostStringShow;
    String userIDLogin = LoginForm.currentUser.getIdUserLogin();

    public NewsFeedHandler(IPAddress ip, Request_NewsFeed request_NewsFeed, Respond_NewsFeed respond_NewsFeed) {
        if (request_NewsFeed != null) {
            this.request_NewsFeed = request_NewsFeed;
            request_NewsFeed.setNewsFeedIP(ip);
//            System.out.println("\n ### Server : Packet == REQ_NewsFeed -- " + request_NewsFeed.getMessageID());
        }
        if (respond_NewsFeed != null) {
            this.respond_NewsFeed = respond_NewsFeed;
//            System.out.println("\n ### Server : Packet == RES_NewsFeed -- " + respond_NewsFeed.getMessageID());

        }
    }

    public static void initRequestNewsFeedTable() {
        request_NewsFeedTable = new Hashtable(5000);
    }

    public static void initRespondNewsFeedTable() {
        respond_NewsFeedTable = new Hashtable(5000);
    }

    public void run() {
        if (request_NewsFeed != null) {
            if (!request_NewsFeedTable.containsKey(request_NewsFeed.getMessageID())) {
                request_NewsFeedTable.put(request_NewsFeed.getMessageID(), request_NewsFeed);

                isFileStoring = utils.checkFileSharing(request_NewsFeed.getIdUserIDReq() + "_NewsFeed.txt");

                if (isFileStoring) { // check server store news feed of the user was requesting
//                    System.out.println("## REQUEST listFileIDSaving.contains OK ");
                    String listFeed = Preferences.readUserPOSTorNEWSFEED(Preferences.NEWSFEED, request_NewsFeed.getIdUserIDReq(), request_NewsFeed.getIndexPost());
                    Respond_NewsFeed respond = new Respond_NewsFeed(request_NewsFeed.getIndexPost(), request_NewsFeed.getIdUserIDReq(), listFeed, request_NewsFeed.getMessageID());
                    NetworkManager.writeToOne(request_NewsFeed.getNewsFeedIP(), respond);

                } else { // server not store news feed of the user was requesting
                    NetworkManager.writeButOne(request_NewsFeed.getNewsFeedIP(), request_NewsFeed);
                }
            }
        }
        if (respond_NewsFeed != null) {
            if (!respond_NewsFeedTable.containsKey(respond_NewsFeed.getMessageID())) {
                respond_NewsFeedTable.put(respond_NewsFeed.getMessageID(), respond_NewsFeed);

                String userIDInRes = respond_NewsFeed.getUserIDReq();
                if (userIDInRes.equals(userIDLogin)) {
                    String listFeedRespond = respond_NewsFeed.getListPost();

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
                            recieveListPost.add(newsFeedObject);
                            showListPost.add(Utils.formSHOWSTATUS(newsFeedObject.getNamePost(), newsFeedObject.getContentPost(), newsFeedObject.getCreatedDate()));
                        }
                        // need sleep 6 seconds before show status
                        int count = 0;
                        long timeout = 1500;
                        while (true) {
                            try {
                                Thread.sleep(timeout);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                            count++;
                            if (count == 2) {
                                AppGUI.loadingForm.hide();
                                startShowLoading = -1;
                                AppGUI.inform(showListPost); // show list post
                                break;
                            }
                        }
                    } else {
                        AppGUI.loadingForm.hide();
                        utils.endOfFile();
                    }
                } else {
                    requestMatch = (Request_NewsFeed) request_NewsFeedTable.get(respond_NewsFeed.getMessageID());
                    requestIP = requestMatch.getNewsFeedIP();
                    NetworkManager.writeToOne(requestIP, respond_NewsFeed);
                }
            }
        }
    }
}