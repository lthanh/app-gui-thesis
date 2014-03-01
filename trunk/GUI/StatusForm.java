package GUI;

import SuperPeerAction.ReqRes_LikeCommentHanlder;
import SuperPeerAction.SaveLikeCmtAction;
import Architecture_Posting.NetworkManager;
import Architecture_Posting.Utils;
import static GUI.AppGUI.loadingForm;
import static GUI.AppGUI.startShowLoading;
import static GUI.AppGUI.statusPOPUP;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.JOptionPane;
import PostingService.Comment;
import PostingService.Like;
import PostingService.LikeCommentHandler;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thanh Le Quoc
 */
public class StatusForm extends javax.swing.JFrame {

    public static Vector<String> listStoreComment = new Vector<String>();
    public static String useIDLogin = LoginForm.currentUser.getIdUserLogin();
    public static String userNameLoginString = LoginForm.currentUser.getUserName();
    public static String postID = "";
    public static String userIDPost = "";
    private static String listUserNameLiked = "";
    Utils utils = new Utils();
    public SaveLikeCmtAction save = new SaveLikeCmtAction();

    public StatusForm(String namePost, String contentPost, int numLike, int comment, Vector<String> tempComment, long postID, String userIDPost) {
        initComponents();
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AppGUI.loadingForm.hide();
                startShowLoading = -1;
                AppGUI.isShowingStatusPopUp = false;
            }
        });
        AppGUI.isShowingStatusPopUp = true;
        txtContentPopUp.setEditable(false);
        setTitle(namePost + "'s status");
        lbUseName.setText(namePost);
        txtContentPopUp.setText(contentPost);
        lbLike.setText(String.valueOf(numLike));
        lbComment.setText(String.valueOf(comment));
        lbIDUserPost.hide();
        lbIDUserPost.setText(userIDPost);
        lbMessageID.hide();
        lbMessageID.setText(String.valueOf(postID));
        btnLike.setEnabled(false);
        btnComment.setEnabled(false);
        txtComment.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbUseName = new javax.swing.JLabel();
        lbLike = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLike = new javax.swing.JButton();
        lbComment = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnComment = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listComment = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtContentPopUp = new javax.swing.JTextPane();
        lbMessageID = new javax.swing.JLabel();
        lbIDUserPost = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();
        txtComment = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(439, 426));
        setResizable(false);

        lbUseName.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbUseName.setForeground(new java.awt.Color(0, 51, 255));
        lbUseName.setText("jLabel1");

        lbLike.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLike.setText("jLabel2");
        lbLike.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLikeMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Like");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        btnLike.setText("LIKE");
        btnLike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLikeActionPerformed(evt);
            }
        });

        lbComment.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbComment.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbComment.setText("jLabel3");
        lbComment.setAlignmentY(0.0F);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Comment");
        jLabel5.setAlignmentY(0.0F);

        btnComment.setText("COMMENT");
        btnComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCommentActionPerformed(evt);
            }
        });

        listComment.setBackground(new java.awt.Color(240, 240, 240));
        listComment.setFixedCellHeight(25);
        jScrollPane3.setViewportView(listComment);

        jScrollPane1.setViewportView(txtContentPopUp);

        txtComment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCommentKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbUseName, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(lbMessageID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbIDUserPost)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLike, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbLike)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(9, 9, 9)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(loading))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(btnComment, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(lbComment)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComment, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUseName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMessageID)
                    .addComponent(lbIDUserPost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbLike)
                    .addComponent(lbComment)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLike)
                    .addComponent(btnComment))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtComment, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLikeActionPerformed
        String postShow = "";
        postID = lbMessageID.getText();
        userIDPost = lbIDUserPost.getText(); //LIKE : postID- [B@75225918
        String post = txtContentPopUp.getText();
        if (post.length() > 30) {
            postShow = post.substring(0, 30) + " ...";
        } else {
            postShow = post + " ...";
        }

        Like likeMessage = new Like(postID.length(), userIDPost.length(), useIDLogin.length(), userNameLoginString.length(), postID, userIDPost, useIDLogin, userNameLoginString, postShow);

        if (utils.checkServerConnectedInGUI()) { // check list server connecting,if at least one super peer exist, then the post message will be send, and vice versa.
            NetworkManager.writeToAll(likeMessage);
            LikeCommentHandler.likeTabble.put(likeMessage.getMessageID(), likeMessage);

            if (btnLike.isEnabled()) {
                btnLike.setEnabled(false);
            }
            int like = Integer.parseInt(lbLike.getText());
            lbLike.setText(String.valueOf(like + 1));
            save.saveLikeSuperPeer(likeMessage);
        } else {
            JOptionPane.showMessageDialog(this, "You are not connecting to any Super Peer !\n\n Please wait for few seconds to connect to a Super Peer\n and re-send another Like ...", "Posting Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnLikeActionPerformed

    private void btnCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommentActionPerformed
        txtComment.requestFocus();
    }//GEN-LAST:event_btnCommentActionPerformed

    private void txtCommentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommentKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            postID = lbMessageID.getText();
            userIDPost = lbIDUserPost.getText();
            String postShow = "";
            String post = txtContentPopUp.getText();
            if (post.length() > 30) {
                postShow = post.substring(0, 30) + " ...";
            } else {
                postShow = post + " ...";
            }

            Comment commentMessage = new Comment(postID.length(), userIDPost.length(), useIDLogin.length(), userNameLoginString.length(), txtComment.getText().length(), postID, userIDPost, useIDLogin, userNameLoginString, txtComment.getText(), postShow);

            if (utils.checkServerConnectedInGUI()) { // check list server connecting,if at least one super peer exist, then the post message will be send, and vice versa.
                NetworkManager.writeToAll(commentMessage);
                LikeCommentHandler.commentTable.put(commentMessage.getMessageID(), commentMessage);

                int comment = Integer.parseInt(lbComment.getText());
                lbComment.setText(String.valueOf(comment + 1));
                save.saveCommentSuperPeer(commentMessage);
                String commentCurrently = "< " + commentMessage.getNameComment() + " >:  " + commentMessage.getComment();
                ReqRes_LikeCommentHanlder.comment.add(0, commentCurrently);
                listComment.setListData(ReqRes_LikeCommentHanlder.comment);
                txtComment.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "You are not connecting to any Super Peer !\n\n Please wait for few seconds to connect to a Super Peer\n and re-send another Comment ...", "Posting Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtCommentKeyPressed

    private void lbLikeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLikeMouseClicked
        int like = Integer.parseInt(lbLike.getText());
        if (like != 0) {
            JOptionPane.showMessageDialog(this, listUserNameLiked, "List of users liked your status!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No one like your status yet!", "List of users liked your status!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_lbLikeMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
    }//GEN-LAST:event_jLabel4MouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(StatusForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatusForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatusForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatusForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    public void updateStatusForm(int numLike, int numComment, String userNameLike, Vector<String> comment) {
        int count = 0;
        long timeout = 2000;
        while (true) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            count++;
            if (count == 2) {
                String[] userNameLIKED = userNameLike.split("\n\n");
                for (int i = 0; i < userNameLIKED.length; i++) {
                    if (!userNameLIKED[i].equals("")) {
                        listUserNameLiked += userNameLIKED[i] + "\n";
                    }
                }

                boolean isLiked = checkLiked(userNameLIKED, userNameLoginString);
                if (isLiked == false) {
                    btnLike.setEnabled(true);
                }
                btnComment.setEnabled(true);
                txtComment.setEnabled(true);
                lbLike.setText(String.valueOf(numLike));
                lbComment.setText(String.valueOf(numComment));
                if (comment.size() > 0) {
                    listComment.setListData(comment);
                }

//                revalidate();
//                repaint();

//                statusPOPUP.validate();
//                statusPOPUP.repaint();

//                pack(); // consider ignore or not
//                statusPOPUP.setVisible(true);

//                statusPOPUP.invalidate();
//                statusPOPUP.validate();
//               
                statusPOPUP.revalidate();
                statusPOPUP.repaint();

//                statusPOPUP.invalidate();
//                statusPOPUP.validate();
//                statusPOPUP.repaint();

                loadingForm.hide();
                startShowLoading = -1;
                break;
            }
        }
    }

    public boolean checkLiked(String[] userNameLike, String userNameLogin) {
        for (int i = 0; i < userNameLike.length; i++) {
            if (userNameLike[i].equals(userNameLogin)) {
                return true;
            }
        }
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnComment;
    public static javax.swing.JButton btnLike;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JLabel lbComment;
    public static javax.swing.JLabel lbIDUserPost;
    public static javax.swing.JLabel lbLike;
    public static javax.swing.JLabel lbMessageID;
    public static javax.swing.JLabel lbUseName;
    public static javax.swing.JList listComment;
    private javax.swing.JLabel loading;
    public static javax.swing.JTextField txtComment;
    public static javax.swing.JTextPane txtContentPopUp;
    // End of variables declaration//GEN-END:variables
}
