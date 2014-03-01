package PostingService;

import Architecture_Posting.Packet;
import Architecture_Posting.IPAddress;
import Architecture_Posting.Utils;
import java.nio.ByteBuffer;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thanh Le Quoc
 */
public class Post extends Packet {

    private IPAddress ip;
    private int index = HEADER_LENGTH;

    public Post(String userID, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        super(Packet.POST, (25 + (userNamePost.length() + post.length() + idGroupFriends.length() + idGroupSP.length() + cDate.length())));
        
        // convert userID to byte 
        byte[] tempUserID = new byte[userID.length()];
        tempUserID = userID.getBytes();
        int q;
        for (q = 0; q < userID.length(); q++) {
            contents[index + q] = tempUserID[q];
        }

        // convert length of cDateLength
        contents[index + userID.length() + 0] = (byte) cDateLength;

        ByteBuffer bB = ByteBuffer.allocate(4);
        bB.putInt(groupdFriendIDLength);
        byte[] byteGroupdFriendIDLength = bB.array();
        contents[index + userID.length() + 1] = byteGroupdFriendIDLength[0];
        contents[index + userID.length() + 2] = byteGroupdFriendIDLength[1];
        contents[index + userID.length() + 3] = byteGroupdFriendIDLength[2];
        contents[index + userID.length() + 4] = byteGroupdFriendIDLength[3];

        // convert length of groupdSuperPeerIDLength 2 bytes
        ByteBuffer dbuf = ByteBuffer.allocate(2);
        dbuf.putShort((short) groupdSuperPeerIDLength);
        byte[] bytes = dbuf.array();
        contents[index + userID.length() + 5] = bytes[0];
        contents[index + userID.length() + 6] = bytes[1];

        // convert length of userNameLength 2 bytes
        ByteBuffer bbNameLength = ByteBuffer.allocate(2);
        bbNameLength.putShort((short) useNameLength);
        byte[] bytesUseNameLength = bbNameLength.array();
        contents[index + userID.length() + 7] = bytesUseNameLength[0];
        contents[index + userID.length() + 8] = bytesUseNameLength[1];

        // convert userName to byte  
        byte[] tempUserName = new byte[userNamePost.length()];
        tempUserName = userNamePost.getBytes();
        int i;
        for (i = 0; i < userNamePost.length(); i++) {
            contents[(index + i + 25)] = tempUserName[i];
        }

        // convert created date to byte  
        byte[] tempCDate = new byte[cDate.length()];
        tempCDate = cDate.getBytes();
        int e;
        for (e = 0; e < cDate.length(); e++) {
            contents[(e + index + 25 + userNamePost.length())] = tempCDate[e];
        }

        // convert groupFriendID to byte  
        byte[] gFriendIDtoByte = new byte[idGroupFriends.length()];
        gFriendIDtoByte = idGroupFriends.getBytes();
        int k;
        for (k = 0; k < idGroupFriends.length(); k++) {
            contents[(k + index + 25 + userNamePost.length() + cDate.length())] = gFriendIDtoByte[k];
        }

        // convert groupSuperPeerID  to byte  
        byte[] tempGroupSuperPeerID = new byte[idGroupSP.length()];
        tempGroupSuperPeerID = idGroupSP.getBytes();
        int y;
        for (y = 0; y < idGroupSP.length(); y++) {
            contents[(y + index + 25 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = tempGroupSuperPeerID[y];
        }

        // convert postContentTOByte  to byte  
        byte[] postContentTOByte = new byte[post.length()];
        postContentTOByte = post.getBytes();
        int u;
        for (u = 0; u < post.length(); u++) {
            contents[(u + index + 25 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = postContentTOByte[u];
        }
    }

    public Post(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getPostIP() {
        return (ip);
    }

    public void setPostIP(IPAddress ip) {
        this.ip = ip;
    }

    public String getUserID() {
        String temp = "";
        for (int i = 0; i < 16; i++) {
            temp = temp + (char) (contents[index + i]);
        }
        return temp;
    }

    public int getCDateLength() {
        return (contents[index + 16 + 0]);
    }

    public int getGroupFriendIDLength() {
        byte[] bytes = new byte[4];
        bytes[0] = contents[index + 16 + 1];
        bytes[1] = contents[index + 16 + 2];
        bytes[2] = contents[index + 16 + 3];
        bytes[3] = contents[index + 16 + 4];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        int groupFriendIDLength = wrapped.getInt(0);
        return groupFriendIDLength;
    }

    public int getGroupSuPeerIDLength() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 5];
        bytes[1] = contents[index + 16 + 6];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public int getUserNameLength() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 7];
        bytes[1] = contents[index + 16 + 8];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public String getUserName() {
        String userName = "";
        for (int i = (index + 25); i < (index + 25 + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }
        return userName;
    }

    public String getCreatedDate() {
        String cdate = "";
        for (int i = (index + 25 + getUserNameLength()); i < (index + 25 + getUserNameLength() + getCDateLength()); i++) {
            cdate = cdate + (char) (contents[i]);
        }
        return cdate;
    }

    public String getGroupFriendID() {
        String groupFriendID = "";
        for (int i = (index + 25 + getUserNameLength() + getCDateLength()); i < (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i++) {
            groupFriendID = groupFriendID + (char) (contents[i]);
        }
        return groupFriendID;
    }

    public String getGroupSuperPeerID() {
        String groupSPID = "";
        for (int i = (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i < (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i++) {
            groupSPID = groupSPID + (char) (contents[i]);
        }
        return groupSPID;
    }

    public String getPostStatusContent() {
        String post = "";
        for (int i = (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
        return post;
    }

    public String getPostTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
