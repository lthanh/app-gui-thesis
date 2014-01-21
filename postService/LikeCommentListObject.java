/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import java.util.Vector;

/**
 *
 * @author admin
 */
public class LikeCommentListObject {

    public LikeCommentListObject() {
    }
    private boolean isLikeMessage = false;
    private boolean isCommentMessage = false;
    private int numLike;
    private int numComment;
    private Vector<String> userNameLike = new Vector<String>();
    private Vector<String> comment = new Vector<String>();

    public boolean isIsLikeMessage() {
        return isLikeMessage;
    }

    public void setIsLikeMessage(boolean isLikeMessage) {
        this.isLikeMessage = isLikeMessage;
    }

    public boolean isIsCommentMessage() {
        return isCommentMessage;
    }

    public void setIsCommentMessage(boolean isCommentMessage) {
        this.isCommentMessage = isCommentMessage;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public Vector<String> getUserNameLike() {
        return userNameLike;
    }

    public void setUserNameLike(Vector<String> userNameLike) {
        this.userNameLike = userNameLike;
    }

    public Vector<String> getComment() {
        return comment;
    }

    public void setComment(Vector<String> comment) {
        this.comment = comment;
    }
}
