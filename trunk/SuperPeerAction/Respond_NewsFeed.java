/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import Architecture_Posting.Packet;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Respond_NewsFeed extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Respond_NewsFeed(int indexRequested, String userIDReq, String listPost, long requestID) {
        super(Packet.RES_NewsFeed, (16 + listPost.length()));

        // convert requestID to respondID
        ByteBuffer bBRequestIDReq = ByteBuffer.allocate(16); // Respond Like comment need to have the same message ID as the generating Request Like comment, so we need to pass it to the constructor.
        bBRequestIDReq.putLong(requestID);
        byte[] byteMessageReqid = bBRequestIDReq.array();
        for (int i = 0; i < 16; i++) {
            contents[i] = byteMessageReqid[i];
        }


        // convert userIDPost to byte  
        byte[] tempuserIDPost = new byte[16];
        tempuserIDPost = userIDReq.getBytes();
        int m;
        for (m = 0; m < userIDReq.length(); m++) {
            contents[(index + m)] = tempuserIDPost[m];
        }

        // convert listPost to byte  
        byte[] tempListPost = new byte[listPost.length()];
        tempListPost = listPost.getBytes();
        int i;
        // System.out.println("length: " + idUserIDReq.length());
        for (i = 0; i < listPost.length(); i++) {
            contents[(index + 16 + i)] = tempListPost[i];
//            System.out.println("userName : [" + i + "]" + contents[(index + 22 + i)]);
        }
    }

    public Respond_NewsFeed(byte[] rawdata) {
        super(rawdata);
    }

//    public int getIndexRequested() {
////        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
//        byte[] bytes = new byte[2];
//        bytes[0] = contents[index + 0];
//        bytes[1] = contents[index + 1];
//        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
////        System.out.println("getPort after: " + wrapped.getInt());
//        return (int) wrapped.getShort();
//    }
    public String getUserIDReq() {
        //  byte[] temp = new byte[16];
        String temp = "";
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp = temp + (char) (contents[index + i]);
        }
//        System.out.println("POST -userID: " + userID);
        return temp;
    }

    public String getListPost() {
        String listPost = "";
        for (int i = (index + 16); i < (contents.length); i++) {
            listPost = listPost + (char) (contents[i]);
        }

//        System.out.println("POST -userName: " + userName);
        return listPost;
    }
}
