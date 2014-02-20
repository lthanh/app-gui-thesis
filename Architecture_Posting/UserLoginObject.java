package Architecture_Posting;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class UserLoginObject {
    public UserLoginObject(){}
    
    public String idUserLogin;
    public String userName;
    public String status;
//    public long numMessageSent;

//    public long getNumMessageSent() {
//        return numMessageSent;
//    }
//
//    public void setNumMessageSent(long numMessageSent) {
//        this.numMessageSent = numMessageSent;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUserLogin() {
        return idUserLogin;
    }

    public void setIdUserLogin(String idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
