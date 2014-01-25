/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import architecture.IPAddress;
import architecture.Packet;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Request_Profile extends Packet {

    int index = Packet.HEADER_LENGTH;

    public Request_Profile(int port, IPAddress ip, String idUserIDReq) {
        super(Packet.REQ_PROFILE, (6 + idUserIDReq.length()));

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

        // convert idUserIDReq to byte  
        byte[] tempIdUserIDReq = new byte[idUserIDReq.length()];
        tempIdUserIDReq = idUserIDReq.getBytes();
        int i;
        // System.out.println("length: " + idUserIDReq.length());
        for (i = 0; i < idUserIDReq.length(); i++) {
            contents[(index + 6 + i)] = tempIdUserIDReq[i];
//            System.out.println("userName : [" + i + "]" + contents[(index + 22 + i)]);
        }
    }

    public Request_Profile(byte[] rawdata) {
        super(rawdata);
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

    public String getIdUserIDReq() {
        String idUserIDReq = "";
        for (int i = (index + 6); i < (contents.length); i++) {
            idUserIDReq = idUserIDReq + (char) (contents[i]);
        }

//        System.out.println("POST -userName: " + userName);
        return idUserIDReq;
    }
}
