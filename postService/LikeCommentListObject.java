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
    private int numLike;
    private int numComment;
    private String userNameLike;
    private String comment;
    private String idUserLike;

    public String getIdUserLike() {
        return idUserLike;
    }

    public void setIdUserLike(String idUserLike) {
        this.idUserLike = idUserLike;
    }

    public String getIdUserComment() {
        return idUserComment;
    }

    public void setIdUserComment(String idUserComment) {
        this.idUserComment = idUserComment;
    }

    public String getIdLike() {
        return idLike;
    }

    public void setIdLike(String idLike) {
        this.idLike = idLike;
    }

    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }
    private String idUserComment;
    private String idLike;
    private String idComment;

    public String getUserNameLike() {
        return userNameLike;
    }

    public void setUserNameLike(String userNameLike) {
        this.userNameLike = userNameLike;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
}
