
public class Pinger extends Thread {

    static int hosts = 0;
    static int totalkb = 0;
    static int totalfiles = 0;
    static Ping myping;
    static int pingCounter = 0;
    checkUserOnlineAction check = new checkUserOnlineAction();

    public void run() {
        while (true) {
            try {
                sleep(Preferences.PINGER_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Searcher.updateInfo(hosts, totalkb, totalfiles);
            myping = new Ping();
            pingCounter++;

            System.out.println("\n PINGer BEFORE times:  " + pingCounter);

            if (pingCounter == 2) {
                System.out.println("\n PINGer 3 times: " + pingCounter);
                check.checkUserOnline();
                
            }
            NetworkManager.writeToAll(myping);
        }
    }

//    public static void inform(Pong pong) {
//        if (pong.compare(myping)) {
//            hosts++;
////            totalfiles += pong.getNumFiles();
////            totalkb += pong.getKb();
//        }
//    }
}
