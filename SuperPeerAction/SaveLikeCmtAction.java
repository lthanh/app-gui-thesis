/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.Preferences;
import architecture.SharedDirectory;
import java.util.Vector;
import postService.Comment;
import postService.Like;
import postService.LikeCommentListObject;

/**
 *
 * @author admin
 */
public class SaveLikeCmtAction {

    private Like like;
    private Comment comment;

    public SaveLikeCmtAction() {
//        this.like = like;
//        this.comment = comment;
    }

    public void saveLikeSuperPeer(Like like) {
        long idPost = like.getIdPost();
        String idUserPost = like.getIdUserPost();
        String idUserLike = like.getIdUserLike();
        String nameLike = like.getNameLike();

        boolean isLikeOfUser = serverCheckLikeCommentForPeer(Preferences.peerManageList, like, null);
        if (isLikeOfUser) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(like.getIdPost(), like.getIdUserPost());
            System.out.println("ID:" + like.getIdUserLike());
            boolean isLike = checkNameLiked(like.getMessageID(), like.getIdUserLike(), likeComment);
            if ((likeComment.getIdUserLike().equals("") || isLike == true) && (SharedDirectory.listFileIDSaving.contains(idUserPost))) {
                //saveLikeSuperPeer(likeMessage);
                Preferences.likeWriteToFileSuperPeer(like.getLikeTypeString(like.getPayload()), idPost, like.getMessageID(), idUserPost, idUserLike, nameLike);
            }
        }
    }

    public void saveCommentSuperPeer(Comment comment) {
        long idPost = comment.getIdPost();
        String idUserPost = comment.getIdUserPost();
        String idUserComment = comment.getIdUserComment();
        String nameComment = comment.getNameComment();
        String commentContent = comment.getComment();

        boolean isCommentOfUser = serverCheckLikeCommentForPeer(Preferences.peerManageList, null, comment);
        if (isCommentOfUser) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(comment.getIdPost(), comment.getIdUserPost());
            boolean isComment = checkCommentID(comment.getMessageID(), likeComment);
            if (isComment && (SharedDirectory.listFileIDSaving.contains(idUserPost))) {
//                saveCommentSuperPeer(commentMessage);
                Preferences.commentWriteToFileSuperPeer(comment.getCommentTypeString(comment.getPayload()), idPost, comment.getMessageID(), idUserPost, idUserComment, nameComment, commentContent);

            }
        }



//        if (SharedDirectory.listFileIDSaving.contains(idUserPost)) {
//            Preferences.commentWriteToFileSuperPeer(comment.getCommentTypeString(comment.getPayload()), idPost, commentMessage.getMessageID(), idUserPost, idUserComment, nameComment, commentContent);
//        }
    }

    public static boolean checkNameLiked(long likeID, String userIDLike, LikeCommentListObject likeComment) {
        System.out.println("likeID: " + String.valueOf(likeID));
        System.out.println("userIDLike: " + userIDLike);
        System.out.println("likeComment iduserLike :" + likeComment.getIdUserLike());
        System.out.println("LIKE ID: "+ likeComment.getIdLike());
        if (likeComment.getIdLike().contains(String.valueOf(likeID))){// || likeComment.getIdUserLike().contains(userIDLike)) {  // still like many times.
            return false;
        }
        return true;
    }

    public static boolean checkCommentID(long commentID, LikeCommentListObject likeComment) {
        if (likeComment.getIdComment().contains(String.valueOf(String.valueOf(commentID)))) {
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
