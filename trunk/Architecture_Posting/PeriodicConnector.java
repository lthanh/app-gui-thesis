package Architecture_Posting;

/*
 Tries to connect periodically to the hosts int the HostCache
 */
public class PeriodicConnector extends Thread {

    private static boolean execute;

    public PeriodicConnector(boolean execute) {
        this.execute = execute;
    }

    public void run() {
        while (true) {
            if ((HostArray.getCount() > 5) || !execute) {
                continue;
            }

            for (int i = 0; i < HostCache.getCount(); i++) {
                String ipString = HostCache.getIP(i);
//                System.out.println("ipString: " + ipString);
                if (!(HostArray.isLive(ipString))) {
                    HostCache.connectHost(i);
                }

                try {
                    sleep(Preferences.CONNECTOR_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void turnOn() {
        execute = true;
    }

    public static void turnOff() {
        execute = false;
    }
}
