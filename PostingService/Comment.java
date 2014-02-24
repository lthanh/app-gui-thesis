/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

import GUI.AppGUI;
import Architecture_Posting.IPAddress;
import Architecture_Posting.Packet;
import Architecture_Posting.Utils;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Comment extends Packet {

    private IPAddress ip;
    int index = Packet.HEADER_LENGTH;
// (int idPostLength, int idUserPostLength, int idUserLikeLength, String idPost, String idUserPost, String idUserLike, String nameLike)

    public Comment(int idPostLength, int idUserPostLength, int idUserCommentLength, int nameCommentLength, String idPost, String idUserPost, String idUserComment, String nameComment, String commentContent) {
        super(Packet.COMMENT, (4 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length() + commentContent.length()));

        // convert idPostLength to byte
        contents[index + 0] = (byte) idPostLength;

        // convert idUserPostLength to byte
        contents[index + 1] = (byte) idUserPostLength;

        // convert idUserLikeLength to byte
        contents[index + 2] = (byte) idUserCommentLength;

        // convert nameCommentLength to byte
        contents[index + 3] = (byte) nameCommentLength;


        // convert idPost to byte  
        byte[] tempidPost = new byte[idPost.length()];
        tempidPost = idPost.getBytes();
        int e;
        for (e = 0; e < idPost.length(); e++) {
            contents[(e + index + 4)] = tempidPost[e];
        }
//        contents[(e + index + 4)] = 0;

        // convert idUserPost to byte  
        byte[] tempidUserPost = new byte[idUserPost.length()];
        tempidUserPost = idUserPost.getBytes();
        int p;
        for (p = 0; p < idUserPost.length(); p++) {
            contents[(p + index + 4 + idPost.length())] = tempidUserPost[p];
        }
//        contents[(p + index + 4 + idPost.length())] = 0;

        // convert idUserComment to byte  
        byte[] tempIdUserComment = new byte[idUserComment.length()];
        tempIdUserComment = idUserComment.getBytes();
        int m;
        for (m = 0; m < idUserComment.length(); m++) {
            contents[(m + index + 4 + idPost.length() + idUserPost.length())] = tempIdUserComment[m];
        }
//        contents[(m + index + 4 + idPost.length() + idUserPost.length())] = 0;

        // convert nameComment to byte  
        byte[] tempName = new byte[nameComment.length()];
        tempName = nameComment.getBytes();
        int b;
        for (b = 0; b < nameComment.length(); b++) {
            contents[(b + index + 4 + idPost.length() + idUserPost.length() + idUserComment.length())] = tempName[b];
        }
//        contents[(b + index + 4 + idPost.length() + idUserPost.length() + idUserComment.length())] = 0;

// convert comment to byte  
        byte[] tempCommentContent = new byte[commentContent.length()];
        tempCommentContent = commentContent.getBytes();
        int g;
        for (g = 0; g < commentContent.length(); g++) {
            contents[(g + index + 4 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length())] = tempCommentContent[g];
        }
//        contents[(g + index + 4 + idPost.length() + idUserPost.length() + idUserComment.length() + nameComment.length())] = 0;

//        AppGUI.numMessageSent++; // count number of message sent at one section to set message id
      //  ip = null;
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
        return contents[index + 0];
    }

    public int getIDUserPostLength() {
        return contents[index + 1];
    }

    public int getIDUserCommentLength() {
        return contents[index + 2];
    }

    public int getNameCommentLength() {
        return contents[index + 3];
    }

    public long getIdPost() {
        String idPost = "";
        for (int i = (index + 4); i < (index + 4 + getIDPostLength()); i++) {
            idPost = idPost + (char) (contents[i]);
        }

        System.out.println("Comment -idPost: " + idPost);
        return Long.parseLong(idPost);
    }

    public String getIdUserPost() {
        String idUserPost = "";
        for (int i = (index + 4 + getIDPostLength()); i < (index + 4 + getIDPostLength() + getIDUserPostLength()); i++) {
            idUserPost = idUserPost + (char) (contents[i]);
        }

        System.out.println("Comment -idUserPost: " + idUserPost);
        return idUserPost;
    }

    public String getIdUserComment() {
        String idUserComment = "";
        for (int i = (index + 4 + getIDPostLength() + getIDUserPostLength()); i < (index + 4 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength()); i++) {
            idUserComment = idUserComment + (char) (contents[i]);
        }

        System.out.println("Comment -idUserComment: " + idUserComment);
        return idUserComment;
    }

    public String getNameComment() {
        String nameComment = "";
        for (int i = (index + 4 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength()); i < (index + 4 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength()); i++) {
            nameComment = nameComment + (char) (contents[i]);
        }

        System.out.println("Comment -nameComment: " + nameComment);
        return nameComment;
    }

    public String getComment() {
        String comment = "";
        for (int i = (index + 4 + getIDPostLength() + getIDUserPostLength() + getIDUserCommentLength() + getNameCommentLength()); i < (contents.length); i++) {
            comment = comment + (char) (contents[i]);
        }

        System.out.println("Comment -comment: " + comment);
        return comment;
    }

    public String getCommentTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
