package clusterTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by vboronin on 25.11.2014.
 */
public class TestUtils {
    private static boolean watch(String fileName, Predicate<String> predicate,
                                 long timeOut) throws IOException, InterruptedException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        Path pathFileName = Paths.get(fileName);
        if (!pathFileName.isAbsolute())
            pathFileName = Paths.get(fileName).toAbsolutePath();
        Path dir = pathFileName.getParent();
        dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

        Long startTime = System.currentTimeMillis();

        try(BufferedReader input = new BufferedReader(new FileReader(pathFileName.toString()))) {
            StringBuilder sb = new StringBuilder();



            // read data in file until now
            input.lines().forEach(sb::append);
            // check for the condition for the data already present in the file
            if (predicate.test(sb.toString())) {
                return true;
            }

            // if condition is not satisfied wait for the specified timeout
            while (true) {
                if (System.currentTimeMillis() - startTime > timeOut)
                    return false;
                WatchKey key = watcher.poll(1, TimeUnit.SECONDS);
                if (key == null)
                    continue;
                for (WatchEvent<?> event : key.pollEvents()) {
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path eventFileName = ev.context();
                    if (eventFileName.toString().equals(pathFileName.getFileName().toString())) {
                        // append only new lines to the already read ones
                        input.lines().forEach(sb::append);
                        // check for the condition
                        if (predicate.test(sb.toString()))
                            return true;
                    }
                }

                boolean valid = key.reset();
                if (!valid)
                    break;
            }
            return false;
        }
    }


    /**
     * Returns true if the pattern appears in the log file
     * within the specified amount of time(msec).
     */
    protected static boolean waitLog(String pattern, int timeout) {
        try {
             return watch("log/akka_test.log", s -> s.contains(pattern), timeout);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
