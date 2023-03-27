import dto.InitialData;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads data from a file and returns a list of InitialData objects
 */
public class ReadFromFile {

    final String FILE_ADDRESS = "src/main/resources/email.log";

    /**
     * Reads data from a file and returns a list of InitialData objects
     * @return A list of InitialData objects
     * @throws FileNotFoundException when data file can not be founded
     */
    public List<InitialData> read() throws FileNotFoundException{

        InitialData data;
        List<InitialData> dataList = new ArrayList<>();
        File file = new File(FILE_ADDRESS);

        String dateColumn;
        String idColumn;
        String logColumn;

        LocalDateTime time;

        if (file.canRead()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                dateColumn = scanner.next();
                time = LocalDateTime.parse(dateColumn);
                idColumn = scanner.next();
                logColumn = scanner.next();
                data = new InitialData(time, idColumn, logColumn);
                dataList.add(data);
            }
        }
        return dataList;
    }
}
