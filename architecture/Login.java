package architecture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public  class Login {

    public static List<UserLoginObject> userLogin = new ArrayList<UserLoginObject>();
    public static String SHAREPATH = "";

    public static void readFileUserID() {
        try {
                                    //NHi - C:\Users\phatn_000\Desktop\src\  Trang - F:\Users\dangphat50\Desktop\src\   Thanh C:\Users\admin\Desktop\src\
            BufferedReader fileIn = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\src\\userLogin.txt")); 
            String line;

            while ((line = fileIn.readLine()) != null) {
                if (line.startsWith("Shared-Directory: ")) {
                    Login.SHAREPATH = line.substring(18);
                    System.out.println("Shared-Directory is " + Login.SHAREPATH);
                    continue;
                } else {
                    UserLoginObject user = new UserLoginObject();
                    // System.out.println("User line: " + line);
                    String[] userNameID = line.split("-");

                    user.setIdUserLogin(userNameID[0]);
                    user.setUserName(userNameID[1]);
                    userLogin.add(user);
                    continue;
                }
            }

            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
    }
}
