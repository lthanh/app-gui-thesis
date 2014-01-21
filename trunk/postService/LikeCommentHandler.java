/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import GUI.LoginForm;
import GUI.StatusForm;
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

    public LikeCommentHandler(Like likeMessage, Comment commentMessage) {
        this.likeMessage = likeMessage;
        this.commentMessage = commentMessage;
    }

    public void run() {

        if (likeMessage != null) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(likeMessage.getIdPost(), likeMessage.getIdUserPost(), likeMessage.getMessageID().toString());
            boolean isLike = StatusForm.checkNameLiked(userNameLogin, likeComment);

            if (isLike == true && likeComment.isIsLikeMessage() == false) {
                saveLikeSuperPeer(likeMessage);
            }

            NetworkManager.writeToAll(likeMessage);  // Query is forwarded to all connected nodes except one from which query came.
        }

        if (commentMessage != null) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(commentMessage.getIdPost(), commentMessage.getIdUserPost(), commentMessage.getMessageID().toString());

            if (likeComment.isIsCommentMessage() == false) {
                saveCommentSuperPeer(commentMessage);
            }
            NetworkManager.writeToAll(commentMessage);  // Query is forwarded to all connected nodes except one from which query came.

        }
    }

    public void saveLikeSuperPeer(Like like) {
        byte[] idPost = like.getIdPost().getBytes();
        String idUserPost = like.getIdUserPost();
        String idUserLike = like.getIdUserLike();
        String nameLike = like.getNameLike();

        if (SharedDirectory.listFileIDSaving.contains(idUserPost)) {
            Preferences.likeWriteToFileSuperPeer(like.getLikeTypeString(like.getPayload()), idPost, like.getMessageID(), idUserPost, idUserLike, nameLike);
        }
    }

    public void saveCommentSuperPeer(Comment comment) {
        byte[] idPost = comment.getIdPost().getBytes();
        String idUserPost = comment.getIdUserPost();
        String idUserComment = comment.getIdUserComment();
        String nameComment = comment.getNameComment();
        String commentContent = comment.getCommentContent();

        if (SharedDirectory.listFileIDSaving.contains(idUserPost)) {
            Preferences.commentWriteToFileSuperPeer(comment.getCommentTypeString(comment.getPayload()), idPost, commentMessage.getMessageID(), idUserPost, idUserComment, nameComment, commentContent);
        }
    }
}
