
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class HostArray {

    private static Connection[] hosts;
    public static Vector<IPAddress> cacheConnection = new Vector<IPAddress>();  // store list of IP connection
    public static Vector<String> showListServer = new Vector<String>();

    public static boolean isNull() {
        if (hosts == null) {
            return true;
        } else {
            return false;
        }
    }

    public static synchronized int getCount() {
        if (isNull()) {
            return 0;
        } else {
            return hosts.length;
        }
    }

    public static synchronized void addConnection(Connection c) {
        if (isNull()) {
            hosts = new Connection[1];
            hosts[0] = c;
            //////////////////////////////////////////////////////////////// Thanh
            //String showIP = c.getIPAddress().toString() + ":" + c.getIPAddress().getPort() + " - Online";
            //  String showIP = showIPGUI(c.getIPAddress(), c.getTypeString());
            boolean checkConnection = checkConnection(cacheConnection, c.getIPAddress());
            if (checkConnection == true) {
                System.out.println("IIIIIIIIIIIPPPPPPPPPPP: " + showListServer);
                cacheConnection.add(c.getIPAddress());
                showListServer.add(showIPGUI(c.getIPAddress(), c.getTypeString()));
                
            }

            System.out.println("\n\nADD CACHECONNECTION 1: " + cacheConnection.toString() + "SHOWIP: " + showListServer.toString());
            AppGUI.listServer.setListData(showListServer);

        } else if (!isLive(c)) {
            Connection[] temp = new Connection[hosts.length + 1];
            System.arraycopy(hosts, 0, temp, 0, hosts.length);
            temp[hosts.length] = c;
            hosts = temp;
            //////////////////////////////////////////////////////////////// Thanh
            //String showIP = c.getIPAddress().toString() + ":" + c.getIPAddress().getPort() + " - Online";

            //  String showIP = showIPGUI(c.getIPAddress(), c.getTypeString());
            boolean checkConnection = checkConnection(cacheConnection, c.getIPAddress());
            if (checkConnection == true) {
                System.out.println("IIIIIIIIIIIPPPPPPPPPPP: " + showListServer);
                cacheConnection.add(c.getIPAddress());
                showListServer.add(showIPGUI(c.getIPAddress(), c.getTypeString()));
                
            }
            System.out.println("\n\nADD CACHECONNECTION 2: " + cacheConnection.toString() + "SHOWIP: " + showListServer.toString());
            AppGUI.listServer.setListData(showListServer);
        }
    }

    public static synchronized void removeConnection(Connection c) {
        removeConnection(c.getIPAddress());
    }

    public static synchronized void removeConnection(IPAddress ip) {
        if (!(isNull()) && isLive(ip)) {
            Connection[] temp = new Connection[hosts.length - 1];
            int j = 0;
            for (int i = 0; i < hosts.length; i++) {
                if (ip.equals(hosts[i].getIPAddress())) {
                    continue;
                }
                temp[j] = hosts[i];
                j++;
            }
            hosts = temp;
            System.out.println("\n\nADD CACHECONNECTION 3: " + cacheConnection.toString() + "SHOWIP: " + showListServer.toString());

            AppGUI.listServer.setListData(updateRemovedConnection(ip));
        }
    }

    public static synchronized Connection getConnection(int i) {
        if ((!isNull() && (i < getCount()))) {
            return hosts[i];
        } else {
            return null;
        }
    }

    public static synchronized Connection getConnection(IPAddress ip) {
        Connection c = null;
        for (int i = 0; i < hosts.length; i++) {
            if (ip.equals(hosts[i].getIPAddress())) {
                c = hosts[i];
            }
        }
        return c;
    }

    public static synchronized boolean isLive(String ipString) {
        if (!isNull()) {
            for (int i = 0; i < hosts.length; i++) {
                InetAddress inet = hosts[i].getSocket().getInetAddress();
                if ((ipString.equals(inet.getHostName())) || (ipString.equals(inet.getHostAddress()))) {
                    //          System.out.println(ipString + " ?= " + inet.getHostName());
                    //          System.out.println(ipString + " ?= " + inet.getHostAddress());
                    return true;
                }

            }
            return false;
        } else {
            return false;
        }
    }

    public static synchronized boolean isLive(Connection c) {
        return (isLive(c.getIPAddress()));
    }

    public static synchronized boolean isLive(IPAddress ip) {
        for (int i = 0; i < hosts.length; i++) {
            if (ip.equals(hosts[i].getIPAddress())) {
                return true;
            }
        }
        return false;
    }

    public static String updateAddedConnection(Connection c) {
        return showIPGUI(c.getIPAddress(), c.getTypeString());
    }

    public static Vector<String> updateRemovedConnection(IPAddress ip) {
        String removeIP = ip.toString();
        for (int i = 0; i < cacheConnection.size(); i++) {
            if (removeIP.equals(cacheConnection.get(i).toString())) {
                cacheConnection.remove(i);
                showListServer.remove(i);
                System.out.println("\n\nREMOVE CACHECONNECTION : " + cacheConnection.toString() + "SHOWIP: " + showListServer.toString());
            }
        }
        return showListServer;
    }

    public static boolean checkConnection(Vector<IPAddress> listConnect, IPAddress ip) {
        String checkIP = ip.toString();
        for (int i = 0; i < listConnect.size(); i++) {
            if (checkIP.equals(listConnect.get(i).toString())) {
                return false;
            }
        }
        return true;
    }

//    public static Vector<String> removeFriendInList(IPAddress ip) {
//        for (int i = 0; i < checkUserOnlineAction.friendObjectCheckOnline.size(); i++) {
//            if (ip.toString().equals(checkUserOnlineAction.friendObjectCheckOnline.get(i).getIp().toString())) {
//                checkUserOnlineAction.friendObjectCheckOnline.remove(i);
//                showListServer.remove(i);
//                System.out.println("HOST ARRAY - friendObject: " + checkUserOnlineAction.friendObjectCheckOnline.get(i));
//                System.out.println("HOST ARRAY - showListServer: " + showListServer.get(i));
//            }
//        }
//        return showListServer;
//    }

    public static String showIPGUI(IPAddress ip, String type) {
        Vector<String> peer = Preferences.ipSuperPeer;

        for (int i = 0; i < peer.size(); i++) {
            System.out.println("\n HostArray IP PEER:" + peer.get(i));


            if (peer.get(i).contains(ip.toString())) {
                return "Super Peer: " + ip.toString() + ":" + ip.getPort();
            }
        }
        return ip.toString() + ":" + ip.getPort() + type;
    }
}
