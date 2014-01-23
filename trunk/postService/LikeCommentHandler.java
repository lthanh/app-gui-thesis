/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import GUI.AppGUI;
import GUI.LoginForm;
import GUI.StatusForm;
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
            boolean isLikeOfUser = serverCheckLikeCommentForPeer(Preferences.peerManageList, likeMessage, null);
            if (isLikeOfUser) {
                LikeCommentListObject likeComment = new LikeCommentListObject();
                likeComment = Preferences.readUserFile(likeMessage.getIdPost(), likeMessage.getIdUserPost());
                boolean isLike = checkNameLiked(likeMessage.getMessageID(), likeMessage.getIdUserLike(), likeComment);
                if (likeComment.getIdUserLike().equals("") || isLike == true) {
                    saveLikeSuperPeer(likeMessage);
                }
            }

            NetworkManager.writeButOne(likeMessage.getIP(), likeMessage); // Query

        }

        if (commentMessage != null) {
            boolean isCommentOfUser = serverCheckLikeCommentForPeer(Preferences.peerManageList, null, commentMessage);
            if (isCommentOfUser) {
                LikeCommentListObject likeComment = new LikeCommentListObject();
                likeComment = Preferences.readUserFile(commentMessage.getIdPost(), commentMessage.getIdUserPost());
                boolean isComment = checkComment(commentMessage.getMessageID(), likeComment);
                if (isComment) {
                    saveCommentSuperPeer(commentMessage);
                }
            }
            NetworkManager.writeButOne(commentMessage.getIP(), commentMessage); // Query
        }
    }

    public void saveLikeSuperPeer(Like like) {
        long idPost = like.getIdPost();
        String idUserPost = like.getIdUserPost();
        String idUserLike = like.getIdUserLike();
        String nameLike = like.getNameLike();

        if (SharedDirectory.listFileIDSaving.contains(idUserPost)) {
            Preferences.likeWriteToFileSuperPeer(like.getLikeTypeString(like.getPayload()), idPost, like.getMessageID(), idUserPost, idUserLike, nameLike);
        }
    }

    public void saveCommentSuperPeer(Comment comment) {
        long idPost = comment.getIdPost();
        String idUserPost = comment.getIdUserPost();
        String idUserComment = comment.getIdUserComment();
        String nameComment = comment.getNameComment();
        String commentContent = comment.getComment();

        if (SharedDirectory.listFileIDSaving.contains(idUserPost)) {
            Preferences.commentWriteToFileSuperPeer(comment.getCommentTypeString(comment.getPayload()), idPost, commentMessage.getMessageID(), idUserPost, idUserComment, nameComment, commentContent);
        }
    }

    public static boolean checkNameLiked(long likeID, String userIDLike, LikeCommentListObject likeComment) {
        System.out.println("likeID: " + String.valueOf(likeID));
        System.out.println("userIDLike: " + userIDLike);
        System.out.println("likeComment iduserLike : " + likeComment.getIdUserLike());
        if (likeComment.getIdLike().contains(String.valueOf(likeID)) || likeComment.getIdUserLike().contains(userIDLike)) {
            return false;
        }
        return true;
    }

    public static boolean checkComment(long commentID, LikeCommentListObject likeComment) {
        if (likeComment.getIdComment().contains(String.valueOf(commentID))) {
            return false;
        }
        return true;
    }

    public static boolean serverCheckLikeCommentForPeer(Vector<String> listPeerOrFriend, Like like, Comment comment) {
        String userPostID = "";
        if (like != null) {
            userPostID = like.getIdUserPost();
        }

        if (comment != null) {
            userPostID = comment.getIdUserPost();

        }
        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (SharedDirectory.listFileIDSaving.contains(userPostID) || userPostID.equals(listPeerOrFriend.get(i))) {
                return true;
            }
        }

        return false;
    }
}
