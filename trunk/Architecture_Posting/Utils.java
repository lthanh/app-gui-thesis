package Architecture_Posting;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.PeerReceivePost;
import static PostingService.PostHandler.recieveListPost;
import SuperPeerAction.PostObject;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Thanh Le Quoc
 */
public class Utils {

    public Utils() {
    }

    public static String formatDate(Date d) {
        String formattedDate = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm MMMM dd, yyyy");
            formattedDate = formatter.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String formSHOWSTATUS(String userName, String post, String cDate) {
        String statusForm = "< " + userName + " >:    " + post + "        : Created -   " + cDate;
        return statusForm;
    }

    public static String getTypeMessage(byte payLoad) {
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

    // check list file sharing
    public boolean checkFileSharing(String userID) {
        String[] tempList = SharedDirectory.listFileIDSaving.split("\n");

        for (int i = 0; i < tempList.length; i++) {
            if (userID.equals(tempList[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean checkServerName(String userNameLogin) {
        for (int i = 1; i < 6; i++) {
            if (userNameLogin.equals("Server" + i)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkServerID(String userIDLogin, String listServerID) {

        if (listServerID.contains(userIDLogin)) {
            return true;
        }

        return false;
    }

    public static Vector<Friends> listFriendsIgnoreUserLogin(Vector<Friends> list) {
        Vector<Friends> listFriends = new Vector<Friends>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getIdUserLogin().equals(LoginForm.currentUser.getIdUserLogin())) {
                listFriends.add(list.get(i));
            }
        }

        return listFriends;
    }

    // check whether any Super Peer are connecting
    public boolean checkServerConnectedInGUI() {
        for (int i = 0; i < HostArray.showListServer.size(); i++) {
            if (HostArray.showListServer.get(i).contains("Super Peer")) {
                return true;
            }
        }
        return false;
    }

    // user to check Super Peer in file Preference to add connection with the Super Peer
    public boolean checkSuperPeer(IPAddress ip, int port) {
        String address = ip.toString() + ":" + port;
        for (int i = 0; i < Preferences.ipSuperPeer.size(); i++) {
            if (Preferences.ipSuperPeer.get(i).contains(address)) {
                return true;
            }
        }
        return false;
    }

    public void endOfFile() {
        JOptionPane.showMessageDialog((new JFrame()), "No more data ...", "End of loading !", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<PostObject> getPrivateMessage() {
        List<PostObject> listPrivate = new ArrayList<PostObject>();
        boolean isFileStoring;
        PeerReceivePost prc = new PeerReceivePost();
        // read private message that only store on user device before
        try {
            isFileStoring = checkFileSharing(AppGUI.idUserSelected + ".txt");
            if (isFileStoring) {
                String listPost = Preferences.readPrivatePOST(AppGUI.idUserSelected);
                if (listPost.trim().length() != 0) {
                    String[] tempPost = listPost.split("\n\n");

                    for (int i = 0; i < tempPost.length; i++) {
                        String[] linePost = tempPost[i].split("~~");
                        boolean isPosted = checkPostInList(Long.parseLong(linePost[1].substring(8)), recieveListPost);
                        if (isPosted == false) {
                            PostObject profileObject = new PostObject();
                            profileObject.setPostID(Long.parseLong(linePost[1].substring(8)));
                            profileObject.setNamePost(linePost[2].substring(9));
                            profileObject.setContentPost(linePost[3].substring(14));
                            profileObject.setGroupID(linePost[4].substring(15));
                            profileObject.setCreatedDate(linePost[5].substring(12));
                            profileObject.setUserIDPost(AppGUI.idUserSelected);
                            listPrivate.add(profileObject);
                            prc.receivePost(profileObject);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPrivate;

    }

    public boolean checkPostInList(long postID, Vector<PostObject> profile) {
        for (int i = 0; i < profile.size(); i++) {
            if (postID == profile.get(i).getPostID()) {
                return true;
            }
        }
        return false;
    }
}

/* Parse byte to String

 byte[] temp = new byte[16];
 for (int i = 0; i < 16; i++) {
 temp[i] = contents[index + 6 + i];
 }
 String userID = new String(temp);
 System.out.println("\nPONG: getUserIDOnline receive - " + userID);
 return userID;
 */
/* Cat chuoi IP byte ra dang decimal *****************************************************
 byte[] test = new byte[newpacket.length];

 for (int i = 0; i < newpacket.length; i++) {
 test[i] = newpacket[i];
 }
 int[] ipints = new int[23];
 for (int i = 0; i < newpacket.length; i++) {
 ipints[i] = ((int) (test[i]) & 0xff);
 }
 */ //end cat chuoi

