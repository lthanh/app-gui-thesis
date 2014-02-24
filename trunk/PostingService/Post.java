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
 * @author admin
 */
public class Post extends Packet {

    private IPAddress ip;
    private int index = HEADER_LENGTH;

    public Post(String userID, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        super(Packet.POST, (25 + (userNamePost.length() + post.length() + idGroupFriends.length() + idGroupSP.length() + cDate.length())));

        //  System.out.println("####### POST : " + getPayload());
        // convert userID to byte array
//        for (int i = 0; i < userID.length; i++) {
//            contents[index + i] = userID[i];
////            System.out.println("userID: [" + i + "]" + userID);
//        }


        // convert userID to byte 
        byte[] tempUserID = new byte[userID.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        tempUserID = userID.getBytes();
        int q;
        for (q = 0; q < userID.length(); q++) {
            contents[index + q] = tempUserID[q];
        }
//        contents[index + q] = 0;



        // convert length of cDateLength
        contents[index + userID.length() + 0] = (byte) cDateLength;
//        System.out.println("cDate length: " + contents[index + userID.length + 0]);

        // convert length of groupFriendIDLength 4 bytes
//        contents[index + userID.length + 4] = (byte) (groupdFriendIDLength >>> 24);
//        contents[index + userID.length + 3] = (byte) ((groupdFriendIDLength & 0xffffff) >>> 16);
//        contents[index + userID.length + 2] = (byte) ((groupdFriendIDLength & 0xffff) >>> 8);
//        contents[index + userID.length + 1] = (byte) (groupdFriendIDLength & 0xff);

        ByteBuffer bB = ByteBuffer.allocate(4);
        bB.putInt(groupdFriendIDLength);
        byte[] byteGroupdFriendIDLength = bB.array();
        contents[index + userID.length() + 1] = byteGroupdFriendIDLength[0];
        contents[index + userID.length() + 2] = byteGroupdFriendIDLength[1];
        contents[index + userID.length() + 3] = byteGroupdFriendIDLength[2];
        contents[index + userID.length() + 4] = byteGroupdFriendIDLength[3];





        // convert length of groupdSuperPeerIDLength 2 bytes
//        contents[index + userID.length + 5] = (byte) (groupdSuperPeerIDLength >>> 8);
//        contents[index + userID.length + 6] = (byte) (groupdSuperPeerIDLength & 0xff);
        ByteBuffer dbuf = ByteBuffer.allocate(2);
        dbuf.putShort((short) groupdSuperPeerIDLength);
        byte[] bytes = dbuf.array();
        contents[index + userID.length() + 5] = bytes[0];
        contents[index + userID.length() + 6] = bytes[1];


        // convert length of userNameLength 2 bytes
//        contents[index + userID.length + 7] = (byte) (useNameLength >>> 8);
//        contents[index + userID.length + 8] = (byte) (useNameLength & 0xff);
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
//            System.out.println("userName : [" + i + "]" + contents[(index + 26 + i)]);
        }

//        contents[(index + 26 + i)] = 0;

        // convert created date to byte  
        byte[] tempCDate = new byte[cDate.length()];
        tempCDate = cDate.getBytes();
        int e;
        for (e = 0; e < cDate.length(); e++) {
            contents[(e + index + 25 + userNamePost.length())] = tempCDate[e];
        }
//        contents[(e + index + 26 + userNamePost.length())] = 0;

        // convert groupFriendID to byte  
        byte[] gFriendIDtoByte = new byte[idGroupFriends.length()];
        gFriendIDtoByte = idGroupFriends.getBytes();
        int k;
        for (k = 0; k < idGroupFriends.length(); k++) {
            contents[(k + index + 25 + userNamePost.length() + cDate.length())] = gFriendIDtoByte[k];
        }
//        System.out.println("Friend ID : " + contents[(k + index + 26 + userNamePost.length() + cDate.length())]);

//        contents[(k + index + 26 + userNamePost.length() + cDate.length())] = 0;

        // convert groupSuperPeerID  to byte  
        byte[] tempGroupSuperPeerID = new byte[idGroupSP.length()];
        tempGroupSuperPeerID = idGroupSP.getBytes();
        int y;
        for (y = 0; y < idGroupSP.length(); y++) {
            contents[(y + index + 25 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = tempGroupSuperPeerID[y];
        }
//        contents[(y + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = 0;


        // convert postContentTOByte  to byte  
        System.out.println("post length origi: " + post.length());
        byte[] postContentTOByte = new byte[post.length()];
        postContentTOByte = post.getBytes();
        int u;
        for (u = 0; u < post.length(); u++) {
            contents[(u + index + 25 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = postContentTOByte[u];
        }
//        contents[(u + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = 0;

        String postT = "";
//        System.out.println("Content length: " + k);
        System.out.println("post length decode: " + contents.length);
        System.out.println("post userNamePost.length() decode: " + userNamePost.length());
        System.out.println("post cDate.length() decode: " + cDate.length());
        System.out.println("post idGroupFriends.length() decode: " + idGroupFriends.length());
        System.out.println("post idGroupSP.length() decode: " + idGroupSP.length());



        for (int m = (index + 25 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length()); m < (contents.length); m++) {
            postT = postT + (char) (contents[m]);
        }
        System.out.println("POST -post length get insert: " + postT.length());

//        AppGUI.numMessageSent++; // count number of message sent at one section to set message id
        // ip = null;  //initialize IPaddress to null.
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

    public int getCDateLength() {
//        System.out.println("POST -CDateLength: " + (contents[index + 16 + 0]));
        return (contents[index + 16 + 0]);
    }

    public int getGroupFriendIDLength() {
        //   int groupFriendIDLength = (((contents[index + 16 + 4] & 0xff) << 24) | ((contents[index + 16 + 3] & 0xff) << 16) | ((contents[index + 16 + 2] & 0xff) << 8) | (contents[index + 16 + 1] & 0xff));

        byte[] bytes = new byte[4];
        bytes[0] = contents[index + 16 + 1];
        bytes[1] = contents[index + 16 + 2];
        bytes[2] = contents[index + 16 + 3];
        bytes[3] = contents[index + 16 + 4];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getListFileIDStoreLength after: " + wrapped.getInt(0));
        int groupFriendIDLength = wrapped.getInt(0);

        return groupFriendIDLength;
    }

    public int getGroupSuPeerIDLength() {
//        System.out.println("POST -GroupSuPeerIDLength: " + (((contents[index + 16 + 5] & 0xff) << 8) | (contents[index + 16 + 6] & 0xff)));
//        return (((contents[index + 16 + 5] & 0xff) << 8) | (contents[index + 16 + 6] & 0xff));
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 5];
        bytes[1] = contents[index + 16 + 6];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public int getUserNameLength() {
//        System.out.println("POST -UserNameLength: " + (((contents[index + 16 + 7] & 0xff) << 8) | (contents[index + 16 + 8] & 0xff)));
//        return (((contents[index + 16 + 7] & 0xff) << 8) | (contents[index + 16 + 8] & 0xff));
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 16 + 7];
        bytes[1] = contents[index + 16 + 8];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public String getUserName() {
        String userName = "";
        for (int i = (index + 25); i < (index + 25 + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }

//        System.out.println("POST -userName: " + userName);
        return userName;
    }

    public String getCreatedDate() {
        String cdate = "";
        for (int i = (index + 25 + getUserNameLength()); i < (index + 25 + getUserNameLength() + getCDateLength()); i++) {
            cdate = cdate + (char) (contents[i]);
        }
//        System.out.println("POST -CreatedDate: " + cdate);
        return cdate;
    }

    public String getGroupFriendID() {
        String groupFriendID = "";
        for (int i = (index + 25 + getUserNameLength() + getCDateLength()); i < (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i++) {
            groupFriendID = groupFriendID + (char) (contents[i]);
        }
//        System.out.println("POST -groupFriendID: " + groupFriendID);
        return groupFriendID;
    }

    public String getGroupSuperPeerID() {
        String groupSPID = "";
        for (int i = (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i < (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i++) {
            groupSPID = groupSPID + (char) (contents[i]);
        }
//        System.out.println("POST -groupSPID: " + groupSPID);
        return groupSPID;
    }

    public String getPostStatusContent() {
        String post = "";
        int k = contents.length;
//        System.out.println("Content length: " + k);
        for (int i = (index + 25 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
        System.out.println("POST -post length receive: " + post.length());
        return post;
    }

    public String getPostTypeString(byte typeMessage) {
        return Utils.getTypeMessage(typeMessage);
    }
}
