

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
//    public static byte[] idUser;
//    private byte publicPrivate; // 1 = Public 0 = Private
//    private String postContent;
//    private int counterLike;
//    private int counterComment;
//    private String groupFriendsID;
//    private String groupSuperPeerID;
//    private String createdDate;

    public Post(byte[] userID, byte prpl, int like, int comment, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        super(Packet.POST, (30 + (userNamePost.length() + post.length() + idGroupFriends.length() + idGroupSP.length() + cDate.length())));

        // convert userID to byte array
        for (int i = 0; i < userID.length; i++) {
            contents[index + i] = userID[i];
        }
        // convert public private to byte
        contents[index + userID.length + 0] = (byte) prpl;

        // convert like to two bytes
        contents[index + userID.length + 1] = (byte) (like >>> 8);
        contents[index + userID.length + 2] = (byte) (like & 0xff);

        // convert comment to two bytes
        contents[index + userID.length + 3] = (byte) (comment >>> 8);
        contents[index + userID.length + 4] = (byte) (comment & 0xff);

        // convert length of cDate
        contents[index + userID.length + 5] = (byte) cDateLength;

        // convert length of groupFriendIDLength
        contents[index + userID.length + 6] = (byte) groupdFriendIDLength;

        // convert length of cDateLength
        contents[index + userID.length + 7] = (byte) groupdSuperPeerIDLength;

        // convert length of userNameLength
        contents[index + userID.length + 8] = (byte) useNameLength;

        // convert userName to byte  
        byte[] tempUserName = new byte[userNamePost.length()];
        tempUserName = userNamePost.getBytes();
        int i;
        for (i = 0; i < userNamePost.length(); i++) {
            contents[(i + 48)] = tempUserName[i];
        }
        contents[(i + 48)] = 0;

        // convert created date to byte  
        byte[] tempCDate = new byte[cDate.length()];
        tempCDate = cDate.getBytes();
        int e;
        for (e = 0; e < cDate.length(); e++) {
            contents[(e + 48 + userNamePost.length())] = tempCDate[e];
        }
        contents[(e + 48 + userNamePost.length())] = 0;

        // convert groupFriendID to byte  
        byte[] gFriendIDtoByte = new byte[idGroupFriends.length()];
        gFriendIDtoByte = idGroupFriends.getBytes();
        int k;
        for (k = 0; k < idGroupFriends.length(); k++) {
            contents[(k + 48 + userNamePost.length() + cDate.length())] = gFriendIDtoByte[k];
        }
        contents[(k + 48 + userNamePost.length() + cDate.length())] = 0;

        // convert groupSuperPeerID  to byte  
        byte[] tempGroupSuperPeerID = new byte[idGroupSP.length()];
        tempGroupSuperPeerID = idGroupSP.getBytes();
        int y;
        for (y = 0; y < idGroupSP.length(); y++) {
            contents[(y + 48 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = tempGroupSuperPeerID[y];
        }
        contents[(y + 48 + userNamePost.length() + cDate.length() + idGroupFriends.length())] = 0;


        // convert group friends id  to byte  
        byte[] postContentTOByte = new byte[post.length()];
        postContentTOByte = post.getBytes();
        int u;
        for (u = 0; u < post.length(); u++) {
            contents[(u + 48 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = postContentTOByte[u];
        }
        contents[(u + 48 + userNamePost.length() + cDate.length() + idGroupFriends.length() + idGroupSP.length())] = 0;

        ip = null;  //initialize IPaddress to null.
    }

    public Post(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getIP() {
        return (ip);
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

        return userID;
    }

    public int getPublicPrivate() {
        return (contents[index + 16]);
    }

    public String getSearchString() {
        String answer = "";
        for (int i = 25; contents[i] != 0; i++) {
            answer = answer + (char) (contents[i]);
        }
        return (answer);
    }

    public int like() {
        int numLike = (((contents[index + 16 + 1] & 0xff) << 8) | (contents[index + 16 + 2] & 0xff));
        return numLike;
    }

    public int comment() {
        int numCom = (((contents[index + 16 + 3] & 0xff) << 8) | (contents[index + 16 + 4] & 0xff));
        return numCom;
    }

    public int getCDateLength() {
        return (contents[index + 16 + 5]);
    }

    public int getGroupFriendIDLength() {
        return (contents[index + 16 + 6]);
    }

    public int getGroupSuPeerIDLength() {
        return (contents[index + 16 + 7]);
    }

    public int getUserNameLength() {
        return (contents[index + 16 + 8]);
    }

    public String getUserName() {
        String userName = "";
        for (int i = 48; i < 48 + getUserNameLength(); i++) {
            userName = userName + (char) (contents[i]);
        }
        return userName;
    }

    public String createdDate() {
        String cdate = "";
        for (int i = 48 + getUserNameLength(); i < 48 + getUserNameLength() + getCDateLength(); i++) {
            cdate = cdate + (char) (contents[i]);
        }
        return cdate;
    }

    public String groupFriendID() {
        String groupFriendID = "";
        for (int i = (48 + getUserNameLength() + getCDateLength()); i < (48 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i++) {
            groupFriendID = groupFriendID + (char) (contents[i]);
        }
        return groupFriendID;
    }

    public String groupSuperPeerID() {
        String groupSPID = "";
        for (int i = (48 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength()); i < (48 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i++) {
            groupSPID = groupSPID + (char) (contents[i]);
        }
        return groupSPID;
    }

    public String getPostStatusContent() {
        String post = "";
        int k = contents.length;
        System.out.println("Content length: " + k);
        for (int i = (48 + getUserNameLength() + getCDateLength() + getGroupFriendIDLength() + getGroupSuPeerIDLength()); i < (contents.length); i++) {
            post = post + (char) (contents[i]);
        }
        return post;
    }

    public void setIP(IPAddress ip) {
        this.ip = ip;
    }
}
