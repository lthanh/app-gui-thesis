/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class Post extends Packet{
    private IPAddress ip;

    public Post(int speed, String post) {
        super(Packet.POST, (3 + (post.length())));
        contents[24] = (byte) (speed >>> 8); // Convert minimum speed to 2 bytes
        contents[23] = (byte) (speed & 0xff);

        byte[] temp = new byte[post.length()];
        temp = post.getBytes();

        int i;
        for (i = 0; i < post.length(); i++) {
            contents[(i + 25)] = temp[i];
        }
        contents[(i + 25)] = 0; // Search strings should be 0-terminated.
        ip = null;  //initialize IPaddress to null.
    }

    public Post(byte[] rawdata) {
        super(rawdata);
    }

    public IPAddress getIP() {
        return (ip);
    }

    public int getSpeed() {
        return (((contents[24] & 0xff) << 8) | (contents[23] & 0xff));
    }

    public String getSearchString() {
        String answer = "";
        for (int i = 25; contents[i] != 0; i++) {
            answer = answer + (char) (contents[i]);
        }
        return (answer);
    }

    public void setIP(IPAddress ip) {
        this.ip = ip;
    }
}
