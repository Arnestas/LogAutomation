import dto.InitialData;
import dto.Session;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The program imports an email log into a log analysis system, which only accepts logs in JSON format (email.log).
 * ● The script combines events based on the session id (column 2):
 *      ○ Events may be happening in parallel and overlapping.
 *      ○ Incomplete sessions (missing any of the fields) should be ignored.
 * ● The Script calculates the duration of each session.
 * ● Prints out all of the combined events in a JSON formatted array (output.json).
 */
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
