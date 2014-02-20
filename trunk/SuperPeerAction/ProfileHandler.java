/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.PeerReceivePost;
import static SuperPeerAction.LikeCommentHanlder.comment;
import Architecture_Posting.IPAddress;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.Preferences;
import Architecture_Posting.SharedDirectory;
import Architecture_Posting.Utils;
import java.util.Vector;
import postService.Comment;
import postService.Like;
import postService.LikeCommentListObject;
import postService.PostHandler;
import static postService.PostHandler.recieveListPost;
import static postService.PostHandler.showListPost;

/**
 *
 * @author admin
 */
public class ProfileHandler extends Thread {

    IPAddress requestIP;
    Request_Profile request_ProfileMsg;
    Respond_Profile respond_ProfileMsg;
    public static Vector<PostObject> listPostVector;
    public static Vector<String> listPostStringShow;

    public ProfileHandler(Request_Profile request_ProfileMsg, Respond_Profile respond_ProfileMsg) {
        if (request_ProfileMsg != null) {
            this.request_ProfileMsg = request_ProfileMsg;
            this.requestIP = request_ProfileMsg.getIP();

        }
        if (respond_ProfileMsg != null) {
            this.respond_ProfileMsg = respond_ProfileMsg;
        }
    }

    public void run() {
        if (request_ProfileMsg != null) { // readUserPOST(String userPostID)

            System.out.println("######## REQUEST");
            System.out.println("## REQUEST - USERID: " + request_ProfileMsg.getIdUserIDReq());
            System.out.println("## REQUEST IP: " + requestIP.toString());

            if (SharedDirectory.listFileIDSaving.contains(request_ProfileMsg.getIdUserIDReq())) {
                System.out.println("## REQUEST listFileIDSaving.contains OK ");
                String listPost = Preferences.readUserPOSTorNEWSFEED(Preferences.PROFILE, request_ProfileMsg.getIdUserIDReq(), request_ProfileMsg.getIndexProfile());
                Respond_Profile respond = new Respond_Profile(request_ProfileMsg.getIdUserIDReq(), listPost);
                System.out.println("REQUEST => RESPOND: " + respond.getListPost());
                NetworkManager.writeToOne(requestIP, respond);
            }
        }
        if (respond_ProfileMsg != null) {
            PeerReceivePost prc = new PeerReceivePost();
            System.out.println("######## RESPOND");

            String userIDInRes = respond_ProfileMsg.getUserIDPost();//respond_ProfileMsg.getListPost().split("\n\n")[0].split("~~")[2].substring(9);
            if (userIDInRes.equals(AppGUI.idUserSelected)) {

                String listPostRespond = respond_ProfileMsg.getListPost();

                System.out.println("######## RESPOND listPostRespond: " + listPostRespond);
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
                        // prc.receivePost(profileObject);

                        recieveListPost.add(profileObject);
                        showListPost.add(Utils.formSHOWSTATUS(profileObject.getNamePost(), profileObject.getContentPost(), profileObject.getCreatedDate()));
                        AppGUI.inform(showListPost);
                    }
                }
                // read private message that only store on user device before
                try {
                    System.out.println("########## RESPOND after show");

                    if (SharedDirectory.listFileIDSaving.contains(AppGUI.idUserSelected)) {
                        String listPost = Preferences.readPrivatePOST(AppGUI.idUserSelected);
                        System.out.println("listPost Preferences:" + listPost);

                        if (listPost.trim().length() != 0) {
                            String[] tempPost = listPost.split("\n\n");

                            for (int i = 0; i < tempPost.length; i++) {
                                String[] linePost = tempPost[i].split("~~");
                                boolean isPosted = checkPostInlist(Long.parseLong(linePost[1].substring(8)), recieveListPost);
                                if (isPosted == false) {
                                    PostObject profileObject = new PostObject();
                                    profileObject.setPostID(Long.parseLong(linePost[1].substring(8)));
                                    profileObject.setNamePost(linePost[2].substring(9));
                                    profileObject.setContentPost(linePost[3].substring(14));
                                    profileObject.setGroupID(linePost[4].substring(15));
                                    profileObject.setCreatedDate(linePost[5].substring(12));
                                    profileObject.setUserIDPost(AppGUI.idUserSelected);
                                    prc.receivePost(profileObject);
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkPostInlist(long postID, Vector<PostObject> profile) {
        System.out.println("profile NULL: " + profile);

//        if (profile != null) {
        for (int i = 0; i < profile.size(); i++) {
            if (postID == profile.get(i).getPostID()) {
                return true;
            }
//            }
        }

        return false;
    }

//    public void showListPostProfile(Vector<PostObject> listPostVector) {
//        for (int i = 0; i < listPostVector.size(); i++) {
//            listPostStringShow.add(Utils.formSHOWSTATUS(listPostVector.get(i).getNamePost(), listPostVector.get(i).getContentPost(), listPostVector.get(i).getCreatedDate()));
//        }
//    }
}
