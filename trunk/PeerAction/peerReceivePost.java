/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerAction;

import GUI.AppGUI;
import SuperPeerAction.PostObject;
import Architecture_Posting.Utils;
import static PostingService.PostHandler.recieveListPost;
import static PostingService.PostHandler.showListPost;

/**
 *
 * @author Thanh Le Quoc
 */
public class PeerReceivePost {
    
    public PeerReceivePost() {
    }
    
    public void receivePost(PostObject post) {
        if (!"".equals(post.getNamePost())) {
            recieveListPost.add(0, post);
            showListPost.add(0, Utils.formSHOWSTATUS(post.getNamePost(), post.getContentPost(), post.getCreatedDate()));
            AppGUI.inform(showListPost);
        }
    }
}
