package GUI;

import static GUI.StatusForm.btnLike;
import static GUI.StatusForm.postID;
import static GUI.StatusForm.useIDLogin;
import static GUI.StatusForm.userNameLoginString;
import architecture.*;
import PeerAction.checkUserOnlineAction;
import SuperPeerAction.Request_LikeCmt;
import SuperPeerAction.RespondStatusFormObject;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import postService.LikeCommentListObject;
import postService.Post;
import postService.PostHandler;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class AppGUI extends javax.swing.JFrame {

    /**
     * Creates new form AppGUI
     */
//    public static long numMessageSent = LoginForm.currentUser.getNumMessageSent(); // count number of message sent in one section to set message id
    public static byte prPl = 1;
    public static long postSelectedID = 0;
    public static String postContentSelected = "";
    public static StatusForm statusPOPUP;

    public AppGUI() {
        initComponents();
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setTitle("Posting Message Service");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                Preferences.writeToUserFile(LoginForm.currentUser.getIdUserLogin(), LoginForm.currentUser.getUserName(), numMessageSent);
                System.exit(0);
            }
        });
        groupRadio.add(rdoPl);
        groupRadio.add(rdoPr);
        rdoPl.setSelected(true);

        // get UserID to byte array
        lbUserName.setText(LoginForm.currentUser.getUserName());

        // Gnutella 
        System.out.println("Setting up hash tables...");
        QHandler.initQueryTable();
        PingHandler.initPingTable();
        System.out.println("Determining network address...");
        Mine.updateAddress();
        System.out.println("Reading preferences file...");
        //  new Searcher();
        Preferences.readFromFile();
        Preferences.readFriendFile();

        if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
            Preferences.readListPeerManage();
        }

        for (int i = 0; i < Preferences.friendList.size(); i++) {
            checkUserOnlineAction.showUserNameFriend.add(Preferences.friendList.get(i).getUserName() + Preferences.friendList.get(i).getStatus());
        }
        listFriends.setListData(checkUserOnlineAction.showUserNameFriend);

        System.out.println("Setting up file table...");
        new SharedDirectory(Login.SHAREPATH, Preferences.SAVEPATH);
        Listener listener = new Listener();
        listener.start(); // Beginning listening for network connections
        PeriodicConnector periodicconnector = new PeriodicConnector(Preferences.AUTO_CONNECT); // Begin actively trying to connect
        periodicconnector.start();
        Pinger pinger = new Pinger();
        pinger.start(); // Start sending out periodic pings.


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupRadio = new javax.swing.ButtonGroup();
        lbUserName = new javax.swing.JLabel();
        btnNoti = new javax.swing.JButton();
        btnFeed = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listServer = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        listFriends = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        btnPost = new javax.swing.JButton();
        rdoPr = new javax.swing.JRadioButton();
        rdoPl = new javax.swing.JRadioButton();
        txtStatus = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listStatus = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbUserName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbUserName.setForeground(new java.awt.Color(0, 0, 255));
        lbUserName.setText("User");

        btnNoti.setText("Notification");
        btnNoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotiActionPerformed(evt);
            }
        });

        btnFeed.setText("News Feed");
        btnFeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedActionPerformed(evt);
            }
        });

        btnSetting.setText("Setting");
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });

        btnProfile.setText("Profile");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));
        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        listServer.setBackground(new java.awt.Color(240, 240, 240));
        listServer.setBorder(javax.swing.BorderFactory.createTitledBorder("Server"));
        listServer.setAlignmentX(0.0F);
        listServer.setAlignmentY(0.0F);
        jScrollPane3.setViewportView(listServer);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        listFriends.setBackground(new java.awt.Color(240, 240, 240));
        listFriends.setBorder(javax.swing.BorderFactory.createTitledBorder("Friends"));
        listFriends.setAlignmentX(0.0F);
        listFriends.setAlignmentY(0.0F);
        listFriends.setFixedCellHeight(30);
        jScrollPane5.setViewportView(listFriends);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        btnPost.setText("Post");
        btnPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostActionPerformed(evt);
            }
        });

        rdoPr.setText("Private");
        rdoPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPrActionPerformed(evt);
            }
        });

        rdoPl.setText("Public");
        rdoPl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoPlActionPerformed(evt);
            }
        });

        txtStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtStatusKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(rdoPl, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoPr, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(103, 103, 103)
                .addComponent(btnPost, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(txtStatus)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoPl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoPr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPost))
                .addGap(6, 6, 6))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Friends' status"));

        listStatus.setBackground(new java.awt.Color(240, 240, 240));
        listStatus.setFixedCellHeight(50);
        listStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listStatusMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listStatus);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lbUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNoti, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnFeed, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnNoti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFeed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNotiActionPerformed

    private void btnFeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFeedActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSettingActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostActionPerformed
        // TODO add your handling code here:
        showPost();
    }//GEN-LAST:event_btnPostActionPerformed

    private void rdoPlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPlActionPerformed
        // TODO add your handling code here:
        prPl = 1;
    }//GEN-LAST:event_rdoPlActionPerformed

    private void rdoPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoPrActionPerformed
        // TODO add your handling code here:
        prPl = 0;
    }//GEN-LAST:event_rdoPrActionPerformed

    private void listStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listStatusMouseClicked
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 1) {
            int indexSelected = list.locationToIndex(evt.getPoint());

            // int indexSelected = listStatus.getSelectedIndex();
            Post postSelected = PostHandler.recieveListPost.get(indexSelected);
            postSelectedID = postSelected.getMessageID();

            System.out.println("Send request before");
            Request_LikeCmt req = new Request_LikeCmt(Mine.getPort(), Mine.getIPAddress(), postSelected.getMessageID(), postSelected.getUserID());
            NetworkManager.writeToAll(req);
            System.out.println("Send request after");

            // StatusForm statusPOPUP = new StatusForm();
            statusPOPUP = new StatusForm();
            Vector<String> tempComment = new Vector<String>();
            statusPOPUP.setTitle(postSelected.getUserName() + "'s status");
            statusPOPUP.lbUseName.setText(postSelected.getUserName());
            statusPOPUP.txtContentPopUp.setText(postSelected.getPostStatusContent());
            statusPOPUP.lbLike.setText("0");
            statusPOPUP.lbComment.setText("0");
            statusPOPUP.listComment.setListData(tempComment);
            long postID = postSelected.getMessageID();
            String userIDPost = postSelected.getUserID();
            statusPOPUP.lbIDUserPost.hide();
            statusPOPUP.lbIDUserPost.setText(userIDPost);
            statusPOPUP.lbMessageID.hide();
            statusPOPUP.lbMessageID.setText(String.valueOf(postID));
            statusPOPUP.btnLike.setVisible(false);
            statusPOPUP.btnComment.setVisible(false);
            statusPOPUP.txtComment.setVisible(false);
            statusPOPUP.show();


        } else if (evt.getClickCount() == 3) {   // Triple-click
            // int index = list.locationToIndex(evt.getPoint());
        }

    }//GEN-LAST:event_listStatusMouseClicked

    private void txtStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStatusKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            showPost();
        }

    }//GEN-LAST:event_txtStatusKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AppGUI app = new AppGUI();
                app.setVisible(true);


            }
        });




    }
    //////////// POST MESSAGE
    static LinkedList post = new LinkedList();

    public static void addPOST(Post postMessage) // Called when the user presses the "Search" button
    {
        post.add(postMessage);
    }

    public static void inform(IPAddress ip, Vector<String> listPostMessage) {

        //Integer port = new Integer(ip.getPort());
        // String myip = ip.toString();
        System.out.println("\n AppGUI - Post: " + listPostMessage.toString());
        listStatus.setListData(listPostMessage);
    }

    public static void showPost() {
        String textPost = txtStatus.getText();
        if (!textPost.trim().isEmpty()) {
            txtStatus.setText(null);
            String createdate = Utils.formatDate(new Date());
            String friend = "12121212-21212121212";
            String groupdSuperPeerID = "192.168.0.110:6346;192.168.0.120:6346"; // ignore
            int liked = 0;
            int commented = 0;

            if (prPl == 0) {
                privateWritePost(textPost, createdate, friend, groupdSuperPeerID, liked, commented);
            } else {
                writePostPublic(LoginForm.currentUser.getIdUserLogin(), prPl, liked, commented, createdate.length(), friend.length(), groupdSuperPeerID.length(), userNameLoginString.length(), userNameLoginString, createdate, friend, groupdSuperPeerID, textPost);

            }
        }
    }

    // send post to other one via Network and save into database
    public static void writePostPublic(String userID, byte prpl, int like, int comment, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        Post postMessage = new Post(userID, cDateLength, groupdFriendIDLength, groupdSuperPeerIDLength, useNameLength, userNamePost, cDate, idGroupFriends, idGroupSP, post);
        System.out.println("POST MESSAGEID : " + postMessage.getMessageID());
        System.out.println("POST MESSAGE GET CONTENT BYTE: " + postMessage.contents());
        // show status on news feed of user logging in when they have just written the status
        PostHandler.recieveListPost.add(0, postMessage);
        PostHandler.showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate()));
        AppGUI.inform(PostHandler.myIP, PostHandler.showListPost);

