package Architecture_Posting;

import GUI.LoginForm;
import SuperPeerAction.NewsFeedHandler;
import SuperPeerAction.ReqRes_LikeCommentHanlder;
import SuperPeerAction.ProfileHandler;
import SuperPeerAction.Request_LikeCmt;
import SuperPeerAction.Request_NewsFeed;
import SuperPeerAction.Request_Profile;
import SuperPeerAction.Respond_LikeCmt;
import SuperPeerAction.Respond_NewsFeed;
import SuperPeerAction.Respond_Profile;
import PostingService.PostHandler;
import PostingService.Post;
import java.io.*;
import PostingService.Comment;
import PostingService.Like;
import PostingService.LikeCommentHandler;

class Server extends Thread {

    BufferedInputStream in;
    IPAddress mine;
    Utils utils = new Utils();
    String useIDLogin = LoginForm.currentUser.getIdUserLogin();
    String userNameLogin = LoginForm.currentUser.getUserName();
    boolean isServer = utils.checkServerName(userNameLogin);

    public Server(Connection c) {
        in = c.getByteReader();
        mine = c.getIPAddress(); // IP of connection request
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
                        && (header.identify() != Packet.POST && (header.identify() != Packet.LIKE)) && (header.identify() != Packet.COMMENT) && (header.identify() != Packet.REQ_LIKECOMMENT) && (header.identify() != Packet.RES_LIKECOMMENT) && (header.identify() != Packet.REQ_PROFILE) && (header.identify() != Packet.RES_PROFILE) && (header.identify() != Packet.REQ_NewsFeed) && (header.identify() != Packet.RES_NewsFeed)) {
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
                    handler.start();
                    continue;
                }

                if (header.identify() == Packet.PONG) {
                    Pong pong = new Pong(newpacket);
//                    Host h = new Host(mine.toString(), mine.getPort());
//                    HostCache.addHost(h);
                    PongHandler handler = new PongHandler(mine, pong);
                    handler.start();
                    continue;
                } else if (header.identify() == Packet.POST) {
                    Post postMessage = new Post(newpacket);
                    PostHandler handler = new PostHandler(mine, postMessage);
                    handler.start();
                    continue;
                } else if (header.identify() == Packet.LIKE) {
                    Like likeMessage = new Like(newpacket);
                    LikeCommentHandler likeAction = new LikeCommentHandler(mine, likeMessage, null);
                    likeAction.start();
                    continue;
                } else if (header.identify() == Packet.COMMENT) {
                    Comment commentMessage = new Comment(newpacket);
                    LikeCommentHandler commentAction = new LikeCommentHandler(mine, null, commentMessage);
                    commentAction.start();
                    continue;
                } else if (header.identify() == Packet.REQ_LIKECOMMENT) {
                    Request_LikeCmt requestMessage = new Request_LikeCmt(newpacket);
                    if (isServer) {
                        ReqRes_LikeCommentHanlder requestMessageAction = new ReqRes_LikeCommentHanlder(mine, requestMessage, null);
                        requestMessageAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_LIKECOMMENT) {
                    Respond_LikeCmt respondMessage = new Respond_LikeCmt(newpacket);
                    ReqRes_LikeCommentHanlder respondMessageAction = new ReqRes_LikeCommentHanlder(mine, null, respondMessage);
                    respondMessageAction.start();
                    continue;
                } else if (header.identify() == Packet.REQ_PROFILE) {
                    Request_Profile requestMessage = new Request_Profile(newpacket);
                    if (isServer) {
                        ProfileHandler requestProfileAction = new ProfileHandler(mine, requestMessage, null);
                        requestProfileAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_PROFILE) {
                    Respond_Profile respondMessage = new Respond_Profile(newpacket);
                    ProfileHandler respondProfileAction = new ProfileHandler(mine, null, respondMessage);
                    respondProfileAction.start();
                    continue;
                } else if (header.identify() == Packet.REQ_NewsFeed) {
                    Request_NewsFeed requestMessage = new Request_NewsFeed(newpacket);
                    if (isServer) {
                        NewsFeedHandler requestProfileAction = new NewsFeedHandler(mine, requestMessage, null);
                        requestProfileAction.start();
                        continue;
                    }
                } else if (header.identify() == Packet.RES_NewsFeed) {
                    Respond_NewsFeed respondMessage = new Respond_NewsFeed(newpacket);
                    NewsFeedHandler respondProfileAction = new NewsFeedHandler(mine, null, respondMessage);
                    respondProfileAction.start();
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
