
public class Pong extends Packet {

    private int index = HEADER_LENGTH;

    public Pong(int port, IPAddress ip, byte[] userID, int userNameLength, int listFileIDStoreLength, String listFileIDStore, String userNameOnline, byte[] messageid) {
        super(Packet.PONG, (26 + listFileIDStore.length() + userNameOnline.length()));


        for (int i = 0; i < messageid.length; i++) // Pongs need the same Message IDs as the pings that generate them.
        {
            contents[i] = messageid[i];
        }

        // convert port to two bytes
        contents[index + 0] = (byte) (port >>> 8);
        contents[index + 1] = (byte) (port & 0xff);

        // convert ip address to 4 bytes; need to check format of ip
        // address -- Little Endian????
        contents[index + 2] = (byte) ip.getFirst();
        contents[index + 3] = (byte) ip.getSecond();
        contents[index + 4] = (byte) ip.getThird();
        contents[index + 5] = (byte) ip.getFourth();

        for (int i = 0; i < userID.length; i++) {
            contents[index + 6 + i] = userID[i];
        }

        // convert userNameLength to byte array
        contents[index + 6 + userID.length + 0] = (byte) userNameLength;

        // convert listFileIDLength to byte array
        contents[index + 6 + userID.length + 1] = (byte) listFileIDStoreLength;

        // convert listFileIDStore to byte  
        byte[] listFileID = new byte[listFileIDStore.length()];
        listFileID = listFileIDStore.getBytes();
        int i;
        for (i = 0; i < listFileIDStore.length(); i++) {
            contents[(i + 24)] = listFileID[i];
        }
        contents[(i + 24)] = 0;

        // convert userNameOnline to byte  
        byte[] tempUserName = new byte[userNameOnline.length()];
        tempUserName = userNameOnline.getBytes();
        int j;
        for (j = 0; j < userNameOnline.length(); j++) {
            contents[(j + 24 + listFileIDStore.length())] = tempUserName[j];
        }
        contents[(j + 24 + listFileIDStore.length())] = 0;

    }

    public Pong(byte[] rawdata) {
        super(rawdata);
    }

    public int getPort() {
        int port = (((contents[index + 1] & 0xff) << 8) | (contents[index + 0] & 0xff));
        return (port);
    }

    public IPAddress getIP() {
        return (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort()));
    }

    public String getUserIDOnline() {
        byte[] temp = new byte[16];
        //StringBuilder userID = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            //temp[i] = contents[index + i];
            temp[i] = contents[index + 6 + i];
        }
        
        String usenName = new String(temp);
        return usenName;
    }

    public int getUserNameLength() {
        return contents[index + 6 + 16];
    }

    public int getListFileIDStoreLength() {
        return contents[index + 6 + 16 + 1];
    }

    public String getListFileIDStore() {
        String list = "";
        for (int i = 24; i < 24 + getListFileIDStoreLength(); i++) {
            list = list + (char) (contents[i]);
        }
        return list;
    }

    public String getUserNameOnline() {
        String userName = "";
        for (int i = (24 + getListFileIDStoreLength()); i < (24 + getListFileIDStoreLength() + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }
        return userName;
    }
}
