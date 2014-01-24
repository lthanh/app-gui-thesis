package architecture;

import GUI.LoginForm;
import PeerAction.peerReceivePost;
import SuperPeerAction.ReqResLikeCmtHanlder;
import SuperPeerAction.Request_LikeCmt;
import SuperPeerAction.Respond_LikeCmt;
import postService.PostHandler;
import postService.Post;
import java.io.*;
import java.net.*;
import postService.Comment;
import postService.Like;
import postService.LikeCommentHandler;

class Server extends Thread {

    BufferedInputStream in;
    IPAddress mine;

    public Server(Connection c) {
        in = c.getByteReader();
        mine = c.getIPAddress();
    }

    public void run() {
        while (true) {
            try {
                if (in.available() < Packet.HEADER_LENGTH) {
                    continue;
                }
                byte[] temp = new byte[Packet.HEADER_LENGTH];

                for (int i = 0; i < Packet.HEADER_LENGTH; i++) {
                    temp[i] = (byte) in.read();
                }

                Packet header = new Packet(temp);

                if ((header.identify() != Packet.PONG) && (header.identify() != Packet.PING)
                        && (header.identify() != Packet.QUERY) && (header.identify() != Packet.QUERYHIT) && (header.identify() != Packet.POST && (header.identify() != Packet.LIKE)) && (header.identify() != Packet.COMMENT) && (header.identify() != Packet.REQ_LIKECOMMENT) && (header.identify() != Packet.RES_LIKECOMMENT)) {
                    break; // If the data is not something we expect, die.
                }
                byte[] newpacket = new byte[(header.length() + Packet.HEADER_LENGTH)]; /* The syntax here is unfortunate, because headers don't store
                 their own size. */
                header.decrementTtl();
                header.incrementHops();


                for (int i = 0; i < Packet.HEADER_LENGTH; i++) // First fill the packet with the header.
                {
                    newpacket[i] = temp[i];
                }
                for (int i = Packet.HEADER_LENGTH; i < (header.length() + Packet.HEADER_LENGTH); i++) // Then fill the rest.
                {
                    newpacket[i] = (byte) in.read();
                }

                if (header.getTtl() < 0) // Kill old packets (but only after we've removed them from the input stream).
                {
                    continue;
                }

                if (header.identify() == Packet.PING) // We don't have to do any packet construction if all we've got is a Ping.
                {
                    Ping ping = new Ping(newpacket);
                    PingHandler handler = new PingHandler(mine, ping);
                    //   System.out.println("\n ### Server : Packet == PING -- " + newpacket.toString());

                    // Cat chuoi IP byte ra dang decimal *****************************************************
                    byte[] test = new byte[newpacket.length];

                    for (int i = 0; i < newpacket.length; i++) {
                        test[i] = newpacket[i];
                    }
                    int[] ipints = new int[23];
                    for (int i = 0; i < newpacket.length; i++) {
                        ipints[i] = ((int) (test[i]) & 0xff);
                        //      System.out.print("\n ### Server : Packet == PING -- " + i + ":" + +ipints[i]);
                    }
                    // end cat chuoi
                    handler.start();
                    continue;
                }

                if (header.identify() == Packet.PONG) {
                    Pong pong = new Pong(newpacket);

//                    System.out.println("\n ### Server : Packet == PONG -- " + newpacket.toString());
//                    System.out.println("\n ### Server : Packet == PONG Length newpacket-- " + newpacket.length);

                    Host h = new Host(mine.toString(), mine.getPort());
                    HostCache.addHost(h);
                    PongHandler handler = new PongHandler(mine, pong);
                    handler.start();
                    //   Pinger.inform(pong);
                    continue;
                } else if (header.identify() == Packet.QUERY) {
                    Query query = new Query(newpacket);
                    //   System.out.println("\n ### Server : Packet == QUERY -- " + newpacket.toString());
                    QHandler handler = new QHandler(mine, query);
                    handler.start();
                    continue;
                } else if (header.identify() == Packet.POST) {
                    Post postMessage = new Post(newpacket);
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                        // forward post message to all connecting
                        PostHandler handler = new PostHandler(mine, postMessage);
                        System.out.println("\n ### Server : Packet == POST -- " + newpacket.toString());

                        handler.start();
                        continue;
                    } else {
                        boolean isPeerFriends = PostHandler.serverCheckListFriendorPeer(postMessage, Preferences.idFriendsListString);
                        if (isPeerFriends) {
                            peerReceivePost peerReceive = new peerReceivePost();
                            peerReceive.receivePost(postMessage);
                        }
                        continue;
                    }

                } else if (header.identify() == Packet.LIKE) {
                    Like likeMessage = new Like(newpacket);
                    System.out.println("\n ### Server : Packet == LIKE -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                        LikeCommentHandler likeAction = new LikeCommentHandler(mine, likeMessage, null);
                        likeAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.COMMENT) {
                    Comment commentMessage = new Comment(newpacket);
                    System.out.println("\n ### Server : Packet == COMMENT -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                        LikeCommentHandler commentAction = new LikeCommentHandler(mine, null, commentMessage);
                        commentAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.REQ_LIKECOMMENT) {
                    Request_LikeCmt requestMessage = new Request_LikeCmt(newpacket);
                    System.out.println("\n ### Server : Packet == REQ_LIKECOMMENT -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                        ReqResLikeCmtHanlder requestMessageAction = new ReqResLikeCmtHanlder(requestMessage, null);
                        requestMessageAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_LIKECOMMENT) {
                    Respond_LikeCmt respondMessage = new Respond_LikeCmt(newpacket);
                    System.out.println("\n ### Server : Packet == RES_LIKECOMMENT -- " + newpacket.toString());

                    ReqResLikeCmtHanlder respondMessageAction = new ReqResLikeCmtHanlder(null, respondMessage);
                    respondMessageAction.start();
                    continue;
                } else {
                    QueryHit queryhit = new QueryHit(newpacket);
                    //  System.out.println("\n ### Server : Packet == QUERYHIT -- " + newpacket.toString());
                    QHitHandler handler = new QHitHandler(mine, queryhit);
                    handler.start();
                }
            } catch (Exception e) // If there's a problem, we just die.
            {
                e.printStackTrace();
                break;
            }
        }
        NetworkManager.notify(mine);
    }
}
