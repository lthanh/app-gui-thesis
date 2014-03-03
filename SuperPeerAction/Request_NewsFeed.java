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
public class Request_NewsFeed extends Packet {

    int index = Packet.HEADER_LENGTH;
    private IPAddress ip;

    public Request_NewsFeed(int indexPost, String idUserIDReq) {
        super(Packet.REQ_NewsFeed, (2 + idUserIDReq.length()));

        ByteBuffer bBfromPost = ByteBuffer.allocate(2);
        bBfromPost.putShort((short) indexPost);
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

    public Request_NewsFeed(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getNewsFeedIP() {
        return (ip);
    }

    public void setNewsFeedIP(IPAddress ip) {
        this.ip = ip;
    }

    public int getIndexPost() {
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
