/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.IPAddress;
import architecture.Packet;

/**
 *
 * @author admin
 */
public class Request_LikeCmt extends Packet {

    public Request_LikeCmt(int port, IPAddress ip, int idUserIDReqLength, String idUserIDReq, String postIDReq ) {
        super(Packet.REQ_LIKECOMMENT, (8 + idUserIDReq.length() + postIDReq.length()));
    }

    public Request_LikeCmt(byte[] rawdata) {
        super(rawdata);
    }
}
