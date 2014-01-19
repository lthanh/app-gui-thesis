package postService;

import architecture.*;
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

    public Post(byte[] userID, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        super(Packet.POST, (30 + (userNamePost.length() + post.length() + idGroupFriends.length() + idGroupSP.length() + cDate.length())));

        // convert userID to byte array
        for (int i = 0; i < userID.length; i++) {
            contents[index + i] = userID[i];
            System.out.println("userID: [" + i + "]" + userID);
        }


        // convert length of cDateLength
        contents[index + userID.length + 0] = (byte) cDateLength;
        System.out.println("cDate length: " + contents[index + userID.length + 0]);

        // convert length of groupFriendIDLength 4 bytes
        contents[index + userID.length + 4] = (byte) (groupdFriendIDLength >>> 24);
        contents[index + userID.length + 3] = (byte) ((groupdFriendIDLength & 0xffffff) >>> 16);
        contents[index + userID.length + 2] = (byte) ((groupdFriendIDLength & 0xffff) >>> 8);
        contents[index + userID.length + 1] = (byte) (groupdFriendIDLength & 0xff);


        // convert length of groupdSuperPeerIDLength 2 bytes
        contents[index + userID.length + 5] = (byte) (groupdSuperPeerIDLength >>> 8);
        contents[index + userID.length + 6] = (byte) (groupdSuperPeerIDLength & 0xff);


        // convert length of userNameLength 2 bytes
        contents[index + userID.length + 7] = (byte) (useNameLength >>> 8);
        contents[index + userID.length + 8] = (byte) (useNameLength & 0xff);


        // convert userName to byte  
        byte[] tempUserName = new byte[userNamePost.length()];
        tempUserName = userNamePost.getBytes();
        int i;
        for (i = 0; i < userNamePost.length(); i++) {
            contents[(index + i + 26)] = tempUserName[i];
            System.out.println("userName : [" + i + "]" + contents[(index + 26 + i)]);
        }
        
        contents[(index + 26 + i)] = 0;

        // convert created date to byte  
        byte[] tempCDate = new byte[cDate.length()];
        tempCDate = cDate.getBytes();
        int e;
        for (e = 0; e < cDate.length(); e++) {
            contents[(e + index + 26 + userNamePost.length())] = tempCDate[e];
        }
        contents[(e + index + 26 + userNamePost.length())] = 0;

        // convert groupFriendID to byte  
        byte[] gFriendIDtoByte = new byte[idGroupFriends.length()];
        gFriendIDtoByte = idGroupFriends.getBytes();
        int k;
        for (k = 0; k < idGroupFriends.length(); k++) {
            contents[(k + index + 26 + userNamePost.length() + cDate.length())] = gFriendIDtoByte[k];
        }
        System.out.println("Friend ID : " + contents[(k + index + 26 + userNamePost.length() + cDate.length())]);

        contents[(k + index + 26 + userNamePost.length() + cDate.length())] = 0;

        // convert groupSuperPeerID  to byte  
        byte[] tempGroupSuperPeerID = new byte[idGroupSP.length()];
        tempGroupSuperPeerID = idGroupSP.getBytes();
        int y;
        for (y = 0; y < idGroupSP.length(); y++) {
            contents[(y + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = tempGroupSuperPeerID[y];
        }
        contents[(y + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = 0;


        // convert group friends id  to byte  
        byte[] postContentTOByte = new byte[post.length()];
        postContentTOByte = post.getBytes();
        int u;
        for (u = 0; u < post.length(); u++) {
            contents[(u + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = postContentTOByte[u];
        }
        contents[(u + index + 26 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = 0;

        ip = null;  //initialize IPaddress to null.
    }

    public Post(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getIP() {
        return (ip);
    }

    public void setIP(IPAddress ip) {
        this.ip = ip;
    }
//    public byte[] getUserID() {
//        byte[] userID = new byte[16];
//        for (int i = 0; i < 16; i++) {
//            userID[i] = contents[index + i];
//        }
//        return (userID);
//    }

    public String getUserID() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + i];
        }

        String userID = new String(temp);

        System.out.println("POST -userID: " + userID);
        return userID;
    }

    public int getCDateLength() {
           System.out.println("POST -CDateLength: " + (contents[index + 16 + 0]));
        return (contents[index + 16 + 0]);
    }

    public int getGroupFriendIDLength() {
        int groupFriendIDLength = (((contents[index + 16 + 4] & 0xff) << 24) | ((contents[index + 16 + 3] & 0xff) << 16) | ((contents[index + 16 + 2] & 0xff) << 8) | (contents[index + 16 + 1] & 0xff));
         System.out.println("POST -groupFriendIDLength: " + groupFriendIDLength);
        return groupFriendIDLength;
    }

    public int getGroupSuPeerIDLength() {
          System.out.println("POST -GroupSuPeerIDLength: " + (((contents[index + 16 + 5] & 0xff) << 8) | (contents[index + 16 + 6] & 0xff)));
        return (((contents[index + 16 + 5] & 0xff) << 8) | (contents[index + 16 + 6] & 0xff));
    }

    public int getUserNameLength() {
         System.out.println("POST -UserNameLength: " + (((contents[index + 16 + 7] & 0xff) << 8) | (contents[index + 16 + 8] & 0xff)));
        return (((contents[index + 16 + 7] & 0xff) << 8) | (contents[index + 16 + 8] & 0xff));
    }

    public String getUserName() {
        String userName = "";
        for (int i = (index + 26); i < (index + 26 + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }

         System.out.println("POST -userName: " + userName);
        return userName;
    }

    public String getCreatedDate() {
        String cdate = "";
        for (int i = (index + 26 + getUserNameLength()); i < (index + 26 + getUserNameLength() + getCDateLength()); i++) {
            cdate = cdate + (char) (contents[i]);
        }
         System.out.println("POST -CreatedDate: " + cdate);
        return cdate;
    }

    public String getGroupFriendID() {
        String groupFriendID = "";
        for (int i = (index + 26 + getUserNameLength() + getCDateLength()); i < (index + 26 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i++) {
            groupFriendID = groupFriendID + (char) (contents[i]);
        }
         System.out.println("POST -groupFriendID: " + groupFriendID);
        return groupFriendID;
    }

    public String getGroupSuperPeerID() {
        String groupSPID = "";
        for (int i = (index + 26 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i < (index + 26 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i++) {
            groupSPID = groupSPID + (char) (contents[i]);
        }
         System.out.println("POST -groupSPID: " + groupSPID);
        return groupSPID;
    }

    public String getPostStatusContent() {
        String post = "";
        int k = contents.length;
          System.out.println("Content length: " + k);
        for (int i = (index + 26 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
          System.out.println("POST -post: " + post);
        return post;
    }

    public String getPostTypeString() {
        byte payLoad = getPayload();
        String typeMessage = "";
        switch (payLoad) {
            case Packet.POST:
                typeMessage = "POST";
                break;
            case Packet.LIKE:
                typeMessage = "LIKE";
                break;
            case Packet.COMMENT:
                typeMessage = "COMMENT";
                break;
        }
        return typeMessage;
    }
}
