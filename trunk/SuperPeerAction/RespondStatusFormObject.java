/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import java.util.Vector;

/**
 *
 * @author admin
 */
public class RespondStatusFormObject {

    private static long postMessageID;
    private static String userIDPost;
    private static int numLike;
    private static int numComment;
    private static Vector<String> comment;

    public  RespondStatusFormObject(){}
        

    public long getPostMessageID() {
        return postMessageID;
    }

    public void setPostMessageID(long postMessageID) {
        this.postMessageID = postMessageID;
    }

    public String getUserIDPost() {
        return userIDPost;
    }

    public void setUserIDPost(String userIDPost) {
        this.userIDPost = userIDPost;
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

    public Vector<String> getComment() {
        return comment;
    }

    public void setComment(Vector<String> comment) {
        this.comment = comment;
    }
}
