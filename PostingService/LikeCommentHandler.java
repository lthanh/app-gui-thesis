/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

import GUI.AppGUI;
import GUI.LoginForm;
import SuperPeerAction.SaveLikeCmtAction;
import Architecture_Posting.IPAddress;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.Preferences;
import Architecture_Posting.Utils;
import java.util.*;

/**
 *
 * @author Thanh Le Quoc
 */
public class LikeCommentHandler extends Thread {

    public static Map likeTabble;
    public static Map commentTable;
    Like likeMessage;
    Comment commentMessage;
    Utils utils = new Utils();
    boolean isFileStoring;
    String useIDLogin = LoginForm.currentUser.getIdUserLogin();
    String userNameLogin = LoginForm.currentUser.getUserName();
    boolean isServer = utils.checkServerName(userNameLogin);
    public static Vector<String> listPeerManage = Preferences.peerManageList;

    public LikeCommentHandler(IPAddress ip, Like likeMessage, Comment commentMessage) {
        if (likeMessage != null) {
            this.likeMessage = likeMessage;
            likeMessage.setLikeIP(ip);
            System.out.println("\n ### Server : Packet == LIKE -- " + likeMessage.getMessageID());

        }
        if (commentMessage != null) {
            this.commentMessage = commentMessage;
            commentMessage.setCommentIP(ip);
            System.out.println("\n ### Server : Packet == COMMENT -- " + commentMessage.getMessageID());

        }
    }

    public static void initLikeTable() {
        likeTabble = new Hashtable(5000);
    }

    public static void initCommentTable() {
        commentTable = new Hashtable(5000);
    }

    public void run() {
        if (likeMessage != null) {
            if (!likeTabble.containsKey(likeMessage.getMessageID())) {
                likeTabble.put(likeMessage.getMessageID(), likeMessage);

                if (isServer) {
                    isFileStoring = utils.checkFileSharing(likeMessage.getIdUserPost() + ".txt");
                    if (isFileStoring) {
                        (new SaveLikeCmtAction()).saveLikeSuperPeer(likeMessage);
                    }
                    NetworkManager.writeButOne(likeMessage.getLikeIP(), likeMessage); // Query
                }
                if (likeMessage.getIdUserPost().equals(useIDLogin)) {
                    AppGUI.updateNotification(1, likeMessage.getNameLike(), "", likeMessage.getPostContent());
                }
            }
        }

        if (commentMessage != null) {
            if (!commentTable.containsKey(commentMessage.getMessageID())) {
                commentTable.put(commentMessage.getMessageID(), commentMessage);
                if (isServer) {
                    isFileStoring = utils.checkFileSharing(commentMessage.getIdUserPost() + ".txt");
                    if (isFileStoring) {
                        (new SaveLikeCmtAction()).saveCommentSuperPeer(commentMessage);
                    }
                    NetworkManager.writeButOne(commentMessage.getCommentIP(), commentMessage);
                }

                if (commentMessage.getIdUserPost().equals(useIDLogin)) {
                    AppGUI.updateNotification(1, "", commentMessage.getNameComment(), commentMessage.getPostContent());
                }
            }
        }
    }
}
