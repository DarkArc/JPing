package ninja.haxing.jping;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class FailureLog {
    private Path logFile;

    public FailureLog(Path logFile) {
        this.logFile = logFile;
    }

    private byte[] createLogString(LocalDateTime failureTime, ConnectionResult result) {
        return ("[" + failureTime.toString() + "] " + result.name() + "\n").getBytes();
    }

    public void initIO() {
        try {
            Files.createFile(this.logFile);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeFailure(LocalDateTime failureTime, ConnectionResult result) {
        assert result != ConnectionResult.SUCCESS;

        try {
            Files.write(
                    logFile, createLogString(failureTime, result),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
