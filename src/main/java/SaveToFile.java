import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.Session;
import lombok.extern.java.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Log
public class SaveToFile {

    final String FILE_ADDRESS = "src/main/resources/output.json";

    public void save(List<Session> sessions){
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        mapper.setDateFormat(dateFormat);

        try {
            FileWriter writer = new FileWriter(FILE_ADDRESS);
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, sessions);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("The data was saved in the file.");
    }

}
