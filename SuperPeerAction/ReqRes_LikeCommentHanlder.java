/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import Architecture_Posting.IPAddress;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.Packet;
import Architecture_Posting.Preferences;
import Architecture_Posting.SharedDirectory;
import Architecture_Posting.Utils;
import GUI.LoginForm;
import java.util.*;
import PostingService.LikeCommentListObject;

/**
 *
 * @author admin
 */
public class ReqRes_LikeCommentHanlder extends Thread {

    public static Map requestLikeCommentTable;
    public static Map respondLikeCommentTable;
    IPAddress requestIP;
    Request_LikeCmt request;
    Respond_LikeCmt respond;
    Request_LikeCmt requestMatch;
    Utils utils = new Utils();
    boolean isFileStoring;
    public static Vector<String> comment;

    public ReqRes_LikeCommentHanlder(IPAddress ip, Request_LikeCmt request, Respond_LikeCmt respond) {
        if (request != null) {
            this.request = request;
            request.setLikeCmtIP(ip);

        }
        if (respond != null) {
            this.respond = respond;
        }
    }

    public static void initRequestLikeCommentTable() {
        requestLikeCommentTable = new Hashtable(5000);
    }

    public static void initRespondLikeCommentTable() {
        respondLikeCommentTable = new Hashtable(5000);
    }

    public void run() {
        if (request != null) {
            if (!requestLikeCommentTable.containsKey(request.getMessageID())) {
                requestLikeCommentTable.put(request.getMessageID(), request);
                System.out.println("######## REQUEST");
                System.out.println("## REQUEST - USERID: " + request.getIdUserIDReq());

                isFileStoring = utils.checkFileSharing(request.getIdUserIDReq() + ".txt");

                if (isFileStoring) { // if server store data of user was requesting
                    System.out.println("## REQUEST listFileIDSaving.contains OK ");

                    LikeCommentListObject likeComment = new LikeCommentListObject();
                    likeComment = Preferences.readUserFile(request.getPostIDReq(), request.getIdUserIDReq());
                    System.out.println("## REQUEST likeComment Object - " + " - " + request.getPostIDReq() + likeComment.getNumLike() + " - " + likeComment.getNumComment() + " - " + request.getIdUserIDReq().length() + " - " + likeComment.getUserNameLike().length() + " - " + request.getIdUserIDReq() + " - " + likeComment.getUserNameLike() + " - " + likeComment.getComment());

                    if (likeComment.isIsContainPost()) { // contain postID and respond the result
                        Respond_LikeCmt respond = new Respond_LikeCmt(request.getPostIDReq(), likeComment.getNumLike(), likeComment.getNumComment(), request.getIdUserIDReq().length(), likeComment.getUserNameLike().length(), likeComment.getComment().length(), request.getIdUserIDReq(), likeComment.getUserNameLike(), likeComment.getComment(), request.getIdViewer(), request.getMessageID());

                        NetworkManager.writeToOne(request.getLikeCmtIP(), respond);
                    } else { // not contain post and forward to other super peers
                        NetworkManager.writeButOne(request.getLikeCmtIP(), request);
                    }
                } else { // not store data of user was requesting
                    NetworkManager.writeButOne(request.getLikeCmtIP(), request);
                }
            }
        }
        if (respond != null) {
            System.out.println("######## RESPOND");
            if (!respondLikeCommentTable.containsKey(respond.getMessageID())) {
                respondLikeCommentTable.put(respond.getMessageID(), respond);

                //////////////////////
                String idViewer = respond.getIdViewer();
                if (idViewer.equals(LoginForm.currentUser.getIdUserLogin())) {

                    try {
                        if (respond.getPostID() == AppGUI.postSelectedID) {
                            comment = new Vector<String>();
                            String commentRespond = respond.getListComment();

                            System.out.println("RESPOND RESPOND" + commentRespond);
                            if (!commentRespond.trim().equals("")) {
                                System.out.println("######## RESPOND commentRespond: " + commentRespond);
                                String[] tempComment = commentRespond.split("\n\n");

                                for (int i = 0; i < tempComment.length; i++) { // have to -1 because last item in tempComment is empty, so we do not need to add this item.
                                    comment.add(0, tempComment[i]);
                                    System.out.println("######## RESPOND commentList: " + tempComment[i]);
                                }

                            }

                            if (AppGUI.statusPOPUP != null) {
                                AppGUI.statusPOPUP.updateStatusForm(respond.getNumLike(), respond.getNumComment(), respond.getListUserNameLike(), comment);
                            }
                            System.out.println("########## RESPOND after show");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    requestMatch = (Request_LikeCmt) requestLikeCommentTable.get(respond.getMessageID());
                    requestIP = requestMatch.getLikeCmtIP();
                    NetworkManager.writeToOne(requestIP, respond);
                }
            }
        }
    }
}