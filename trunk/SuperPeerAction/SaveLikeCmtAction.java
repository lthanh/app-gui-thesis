/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import Architecture_Posting.Preferences;
import Architecture_Posting.Utils;
import java.util.Vector;
import PostingService.Comment;
import PostingService.Like;
import PostingService.LikeCommentListObject;

/**
 *
 * @author admin
 */
public class SaveLikeCmtAction {

    private Like like;
    private Comment comment;
    Utils utils = new Utils();
    boolean isFileStoring;

    public SaveLikeCmtAction() {
//        this.like = like;
//        this.comment = comment;
    }

    public void saveLikeSuperPeer(Like like) {
        long idPost = like.getIdPost();
        String idUserPost = like.getIdUserPost();
        String idUserLike = like.getIdUserLike();
        String nameLike = like.getNameLike();
        isFileStoring = utils.checkFileSharing(idUserPost + ".txt");

        boolean isLikeOfUser = serverCheckLikeCommentForPeer(Preferences.peerManageList, like, null);
        if (isLikeOfUser) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(like.getIdPost(), like.getIdUserPost());
            System.out.println("ID:" + like.getIdUserLike());
            boolean isLike = checkNameLiked(like.getMessageID(), like.getIdUserLike(), likeComment);
            if ((likeComment.getIdUserLike().equals("") || isLike == true) && (isFileStoring)) {
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
        isFileStoring = utils.checkFileSharing(idUserPost + ".txt");

        if (isCommentOfUser) {
            LikeCommentListObject likeComment = new LikeCommentListObject();
            likeComment = Preferences.readUserFile(comment.getIdPost(), comment.getIdUserPost());
            boolean isComment = checkCommentID(comment.getMessageID(), likeComment);
            if (isComment && isFileStoring) {
                Preferences.commentWriteToFileSuperPeer(comment.getCommentTypeString(comment.getPayload()), idPost, comment.getMessageID(), idUserPost, idUserComment, nameComment, commentContent);

            }
        }
    }

    public boolean checkNameLiked(long likeID, String userIDLike, LikeCommentListObject likeComment) {
        System.out.println("likeID: " + String.valueOf(likeID));
        System.out.println("userIDLike: " + userIDLike);
        System.out.println("likeComment iduserLike :" + likeComment.getIdUserLike());
        System.out.println("LIKE ID: " + likeComment.getIdLike());
        if (likeComment.getIdLike().contains(String.valueOf(likeID))) {// || likeComment.getIdUserLike().contains(userIDLike)) {  // still like many times.
            return false;
        }
        return true;
    }

    public boolean checkCommentID(long commentID, LikeCommentListObject likeComment) {
        if (likeComment.getIdComment().contains(String.valueOf(String.valueOf(commentID)))) {
            return false;
        }
        return true;
    }

    public boolean serverCheckLikeCommentForPeer(Vector<String> listPeerOrFriend, Like like, Comment comment) {
        String userPostID = "";

        if (like != null) {
            userPostID = like.getIdUserPost();
        }
        if (comment != null) {
            userPostID = comment.getIdUserPost();
        }

        isFileStoring = utils.checkFileSharing(userPostID + ".txt");

        for (int i = 0; i < listPeerOrFriend.size(); i++) {
            if (isFileStoring || userPostID.equals(listPeerOrFriend.get(i))) {
                return true;
            }
        }

        return false;
    }
}
