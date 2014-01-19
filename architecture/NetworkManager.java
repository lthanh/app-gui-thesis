package architecture;

/*
 Network Manager - started by main
 */

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class NetworkManager {

    public static void writeToOne(IPAddress ip, Packet packet) {
        if (HostArray.isLive(ip)) {
            Connection connection = HostArray.getConnection(ip);
            try {
                connection.getByteWriter().write(packet.contents(), 0, packet.totalLength());
                connection.getByteWriter().flush();
            } catch (IOException e) {
                try {
                    connection.getSocket().close();
                    HostArray.removeConnection(ip);
                } catch (IOException exception) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeToAll(Packet packet) {
        for (int i = 0; i < HostArray.getCount(); i++) {
            Connection c = HostArray.getConnection(i);
            try {
                if (packet.getPayload() == 1) {
                    System.out.println("PACKET PONG CONTENT: Payload - " + packet.getPayload() + " Content: " + packet.contents().toString());
                    System.out.println("PACKET PONG TOTAL LENGTH: " + packet.totalLength());
                    System.out.println("PACKET PONG Content LENGTH: " + packet.contents.length);
//                    for (int j = 0; j < packet.contents.length; j++) {
//                        System.out.println("CONTEN PACKET NETWORK MANAGER [" + j + "]" + packet.contents[j]);
//                    }
                }

                c.getByteWriter().write(packet.contents(), 0, packet.totalLength());
                c.getByteWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    c.getSocket().close();
                    HostArray.removeConnection(c);
                } catch (IOException exception) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeButOne(IPAddress ip, Packet packet) {
        for (int i = 0; i < HostArray.getCount(); i++) {
            Connection c = HostArray.getConnection(i);
            if (!(c.compareConnections(ip))) {
                try {
                    c.getByteWriter().write(packet.contents(), 0, packet.totalLength());
                    c.getByteWriter().flush();
                } catch (IOException e) {
                    try {
                        c.getSocket().close();
                        HostArray.removeConnection(c);
                    } catch (IOException exception) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void notify(IPAddress ip) // Remove socket from open connection list, based on its IP.
    {
        if (HostArray.isLive(ip)) {
            System.out.println("Killing " + ip);
            Connection c = HostArray.getConnection(ip);
            try {
                c.getSocket().close();
                HostArray.removeConnection(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
