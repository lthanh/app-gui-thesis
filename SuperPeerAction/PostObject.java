/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

/**
 *
 * @author admin
 */
public class PostObject {

    public PostObject() {
    }
    private long postID;
    private String namePost;
    private String contentPost;
    private String groupID;
    private String createdDate;
    private String userIDPost;

    public String getUserIDPost() {
        return userIDPost;
    }

    public void setUserIDPost(String userIDPost) {
        this.userIDPost = userIDPost;
    }
    public long getPostID() {
        return postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public String getContentPost() {
        return contentPost;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
