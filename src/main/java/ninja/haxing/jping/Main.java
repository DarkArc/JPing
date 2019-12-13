package ninja.haxing.jping;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("jping <target host> <status page>");
            return;
        }

        String targetHostname = args[0];
        System.out.println("Will test: " + targetHostname);

        ConnectionTester connectionTester = new ConnectionTester(targetHostname);

        Path failureLogPath = Paths.get("failure_log.txt");
        System.out.println("Writing failures to: " + failureLogPath.toAbsolutePath().toString());

        FailureLog log = new FailureLog(failureLogPath);
        log.initIO();

        URL url;
        try {
            url = new URL(args[1]);
            System.out.println("Fetching status page at: " + url.toString());
        } catch (MalformedURLException e) {
            System.err.println("Invalid status page URL format.");
            return;
        }

        Path statusPageDir = Paths.get("status_pages");
        System.out.println("Writing status pages to: " + statusPageDir.toAbsolutePath().toString());

        StatusPageFetcher statusPageFetcher = new StatusPageFetcher(statusPageDir, url);
        statusPageFetcher.initIO();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ConnectionResult result = connectionTester.test();
                if (result != ConnectionResult.SUCCESS) {
                    LocalDateTime failureTime = LocalDateTime.now();
                    log.writeFailure(failureTime, result);
                    ConnectionResult statusPageResult = statusPageFetcher.fetch(failureTime);
                    if (statusPageResult != ConnectionResult.SUCCESS) {
                        log.writeFailure(failureTime, statusPageResult);
                    }
                }
            }
        }, 0, 1000);
    }
}
