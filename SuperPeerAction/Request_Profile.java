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
public class Request_Profile extends Packet {

    IPAddress ip;
    int index = Packet.HEADER_LENGTH;

    public Request_Profile(int indexProfile, String idUserIDReq) {
        super(Packet.REQ_PROFILE, (2 + idUserIDReq.length()));

        ByteBuffer bBfromPost = ByteBuffer.allocate(2);
        bBfromPost.putShort((short) indexProfile);
        byte[] bytefromPost = bBfromPost.array();
        contents[index + 0] = bytefromPost[0];
        contents[index + 1] = bytefromPost[1];

        // convert idUserIDReq to byte  
        byte[] tempIdUserIDReq = new byte[idUserIDReq.length()];
        tempIdUserIDReq = idUserIDReq.getBytes();
        int i;
        for (i = 0; i < idUserIDReq.length(); i++) {
            contents[(index + 2 + i)] = tempIdUserIDReq[i];
        }
    }

    public Request_Profile(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getProfileIP() {
        return (ip);
    }

    public void setProfileIP(IPAddress ip) {
        this.ip = ip;
    }

    public int getIndexProfile() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 0];
        bytes[1] = contents[index + 1];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public String getIdUserIDReq() {
        String idUserIDReq = "";
        for (int i = (index + 2); i < (contents.length); i++) {
            idUserIDReq = idUserIDReq + (char) (contents[i]);
        }
        return idUserIDReq;
    }
}
