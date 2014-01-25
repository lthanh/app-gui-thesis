package GUI;

import static GUI.AppGUI.statusPOPUP;
import SuperPeerAction.ReqResLikeCmtHanlder;
import SuperPeerAction.SaveLikeCmtAction;
import architecture.NetworkManager;
import architecture.Preferences;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import postService.Comment;
import postService.Like;
import postService.LikeCommentListObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class StatusForm extends javax.swing.JFrame {

    public static Vector<String> listStoreComment = new Vector<String>();
    public static String useIDLogin = LoginForm.currentUser.getIdUserLogin();
//    public byte[] userIDLOGINBYTE = new byte[16];
//    public byte[] postIDBYTE = new byte[16];
//    public byte[] userIDPostBYTE = new byte[16];
    public static String userNameLoginString = LoginForm.currentUser.getUserName();
    public static String postID = "";
    public static String userIDPost = "";

    public StatusForm(String namePost, String contentPost, int numLike, int comment, Vector<String> tempComment, long postID, String userIDPost) {
        initComponents();
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        txtContentPopUp.disable();

        setTitle(namePost + "'s status");
        lbUseName.setText(namePost);
        txtContentPopUp.setText(contentPost);
        lbLike.setText(String.valueOf(numLike));
        lbComment.setText(String.valueOf(comment));
        listComment.setListData(tempComment);
        lbIDUserPost.hide();
        lbIDUserPost.setText(userIDPost);
        lbMessageID.hide();
        lbMessageID.setText(String.valueOf(postID));
        btnLike.setVisible(false);
        btnComment.setVisible(false);
        txtComment.setVisible(false);

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
        lbLoading = new javax.swing.JLabel();
        txtComment = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 362));
        setResizable(false);

        lbUseName.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbUseName.setForeground(new java.awt.Color(0, 51, 255));
        lbUseName.setText("jLabel1");

        lbLike.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLike.setText("jLabel2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Like");

        btnLike.setText("LIKE");
        btnLike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLikeActionPerformed(evt);
            }
        });

        lbComment.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbComment.setText("jLabel3");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Comment");

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

        lbLoading.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbLoading.setForeground(new java.awt.Color(255, 153, 0));
        lbLoading.setText("Loading ...");

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
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbUseName, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbLoading)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbMessageID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbIDUserPost)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbLike)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addComponent(btnLike, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loading)
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbComment)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(58, 58, 58))))
                    .addComponent(txtComment))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUseName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMessageID)
                    .addComponent(lbIDUserPost)
                    .addComponent(lbLoading))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addComponent(loading))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbLike)
                                .addComponent(jLabel4))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbComment)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnComment)
                    .addComponent(btnLike))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtComment, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLikeActionPerformed
        // TODO add your handling code here:
        if (btnLike.isEnabled()) {
            btnLike.setEnabled(false);
        }

        postID = lbMessageID.getText();
        userIDPost = lbIDUserPost.getText(); //LIKE : postID- [B@75225918

        System.out.println("LIKE : postIDBYTE - " + postID);
        System.out.println("LIKE : userIDPostBYTE - " + userIDPost);
        System.out.println("LIKE : userIDLOGINBYTE - " + useIDLogin);
        System.out.println("LIKE : userNameLoginString - " + userNameLoginString);



        Like likeMessage = new Like(postID.length(), userIDPost.length(), useIDLogin.length(), postID, userIDPost, useIDLogin, userNameLoginString);
        (new SaveLikeCmtAction()).saveLikeSuperPeer(likeMessage);

        System.out.println("######### LIKE before: " + likeMessage);
        System.out.println("######### LIKE getIdPost: " + likeMessage.getIdPost());
        System.out.println("######### LIKE getIdUserLike: " + likeMessage.getIdUserLike());
        System.out.println("######### LIKE getIdUserPost: " + likeMessage.getIdUserPost());
        System.out.println("######### LIKE getNameLike: " + likeMessage.getNameLike());
        System.out.println("######### LIKE getLikeTypeString: " + likeMessage.getLikeTypeString(likeMessage.getPayload()));


        NetworkManager.writeToAll(likeMessage);




    }//GEN-LAST:event_btnLikeActionPerformed

    private void btnCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommentActionPerformed
        // TODO add your handling code here:
        txtComment.requestFocus();
    }//GEN-LAST:event_btnCommentActionPerformed

    private void txtCommentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommentKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            postID = lbMessageID.getText();
            userIDPost = lbIDUserPost.getText();

            System.out.println("COMMENT : postIDBYTE - " + postID);
            System.out.println("COMMENT : userIDPostBYTE - " + userIDPost);
            System.out.println("COMMENT : userIDLOGINBYTE - " + useIDLogin);
            System.out.println("COMMENT : userNameLoginString - " + userNameLoginString);

            Comment commentMessage = new Comment(postID.length(), userIDPost.length(), useIDLogin.length(), userNameLoginString.length(), postID, userIDPost, useIDLogin, userNameLoginString, txtComment.getText());
            (new SaveLikeCmtAction()).saveCommentSuperPeer(commentMessage);
            String commentCurrently = "< " + commentMessage.getNameComment() + " >:  " + commentMessage.getComment();
            ReqResLikeCmtHanlder.comment.add(0, commentCurrently);
            listComment.setListData(ReqResLikeCmtHanlder.comment);
            //                         comment = comment + "< " + itemLine[5].substring(17) + " >:  " + itemLine[6].substring(20) + "\n\n";


            System.out.println("######### COMMENT before: " + commentMessage);
            System.out.println("######### COMMENT getIdPost: " + commentMessage.getIdPost());
            System.out.println("######### COMMENT getIdUserLike: " + commentMessage.getIdUserComment());
            System.out.println("######### COMMENT getIdUserPost: " + commentMessage.getIdUserPost());
            System.out.println("######### COMMENT getNameLike: " + commentMessage.getNameComment());
            System.out.println("######### COMMENT getLikeTypeString: " + commentMessage.getCommentTypeString(commentMessage.getPayload()));

            NetworkManager.writeToAll(commentMessage);
            txtComment.setText("");
        }
    }//GEN-LAST:event_txtCommentKeyPressed

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
//                StatusForm s = new StatusForm(String namePost, String contentPost, int numLike, int comment, Vector<String> tempComment, long postID, String userIDPost);
//                s.setVisible(true);
            }
        });


    }

    public void updateStatusForm(int numLike, int numComment, String userLike, Vector<String> comment) {

        System.out.println("###### RECEIVE REPOSND");
        btnLike.setVisible(true);
        btnComment.setVisible(true);
        txtComment.setVisible(true);
        txtComment.repaint();
        lbLike.setText(String.valueOf(numLike));
        lbComment.setText(String.valueOf(numComment));
        if (!comment.isEmpty()) {
            listComment.setListData(comment);
        }
        lbLoading.setVisible(false);
        invalidate();
        revalidate();
        repaint();

//        invalidate();
//        validate();
//        repaint();


        System.out.println("###### RECEIVE After");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComment;
    private javax.swing.JButton btnLike;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbComment;
    public static javax.swing.JLabel lbIDUserPost;
    private javax.swing.JLabel lbLike;
    private javax.swing.JLabel lbLoading;
    public static javax.swing.JLabel lbMessageID;
    public static javax.swing.JLabel lbUseName;
    private javax.swing.JList listComment;
    private javax.swing.JLabel loading;
    private javax.swing.JTextField txtComment;
    public static javax.swing.JTextPane txtContentPopUp;
    // End of variables declaration//GEN-END:variables
}
