package ninja.haxing.jping;

import ninja.haxing.jping.connection.TestResult;
import ninja.haxing.jping.connection.Tester;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("jping <target host> <failure file>");
            return;
        }

        String targetHostname = args[0];
        System.out.println("Will test: " + targetHostname);

        Tester connectionTester = new Tester(targetHostname);

        Path failureLogPath = Paths.get(args[1]);
        System.out.println("Writing failures to: " + failureLogPath.toAbsolutePath().toString());

        FailureLog log = new FailureLog(failureLogPath);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                TestResult result = connectionTester.test();
                if (result != TestResult.SUCCESS) {
                    log.writeFailure(result);
                }
            }
        }, 0, 1000);
    }
}
