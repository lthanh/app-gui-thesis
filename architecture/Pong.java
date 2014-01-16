package architecture;


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
//        System.out.println("\nPONG: port send - " + contents[index + 0]);
//        System.out.println("\nPONG: port send - " + contents[index + 1]);

        // convert ip address to 4 bytes; need to check format of ip
        // address -- Little Endian????
        contents[index + 2] = (byte) ip.getFirst();
        contents[index + 3] = (byte) ip.getSecond();
        contents[index + 4] = (byte) ip.getThird();
        contents[index + 5] = (byte) ip.getFourth();

//        System.out.println("\nPONG: ip address send - " + contents[index + 2]);
//        System.out.println("\nPONG: ip address send - " + contents[index + 3]);
//        System.out.println("\nPONG: ip address send - " + contents[index + 4]);
//        System.out.println("\nPONG: ip address send - " + contents[index + 5]);


        for (int i = 0; i < userID.length; i++) {
            contents[index + 6 + i] = userID[i];
           // System.out.println("\nPONG: userID send - [" + i + "] " + contents[index + 6 + i]);
        }


        // convert userNameLength to byte array
        contents[index + 6 + userID.length + 0] = (byte) userNameLength;
      //  System.out.println("\nPONG: userNameLength send - " + contents[index + 6 + 16]);

        // convert listFileIDLength to byte array
        contents[index + 6 + userID.length + 1] = (byte) listFileIDStoreLength;
        System.out.println("\nPONG: listFileIDLength send - " + contents[index + 6 + userID.length + 1]);

        // convert listFileIDStore to byte  
        byte[] listFileID = new byte[listFileIDStore.length()];
        listFileID = listFileIDStore.getBytes();
        int i;
        for (i = 0; i < listFileIDStore.length(); i++) {
            contents[(index + i + 24)] = listFileID[i];
            System.out.println("\nPONG: listFileIDStore send - [" + i + "] " + contents[(index + i + 24)]);
        }
        contents[(index + i + 24)] = 0;

        // convert userNameOnline to byte  
        byte[] tempUserName = new byte[userNameOnline.length()];
        tempUserName = userNameOnline.getBytes();
        int j;
        for (j = 0; j < userNameOnline.length(); j++) {
            contents[(index + j + 24 + listFileIDStore.length())] = tempUserName[j];
          //  System.out.println("\nPONG: userID send - [" + i + "] " + contents[(index + j + 24 + listFileIDStore.length())]);
        }
        contents[(index + j + 24 + listFileIDStore.length())] = 0;

    }

    public Pong(byte[] rawdata) {
        super(rawdata);
    }

    public int getPort() {
        int port = (((contents[index + 1] & 0xff) << 8) | (contents[index + 0] & 0xff));
       // System.out.println("\nPONG: getUserIDOnline getPort receive - " + port);
        return (port);
    }

    public IPAddress getIP() {
       // System.out.println("\nPONG: getIP receive - " + (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort())));
        return (new IPAddress((contents[index + 2] & 0xff), (contents[index + 3] & 0xff), (contents[index + 4] & 0xff), (contents[index + 5] & 0xff), getPort()));
    }

    public String getUserIDOnline() {
        byte[] temp = new byte[16];
        for (int i = 0; i < 16; i++) {
            temp[i] = contents[index + 6 + i];
        }

        String userID = new String(temp);
        //System.out.println("\nPONG: getUserIDOnline receive - " + userID);
        return userID;
    }

    public int getUserNameLength() {
       // System.out.println("\nPONG: getUserNameLength receive - " + contents[index + 6 + 16]);
        return contents[index + 6 + 16];
    }

    public int getListFileIDStoreLength() {
      //  System.out.println("\nPONG: getListFileIDStoreLength receive - " + contents[index + 6 + 16 + 1]);
        return contents[index + 6 + 16 + 1];
    }

    public String getListFileIDStore() {
        String list = "";
        for (int i = (index + 24); i < (index + 24 + getListFileIDStoreLength()); i++) {
            list = list + (char) (contents[i]);
        }

//        System.out.println("\nPONG: getListFileIDStore receive - " + list);
//        System.out.println("\nPONG: getListFileIDStore receive - " + list.length());
        return list;
    }

    public String getUserNameOnline() {
        String userName = "";
        for (int i = (index + 24 + getListFileIDStoreLength()); i < (index + 24 + getListFileIDStoreLength() + getUserNameLength()); i++) {
            userName = userName + (char) (contents[i]);
        }

//        System.out.println("\nPONG: getUserNameOnline receive - " + userName);
//        System.out.println("\nPONG: getUserNameOnline receive - " + userName.length());

        return userName;
    }
}
