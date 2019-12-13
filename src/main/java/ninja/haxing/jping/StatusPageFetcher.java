package ninja.haxing.jping;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class StatusPageFetcher {
    private final Path statusPageDir;
    private final URL url;

    public StatusPageFetcher(Path statusPageDir, URL url) {
        this.statusPageDir = statusPageDir;
        this.url = url;
    }

    public void initIO() {
        try {
            Files.createDirectories(this.statusPageDir);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ConnectionResult fetch(LocalDateTime time) {
        try (InputStream is = url.openStream()) {
            Files.copy(is, statusPageDir.resolve("status-page-" + time.toString() + ".html"));
            return ConnectionResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return ConnectionResult.FAILURE_MODEM_STATUS_UNREACHABLE;
        }
    }
}
