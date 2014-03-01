/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

/**
 *
 * @author Thanh Le Quoc
 */
public class LikeCommentObject {

    public LikeCommentObject() {
    }
    private String userNameLiked;
    private String userNameComment;
    private String postContent;

    public String getUserNameLiked() {
        return userNameLiked;
    }

    public void setUserNameLiked(String userNameLiked) {
        this.userNameLiked = userNameLiked;
    }

    public String getUserNameComment() {
        return userNameComment;
    }

    public void setUserNameComment(String userNameComment) {
        this.userNameComment = userNameComment;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}
