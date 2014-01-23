/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.Packet;

/**
 *
 * @author admin
 */
public class Respond_LikeCmt extends Packet {

    public Respond_LikeCmt(int numLike, int numComment, int postIDReqLength, int userIDReqLength, int listUserNameLikeLength, String userIDReq, String postIDReq, String listUserNameLike, String listComment) {
        super(Packet.RES_LIKECOMMENT, (17 + userIDReq.length() + postIDReq.length() + listUserNameLike.length() + listComment.length()));
    }

    public Respond_LikeCmt(byte[] rawdata) {
        super(rawdata);
    }
}
