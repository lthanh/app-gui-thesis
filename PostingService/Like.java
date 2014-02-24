/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

import Architecture_Posting.Packet;
import Architecture_Posting.IPAddress;
import Architecture_Posting.Utils;
import GUI.AppGUI;

/**
 *
 * @author admin
 */
public class Like extends Packet {

    private IPAddress ip;
    int index = Packet.HEADER_LENGTH;

    public Like(int idPostLength, int idUserPostLength, int idUserLikeLength, String idPost, String idUserPost, String idUserLike, String nameLike) {
        super(Packet.LIKE, (3 + idPost.length() + idUserPost.length() + idUserLike.length() + nameLike.length()));

        // convert idPostLength to byte
        contents[index + 0] = (byte) idPostLength;

        // convert idUserPostLength to byte
        contents[index + 1] = (byte) idUserPostLength;

        // convert idUserLikeLength to byte
        contents[index + 2] = (byte) idUserLikeLength;

        System.out.println("Decode idPost- " + idPost);
        // convert idPost to byte  
        byte[] tempidPost = new byte[idPost.length()];
        tempidPost = idPost.getBytes();
        int e;
        for (e = 0; e < idPost.length(); e++) {
            contents[(e + index + 3)] = tempidPost[e];
        }
//        contents[(e + index + 3)] = 0;

        System.out.println("Decode idUserPost- " + idUserPost);
        // convert idUserPost to byte  
        byte[] tempidUserPost = new byte[idUserPost.length()];
        tempidUserPost = idUserPost.getBytes();
        int p;
        for (p = 0; p < idUserPost.length(); p++) {
            contents[(p + index + 3 + idPost.length())] = tempidUserPost[p];
        }
//        contents[(p + index + 3 + idPost.length())] = 0;

        System.out.println("Decode idUserLike- " + idUserLike);

        // convert idUserLike to byte  
        byte[] tempidUserLike = new byte[idUserLike.length()];
        tempidUserLike = idUserLike.getBytes();
        int m;
        for (m = 0; m < idUserLike.length(); m++) {
            contents[(m + index + 3 + idPost.length() + idUserPost.length())] = tempidUserLike[m];
        }
//        contents[(m + index + 3 + idPost.length() + idUserPost.length())] = 0;

        System.out.println("Decode nameLike- " + nameLike);

        // convert nameLike to byte  
        byte[] tempName = new byte[nameLike.length()];
        tempName = nameLike.getBytes(); // [B@37113859
        int b;
        for (b = 0; b < nameLike.length(); b++) {
            contents[(b + index + 3 + idPost.length() + idUserPost.length() + idUserLike.length())] = tempName[b];
        }
//        contents[(b + index + 3 + idPost.length() + idUserPost.length() + idUserLike.length())] = 0;


        System.out.println("###################### DECODE ");

        String idPostD = "";
        for (int i = (index + 3); i < (index + 3 + getIDPostLength()); i++) {
            idPostD = idPostD + (char) (contents[i]);
        }

        System.out.println("Like -getIdPost: " + idPostD);


        String idUserPostD = "";
        for (int i = (index + 3 + getIDPostLength()); i < (index + 3 + getIDPostLength() + getIDUserPostLength()); i++) {
            idUserPostD = idUserPostD + (char) (contents[i]);
        }

        System.out.println("Like -getIdUserPost: " + idUserPostD);

        String idUserLikeD = "";
        for (int i = (index + 3 + getIDPostLength() + getIDUserPostLength()); i < (index + 3 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i++) {
            idUserLikeD = idUserLikeD + (char) (contents[i]);
        }

        System.out.println("Like -getIdUserLike: " + idUserLikeD);

        String nameLikeD = "";
        for (int i = (index + 3 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i < (contents.length - 3); i++) {
            nameLikeD = nameLikeD + (char) (contents[i]);
        }

        System.out.println("Like -getNameLike: " + nameLikeD);
        
//        AppGUI.numMessageSent++; // count number of message sent at one section to set message id
     //   ip = null;

    }

    public Like(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getLikeIP() {
        return (ip);
    }

    public void setLikeIP(IPAddress ip) {
        this.ip = ip;
    }

    public int getIDPostLength() {
        return contents[index + 0];
    }

    public int getIDUserPostLength() {
        return contents[index + 1];
    }

    public int getIDUserLikeLength() {
        return contents[index + 2];
    }

    public long getIdPost() {
        String idPost = "";
        for (int i = (index + 3); i < (index + 3 + getIDPostLength()); i++) {
            idPost = idPost + (char) (contents[i]);
        }

        System.out.println("Like -getIdPost: " + idPost);
        return Long.parseLong(idPost);
    }

    public String getIdUserPost() {
        String idUserPost = "";
        for (int i = (index + 3 + getIDPostLength()); i < (index + 3 + getIDPostLength() + getIDUserPostLength()); i++) {
            idUserPost = idUserPost + (char) (contents[i]);
        }

        System.out.println("Like -getIdUserPost: " + idUserPost);
        return idUserPost;
    }

    public String getIdUserLike() {
        String idUserLike = "";
        for (int i = (index + 3 + getIDPostLength() + getIDUserPostLength()); i < (index + 3 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i++) {
            idUserLike = idUserLike + (char) (contents[i]);
        }

        System.out.println("Like -getIdUserLike: " + idUserLike);
        return idUserLike;
    }

    public String getNameLike() {
        String nameLike = "";
        for (int i = (index + 3 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i < (contents.length); i++) {
            nameLike = nameLike + (char) (contents[i]);
        }

        System.out.println("Like -getNameLike: " + nameLike);
        return nameLike;
    }

    public String getLikeTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
