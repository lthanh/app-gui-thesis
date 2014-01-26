/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import GUI.AppGUI;
import GUI.LoginForm;
import GUI.StatusForm;
import SuperPeerAction.SaveLikeCmtAction;
import architecture.IPAddress;
import architecture.NetworkManager;
import architecture.Preferences;
import architecture.SharedDirectory;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class LikeCommentHandler extends Thread {

    Like likeMessage;
    Comment commentMessage;
    String useIDLogin = LoginForm.currentUser.getIdUserLogin();
    String userNameLogin = LoginForm.currentUser.getUserName();
    public static Vector<String> listPeerManage = Preferences.peerManageList;

    public LikeCommentHandler(IPAddress ip, Like likeMessage, Comment commentMessage) {
        if (likeMessage != null) {
            this.likeMessage = likeMessage;
            this.likeMessage.setIP(ip);

        }
        if (commentMessage != null) {
            this.commentMessage = commentMessage;
            this.commentMessage.setIP(ip);
        }


    }

    public void run() {
        if (likeMessage != null) {
            if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                (new SaveLikeCmtAction()).saveLikeSuperPeer(likeMessage);
                AppGUI.updateNotification(1, likeMessage.getNameLike(), "");
            }
            NetworkManager.writeButOne(likeMessage.getIP(), likeMessage); // Query
        }

        if (commentMessage != null) {
            if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                (new SaveLikeCmtAction()).saveCommentSuperPeer(commentMessage);
                AppGUI.updateNotification(1, "", commentMessage.getNameComment());
            }
            NetworkManager.writeButOne(commentMessage.getIP(), commentMessage); // Query
        }
    }
}
