package Architecture_Posting;

import java.io.*;
import java.util.*;
import PostingService.ReqRes_LikeCommentListObject;

public class Preferences {

    public static String FILE_NAME = Login.SHAREPATH + "preferences.txt"; // NHi - C:\Users\phatn_000\Desktop\src\  Trang - F:\Users\dangphat50\Desktop\src\  C:\Users\admin\Desktop\src\
    public static int MAX_LIVE = 5;
    public static int MAX_CACHE = 100;
    public static boolean AUTO_CONNECT = true;
    public static int PINGER_TIME = 5000;
    public static int CONNECTOR_TIME = 10000;
    public static String ONLINE = " - Online    (^^)";
    public static String OFFLINE = " - Offline";
    public static String CHECKED = "Checked";
    public static String UNCHECKED = "UnChecked";
    public static int COUNTER_OFFLINE = 3;
    public static int COUNTER_ONLINE = 0;
    public static String NEWSFEED = "NewsFeed";
    public static String PROFILE = "Profile";
    public static Vector<String> ipSuperPeer = new Vector<String>();
    public static Vector<String> peerManageList = new Vector<String>();
    public static Vector<Friends> friendList = new Vector<Friends>();
    // public static Vector<String> idFriendsListString = new Vector<String>();

    public static void readFromFile() {
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = fileIn.readLine()) != null) {
                System.out.println("Preferences line: " + line);
                if (line.startsWith("Host: ")) {
                    String address = line.substring(6);
                    ipSuperPeer.add(0, address); // list server in preference file

                    StringTokenizer t = new StringTokenizer(address, ":");
                    Host h = new Host(t.nextToken(), Integer.parseInt(t.nextToken()));
                    HostCache.addHost(h);
                    continue;
                } else if (line.startsWith("Max-Live: ")) {
                    MAX_LIVE = Integer.parseInt(line.substring(10));
                    continue;
                } else if (line.startsWith("Max-Cache: ")) {
                    MAX_CACHE = Integer.parseInt(line.substring(11));
                    continue;
                } else if (line.startsWith("Auto-Connect: ")) {
                    AUTO_CONNECT = ((Boolean.valueOf(line.substring(14))).booleanValue());
                    continue;
                } else if (line.startsWith("Pinger-Time: ")) {
                    PINGER_TIME = Integer.parseInt(line.substring(13));
                    continue;
                } else if (line.startsWith("Connector-Time: ")) {
                    CONNECTOR_TIME = Integer.parseInt(line.substring(16));
                    continue;
                } else if (line.startsWith("Shared-Directory: ")) {
                    Login.SHAREPATH = line.substring(18);
                    System.out.println("Shared-Directory is " + Login.SHAREPATH);
                    continue;
                }
            }
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
    }

    public static void writeToFriendsPeer(Vector<Friends> listFriends) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + "listFriendPeer.txt");
            BufferedWriter writeStatus = new BufferedWriter(fw);
            for (int i = 0; i < listFriends.size(); i++) {
                writeStatus.write(listFriends.get(i).getIdUserLogin() + "~~" + listFriends.get(i).getUserName() + "~~" + listFriends.get(i).getCheckFriendsGroup() + "\n");
            }
            SharedDirectory.generateFileList(new File(Login.SHAREPATH));
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }

    public static void statusWriteToFilePeer(String messageType, String userID, String userName, long messageID, int prPL, int like, int comment, String createdDate, String groupFriendID, String groupSuPeerID, String statusContent) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + userID + ".txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + messageID + "~~NamePost:" + userName + "~~StatusContent:" + statusContent + "~~GroupFriendID: " + groupFriendID + "~~CreatedDate:" + createdDate + "\n");
            SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }

    public static void statusWriteToFileSuperPeer(String messageType, String userID, String userName, long messageID, String groupFriendID, String statusContent, String createdDate) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + userID + ".txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            System.out.println("########## PREFERENCE: " + messageType);
            writeStatus.write(messageType + " ~~PostID: " + messageID + "~~NamePost:" + userName + "~~StatusContent:" + statusContent + "~~GroupFriendID: " + groupFriendID + "~~CreatedDate:" + createdDate + "\n");
            SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }

    public static void likeWriteToFileSuperPeer(String messageType, long postID, long likeID, String idUserPost, String idUserLike, String nameLike) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + idUserPost + ".txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + postID + "~~LikeID: " + likeID + "~~IDUserPost:" + idUserPost + "~~IDUserLike:" + idUserLike + "~~NameUserLike: " + nameLike + "\n");

            // update list file sharing after write file
            SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }

    public static void commentWriteToFileSuperPeer(String messageType, long postID, long commentID, String idUserPost, String idUserComment, String nameComment, String commentContent) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + idUserPost + ".txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + postID + "~~CommentID: " + commentID + "~~IDUserPost:" + idUserPost + "~~IDUserComment:" + idUserComment + "~~NameUserComment: " + nameComment + "~~Content of Comment: " + commentContent + "\n");

            // update list file sharing after write file
            SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }

    // read friend file
    public static void readFriendFile() {
        try {

            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + "listFriendPeer.txt")); // NHi - C:\Users\phatn_000\Desktop\src  Trang - F:\Users\dangphat50\Desktop\src\
            String line;
            while ((line = fileIn.readLine()) != null) {
                if (!line.trim().equals("")) {
                    Friends user = new Friends();
                    String[] userNameID = line.split("~~");
                    user.setIdUserLogin(userNameID[0]);
                    user.setUserName(userNameID[1]);
                    user.setCheckFriendsGroup(userNameID[2]);
                    user.setStatus(OFFLINE);
                    user.setCountOffline(COUNTER_OFFLINE);
                    friendList.add(user);
                    continue;
                }
            }

            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read readFriend Filepreferences file");
        }
    }

    public static void readListPeerManage() {
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + "listPeerSPManage.txt")); // NHi - C:\Users\phatn_000\Desktop\src  Trang - F:\Users\dangphat50\Desktop\src\
            String line;
            while ((line = fileIn.readLine()) != null) {
                peerManageList.add(line);
                continue;
            }
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read listPeerSPManage preferences file");
        }
    }

    public static String readPrivatePOST(String userPostID) {
        String listPost = "";
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + userPostID + ".txt"));
            String line;
            while ((line = fileIn.readLine()) != null) {
                if (line.contains("POST ") && line.contains("<Private> ")) {
                    listPost = listPost + line + "\n\n";
                    continue;
                }
            }
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
        return listPost;
    }

    public static String readUserPOSTorNEWSFEED(String type, String userPostID, int indexPost) {
        String postFix = "";
        String newsFeedString = "";
        List<String> newsFeed = new ArrayList<String>();
        if (type.equals(PROFILE)) {
            postFix = ".txt";
        } else if (type.equals(NEWSFEED)) {
            postFix = "_NewsFeed.txt";
        }
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + userPostID + postFix));
            String line;
            while ((line = fileIn.readLine()) != null) {
                if (line.contains("POST ")) {
                    newsFeed.add(0, line);
                    continue;
                }
            }

            if (indexPost == -1) {
                for (int i = 0; i < newsFeed.size(); i++) {
                    newsFeedString = newsFeedString + newsFeed.get(i) + "\n\n";
                    if (i == 19) {
                        break;
                    }
                }
            } else {
//                int sizeRemaining = newsFeed.size() - (indexPost + 1);
                int sizeRemaining = newsFeed.size() - indexPost;
                for (int j = indexPost; j < newsFeed.size(); j++) {
                    newsFeedString = newsFeedString + newsFeed.get(j) + "\n\n";
                    if (sizeRemaining > 20) {
                        if (j == (indexPost + 19)) {
                            break;
                        }
                    }
                }
            }
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
        return newsFeedString;
    }

    public static ReqRes_LikeCommentListObject readUserFile(long postID, String userPostID) {
        ReqRes_LikeCommentListObject objectLikeComment = new ReqRes_LikeCommentListObject();
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + userPostID + ".txt"));
            String line;
            int counterLike = 0;
            int counterComment = 0;
            String likeName = "";
            String comment = "";
            String likeID = "";
            String commentID = "";
            String idUserLike = "";
            String idUserComment = "";
            while ((line = fileIn.readLine()) != null) {
                if (line.contains("POST ") && line.contains(String.valueOf(postID))) {
                    objectLikeComment.setIsContainPost(true);
                }
                if (line.contains("LIKE ")) {
                    String[] itemLine = line.split("~~");
                    if (itemLine[1].substring(8).equals(String.valueOf(postID))) {
                        counterLike++;
                        likeName = likeName + "\n\n" + itemLine[5].substring(14);
                    }
                    likeID += itemLine[2].substring(8) + "~~";
                    idUserLike += itemLine[4].substring(11) + "~~";
                    continue;
                } else if (line.startsWith("COMMENT ")) {
                    String[] itemLine = line.split("~~");
                    if (itemLine[1].substring(8).equals(String.valueOf(postID))) {
                        counterComment++;
                        comment = comment + "< " + itemLine[5].substring(17) + " >:  " + itemLine[6].substring(20) + "\n\n";

                    }
                    commentID += itemLine[2].substring(11) + "~~";
                    idUserComment += itemLine[4].substring(14) + "~~";
                    continue;
                }
            }
            objectLikeComment.setNumLike(counterLike);
            objectLikeComment.setNumComment(counterComment);
            objectLikeComment.setUserNameLike(likeName);
            objectLikeComment.setComment(comment);
            objectLikeComment.setIdComment(commentID);
            objectLikeComment.setIdLike(likeID);
            objectLikeComment.setIdUserComment(idUserComment);
            objectLikeComment.setIdUserLike(idUserLike);
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
        return objectLikeComment;
    }

    public static void writeNewsFeed(String messageType, long postID, String userIDOnline, String idUserPost, String namePost, String statusContent, String createdDate) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + userIDOnline + "_NewsFeed.txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + postID + "~~IDUserPost:" + idUserPost + "~~NameUserPost: " + namePost + "~~StatusContent:" + statusContent + "~~CreatedDate:" + createdDate + "\n");

            // update list file sharing after write file
            SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }
}
