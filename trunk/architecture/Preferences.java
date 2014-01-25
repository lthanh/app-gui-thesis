package architecture;

import GUI.LoginForm;
import SuperPeerAction.PostObject;
import java.io.*;
import java.util.*;
import postService.LikeCommentListObject;

public class Preferences {

    public static String FILE_NAME = Login.SHAREPATH + "preferences.txt"; // NHi - C:\Users\phatn_000\Desktop\src\  Trang - F:\Users\dangphat50\Desktop\src\  C:\Users\admin\Desktop\src\
    public static int MAX_LIVE = 5;
    public static int MAX_CACHE = 100;
    public static boolean AUTO_CONNECT = true;
    public static int PINGER_TIME = 10000;
    public static int CONNECTOR_TIME = 10000;
    public static String SAVEPATH = "";
    public static String ONLINE = " - Online    (^^)";
    public static String OFFLINE = " - Offline";
    public static int COUNTER_OFFLINE = 3;
    public static int COUNTER_ONLINE = 0;
    public static Vector<String> ipSuperPeer = new Vector<String>();
    public static Vector<String> peerManageList = new Vector<String>();
    public static Vector<Friends> friendList = new Vector<Friends>();
    public static Vector<String> idFriendsListString = new Vector<String>();

    public static void readFromFile() {
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = fileIn.readLine()) != null) {
                System.out.println("Preferences line: " + line);
                if (line.startsWith("Host: ")) {
                    String address = line.substring(6);

                    ipSuperPeer.add(0, address); // list server in preference file

                    System.out.println("\n IP PEER:" + ipSuperPeer.get(0));

                    System.out.println("address:" + address);
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
                } else if (line.startsWith("Download-Directory: ")) {
                    SAVEPATH = line.substring(20);
                    System.out.println("Download-Directory is " + SAVEPATH);
                    continue;
                }
            }
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
    }

    public static void writeToUserFile(String userID, String userName, long numMessage) {
        List<String> user = new ArrayList<String>();
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + "userLogin.txt"));
            String line;

            while ((line = fileIn.readLine()) != null) {
                String[] check = line.split("-");
                if (userID.equals(check[0])) {
                    line = check[0] + "~~" + check[1] + "~~" + numMessage;//line.replace(userName, (userName + "-" + numMessage));
                    System.out.println(line);
                }
                user.add(line);
                continue;
            }

            fileIn.close();

            PrintWriter fileOut = new PrintWriter(new FileWriter(Login.SHAREPATH + "userLogin.txt"));
            for (String i : user) {
                fileOut.println(i);
            }
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
    }

    //////////////////////// byte[] userID, byte prpl, int like, int comment, String cDate, String idGroupFriends, String idGroupSP, String post
    public static void statusWriteToFilePeer(String messageType, String userID, String userName, long messageID, int prPL, int like, int comment, String createdDate, String groupFriendID, String groupSuPeerID, String statusContent) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + userID + ".txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + messageID + "~~NamePost:" + userName + "~~StatusContent:" + statusContent + "~~GroupFriendID: " + groupFriendID + "~~CreatedDate:" + createdDate + "\n");

            new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
            System.out.println("Update list sharing file successful");

            // writeStatus.newLine();
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

            // update list file sharing after write file
            new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
            System.out.println("Update list sharing file successful");

            //  writeStatus.newLine();
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
            new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
            System.out.println("Update list sharing file successful");

            //writeStatus.newLine();
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
            new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
            System.out.println("Update list sharing file successful");

            //  writeStatus.newLine();
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
                Friends user = new Friends();

                String[] userNameID = line.split("~~");

                user.setIdUserLogin(userNameID[0]);
                user.setUserName(userNameID[1]);
                if (user.getUserName().equals("Quoc Thanh") || user.getUserName().equals("Quynh Dao") || user.getUserName().equals("Thanh Thao") || user.getUserName().equals("Tien Thanh") || user.getUserName().equals("Minh Hieu")) {
                    user.setStatus(ONLINE);
                } else {
                    user.setStatus(OFFLINE);
                }

                user.setCountOffline(COUNTER_OFFLINE);
//                System.out.println("user.setIdUserLogin: " + user.getIdUserLogin());
//                System.out.println("user.setUserName: " + user.getUserName());
//                System.out.println("user.setStatus: " + user.getStatus());
                friendList.add(user);
                idFriendsListString.add(userNameID[0]);


                continue; // 
            }

            fileIn.close();
        } catch (Exception e) {
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
                continue; // 
            }

            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read listPeerSPManage preferences file");
        }
    }

    public static String readNewsFeedFile(String userID, int indexPost) {
        String newsFeedString = "";
        List<String> newsFeed = new ArrayList<String>();
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + userID + "_NewsFeed.txt"));
            String line;
            //  int count = 0;
            while ((line = fileIn.readLine()) != null) {
                //  count++;
                if (line.contains("POST ")) {
                    // newsFeed = newsFeed + line + "\n\n";
                    newsFeed.add(line);
                    continue;
                }
                // continue; // 
            }

            if (newsFeed.size() > 20) {
                if (indexPost == -2) {
                    for (int i = newsFeed.size() - 20; i <= newsFeed.size(); i++) {
                        newsFeedString = newsFeedString + newsFeed.get(i) + "\n\n";
                    }
                } else {
                    for (int i = (indexPost + 1) - 20; i < indexPost; i++) {
                        newsFeedString = newsFeedString + newsFeed.get(i) + "\n\n";
                    }
                }
            } else {
                for (int i = 0; i < newsFeed.size(); i++) {
                    newsFeedString = newsFeedString + newsFeed.get(i) + "\n\n";
                }
            }

            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to read readFriend Filepreferences file");
        }

        return newsFeedString;
    }

    public static LikeCommentListObject readUserFile(long postID, String userPostID) {
        LikeCommentListObject objectLikeComment = new LikeCommentListObject();
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
//                System.out.println("UserFile line: " + line);
                if (line.contains("LIKE ")) {
                    // System.out.println("UserFile line: " + line);
                    String[] itemLine = line.split("~~");
                    System.out.println("########## UserFile line: " + itemLine[1].substring(8));

                    if (itemLine[1].substring(8).equals(String.valueOf(postID))) {
                        counterLike++;
                        likeName = likeName + "\n\n" + itemLine[4].substring(14);
                    }
                    System.out.println("like id: " + (itemLine[2].substring(8)));
                    System.out.println("idUserLike : " + itemLine[4].substring(11));

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

    public static String readUserPOST(String userPostID) {
        String listPost = "";
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(Login.SHAREPATH + userPostID + ".txt"));
            String line;

            while ((line = fileIn.readLine()) != null) {
                if (line.contains("POST ")) {
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

    public static void writeNewsFeed(String messageType, long postID, String userIDOnline, String idUserPost, String namePost, String statusContent, String createdDate) {
        try {
            FileWriter fw = new FileWriter(Login.SHAREPATH + userIDOnline + "_NewsFeed.txt", true);
            BufferedWriter writeStatus = new BufferedWriter(fw);
            writeStatus.write(messageType + " ~~PostID: " + postID + "~~IDUserPost:" + idUserPost + "~~NameUserPost: " + namePost + "~~StatusContent:" + statusContent + "~~CreatedDate:" + createdDate + "\n");

            // update list file sharing after write file
            new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
            System.out.println("Update list sharing file successful");

            //writeStatus.newLine();
            writeStatus.close();
            System.out.println("Written to file");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to write to preferences file");
        }
    }
}
