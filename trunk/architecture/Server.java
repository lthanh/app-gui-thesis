package architecture;

import GUI.AppGUI;
import GUI.LoginForm;
import PeerAction.peerReceivePost;
import SuperPeerAction.NewsFeedHandler;
import SuperPeerAction.PostObject;
import SuperPeerAction.ReqResLikeCmtHanlder;
import SuperPeerAction.ReqRes_ProfileHandler;
import SuperPeerAction.Request_LikeCmt;
import SuperPeerAction.Request_NewsFeed;
import SuperPeerAction.Request_Profile;
import SuperPeerAction.Respond_LikeCmt;
import SuperPeerAction.Respond_NewsFeed;
import SuperPeerAction.Respond_Profile;
import postService.PostHandler;
import postService.Post;
import java.io.*;
import java.net.*;
import postService.Comment;
import postService.Like;
import postService.LikeCommentHandler;
import static postService.PostHandler.checkGroupFriendIDPost;
import static postService.PostHandler.serverCheckListFriendorPeer;

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
                        && (header.identify() != Packet.QUERY) && (header.identify() != Packet.QUERYHIT) && (header.identify() != Packet.POST && (header.identify() != Packet.LIKE)) && (header.identify() != Packet.COMMENT) && (header.identify() != Packet.REQ_LIKECOMMENT) && (header.identify() != Packet.RES_LIKECOMMENT) && (header.identify() != Packet.REQ_PROFILE) && (header.identify() != Packet.RES_PROFILE) && (header.identify() != Packet.REQ_NewsFeed) && (header.identify() != Packet.RES_NewsFeed)) {
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
                        String listFriendID = postMessage.getGroupFriendID();
                        String[] tempListFriendID = listFriendID.split("~~");

                        //boolean isFriends = serverCheckListFriendorPeer(postMessage, Preferences.idFriendsListString);
                        boolean isFriends = checkGroupFriendIDPost(LoginForm.currentUser.getIdUserLogin(), tempListFriendID);
                        if (isFriends) { // if friend,show on news feed.
                            PostObject post = new PostObject();
                            post.setNamePost(postMessage.getUserName());
                            post.setPostID(postMessage.getMessageID());
                            post.setContentPost(postMessage.getPostStatusContent());
                            post.setGroupID(postMessage.getGroupFriendID());
                            post.setCreatedDate(postMessage.getCreatedDate());
                            post.setUserIDPost(postMessage.getUserID());
                            (new peerReceivePost()).receivePost(post);
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
                    } else {
                        AppGUI.updateNotification(1, likeMessage.getNameLike(), "");

                    }
                } else if (header.identify() == Packet.COMMENT) {
                    Comment commentMessage = new Comment(newpacket);
                    System.out.println("\n ### Server : Packet == COMMENT -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {
                        LikeCommentHandler commentAction = new LikeCommentHandler(mine, null, commentMessage);
                        commentAction.start();
                        continue;
                    } else {
                        AppGUI.updateNotification(1, "", commentMessage.getNameComment());

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
//                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {

                    ReqResLikeCmtHanlder respondMessageAction = new ReqResLikeCmtHanlder(null, respondMessage);
                    respondMessageAction.start();
                    continue;
//                    }
                } else if (header.identify() == Packet.REQ_PROFILE) {
                    Request_Profile requestMessage = new Request_Profile(newpacket);
                    System.out.println("\n ### Server : Packet == REQ_PROFILE -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {

                        ReqRes_ProfileHandler requestProfileAction = new ReqRes_ProfileHandler(requestMessage, null);
                        requestProfileAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_PROFILE) {
                    Respond_Profile respondMessage = new Respond_Profile(newpacket);
                    System.out.println("\n ### Server : Packet == RES_PROFILE -- " + newpacket.toString());

                    ReqRes_ProfileHandler respondProfileAction = new ReqRes_ProfileHandler(null, respondMessage);
                    respondProfileAction.start();
                    continue;
                } else if (header.identify() == Packet.REQ_NewsFeed) {
                    Request_NewsFeed requestMessage = new Request_NewsFeed(newpacket);
                    System.out.println("\n ### Server : Packet == REQ_NewsFeed -- " + newpacket.toString());
                    if (LoginForm.currentUser.getUserName().equals("Server") || LoginForm.currentUser.getUserName().equals("Server1")) {

                        NewsFeedHandler requestProfileAction = new NewsFeedHandler(requestMessage, null);
                        requestProfileAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_NewsFeed) {
                    Respond_NewsFeed respondMessage = new Respond_NewsFeed(newpacket);
                    System.out.println("\n ### Server : Packet == RES_NewsFeed -- " + newpacket.toString());

                    NewsFeedHandler respondProfileAction = new NewsFeedHandler(null, respondMessage);
                    respondProfileAction.start();
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
