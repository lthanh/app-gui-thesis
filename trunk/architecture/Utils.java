package architecture;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        String statusForm = "< " + userName + " >:  " + post + "        : Created - " + cDate;
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
    /* Parse byte to String

     byte[] temp = new byte[16];
     for (int i = 0; i < 16; i++) {
     temp[i] = contents[index + 6 + i];
     }
     String userID = new String(temp);
     System.out.println("\nPONG: getUserIDOnline receive - " + userID);
     return userID;
     
     
     */
}