//        byte[] temp = new byte[16];
//        for (int i = 0; i < 16; i++) {
//            temp[i] = userIDLoginToByte[i];
//        }
//
//        String usenID = new String(temp);

        // check user to store data
        Preferences.statusWriteToFilePeer(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), prpl, like, comment, cDate, idGroupFriends, idGroupSP, post);

        NetworkManager.writeToAll(postMessage);
        addPOST(postMessage);
    }

    public static void privateWritePost(String postText, String createdate, String friend, String groupdSuperPeerID, int liked, int commented) {
        Post postMessage = new Post(LoginForm.currentUser.getIdUserLogin(), createdate.length(), friend.length(), groupdSuperPeerID.length(), userNameLoginString.length(), userNameLoginString, createdate, friend, groupdSuperPeerID, postText);
        PostHandler.recieveListPost.add(0, postMessage);
        PostHandler.showListPost.add(0, Utils.formSHOWSTATUS(postMessage.getUserName(), postMessage.getPostStatusContent(), postMessage.getCreatedDate()));
        AppGUI.inform(PostHandler.myIP, PostHandler.showListPost);

        Preferences.statusWriteToFilePeer(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), prPl, liked, commented, createdate, friend, groupdSuperPeerID, postText);

    }
    public static void updateStatusForm(int numLike, int numComment, String userLike, Vector<String> comment) {
        System.out.println("###### RECEIVE REPOSND");
        StatusForm.btnLike.setVisible(true);
        StatusForm.btnComment.setVisible(true);
        StatusForm.txtComment.setVisible(true);
        StatusForm.lbLike.setText(String.valueOf(numLike));
        StatusForm.lbComment.setText(String.valueOf(numComment));
        if (!comment.isEmpty()) {
            StatusForm.listComment.setListData(comment);
        }
        StatusForm.lbLoading.setVisible(false);
    }
    //////////// END POST MESSAGE
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btnFeed;
    private static javax.swing.JButton btnNoti;
    private static javax.swing.JButton btnPost;
    private static javax.swing.JButton btnProfile;
    private static javax.swing.JButton btnSetting;
    private javax.swing.ButtonGroup groupRadio;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public static javax.swing.JLabel lbUserName;
    public static javax.swing.JList listFriends;
    public static javax.swing.JList listServer;
    public static javax.swing.JList listStatus;
    private static javax.swing.JRadioButton rdoPl;
    private static javax.swing.JRadioButton rdoPr;
    public static javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
