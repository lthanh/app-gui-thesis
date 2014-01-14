
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
public class Login {

    public static List<UserLoginObject> userLogin = new ArrayList<UserLoginObject>();
    // public static String path = getClassLoader().getResource("listUser.txt").getPath();

    public static void readFileUserID() {
        try {

            BufferedReader fileIn = new BufferedReader(new FileReader("src/userLogin.txt"));
            String line;

            while ((line = fileIn.readLine()) != null) {
                UserLoginObject user = new UserLoginObject();
                System.out.println("User line: " + line);
                String[] userNameID = line.split("-");

                user.setIdUserLogin(userNameID[0]);
                user.setUserName(userNameID[1]);
                userLogin.add(user);
                continue;
            }

            for (int i = 0; i < userLogin.size(); i++) {
                System.out.println("List user login: " + userLogin.get(i).getIdUserLogin());
                System.out.println("List user login: " + userLogin.get(i).getUserName());
            }
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to read preferences file");
        }
    }
}
