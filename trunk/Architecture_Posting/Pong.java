package Architecture_Posting;

import static Architecture_Posting.Packet.HEADER_LENGTH;
import java.nio.*;

public class Pong extends Packet {

    IPAddress ip;
    private int index = HEADER_LENGTH;

    public Pong(int port, IPAddress ip, String userID, int userNameLength, int listFileIDStoreLength, String listFileIDStore, String userNameOnline, long messageid) {
        super(Packet.PONG, (28 + listFileIDStore.length() + userNameOnline.length()));
//        int index = HEADER_LENGTH;

        ByteBuffer bBMessageid = ByteBuffer.allocate(16);
        bBMessageid.putLong(messageid);
        byte[] byteMessageid = bBMessageid.array();
        for (int i = 0; i < 16; i++) {
            contents[i] = byteMessageid[i];
        }


        // convert port to two bytes
//        System.out.println("\nPONG: port before - " + port);
        ByteBuffer bBPort = ByteBuffer.allocate(2);
        bBPort.putShort((short) port);
        byte[] bytePort = bBPort.array();
        contents[index + 0] = bytePort[0];
        contents[index + 1] = bytePort[1];

        // convert ip address to 4 bytes; need to check format of ip
        // address -- Little Endian????
        contents[index + 2] = (byte) ip.getFirst();
        contents[index + 3] = (byte) ip.getSecond();
        contents[index + 4] = (byte) ip.getThird();
        contents[index + 5] = (byte) ip.getFourth();

        // convert userID to byte 
        byte[] tempUserID = new byte[userID.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        tempUserID = userID.getBytes();
        int q;
        for (q = 0; q < userID.length(); q++) {
            contents[index + 6 + q] = tempUserID[q];
        }

        //conver fromt short to byte arr
        ByteBuffer dbuf = ByteBuffer.allocate(2);
        dbuf.putShort((short) userNameLength);
        byte[] bytes = dbuf.array();
        contents[index + 6 + userID.length() + 0] = bytes[0];
        contents[index + 6 + userID.length() + 1] = bytes[1];

        ByteBuffer bB = ByteBuffer.allocate(4);
        bB.putInt(listFileIDStoreLength);
        byte[] byteListFileIDStoreLength = bB.array();
        contents[index + 6 + userID.length() + 2] = byteListFileIDStoreLength[0];
        contents[index + 6 + userID.length() + 3] = byteListFileIDStoreLength[1];
        contents[index + 6 + userID.length() + 4] = byteListFileIDStoreLength[2];
        contents[index + 6 + userID.length() + 5] = byteListFileIDStoreLength[3];

        byte[] listFileID = new byte[listFileIDStore.length()];
        listFileID = listFileIDStore.getBytes();
        int i;
        for (i = 0; i < listFileIDStore.length(); i++) {
            contents[(index + i + 28)] = listFileID[i];
        }

        // convert userNameOnline to byte 
        byte[] tempUserName = new byte[userNameOnline.length()];
        tempUserName = userNameOnline.getBytes();
        int j;
        for (j = 0; j < userNameOnline.length(); j++) {
            contents[(index + j + 28 + listFileIDStore.length())] = tempUserName[j];
        }
    }

    public Pong(byte[] rawdata) {
        super(rawdata);
    }

    public void setPongRespondIP(IPAddress ip) { // Ip in connection when receive a pong message
        this.ip = ip;
    }

    public IPAddress getPongRespondIP() {
        return (ip);
    }

    public int getPort() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 0];
        bytes[1] = contents[index + 1];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public IPAddress getIP() {
        return (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort()));
    }

    public String getUserIDOnline() {
        String temp = "";
        for (int i = 0; i < 16; i++) {
            temp = temp + (char) (contents[index + 6 + i]);
        }
        return temp;
    }

    public int getUserNameLength() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 6 + 16];
        bytes[1] = contents[index + 6 + 17];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return (int) wrapped.getShort();
    }

    public int getListFileIDStoreLength() {
        byte[] bytes = new byte[4];
        bytes[0] = contents[index + 6 + 18];
        bytes[1] = contents[index + 6 + 19];
        bytes[2] = contents[index + 6 + 20];
        bytes[3] = contents[index + 6 + 21];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return wrapped.getInt(0);
    }

    public String getListFileIDStore() {
        String list = "";
        for (int i = (index + 28); i < (index + 28 + getListFileIDStoreLength()); i++) {
            list = list + (char) (contents[i]);
        }
        return list;
    }

    public String getUserNameOnline() {
        String userName = "";
        for (int i = (index + 28 + getListFileIDStoreLength()); i < (index + 28 + getListFileIDStoreLength() + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }
        return userName;
    }
}
