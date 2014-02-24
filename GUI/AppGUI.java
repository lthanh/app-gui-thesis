package GUI;

import Architecture_Posting.Friends;
import Architecture_Posting.QHandler;
import Architecture_Posting.Mine;
import Architecture_Posting.Utils;
import Architecture_Posting.SharedDirectory;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.PingHandler;
import Architecture_Posting.Pinger;
import Architecture_Posting.Preferences;
import Architecture_Posting.Login;
import Architecture_Posting.PeriodicConnector;
import Architecture_Posting.Listener;
import Architecture_Posting.PongHandler;
import static GUI.StatusForm.btnLike;
import static GUI.StatusForm.postID;
import static GUI.StatusForm.useIDLogin;
import static GUI.StatusForm.userNameLoginString;
import PeerAction.CheckUserOnlineAction;
import PeerAction.PeerReceivePost;
import PostingService.LikeCommentHandler;
import SuperPeerAction.PostObject;
import SuperPeerAction.Request_LikeCmt;
import SuperPeerAction.Request_NewsFeed;
import SuperPeerAction.Request_Profile;
import SuperPeerAction.RespondStatusFormObject;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import PostingService.LikeCommentListObject;
import PostingService.Post;
import PostingService.PostHandler;
import SuperPeerAction.NewsFeedHandler;
import SuperPeerAction.ProfileHandler;
import SuperPeerAction.ReqRes_LikeCommentHanlder;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class AppGUI extends javax.swing.JFrame {

    public static byte prPl = 1;
    public static long postSelectedID = 0;
    public static String nameUserSelected = "";
    public static String idUserSelected = "";
    public static String postContentSelected = "";
    public static StatusForm statusPOPUP;
    public static boolean isScroll = false;
    public static String userNameLiked = "";
    public static String userNameCommented = "";
    public static int numLikeCommented = 0;
    public int previousIndexScroll = 0;
    public static boolean isNewsFeed = true;  // TRUE = NEWSFEED session ; FALSE = PROFILE session
    public static boolean isShowingStatusPopUp = false;
    public static boolean isShowingFriendsGroupPopUp = false;
    public static String useIDLogin = LoginForm.currentUser.getIdUserLogin();
    public static String userNameLoginString = LoginForm.currentUser.getUserName();
    public static JFrame loadingForm = (new LoadingPopUp()).loadingForm();
    public static long startShowLoading; // use to counter time of loading
    // Get elapsed time in seconds
    float elapsedTimeSec;
    Utils utils = new Utils();
    boolean isServer = utils.checkServerName(userNameLoginString);
    Vector<Friends> tempListFriend;

    public AppGUI() {
        initComponents();
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setTitle("Posting Message Service");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        groupRadio.add(rdoPl);
        groupRadio.add(rdoPr);
        rdoPl.setSelected(true);
        lbUserName.setText(userNameLoginString);

        // Gnutella 
        System.out.println("Setting up hash tables...");
        PingHandler.initPingTable();
        PongHandler.initPongTable();
        PostHandler.initPostTable();
        NewsFeedHandler.initRequestNewsFeedTable();
        NewsFeedHandler.initRespondNewsFeedTable();
        ProfileHandler.initRequestProfileTable();
        ProfileHandler.initRespondProfileTable();
        LikeCommentHandler.initLikeTable();
        LikeCommentHandler.initCommentTable();
        ReqRes_LikeCommentHanlder.initRequestLikeCommentTable();
        ReqRes_LikeCommentHanlder.initRespondLikeCommentTable();
        System.out.println("Determining network address...");
        Mine.updateAddress();
        System.out.println("Reading preferences file...");
        SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing
        Preferences.readFromFile();
        Preferences.readFriendFile();

        if (isServer) {
            Preferences.readListPeerManage();
        }

        tempListFriend = Utils.listFriendsIgnoreUserLogin(Preferences.friendList);
        for (int i = 0; i < tempListFriend.size(); i++) {
            CheckUserOnlineAction.showUserNameFriend.add(tempListFriend.get(i).getUserName() + tempListFriend.get(i).getStatus());
        }
        listFriends.setListData(CheckUserOnlineAction.showUserNameFriend);

        System.out.println("Setting up file table...");
        Listener listener = new Listener();
        listener.start(); // Beginning listening for network connections
        PeriodicConnector periodicconnector = new PeriodicConnector(Preferences.AUTO_CONNECT); // Begin actively trying to connect
        periodicconnector.start();
        Pinger pinger = new Pinger();
        pinger.start(); // Start sending out periodic pings.

        for (int i = 0; i < Preferences.peerManageList.size(); i++) {
            if (!SharedDirectory.listFileIDSaving.contains(Preferences.peerManageList.get(i))) {
                try {
                    File userFile = new File(Login.SHAREPATH + Preferences.peerManageList.get(i) + ".txt");
                    File newsfeedFile = new File(Login.SHAREPATH + Preferences.peerManageList.get(i) + "_NewsFeed.txt");
                    userFile.createNewFile(); // create peer's file if they are not exist
                    newsfeedFile.createNewFile(); // create newsfeed peer's file
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        SharedDirectory.generateFileList(new File(Login.SHAREPATH)); // update list file sharing after check list file sharing

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
        btnFriendsGroup = new javax.swing.JButton();
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
        listFriends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listFriendsMouseClicked(evt);
            }
        });
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
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        btnPost.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        btnFriendsGroup.setText("Friends Group");
        btnFriendsGroup.setAlignmentY(2.5F);
        btnFriendsGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFriendsGroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(rdoPl, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoPr, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFriendsGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPost))
            .addComponent(txtStatus)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoPl)
                    .addComponent(rdoPr)
                    .addComponent(btnPost)
                    .addComponent(btnFriendsGroup))
                .addGap(2, 2, 2))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Friends' status"));

        listStatus.setBackground(new java.awt.Color(240, 240, 240));
        listStatus.setFixedCellHeight(50);
        listStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listStatusMouseClicked(evt);
            }
        });
        listStatus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                listStatusMouseMoved(evt);
            }
        });
        listStatus.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listStatusValueChanged(evt);
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNoti, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnFeed, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFeed, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNoti, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
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
        String noti = showDialogNotification(userNameLiked, userNameCommented);
        if (noti.equals("\n") || noti.equals("\n\n")) {
            noti = "0 - Notification";
        }
        btnNoti.setText("Notification");
        numLikeCommented = 0;
        userNameLiked = "";
        userNameCommented = "";


        loadingForm.hide();
        startShowLoading = -1;
        JOptionPane.showMessageDialog(this, noti, "Notification", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btnNotiActionPerformed

    private void btnFeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedActionPerformed
        // TODO add your handling code here:
        System.out.println("ONCLICK Feed Before");

        isNewsFeed = true;
        PostHandler.recieveListPost.removeAllElements();
        PostHandler.showListPost.removeAllElements();
        listStatus.setListData(new Object[0]);

        Request_NewsFeed requestFeed = new Request_NewsFeed(Mine.getPort(), Mine.getIPAddress(), -1, LoginForm.currentUser.getIdUserLogin());
        NetworkManager.writeToAll(requestFeed);
        System.out.println("ONCLICK Feed After");
        previousIndexScroll = 19;

        loadingForm.show();
        startShowLoading = System.currentTimeMillis();
        System.out.println("startShowLoading: " + startShowLoading);
    }//GEN-LAST:event_btnFeedActionPerformed

    private void btnFriendsGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFriendsGroupActionPerformed
        // TODO add your handling code here:
        if (isShowingFriendsGroupPopUp == false) {
            btnFriendsGroup.setEnabled(false);
            FriendsGroupForm a = new FriendsGroupForm();
        }
    }//GEN-LAST:event_btnFriendsGroupActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
        System.out.println("ONCLICK Profile Before");

        isNewsFeed = false;
        PostHandler.recieveListPost.removeAllElements();
        PostHandler.showListPost.removeAllElements();
        listStatus.setListData(new Object[0]);

        nameUserSelected = LoginForm.currentUser.getUserName();
        idUserSelected = LoginForm.currentUser.getIdUserLogin();

        Request_Profile requestProfile = new Request_Profile(Mine.getPort(), Mine.getIPAddress(), -1, idUserSelected);
        NetworkManager.writeToAll(requestProfile);
        System.out.println("ONCLICK Profile After");

        previousIndexScroll = 19;
        loadingForm.show();
        startShowLoading = System.currentTimeMillis();
        System.out.println("startShowLoading: " + startShowLoading);

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
            System.out.println("locationToIndex:" + indexSelected);
            if (indexSelected != -1) {
                // int indexSelected = listStatus.getSelectedIndex();
                // Post postSelected = PostHandler.recieveListPost.get(indexSelected).getPostID();
                PostObject postSelectedObject = PostHandler.recieveListPost.get(indexSelected);
                postSelectedID = postSelectedObject.getPostID();
                if (postSelectedObject.getUserIDPost() != "") {
                    System.out.println("Send request before");
                    if (isShowingStatusPopUp == false) {
                        Request_LikeCmt req = new Request_LikeCmt(Mine.getPort(), Mine.getIPAddress(), postSelectedObject.getPostID(), postSelectedObject.getUserIDPost(), useIDLogin);
                        NetworkManager.writeToAll(req);
                        loadingForm.show();
                        startShowLoading = System.currentTimeMillis();

                        System.out.println("startShowLoading: " + startShowLoading);
                        Vector<String> tempComment = new Vector<String>();
                        statusPOPUP = new StatusForm(postSelectedObject.getNamePost(), postSelectedObject.getContentPost(), 0, 0, tempComment, postSelectedObject.getPostID(), postSelectedObject.getUserIDPost());
//                        statusPOPUP.setEnabled(false);
                        statusPOPUP.show();
                    } else {
                        JOptionPane.showMessageDialog(this, "You are opening another Status window !\n\nPlease close it before you open another one.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_listStatusMouseClicked

    private void listFriendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listFriendsMouseClicked
        // TODO add your handling code here:
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 1) {

            isNewsFeed = false;
            PostHandler.recieveListPost.removeAllElements();
            PostHandler.showListPost.removeAllElements();
            listStatus.setListData(new Object[0]);

            int indexSelected = list.locationToIndex(evt.getPoint());
            nameUserSelected = Preferences.friendList.get(indexSelected).getUserName();
            idUserSelected = Preferences.friendList.get(indexSelected).getIdUserLogin();

            Request_Profile requestProfile = new Request_Profile(Mine.getPort(), Mine.getIPAddress(), -1, idUserSelected);
            NetworkManager.writeToAll(requestProfile);

            previousIndexScroll = 19;
            loadingForm.show();
            startShowLoading = System.currentTimeMillis();
            System.out.println("startShowLoading: " + startShowLoading);

        }

    }//GEN-LAST:event_listFriendsMouseClicked

    // do not do anything
    private void listStatusValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listStatusValueChanged
    }//GEN-LAST:event_listStatusValueChanged

    private void listStatusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listStatusMouseMoved
        // TODO add your handling code here:


        elapsedTimeSec = (System.currentTimeMillis() - startShowLoading) / 1000F; // get time to close loading when they can not auto close, in case of message does not respond.
        if (startShowLoading != -1 && elapsedTimeSec > 15) {
            loadingForm.hide();
            startShowLoading = -1;
        }
        System.out.println("elapsedTimeSec: " + elapsedTimeSec);


        int lastVisibleIndex = listStatus.getLastVisibleIndex();
        System.out.println("LAST VISIBLE: " + lastVisibleIndex);

        if ((lastVisibleIndex >= previousIndexScroll)) {
            previousIndexScroll += 19;
            loadMore(isNewsFeed, lastVisibleIndex + 1);

            System.out.println("######### previousIndexScroll: " + previousIndexScroll);

        }
    }//GEN-LAST:event_listStatusMouseMoved

    private void txtStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStatusKeyPressed
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
//                JPanel p = new FriendsGroup();
//                app.getContentPane().add(p);
                // app.add(loadingForm);
                app.pack();
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

    public static void inform(Vector<String> listPostMessage) {
        System.out.println("\n AppGUI - Post: " + listPostMessage.toString());
        listStatus.setListData(listPostMessage);
    }

    public void loadMore(boolean isNewsFeed, int index) {
        if (isNewsFeed) {
            Request_NewsFeed requestFeed = new Request_NewsFeed(Mine.getPort(), Mine.getIPAddress(), index, LoginForm.currentUser.getIdUserLogin());
            NetworkManager.writeToAll(requestFeed);
            loadingForm.show();
            startShowLoading = System.currentTimeMillis();
            System.out.println("startShowLoading: " + startShowLoading);
        } else {
            Request_Profile requestProfile = new Request_Profile(Mine.getPort(), Mine.getIPAddress(), index, idUserSelected);
            NetworkManager.writeToAll(requestProfile);
            loadingForm.show();
            startShowLoading = System.currentTimeMillis();
            System.out.println("startShowLoading: " + startShowLoading);
        }
    }

    public void showPost() {

        String textPost = txtStatus.getText();
        if (!textPost.trim().isEmpty()) {
            txtStatus.setText(null);
            String createdate = Utils.formatDate(new Date());
            String friend = "";
            String groupdSuperPeerID = "9999999999999999:0000000000000000:1212121212121212:2323232323232323:3434343434343434"; // 5 server form Server1 to 5
            int liked = 0;
            int commented = 0;

            for (int i = 0; i < Preferences.friendList.size(); i++) {
                if (Preferences.friendList.get(i).getCheckFriendsGroup().equals(Preferences.CHECKED)) {
                    friend += Preferences.friendList.get(i).getIdUserLogin() + ":";
                }
            }

            if (prPl == 0) {
                privateWritePost(textPost, createdate, friend, groupdSuperPeerID, liked, commented);
            } else {
                writePostPublic(LoginForm.currentUser.getIdUserLogin(), prPl, liked, commented, createdate.length(), friend.length(), groupdSuperPeerID.length(), userNameLoginString.length(), userNameLoginString, createdate, friend, groupdSuperPeerID, textPost);

            }
        } else {
            JOptionPane.showMessageDialog(this, "This status update appears to be blank.\n\n Please write something to update your status!", "Status Is Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // send post to other one via Network and save into database
    public static void writePostPublic(String userID, byte prpl, int like, int comment, int cDateLength, int groupdFriendIDLength, int groupdSuperPeerIDLength, int useNameLength, String userNamePost, String cDate, String idGroupFriends, String idGroupSP, String post) {
        Post postMessage = new Post(userID, cDateLength, groupdFriendIDLength, groupdSuperPeerIDLength, useNameLength, userNamePost, cDate, idGroupFriends, idGroupSP, post);
        System.out.println("POST MESSAGEID : " + postMessage.getMessageID());
        System.out.println("POST MESSAGE GET CONTENT BYTE: " + postMessage.contents());
        // show status on news feed of user logging in when they have just written the status
        PostObject postWrite = new PostObject();
        postWrite.setNamePost(postMessage.getUserName());
        postWrite.setPostID(postMessage.getMessageID());
        postWrite.setContentPost(postMessage.getPostStatusContent());
        postWrite.setGroupID(postMessage.getGroupFriendID());
        postWrite.setCreatedDate(postMessage.getCreatedDate());
        postWrite.setUserIDPost(postMessage.getUserID());

        PostHandler.recieveListPost.add(0, postWrite);
        PostHandler.showListPost.add(0, Utils.formSHOWSTATUS(postWrite.getNamePost(), postWrite.getContentPost(), postWrite.getCreatedDate()));
        AppGUI.inform(PostHandler.showListPost);

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
        PostObject postWrite = new PostObject();
        postWrite.setNamePost(postMessage.getUserName());
        postWrite.setPostID(postMessage.getMessageID());
        postWrite.setContentPost("<Private> " + postMessage.getPostStatusContent());
        postWrite.setGroupID(postMessage.getGroupFriendID());
        postWrite.setCreatedDate(postMessage.getCreatedDate());
        postWrite.setUserIDPost(postMessage.getUserID());
        PostHandler.recieveListPost.add(0, postWrite);
        PostHandler.showListPost.add(0, Utils.formSHOWSTATUS(postWrite.getNamePost(), postWrite.getContentPost(), postWrite.getCreatedDate()));
        AppGUI.inform(PostHandler.showListPost);

        Preferences.statusWriteToFilePeer(postMessage.getPostTypeString(postMessage.getPayload()), postMessage.getUserID(), postMessage.getUserName(), postMessage.getMessageID(), prPl, liked, commented, createdate, friend, groupdSuperPeerID, "<Private>    " + postText);

    }

//    public static void updateStatusForm(int numLike, int numComment, String userLike, Vector<String> comment) {
//        System.out.println("###### RECEIVE REPOSND");
//        StatusForm.btnLike.setVisible(true);
//        StatusForm.btnComment.setVisible(true);
//        StatusForm.txtComment.setVisible(true);
//        StatusForm.lbLike.setText(String.valueOf(numLike));
//        StatusForm.lbComment.setText(String.valueOf(numComment));
//        if (!comment.isEmpty()) {
//            StatusForm.listComment.setListData(comment);
//        }
//        StatusForm.lbLoading.setVisible(false);
//    }
    public static void updateNotification(int numLikeComment, String userNameLike, String userNameComment) {
        numLikeCommented += numLikeComment;
        if (userNameLike != "") {
            userNameLiked = userNameLike;
        }
        if (userNameComment != "") {
            userNameCommented = userNameComment;
        }
        btnNoti.setText("<" + numLikeCommented + "> Notification");
    }

    public String showDialogNotification(String userNameLike, String userNameComment) {
        String like = "";
        String comment = "";
        if (userNameLike != "") {
            like = "Like: " + userNameLike + " liked your status!";
        }
        if (userNameComment != "") {
            comment = "Comment: " + userNameComment + " commented on your status!";
        }
        return like + "\n\n" + comment;
    }
    //////////// END POST MESSAGE
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btnFeed;
    public static javax.swing.JButton btnFriendsGroup;
    private static javax.swing.JButton btnNoti;
    private static javax.swing.JButton btnPost;
    private static javax.swing.JButton btnProfile;
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
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
