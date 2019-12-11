package ninja.haxing.jping.connection;

import java.io.IOException;
import java.net.*;

public class Tester {
    private final String hostname;

    public Tester(String hostname) {
        this.hostname = hostname;
    }

    public TestResult test() {
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(hostname, 80), 1000);
            }

            return TestResult.SUCCESS;
        } catch (SocketTimeoutException e) {
            return TestResult.FAILURE_TIMEOUT;
        } catch (UnknownHostException e) {
            return TestResult.FAILURE_UNKNOWN_HOST;
        } catch (ConnectException e) {
            return TestResult.FAILURE_UNREACHABLE;
        } catch (IOException e) {
            e.printStackTrace();
            return TestResult.FAILURE_IO;
        }
    }
}
