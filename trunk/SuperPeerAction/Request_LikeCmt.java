/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeerAction;

import Architecture_Posting.IPAddress;
import Architecture_Posting.Packet;
import Architecture_Posting.Preferences;
import java.nio.ByteBuffer;

/**
 *
 * @author admin
 */
public class Request_LikeCmt extends Packet {

    int index = Packet.HEADER_LENGTH;
    private IPAddress ip;

    public Request_LikeCmt(int port, IPAddress ip,  long postIDReq, String idUserIDReq, String idViewer) {
        super(Packet.REQ_LIKECOMMENT, (22 + idUserIDReq.length() + idViewer.length()));

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

        // convert postIDReq to byte  
        ByteBuffer bBMessageid = ByteBuffer.allocate(16);
        bBMessageid.putLong(postIDReq);
        byte[] bytePostIDReq = bBMessageid.array();
        for (int h = 0; h < 16; h++) {
            contents[index + 6 + h] = bytePostIDReq[h];
        }


        // convert idUserIDReqLength
        // contents[index + 6] = (byte) idUserIDReqLength;

        // convert idUserIDReq to byte  
        byte[] tempIdUserIDReq = new byte[idUserIDReq.length()];
        tempIdUserIDReq = idUserIDReq.getBytes();
        int i;
        // System.out.println("length: " + idUserIDReq.length());
        for (i = 0; i < idUserIDReq.length(); i++) {
            contents[(index + 22 + i)] = tempIdUserIDReq[i];
//            System.out.println("userName : [" + i + "]" + contents[(index + 22 + i)]);
        }


        // convert idViewer to byte  
        byte[] tempIdViewer = new byte[idViewer.length()];
        tempIdViewer = idViewer.getBytes();
        int k;
        // System.out.println("length: " + idUserIDReq.length());
        for (k = 0; k < idViewer.length(); k++) {
            contents[(index + 22 + idUserIDReq.length() + k)] = tempIdViewer[k];
//            System.out.println("userName : [" + i + "]" + contents[(index + 22 + i)]);
        }


//        contents[(index + 6 + 16 + i)] = 0;

//        String idUserIDReqDecode = "";
//        for (int g = (index + 22); g < (contents.length); g++) {
//            idUserIDReqDecode = idUserIDReqDecode + (char) (contents[g]);
////            System.out.println("userName : [" + g + "]" + contents[(g)]);
//
//        }

//        System.out.println("UserID Insert: " + idUserIDReqDecode);
//  



//        byte[] tempPostIDReq = new byte[postIDReq.length()];
//        tempPostIDReq = postIDReq.getBytes();
//        int l;
//        for (l = 0; l < postIDReq.length(); l++) {
//            contents[(index + l + 7 + idUserIDReq.length())] = tempPostIDReq[l];
////            System.out.println("userName : [" + i + "]" + contents[(index + 26 + i)]);
//        }
//
//        contents[(index + l + 7 + idUserIDReq.length())] = 0;

    }

    public Request_LikeCmt(byte[] rawdata) {
        super(rawdata);
    }

//    public int getPort() {
////        System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
//        byte[] bytes = new byte[2];
//        bytes[0] = contents[index + 0];
//        bytes[1] = contents[index + 1];
//        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
////        System.out.println("getPort after: " + wrapped.getInt());
//        return (int) wrapped.getShort();
//    }
//    public IPAddress getIP() {
////        System.out.println("\nPONG: getIP receive - " + (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort())));
//        return (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort()));
//    }
//    public int getIdUserIDReqLength() {
////        System.out.println("POST -CDateLength: " + (contents[index + 16 + 0]));
//        return (contents[index + 6]);
//    }
    public IPAddress getLikeCmtIP() {
        return (ip);
    }

    public void setLikeCmtIP(IPAddress ip) {
        this.ip = ip;
    }

    public long getPostIDReq() {
        byte[] bytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            bytes[i] = contents[index + 6 + i];
        }

        ByteBuffer wrapped = ByteBuffer.wrap(bytes);
        return wrapped.getLong(0);
    }

    public String getIdUserIDReq() {
        String idUserIDReq = "";
        for (int i = (index + 22); i < (index + 22 + 16); i++) { // length of idUser = 16 char
            idUserIDReq = idUserIDReq + (char) (contents[i]);
        }
        return idUserIDReq;
    }
    
      public String getIdViewer() {
        String idViewer = "";
        for (int i = (index + 22 + 16); i < (contents.length); i++) {
            idViewer = idViewer + (char) (contents[i]);
        }
        return idViewer;
    }
}
