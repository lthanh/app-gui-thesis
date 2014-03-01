/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import PeerAction.PeerReceivePost;
import Architecture_Posting.*;
import static GUI.AppGUI.startShowLoading;
import java.util.*;
import static PostingService.PostHandler.recieveListPost;
import static PostingService.PostHandler.showListPost;

/**
 *
 * @author Thanh Le Quoc
 */
public class ProfileHandler extends Thread {

    public static Map request_ProfileTable;  // request_Profile table   
    public static Map respond_ProfileTable;  // request_Profile table     
    IPAddress requestIP;
    Request_Profile request_ProfileMsg;
    Respond_Profile respond_ProfileMsg;
    Request_Profile requestMatch;
    Utils utils = new Utils();
    boolean isFileStoring;
    public static boolean isLoadedPrivate = false;
    public static Vector<PostObject> listPostVector;
    public static Vector<String> listPostStringShow;
    public static int lengthOfListPrivatePost;

    public ProfileHandler(IPAddress ip, Request_Profile request_ProfileMsg, Respond_Profile respond_ProfileMsg) {
        if (request_ProfileMsg != null) {
            this.request_ProfileMsg = request_ProfileMsg;
            request_ProfileMsg.setProfileIP(ip);
//            System.out.println("\n ### Server : Packet == REQ_PROFILE -- " + request_ProfileMsg.getMessageID());

        }
        if (respond_ProfileMsg != null) {
            this.respond_ProfileMsg = respond_ProfileMsg;
//            System.out.println("\n ### Server : Packet == RES_PROFILE -- " + respond_ProfileMsg.getMessageID());

        }
    }

    public static void initRequestProfileTable() {
        request_ProfileTable = new Hashtable(5000);
    }

    public static void initRespondProfileTable() {
        respond_ProfileTable = new Hashtable(5000);
    }

    public void run() {
        if (request_ProfileMsg != null) { // readUserPOST(String userPostID)
            if (!request_ProfileTable.containsKey(request_ProfileMsg.getMessageID())) //check that request_ProfileMsg is not already in table
            {
                request_ProfileTable.put(request_ProfileMsg.getMessageID(), request_ProfileMsg);
                isFileStoring = utils.checkFileSharing(request_ProfileMsg.getIdUserIDReq() + ".txt");
                if (isFileStoring) { // check server store profile of the user was requesting
                    String listPost = Preferences.readUserPOSTorNEWSFEED(Preferences.PROFILE, request_ProfileMsg.getIdUserIDReq(), request_ProfileMsg.getIndexProfile());
                    Respond_Profile respond = new Respond_Profile(request_ProfileMsg.getIdUserIDReq(), listPost, request_ProfileMsg.getMessageID());
                    NetworkManager.writeToOne(request_ProfileMsg.getProfileIP(), respond);
                } else { // server not store profile of the user was requesting
                    NetworkManager.writeButOne(request_ProfileMsg.getProfileIP(), request_ProfileMsg);
                }
            }
        }
        if (respond_ProfileMsg != null) {
            if (!respond_ProfileTable.containsKey(respond_ProfileMsg.getMessageID())) //check that request_ProfileMsg is not already in table
            {
                respond_ProfileTable.put(respond_ProfileMsg.getMessageID(), respond_ProfileMsg);
                String userIDInRes = respond_ProfileMsg.getUserIDPost();//respond_ProfileMsg.getListPost().split("\n\n")[0].split("~~")[2].substring(9);
                if (userIDInRes.equals(AppGUI.idUserSelected)) {
                    String listPostRespond = respond_ProfileMsg.getListPost();

                    if (listPostRespond.trim().length() != 0) { // add
                        String[] tempListPost = listPostRespond.split("\n\n");
                        for (int i = 0; i < tempListPost.length; i++) {
                            PostObject profileObject = new PostObject();
                            String[] line = tempListPost[i].split("~~");
                            profileObject.setPostID(Long.parseLong(line[1].substring(8)));
                            profileObject.setNamePost(line[2].substring(9));
                            profileObject.setContentPost(line[3].substring(14));
                            profileObject.setGroupID(line[4].substring(15));
                            profileObject.setCreatedDate(line[5].substring(12));
                            profileObject.setUserIDPost(respond_ProfileMsg.getUserIDPost());
                            recieveListPost.add(profileObject);
                            showListPost.add(Utils.formSHOWSTATUS(profileObject.getNamePost(), profileObject.getContentPost(), profileObject.getCreatedDate()));
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
                                AppGUI.inform(showListPost);
                                break;
                            }
                        }
                    } else {
                        AppGUI.loadingForm.hide();
                        utils.endOfFile();
                    }

                    // read private message that only store on user device before
                    if (isLoadedPrivate == false) {
                        isLoadedPrivate = true;
                        List<PostObject> listPrivate = utils.getPrivateMessage();
                        lengthOfListPrivatePost = listPrivate.size();
                    }


                } else {
                    requestMatch = (Request_Profile) request_ProfileTable.get(respond_ProfileMsg.getMessageID());
                    requestIP = requestMatch.getProfileIP();
                    NetworkManager.writeToOne(requestIP, respond_ProfileMsg);
                }
            }
        }
    }
}
