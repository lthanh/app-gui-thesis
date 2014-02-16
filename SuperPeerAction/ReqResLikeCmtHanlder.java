/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import GUI.AppGUI;
import static SuperPeerAction.ReqResLikeCmtHanlder.comment;
import architecture.IPAddress;
import architecture.NetworkManager;
import architecture.Preferences;
import architecture.SharedDirectory;
import java.util.Vector;
import postService.LikeCommentListObject;

/**
 *
 * @author admin
 */
public class ReqResLikeCmtHanlder extends Thread {

    IPAddress requestIP;
    Request_LikeCmt request;
    Respond_LikeCmt respond;
    public static Vector<String> comment;

    public ReqResLikeCmtHanlder(Request_LikeCmt request, Respond_LikeCmt respond) {
        if (request != null) {
            this.request = request;
            this.requestIP = request.getIP();

        }
        if (respond != null) {
            this.respond = respond;
        }
    }

    public void run() {
        if (request != null) {

            System.out.println("######## REQUEST");
            System.out.println("## REQUEST - USERID: " + request.getIdUserIDReq());

            System.out.println("## REQUEST IP: " + requestIP.toString());

            if (SharedDirectory.listFileIDSaving.contains(request.getIdUserIDReq())) {
                System.out.println("## REQUEST listFileIDSaving.contains OK ");

                LikeCommentListObject likeComment = new LikeCommentListObject();
                likeComment = Preferences.readUserFile(request.getPostIDReq(), request.getIdUserIDReq());
                System.out.println("## REQUEST likeComment Object - " + " - " + request.getPostIDReq() + likeComment.getNumLike() + " - " + likeComment.getNumComment() + " - " + request.getIdUserIDReq().length() + " - " + likeComment.getUserNameLike().length() + " - " + request.getIdUserIDReq() + " - " + likeComment.getUserNameLike() + " - " + likeComment.getComment());

                Respond_LikeCmt respond = new Respond_LikeCmt(request.getPostIDReq(), likeComment.getNumLike(), likeComment.getNumComment(), request.getIdUserIDReq().length(), likeComment.getUserNameLike().length(), request.getIdUserIDReq(), likeComment.getUserNameLike(), likeComment.getComment());
                System.out.println("REQUEST => RESPOND: " + respond.getUserIDReq() + " - " + respond.getNumComment() + " - " + respond.getNumLike() + " - " + respond.getListUserNameLike() + " - " + respond.getListUserNameLikeLength() + " - " + respond.getListComment());
                NetworkManager.writeToOne(requestIP, respond);
            }

        }
        if (respond != null) {
            System.out.println("######## RESPOND");
            try {
                comment = new Vector<String>();
                String commentRespond = respond.getListComment();
                if (!commentRespond.equals("")) {
                    System.out.println("######## RESPOND commentRespond: " + commentRespond);
                    String[] tempComment = commentRespond.split("\n\n");
                    for (int i = 0; i < tempComment.length; i++) {
                        comment.add(0, tempComment[i]);
                        System.out.println("######## RESPOND commentList: " + tempComment[i]);
                    }

                }
                if (respond.getPostID() == AppGUI.postSelectedID) {
                    System.out.println("########## RESPOND before show");
                    System.out.println("########## RESPOND respond.getPostID() show :" + respond.getPostID());
                    System.out.println("########## RESPOND respond.getNumLike() show :" + respond.getNumLike());
                    System.out.println("########## RESPOND respond.getNumComment() show :" + respond.getNumComment());
                    System.out.println("########## RESPOND respond.getListUserNameLike() show :" + respond.getListUserNameLike());
//                    if (comment.size() > 1) {
//                        for (String i : comment) {
//                            System.out.println("########## RESPOND comment show :" + i);
//                        }
//                    }
                    if (AppGUI.statusPOPUP != null) {
                        AppGUI.statusPOPUP.updateStatusForm(respond.getNumLike(), respond.getNumComment(), respond.getListUserNameLike(), comment);
                    }
                    System.out.println("########## RESPOND after show");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}