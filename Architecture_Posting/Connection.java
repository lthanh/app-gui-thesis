package Architecture_Posting;

/**
 * Connection object Socket, IPAddress, Reader, Writer Whoever calls should
 * catch IOExceptions
 */
import java.net.*;
import java.io.*;

public class Connection {

    public static int INCOMING = 1;
    public static int OUTGOING = 2;
    private Socket socket;
    private int type;
    private IPAddress ip;
    private BufferedReader textReader;
    private BufferedInputStream byteReader;
    private BufferedOutputStream byteWriter;

    public Connection(Socket socket, int type) throws IOException {
        this.socket = socket;
        this.type = type;

//    int port = socket.getPort();
        int port = 6346;
        byte[] ipbytes = socket.getInetAddress().getAddress();

        // Cat chuoi IP byte ra dang decimal *****************************************************
        int[] ipints = new int[4];
        for (int i = 0; i < 4; i++) {
            ipints[i] = ((int) (ipbytes[i]) & 0xff);
        }

        // end cat chuoi


        ip = new IPAddress(ipints[0], ipints[1], ipints[2], ipints[3], port);
        textReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        byteReader = new BufferedInputStream(socket.getInputStream());
        byteWriter = new BufferedOutputStream(socket.getOutputStream());
    }

    public String getTypeString() {
        if (type == 1) {
            return "Peer - In  - ";
        } else if (type == 2) {
            return "Peer - In  - ";
//            return "Peer - Out - ";
        } else {
            return "";
        }
    }

    public int getType() {
        return (type);
    }

    public void changeType(int type) {
        this.type = type;
    }

    public Socket getSocket() {
        return socket;
    }

    public IPAddress getIPAddress() {
        return ip;
    }

    public BufferedReader getTextReader() {
        return textReader;
    }

    public BufferedInputStream getByteReader() {
        return byteReader;
    }

    public BufferedOutputStream getByteWriter() {
        return byteWriter;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void closeTextReader() throws IOException {
        textReader.close();
    }

    public void closeByteReader() throws IOException {
        byteReader.close();
    }

    public void closeByteWriter() throws IOException {
        byteWriter.close();
    }

    public void closeStreams() throws IOException {
        this.closeTextReader();
        this.closeByteReader();
        this.closeByteWriter();
    }

    public boolean compareConnections(IPAddress ip) {
        if (ip.equals(this.getIPAddress())) {
            return true;
        } else {
            return false;
        }
    }
}
