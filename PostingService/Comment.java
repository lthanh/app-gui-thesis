/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

import Architecture_Posting.IPAddress;
import Architecture_Posting.Packet;
import Architecture_Posting.Utils;
import java.nio.ByteBuffer;

/**
 *
 * @author Thanh Le Quoc
 */
public class Comment extends Packet {

    private IPAddress ip;
    int index = Packet.HEADER_LENGTH;

    public Comment(int idPostLength, int idUserPostLength, int idUserCommentLength, int nameCommentLength, int commentContentLength, String idPost, String idUserPost, String idUserComment, String nameComment, String commentContent, String postContent) {
        super(Packet.COMMENT, (8 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length() + commentContent.length() + postContent.length()));

        // convert idPostLength to byte
        contents[index + 0] = (byte) idPostLength;

        // convert idUserPostLength to byte
        contents[index + 1] = (byte) idUserPostLength;

        // convert idUserLikeLength to byte
        contents[index + 2] = (byte) idUserCommentLength;

        // convert nameCommentLength to byte
        contents[index + 3] = (byte) nameCommentLength;

        // convert nameLikeLength to byte
        ByteBuffer bBCommentContentLength = ByteBuffer.allocate(4);
        bBCommentContentLength.putInt(commentContentLength);
        byte[] byteCommentContentLength = bBCommentContentLength.array();
        contents[index + 4] = byteCommentContentLength[0];
        contents[index + 5] = byteCommentContentLength[1];
        contents[index + 6] = byteCommentContentLength[2];
        contents[index + 7] = byteCommentContentLength[3];


        // convert idPost to byte  
        byte[] tempidPost = new byte[idPost.length()];
        tempidPost = idPost.getBytes();
        int e;
        for (e = 0; e < idPost.length(); e++) {
            contents[(e + index + 8)] = tempidPost[e];
        }

        // convert idUserPost to byte  
        byte[] tempidUserPost = new byte[idUserPost.length()];
        tempidUserPost = idUserPost.getBytes();
        int p;
        for (p = 0; p < idUserPost.length(); p++) {
            contents[(p + index + 8 + idPost.length())] = tempidUserPost[p];
        }

        // convert idUserComment to byte  
        byte[] tempIdUserComment = new byte[idUserComment.length()];
        tempIdUserComment = idUserComment.getBytes();
        int m;
        for (m = 0; m < idUserComment.length(); m++) {
            contents[(m + index + 8 + idPost.length() + idUserPost.length())] = tempIdUserComment[m];
        }

        // convert nameComment to byte  
        byte[] tempName = new byte[nameComment.length()];
        tempName = nameComment.getBytes();
        int b;
        for (b = 0; b < nameComment.length(); b++) {
            contents[(b + index + 8 + idPost.length() + idUserPost.length() + idUserComment.length())] = tempName[b];
        }

        // convert comment to byte  
        byte[] tempCommentContent = new byte[commentContent.length()];
        tempCommentContent = commentContent.getBytes();
        int g;
        for (g = 0; g < commentContent.length(); g++) {
            contents[(g + index + 8 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length())] = tempCommentContent[g];
        }

        // convert postContent to byte  
        byte[] tempPostContent = new byte[postContent.length()];
        tempPostContent = postContent.getBytes();
        int t;
        for (t = 0; t < postContent.length(); t++) {
            contents[(t + index + 8 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length() + commentContent.length())] = tempPostContent[t];
        }
    }

    public Comment(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getCommentIP() {
        return (ip);
    }

    public void setCommentIP(IPAddress ip) {
        this.ip = ip;
    }

    public int getIDPostLength() {
        System.out.println("" + contents[index + 0]);
        return contents[index + 0];
    }

    public int getIDUserPostLength() {
        System.out.println("" + contents[index + 1]);
        return contents[index + 1];
    }

    public int getIDUserCommentLength() {
        System.out.println("" + contents[index + 2]);
        return contents[index + 2];
    }

    public int getNameCommentLength() {
        System.out.println("" + contents[index + 3]);
        return contents[index + 3];
    }

    public int getCommentContentLength() {
        byte[] bytes = new byte[4];
        bytes[0] = contents[index + 4];
        bytes[1] = contents[index + 5];
        bytes[2] = contents[index + 6];
        bytes[3] = contents[index + 7];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        int commentContent = wrapped.getInt(0);
        System.out.println("" + commentContent);
        return commentContent;
    }

    public long getIdPost() {
        String idPost = "";
        for (int i = (index + 8); i < (index + 8 + getIDPostLength()); i++) {
            idPost = idPost + (char) (contents[i]);
        }
        return Long.parseLong(idPost);
    }

    public String getIdUserPost() {
        String idUserPost = "";
        for (int i = (index + 8 + getIDPostLength()); i < (index + 8 + getIDPostLength() + getIDUserPostLength()); i++) {
            idUserPost = idUserPost + (char) (contents[i]);
        }
        return idUserPost;
    }

    public String getIdUserComment() {
        String idUserComment = "";
        for (int i = (index + 8 + getIDPostLength() + getIDUserPostLength()); i < (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength()); i++) {
            idUserComment = idUserComment + (char) (contents[i]);
        }
        return idUserComment;
    }

    public String getNameComment() {
        String nameComment = "";
        for (int i = (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength()); i < (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength()); i++) {
            nameComment = nameComment + (char) (contents[i]);
        }
        return nameComment;
    }

    public String getComment() {
        String comment = "";
        for (int i = (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength()); i < (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength() + getCommentContentLength()); i++) {
            comment = comment + (char) (contents[i]);
        }
        return comment;
    }

    public String getPostContent() {
        String post = "";
        for (int i = (index + 8 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength() + getCommentContentLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
        return post;
    }

    public String getCommentTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
