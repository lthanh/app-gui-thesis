/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Architecture_Posting.Friends;
import Architecture_Posting.Preferences;
import static Architecture_Posting.Preferences.friendList;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Thanh Le Quoc
 */
public class FriendsGroupForm extends JFrame {

    Map tempList = new Hashtable();
    Vector<Friends> tempListFriend = AppGUI.tempListFriend;

    public FriendsGroupForm() {
        JFrame f = new JFrame();
        AppGUI.isShowingFriendsGroupPopUp = true; // check whether pop up is showing
        f.setLayout(new FlowLayout());
        f.setSize(300, 440);
        f.setTitle("Create a friends group");
        f.setMaximumSize(new Dimension(300, 440));
        f.setMaximumSize(new Dimension(300, 440));
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (int i = 0; i < Preferences.friendList.size(); i++) { // use friendList to keep list user as original listFriendPeer.
                    String userName = Preferences.friendList.get(i).getUserName();
                    if (tempList.containsKey(userName)) {
                        Preferences.friendList.get(i).setCheckFriendsGroup(tempList.get(userName).toString());
                    }
                }
                AppGUI.isShowingFriendsGroupPopUp = false;
                AppGUI.btnFriendsGroup.setEnabled(true);
                Preferences.writeToFriendsPeer(friendList);
            }
        });

        for (int i = 0; i < tempListFriend.size(); i++) {
            tempList.put(tempListFriend.get(i).getUserName(), tempListFriend.get(i).getCheckFriendsGroup());
        }

        ActionListener actionPrinter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selected = e.getActionCommand();
                    if (tempList.get(selected).equals(Preferences.CHECKED)) {
                        tempList.put(selected, Preferences.UNCHECKED);
                    } else {
                        tempList.put(selected, Preferences.CHECKED);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Box box = Box.createVerticalBox();
        JCheckBox e;
        for (int i = 0; i < tempListFriend.size(); i++) {
            e = new JCheckBox(tempListFriend.get(i).getUserName());
            e.addActionListener(actionPrinter);
            if (tempListFriend.get(i).getCheckFriendsGroup().equals(Preferences.CHECKED)) {
                e.setSelected(true);
            } else {
                e.setSelected(false);
            }
            box.add(e);
        }
        JScrollPane jscrlpBox = new JScrollPane(box);
        jscrlpBox.setPreferredSize(new Dimension(280, 400));
        f.add(jscrlpBox);
        f.setVisible(true);
    }

    public static void main(String args[]) {
        FriendsGroupForm e = new FriendsGroupForm();
    }
}
