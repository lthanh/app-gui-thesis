/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.Packet;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Respond_LikeCmt extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Respond_LikeCmt(long postIDReq, int numLike, int numComment, int userIDReqLength, int listUserNameLikeLength, String userIDReq, String listUserNameLike, String listComment) {
        super(Packet.RES_LIKECOMMENT, (25 + userIDReq.length() + listUserNameLike.length() + listComment.length()));

        // convert postIDReq
        ByteBuffer bBPostIDReq = ByteBuffer.allocate(16);
        bBPostIDReq.putLong(postIDReq);
        byte[] byteMessageid = bBPostIDReq.array();
        for (int i = 0; i < 16; i++) {
            contents[index + i] = byteMessageid[i];
        }

        // convert numLike
        ByteBuffer bBPort = ByteBuffer.allocate(2);
        bBPort.putShort((short) numLike);
        byte[] bytePort = bBPort.array();
        contents[index + 16 + 0] = bytePort[0];
        contents[index + 16 + 1] = bytePort[1];

        // convert numComment
        ByteBuffer bBPortComment = ByteBuffer.allocate(2);
        bBPortComment.putShort((short) numComment);
        byte[] bytePortComment = bBPortComment.array();
        contents[index + 16 + 2] = bytePortComment[0];
        contents[index + 16 + 3] = bytePortComment[1];

        // convert userIDReqLength
        contents[index + 16 + 4] = (byte) userIDReqLength;

        // convert listUserNameLikeLength
        ByteBuffer bBlistUserNameLikeLength = ByteBuffer.allocate(2);
        bBlistUserNameLikeLength.putShort((short) listUserNameLikeLength);
        byte[] bytebBlistUserNameLikeLength = bBlistUserNameLikeLength.array();
        contents[index + 16 + 5] = bytebBlistUserNameLikeLength[0];
        contents[index + 16 + 6] = bytebBlistUserNameLikeLength[1];


        // convert userIDReq to byte 
        byte[] tempuserIDReq = new byte[userIDReq.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        tempuserIDReq = userIDReq.getBytes();
        int q;
        for (q = 0; q < userIDReq.length(); q++) {
            contents[index + 16 + 7 + q] = tempuserIDReq[q];
        }
        contents[index + 16 + 7 + q] = 0;

        // convert listUserNameLike to byte 
        byte[] templistUserNameLike = new byte[listUserNameLike.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        templistUserNameLike = listUserNameLike.getBytes();
        int t;
        for (t = 0; t < listUserNameLike.length(); t++) {
            contents[index + 16 + 7 + userIDReq.length() + t] = templistUserNameLike[t];
        }
        contents[index + 16 + 7 + userIDReq.length() + t] = 0;


        // convert listComment to byte 
        byte[] templistComment = new byte[listComment.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        templistComment = listComment.getBytes();
        int y;
        for (y = 0; y < listComment.length(); y++) {
            contents[index + 16 + 7 + userIDReq.length() + listUserNameLike.length() + y] = templistComment[y];
        }
        contents[index + 16 + 7 + userIDReq.length() + listUserNameLike.length() + y] = 0;

    }

    public Respond_LikeCmt(byte[] rawdata) {
        super(rawdata);
    }

    public long getPostID() {
        byte[] messageID = new byte[16];
        for (int i = 0; i < 16; i++) {
            messageID[i] = contents[index + i];
        }

        ByteBuffer wrapped = ByteBuffer.wrap(messageID);
        long idMessage = wrapped.getLong(0);
        System.out.println("Packet long : " + idMessage);

        return (idMessage);
    }

    public int getNumLike() {
//        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 0];
        bytes[1] = contents[index + 16 + 1];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public int getNumComment() {
//        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 2];
        bytes[1] = contents[index + 16 + 3];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public int getUserIDReqLength() {
        return contents[index + 16 + 4];
    }

    public int getListUserNameLikeLength() {
//        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 5];
        bytes[1] = contents[index + 16 + 6];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public String getUserIDReq() {
        //   byte[] temp = new byte[16];
        String temp = "";
        for (int i = 0; i < getUserIDReqLength(); i++) {
            temp = temp + (char) (contents[index + 16 + 7 + i]);
        }
        // String userID = new String(temp);
//        System.out.println("\nPONG: getUserIDOnline receive - " + userID);
        return temp;
    }

    public String getListUserNameLike() {
        //   byte[] temp = new byte[16];
        String temp = "";
        for (int i = 0; i < getListUserNameLikeLength(); i++) {
            temp = temp + (char) (contents[index + 16 + 7 + getUserIDReqLength() + i]);
        }
        // String userID = new String(temp);
//        System.out.println("\nPONG: getUserIDOnline receive - " + userID);
        return temp;
    }

    public String getListComment() {
        //   byte[] temp = new byte[16];
        String temp = "";
        for (int i = (index + 23 + getUserIDReqLength() + getListUserNameLikeLength()); i < contents.length; i++) {
            temp = temp + (char) (contents[i]);
        }
        // String userID = new String(temp);
//        System.out.println("\nPONG: getUserIDOnline receive - " + userID);
        return temp;
    }
}
