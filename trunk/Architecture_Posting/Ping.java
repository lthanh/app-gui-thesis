package Architecture_Posting;

import GUI.AppGUI;

public class Ping extends Packet {

    private IPAddress ip;

    /**
     * need to store ip address in order to properly route matching pong
     */
    public Ping() {
        super(Packet.PING, 0);
//        AppGUI.numMessageSent++; // count number of message sent at one section to set message id

    }

    public Ping(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getIP() {
        return (ip);
    }

    public void setIP(IPAddress ip) {
        this.ip = ip;
    }
}
