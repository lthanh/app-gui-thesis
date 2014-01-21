/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerAction;

import GUI.AppGUI;
import architecture.Utils;
import postService.Post;
import static postService.PostHandler.myIP;
import static postService.PostHandler.recieveListPost;
import static postService.PostHandler.showListPost;

/**
 *
 * @author admin
 */
public class peerReceivePost {

    public peerReceivePost() {
    }

    public void receivePost(Post postMessage) {
        recieveListPost.add(0, postMessage);
        showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate()));
        AppGUI.inform(myIP, showListPost);
    }
}
