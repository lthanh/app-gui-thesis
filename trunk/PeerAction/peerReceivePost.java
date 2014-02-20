/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerAction;

import GUI.AppGUI;
import SuperPeerAction.PostObject;
import Architecture_Posting.Utils;
import postService.Post;
import static postService.PostHandler.myIP;
import static postService.PostHandler.recieveListPost;
import static postService.PostHandler.showListPost;

/**
 *
 * @author admin
 */
public class PeerReceivePost {
    
    public PeerReceivePost() {
    }
    
    public void receivePost(PostObject post) {
        if (post.getNamePost() != "") {
//            if (post.getContentPost().equals("ClearList")) {
//                post.setContentPost("");
//            }
            recieveListPost.add(0, post);
            showListPost.add(0, Utils.formSHOWSTATUS(post.getNamePost(), post.getContentPost(), post.getCreatedDate()));
            AppGUI.inform(showListPost);
        }
    }
}
