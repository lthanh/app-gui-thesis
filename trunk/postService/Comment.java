/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import architecture.Packet;
import architecture.Utils;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Comment extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Comment(byte[] idPost, byte[] idUserPost, byte[] idUserComment, int nameCommentLength, String nameComment, String commentContent) {
        super(Packet.COMMENT, (51 + nameComment.length() + commentContent.length()));

        // convert idPost to byte array
        for (int i = 0; i < idPost.length; i++) {
            contents[index + i] = idPost[i];
            System.out.println("COMMENT -idPost: [" + i + "]" + idPost);
        }

        // convert idUserPost to byte array
        for (int i = 0; i < idUserPost.length; i++) {
            contents[index + 16 + i] = idUserPost[i];
            System.out.println("COMMENT -idUserPost: [" + i + "]" + idUserPost);
        }

        // convert idUserLike to byte array
        for (int i = 0; i < idUserComment.length; i++) {
            contents[index + 16 + 16 + i] = idUserComment[i];
            System.out.println("COMMENT -idUserLike: [" + i + "]" + idUserComment);
        }

        // convert nameCommentLength to byte array
        ByteBuffer bBPort = ByteBuffer.allocate(2);
        bBPort.putShort((short) nameCommentLength);
        byte[] byteNameCommentLength = bBPort.array();

        contents[index + 48] = byteNameCommentLength[0];
        contents[index + 49] = byteNameCommentLength[1];



        // convert nameComment to byte  
        byte[] tempName = new byte[nameComment.length()];
        tempName = nameComment.getBytes();
        int e;
        for (e = 0; e < nameComment.length(); e++) {
            contents[(e + index + 50 + nameComment.length())] = tempName[e];
        }
        contents[(e + index + 50 + nameComment.length())] = 0;

        // convert commentContent to byte  
        byte[] tempCommentContent = new byte[commentContent.length()];
        tempCommentContent = commentContent.getBytes();
        int p;
        for (p = 0; p < commentContent.length(); p++) {
            contents[(p + index + 51 + commentContent.length())] = tempCommentContent[p];
        }
        contents[(p + index + 51 + commentContent.length())] = 0;

    }

    public Comment(byte[] rawdata) {
        super(rawdata);
    }

    public String getIdPost() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + i];
        }

        String idPost = new String(temp);

        System.out.println("COMMENT -getIdPost: " + idPost);
        return idPost;
    }

    public String getIdUserPost() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + 16 + i];
        }

        String userPost = new String(temp);

        System.out.println("COMMENT -getIdUserPost: " + userPost);
        return userPost;
    }

    public String getIdUserComment() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + 32 + i];
        }

        String idUserLike = new String(temp);

        System.out.println("COMMENT -getIdUserComment: " + idUserLike);
        return idUserLike;
    }

    public int getNameCommentLength() {
//        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 48];
        bytes[1] = contents[index + 49];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public String getNameComment() {
        String nameComment = "";
        //StringBuilder userID = new StringBuilder();
        for (int i = 50; i < 50 + getNameCommentLength(); i++) {
            nameComment = nameComment + (char) (contents[i]);
        }
        System.out.println("COMMENT -getNameComment: " + nameComment);
        return nameComment;
    }

    public String getCommentContent() {
        String commentContent = "";
        //StringBuilder userID = new StringBuilder();
        for (int i = (49 + getNameCommentLength()); i < contents.length; i++) {
            commentContent = commentContent + (char) (contents[i]);
        }
        System.out.println("COMMENT -getNameComment: " + commentContent);
        return commentContent;
    }

    public String getCommentTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
