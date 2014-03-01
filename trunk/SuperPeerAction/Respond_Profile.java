/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import Architecture_Posting.*;
import java.nio.ByteBuffer;

/**
 *
 * @author Thanh Le Quoc
 */
public class Respond_Profile extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Respond_Profile(String userIDPost, String listPost, long requestProfileID) {
        super(Packet.RES_PROFILE, (16 + listPost.length()));

        // convert requestID to respondID
        ByteBuffer bBRequestIDReq = ByteBuffer.allocate(16); // Respond Like comment need to have the same message ID as the generating Request Like comment, so we need to pass it to the constructor.
        bBRequestIDReq.putLong(requestProfileID);
        byte[] byteMessageReqid = bBRequestIDReq.array();
        for (int i = 0; i < 16; i++) {
            contents[i] = byteMessageReqid[i];
        }

        // convert userIDPost to byte  
        byte[] tempuserIDPost = new byte[16];
        tempuserIDPost = userIDPost.getBytes();
        int m;
        for (m = 0; m < userIDPost.length(); m++) {
            contents[(index + m)] = tempuserIDPost[m];
        }

        // convert listPost to byte  
        byte[] tempListPost = new byte[listPost.length()];
        tempListPost = listPost.getBytes();
        int i;
        for (i = 0; i < listPost.length(); i++) {
            contents[(index + 16 + i)] = tempListPost[i];
        }
    }

    public Respond_Profile(byte[] rawdata) {
        super(rawdata);
    }

    public String getUserIDPost() {
        String temp = "";
        for (int i = 0; i < 16; i++) {
            temp = temp + (char) (contents[index + i]);
        }
        return temp;
    }

    public String getListPost() {
        String listPost = "";
        for (int i = (index + 16); i < (contents.length); i++) {
            listPost = listPost + (char) (contents[i]);
        }
        return listPost;
    }
}
