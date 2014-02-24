package Architecture_Posting;

import GUI.AppGUI;
import static Architecture_Posting.Packet.HEADER_LENGTH;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Pong extends Packet {

    IPAddress ip;
    private int index = HEADER_LENGTH;

    public Pong(int port, IPAddress ip, String userID, int userNameLength, int listFileIDStoreLength, String listFileIDStore, String userNameOnline, long messageid) {
        super(Packet.PONG, (28 + listFileIDStore.length() + userNameOnline.length()));
        int index = HEADER_LENGTH;

//        for (int i = 0; i < messageid.length; i++) // Pongs need the same Message IDs as the pings that generate them.
//        {
//            contents[i] = messageid[i];
//            //   System.out.println("\nPONG: userID send - " + contents[i]);
//
//        }

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
        //System.out.println("bBPort Index: " + (index + 0));
        contents[index + 1] = bytePort[1];
//        System.out.println("bBPort Index: " + (index + 1));
//        System.out.println("\nPONG: port send - " + contents[index + 0]);
//        System.out.println("\nPONG: port send - " + contents[index + 1]);

        // convert ip address to 4 bytes; need to check format of ip
        // address -- Little Endian????
        contents[index + 2] = (byte) ip.getFirst();
        contents[index + 3] = (byte) ip.getSecond();
        contents[index + 4] = (byte) ip.getThird();
        contents[index + 5] = (byte) ip.getFourth();
//        System.out.println("ip.getFirst Index: " + (index + 2));
//        System.out.println("ip.getSecond Index: " + (index + 3));
//        System.out.println("ip.getThird Index: " + (index + 4));
//        System.out.println("ip.getFourth Index: " +( index + 5));

        // convert userID to bytes
//        for (int i = 0; i < userID.length; i++) {
//            contents[index + 6 + i] = userID[i];
//        }

        // convert userID to byte 
        byte[] tempUserID = new byte[userID.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        tempUserID = userID.getBytes();
        int q;
        for (q = 0; q < userID.length(); q++) {
            contents[index + 6 + q] = tempUserID[q];
        }
//        contents[index + 6 + q] = 0;


        //  System.out.println("port after: " +(((contents[index + 1] & 0xff) << 8) | (contents[index + 0] & 0xff)));

        //conver fromt short to byte arr
        ByteBuffer dbuf = ByteBuffer.allocate(2);
        dbuf.putShort((short) userNameLength);
        byte[] bytes = dbuf.array();



        // convert userNameLength to byte array

        contents[index + 6 + userID.length() + 0] = bytes[0];
        contents[index + 6 + userID.length() + 1] = bytes[1];
//        System.out.println("userNameLength Index: " + (index + 6 + 16 + 0));
//        System.out.println("userNameLength Index: " + (index + 6 + 16 + 1));
//        System.out.println("\nPONG: userNameLength send - " + contents[index + 6 + 16]);
//        System.out.println("\nPONG: userNameLength send - " + contents[index + 6 + 17]);



//          System.out.println("listFileIDStoreLength send: " + listFileIDStoreLength);
        ByteBuffer bB = ByteBuffer.allocate(4);
        bB.putInt(listFileIDStoreLength);
        byte[] byteListFileIDStoreLength = bB.array();
//         System.out.println("listFileIDStoreLength length: " + byteListFileIDStoreLength.length);
//        System.out.println("listFileIDStoreLength convert: " + bB.getInt(0));

        contents[index + 6 + userID.length() + 2] = byteListFileIDStoreLength[0];
        contents[index + 6 + userID.length() + 3] = byteListFileIDStoreLength[1];
        contents[index + 6 + userID.length() + 4] = byteListFileIDStoreLength[2];
        contents[index + 6 + userID.length() + 5] = byteListFileIDStoreLength[3];
//        System.out.println("byteListFileIDStoreLength Index: " + (index + 6 + 16 + 2));
//        System.out.println("byteListFileIDStoreLength Index: " + (index + 6 + 16 + 3));
//        System.out.println("byteListFileIDStoreLength Index: " + (index + 6 + 16 + 4));
//        System.out.println("byteListFileIDStoreLength Index: " + (index + 6 + 16 + 5));
//        System.out.println("\nPONG: listFileIDLength send 2 -" + contents[index + userID.length + 6 + 2]);
//        System.out.println("\nPONG: listFileIDLength send 3 -" + contents[index + userID.length + 6 + 3]);
//        System.out.println("\nPONG: listFileIDLength send 4 -" + contents[index + userID.length + 6 + 4]);
//        System.out.println("\nPONG: listFileIDLength send 5 -" + contents[index + userID.length + 6 + 5]);

        byte[] bytesList = new byte[4];
        bytesList[0] = contents[index + 6 + userID.length() + 2];
        bytesList[1] = contents[index + 6 + userID.length() + 3];
        bytesList[2] = contents[index + 6 + userID.length() + 4];
        bytesList[3] = contents[index + 6 + userID.length() + 5];

//        System.out.println("OK getListFileIDStoreLength after: " + ByteBuffer.wrap(bytesList).getInt(0));



        byte[] listFileID = new byte[listFileIDStore.length()];
        listFileID = listFileIDStore.getBytes();
        int i;
        for (i = 0; i < listFileIDStore.length(); i++) {
            contents[(index + i + 28)] = listFileID[i];
//            System.out.println("\nPONG: listFileIDStore send - [" + i + "] " + contents[(index + i + 29)]);
//             System.out.println("listFileIDStore Index: " + (index + i + 28));
        }
//        contents[(index + i + 28)] = 0;

        // convert userNameOnline to byte 
        byte[] tempUserName = new byte[userNameOnline.length()];
        //System.out.println("PONG: userNameOnline send : " + userNameOnline);
        tempUserName = userNameOnline.getBytes();
        int j;
        for (j = 0; j < userNameOnline.length(); j++) {
            contents[(index + j + 28 + listFileIDStore.length())] = tempUserName[j];
//            System.out.println("userNameOnline Index: " + (index + i + 28 + listFileIDStore.length()));
            //  System.out.println("\nPONG: userNameOnline send - [" + j + "] " + contents[(index + j + 28 + listFileIDStore.length())]);
        }
//        contents[(index + j + 28 + listFileIDStore.length())] = 0;

//        AppGUI.numMessageSent++; // count number of message sent at one section to set message id



        //  System.out.println("DECODE #############");

//        byte[] bytesPort = new byte[2];
//        bytesPort[0] = contents[index + 0];
//        System.out.println("DECODE bytesPort Index: " + (index + 0));
//
//        bytesPort[1] = contents[index + 1];
//        System.out.println("DECODE bytesPort Index: " + (index + 1));
//
//        ByteBuffer wrappedPort = ByteBuffer.wrap(bytesPort);
//        System.out.println("getPort after: " + wrappedPort.getShort());
//
//        byte[] bytesName = new byte[2];
//        bytesName[0] = contents[index + 6 + 16];
//        bytesName[1] = contents[index + 6 + 17];
//        System.out.println("DECODE bytesName Index: " + (index + 6 + 16));
//        System.out.println("DECODE bytesName Index: " + (index + 6 + 17));
//
//        ByteBuffer wrapped = ByteBuffer.wrap(bytesName);
//        System.out.println("getUserNameLength after: " + wrapped.getShort());
//
//
//        byte[] bytesList1 = new byte[4];
//        bytesList1[0] = contents[index + 6 + userID.length + 2];
//        bytesList1[1] = contents[index + 6 + userID.length + 3];
//        bytesList1[2] = contents[index + 6 + userID.length + 4];
//        bytesList1[3] = contents[index + 6 + userID.length + 5];
////        System.out.println("DECODE bytesList1 Index: " + (index + 6 + userID.length + 2));
////        System.out.println("DECODE bytesList1 Index: " + (index + 6 + userID.length + 3));
////        System.out.println("DECODE bytesList1 Index: " + (index + 6 + userID.length + 4));
////        System.out.println("DECODE bytesList1 Index: " + (index + 6 + userID.length + 5));
//
//        System.out.println("  getListFileIDStoreLength after: " + ByteBuffer.wrap(bytesList1).getInt(0));



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
//        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 0];
        bytes[1] = contents[index + 1];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getPort after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public IPAddress getIP() {
//        System.out.println("\nPONG: getIP receive - " + (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort())));
        return (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort()));
    }

    public String getUserIDOnline() {
        //   byte[] temp = new byte[16];
        String temp = "";
        for (int i = 0; i < 16; i++) {
            temp = temp + (char) (contents[index + 6 + i]);
        }
        // String userID = new String(temp);
//        System.out.println("\nPONG: getUserIDOnline receive - " + userID);
        return temp;
    }

    public int getUserNameLength() {
        byte[] bytes = new byte[2];
        bytes[0] = contents[index + 6 + 16];
        bytes[1] = contents[index + 6 + 17];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getUserNameLength after: " + wrapped.getInt());
        return (int) wrapped.getShort();
    }

    public int getListFileIDStoreLength() {
        byte[] bytes = new byte[4];
        bytes[0] = contents[index + 6 + 18];
        bytes[1] = contents[index + 6 + 19];
        bytes[2] = contents[index + 6 + 20];
        bytes[3] = contents[index + 6 + 21];
        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
//        System.out.println("getListFileIDStoreLength after: " + wrapped.getInt(0));
        return wrapped.getInt(0);
    }

    public String getListFileIDStore() {
        String list = "";
        for (int i = (index + 28); i < (index + 28 + getListFileIDStoreLength()); i++) {
            list = list + (char) (contents[i]);
        }

//        System.out.println("\nPONG: getListFileIDStore receive - " + list);
//        System.out.println("\nPONG: getListFileIDStore receive - " + list.length());
        return list;
    }

    public String getUserNameOnline() {
        String userName = "";
//        System.out.println("PONG: getListFileIDStoreLength() :  " + (getListFileIDStoreLength()));
//        System.out.println("PONG: getUserNameOnline :  " + (getUserNameLength()));
//        System.out.println("PONG: getUserNameOnline : from " + (index + 28 + getListFileIDStoreLength()));
//        System.out.println("PONG: getUserNameOnline : to " + (index + 28 + getListFileIDStoreLength() + getUserNameLength()));

        for (int i = (index + 28 + getListFileIDStoreLength()); i < (index + 28 + getListFileIDStoreLength() + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }
//        System.out.println("USERNAME - PONG: " + userName);
        return userName;
    }
}
