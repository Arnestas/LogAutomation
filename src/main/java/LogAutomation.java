import dto.InitialData;
import dto.Session;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LogAutomation {
    public static void main(String[] args) {
        ReadFromFile readFromFile = new ReadFromFile();
        PrintToConsole print = new PrintToConsole();
        List<InitialData> initialDataList = new ArrayList<>();
        SessionService repository = new SessionService();
        SaveToFile saveToFile = new SaveToFile();

        try {
            initialDataList = readFromFile.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        print.printInitialData(initialDataList);
        List<Session> sessions = repository.generateSessionList(initialDataList);
        print.printSessions(sessions, " -- Sessions (Initial Data) -- ");
        sessions = repository.mergeSessionsBySessionId(sessions);
        print.printSessions(sessions, " -- Merged Sessions -- ");
        List<Session> sessionsWithoutNull = repository.removeIncompleteSessions(sessions);
        print.printSessions(sessionsWithoutNull, " -- Sessions without NULL values-- ");
        saveToFile.save(sessionsWithoutNull);
    }
}
