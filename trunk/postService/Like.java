/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postService;

import architecture.*;

/**
 *
 * @author admin
 */
public class Like extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Like(byte[] idPost, byte[] idUserPost, byte[] idUserLike, String nameLike) {
        super(Packet.LIKE, (48 + nameLike.length()));

        // convert idPost to byte array
        for (int i = 0; i < idPost.length; i++) {
            contents[index + i] = idPost[i];
            System.out.println("idPost: [" + i + "]" + idPost);
        }

        // convert idUserPost to byte array
        for (int i = 0; i < idUserPost.length; i++) {
            contents[index + 16 + i] = idUserPost[i];
            System.out.println("idUserPost: [" + i + "]" + idUserPost);
        }

        // convert idUserLike to byte array
        for (int i = 0; i < idUserLike.length; i++) {
            contents[index + 16 + 16 + i] = idUserLike[i];
            System.out.println("idUserLike: [" + i + "]" + idUserLike);
        }

        // convert nameLike to byte  
        byte[] tempName = new byte[nameLike.length()];
        tempName = nameLike.getBytes();
        int e;
        for (e = 0; e < nameLike.length(); e++) {
            contents[(e + index + 48 + nameLike.length())] = tempName[e];
        }
        contents[(e + index + 48 + nameLike.length())] = 0;
    }

    public Like(byte[] rawdata) {
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

        System.out.println("LIKE -getIdPost: " + idPost);
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

        System.out.println("LIKE -getIdUserPost: " + userPost);
        return userPost;
    }

    public String getIdUserLike() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + 32 + i];
        }

        String idUserLike = new String(temp);

        System.out.println("LIKE -getIdUserLike: " + idUserLike);
        return idUserLike;
    }

    public String getNameLike() {
        String nameLike = "";
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < contents.length; i++) {
            nameLike = nameLike + (char) (contents[i]);
        }
        System.out.println("LIKE -getNameLike: " + nameLike);
        return nameLike;
    }

    public String getLikeTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
