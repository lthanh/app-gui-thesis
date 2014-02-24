/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 *
 * @author admin
 */
public class LoadingPopUp {

    public LoadingPopUp() {
    }

    public JFrame loadingForm() {
        JFrame loadingFrame = new JFrame();
        loadingFrame.setLayout(new FlowLayout());
        loadingFrame.setSize(200, 150);
        loadingFrame.setTitle("Loading ...");
        loadingFrame.setMaximumSize(new Dimension(200, 150));
        loadingFrame.setMaximumSize(new Dimension(200, 150));
        loadingFrame.setResizable(false);
        loadingFrame.setLocationRelativeTo(null);
        // loadingFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loadingFrame.setUndecorated(true);
//        loadingFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        loadingFrame.setBackground(new Color(0, 0, 0, 0));

        JPanel panel = new JPanel();
        BoxLayout layoutMgr = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layoutMgr);
        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL imageURL = cldr.getResource("GUI/loading.gif");
        ImageIcon imageIcon = new ImageIcon(imageURL);
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(imageIcon);
        imageIcon.setImageObserver(iconLabel);
        panel.add(iconLabel);
        JLabel label = new JLabel("Loading ...");
        label.setFont(new Font("Serif", Font.BOLD, 18));
        panel.add(label);
        panel.setBackground(new Color(0, 0, 0, 0));
        loadingFrame.add(panel);
        loadingFrame.setAlwaysOnTop(true);
        return loadingFrame;
    }
}
