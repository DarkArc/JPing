package ninja.haxing.jping;

import ninja.haxing.jping.connection.TestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class FailureLog {
    private Path logFile;

    public FailureLog(Path logFile) {
        this.logFile = logFile;
    }

    private byte[] createLogString(TestResult result) {
        return new String("[" + LocalDateTime.now().toString() + "] " + result.name() + "\n").getBytes();
    }

    public void writeFailure(TestResult result) {
        assert result != TestResult.SUCCESS;

        try {
            Files.write(logFile, createLogString(result), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
