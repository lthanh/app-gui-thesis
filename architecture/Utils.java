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
}
