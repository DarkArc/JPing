package ninja.haxing.jping;

import java.io.IOException;
import java.net.*;

public class ConnectionTester {
    private final String hostname;

    public ConnectionTester(String hostname) {
        this.hostname = hostname;
    }

    public ConnectionResult test() {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(hostname, 80), 1000);
            }

            return ConnectionResult.SUCCESS;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return ConnectionResult.FAILURE_TIMEOUT;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return ConnectionResult.FAILURE_UNKNOWN_HOST;
        } catch (ConnectException e) {
            e.printStackTrace();
            return ConnectionResult.FAILURE_UNREACHABLE;
        } catch (IOException e) {
            e.printStackTrace();
            return ConnectionResult.FAILURE_IO;
        }
    }
}
