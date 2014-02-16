/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.Packet;

/**
 *
 * @author admin
 */
public class Respond_NewsFeed extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Respond_NewsFeed(String userIDReq, String listPost) {
        super(Packet.RES_NewsFeed, (16 + listPost.length()));

        // convert userIDPost to byte  
        byte[] tempuserIDPost = new byte[16];
        tempuserIDPost = userIDReq.getBytes();
        int m;
        for (m = 0; m < userIDReq.length(); m++) {
            contents[(index + m)] = tempuserIDPost[m];
        }

        // convert listPost to byte  
        byte[] tempListPost = new byte[listPost.length()];
        tempListPost = listPost.getBytes();
        int i;
        // System.out.println("length: " + idUserIDReq.length());
        for (i = 0; i < listPost.length(); i++) {
            contents[(index + 16 + i)] = tempListPost[i];
//            System.out.println("userName : [" + i + "]" + contents[(index + 22 + i)]);
        }
    }

    public Respond_NewsFeed(byte[] rawdata) {
        super(rawdata);
    }

    public String getUserIDReq() {
        //  byte[] temp = new byte[16];
        String temp = "";
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp = temp + (char) (contents[index + i]);
        }
//        System.out.println("POST -userID: " + userID);
        return temp;
    }

    public String getListPost() {
        String listPost = "";
        for (int i = (index + 16); i < (contents.length); i++) {
            listPost = listPost + (char) (contents[i]);
        }

//        System.out.println("POST -userName: " + userName);
        return listPost;
    }
}
