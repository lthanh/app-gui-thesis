package Architecture_Posting;

import GUI.LoginForm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class Utils {
    
    public Utils() {
    }
    
    public static String formatDate(Date d) {
        String formattedDate = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm MMMM dd, yyyy");
            //SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
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
