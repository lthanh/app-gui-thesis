/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PostingService;

import Architecture_Posting.Packet;
import Architecture_Posting.IPAddress;
import Architecture_Posting.Utils;
import java.nio.ByteBuffer;

/**
 *
 * @author Thanh Le Quoc
 */
public class Like extends Packet {

    private IPAddress ip;
    int index = Packet.HEADER_LENGTH;

    public Like(int idPostLength, int idUserPostLength, int idUserLikeLength, int nameLikeLength, String idPost, String idUserPost, String idUserLike, String nameLike, String postContent) { // postContent including 34 characters including " ..."
        super(Packet.LIKE, (5 + idPost.length() + idUserPost.length() + idUserLike.length() + nameLike.length() + postContent.length()));

        // convert idPostLength to byte
        contents[index + 0] = (byte) idPostLength;

        // convert idUserPostLength to byte
        contents[index + 1] = (byte) idUserPostLength;

        // convert idUserLikeLength to byte
        contents[index + 2] = (byte) idUserLikeLength;

        // convert nameLikeLength to byte
        ByteBuffer bBPostContentLength = ByteBuffer.allocate(2);
        bBPostContentLength.putShort((short) nameLikeLength);
        byte[] bytePostContentLength = bBPostContentLength.array();
        contents[index + 3] = bytePostContentLength[0];
        contents[index + 4] = bytePostContentLength[1];

        // convert idPost to byte  
        byte[] tempidPost = new byte[idPost.length()];
        tempidPost = idPost.getBytes();
        int e;
        for (e = 0; e < idPost.length(); e++) {
            contents[(e + index + 5)] = tempidPost[e];
        }


        // convert idUserPost to byte  
        byte[] tempidUserPost = new byte[idUserPost.length()];
        tempidUserPost = idUserPost.getBytes();
        int p;
        for (p = 0; p < idUserPost.length(); p++) {
            contents[(p + index + 5 + idPost.length())] = tempidUserPost[p];
        }

        // convert idUserLike to byte  
        byte[] tempidUserLike = new byte[idUserLike.length()];
        tempidUserLike = idUserLike.getBytes();
        int m;
        for (m = 0; m < idUserLike.length(); m++) {
            contents[(m + index + 5 + idPost.length() + idUserPost.length())] = tempidUserLike[m];
        }

        // convert nameLike to byte  
        byte[] tempName = new byte[nameLike.length()];
        tempName = nameLike.getBytes(); // [B@37113859
        int b;
        for (b = 0; b < nameLike.length(); b++) {
            contents[(b + index + 5 + idPost.length() + idUserPost.length() + idUserLike.length())] = tempName[b];
        }


        // convert postContent to byte  
        byte[] tempPostContent = new byte[postContent.length()];
        tempPostContent = postContent.getBytes(); // [B@37113859
        int q;
        for (q = 0; q < postContent.length(); q++) {
            contents[(q + index + 5 + idPost.length() + idUserPost.length() + idUserLike.length() + nameLike.length())] = tempPostContent[q];
        }
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

    public int getNameLikeLength() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 3];
        bytes[1] = contents[index + 4];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public long getIdPost() {
        String idPost = "";
        for (int i = (index + 5); i < (index + 5 + getIDPostLength()); i++) {
            idPost = idPost + (char) (contents[i]);
        }
        return Long.parseLong(idPost);
    }

    public String getIdUserPost() {
        String idUserPost = "";
        for (int i = (index + 5 + getIDPostLength()); i < (index + 5 + getIDPostLength() + getIDUserPostLength()); i++) {
            idUserPost = idUserPost + (char) (contents[i]);
        }
        return idUserPost;
    }

    public String getIdUserLike() {
        String idUserLike = "";
        for (int i = (index + 5 + getIDPostLength() + getIDUserPostLength()); i < (index + 5 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i++) {
            idUserLike = idUserLike + (char) (contents[i]);
        }
        return idUserLike;
    }

    public String getNameLike() {
        String nameLike = "";
        for (int i = (index + 5 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength()); i < (index + 5 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength() + getNameLikeLength()); i++) {
            nameLike = nameLike + (char) (contents[i]);
        }
        return nameLike;
    }

    public String getPostContent() {
        String post = "";
        for (int i = (index + 5 + getIDPostLength() + getIDUserPostLength() + getIDUserLikeLength() + getNameLikeLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
        return post;
    }

    public String getLikeTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
