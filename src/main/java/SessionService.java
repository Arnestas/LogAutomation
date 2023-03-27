
import dto.Address;
import dto.InitialData;
import dto.Session;
import dto.Time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods for generating and merging sessions from initial data.
 * The class includes a method for generating a list of sessions from a list of initial data,
 * a method for merging sessions with the same ID,
 * and several utility methods for comparing and updating session properties.
 */
public class SessionService {

    /**
     * Generates a list of Session objects from a list of InitialData objects.
     * @param dataList The list of InitialData objects to be used in generating the list of Session objects.
     * @return The list of Session objects generated from the list of InitialData objects.
     */
    public List<Session> generateSessionList(List<InitialData> dataList){
        List<Session> sessions = new ArrayList<>();
        String[] logValue;
        for (InitialData data : dataList){
            Session tempSession = new Session();
            Time tempTime = new Time();
            Address tempAddress = new Address();

            tempTime.setStart(data.getDateTime());
            tempTime.setEnd(data.getDateTime());
            tempSession.setTime(tempTime);
            tempSession.setSessionId(data.getId());

            logValue = manageLog(data.getLog());

            switch (logValue[0]){
                case "client":
                    tempSession.setClient(logValue[1]);
                    break;
                case "message":
                    tempSession.setMessageId(logValue[1]);
                    break;
                case "from":
                    tempAddress.setFrom(logValue[1]);
                    tempSession.setAddress(tempAddress);
                    break;
                case "to":
                    tempAddress.setTo(logValue[1]);
                    tempSession.setAddress(tempAddress);
                    break;
                case "status":
                    tempSession.setStatus(logValue[1]);
                    break;
            }
            sessions.add(tempSession);
        }
        return sessions;
    }

    /**
     * Parses a log string and returns an array of two strings containing the key and value of the log.
     * @param log The log string to be parsed.
     * @return An array of two strings containing the key and value of the log.
     */
    public String[] manageLog(String log){
        String[] logValue = new String[2];

        if (log.contains("client")){
            logValue[0] = "client";
            logValue[1] =log.replaceAll("client=","");

        } else if (log.contains("message")){
            logValue[0] = "message";
            logValue[1] =log.replaceAll("message-id=", "");

        } else if (log.contains("from")){
            logValue[0] = "from";
            logValue[1] =log.replaceAll("from=", "");

        } else if (log.contains("to")){
            logValue[0] = "to";
            logValue[1] =log.replaceAll("to=", "");

        } else if (log.contains("status")){
            if (log.contains("sent")){
                logValue[0] = "status";
                logValue[1] ="sent";
            } else{
                logValue[0] = "status";
                logValue[1] ="rejected";
            }
        }
        return logValue;
    }

    /**
     * Merge a list of sessions by Session ID. Sessions with the same Session ID will be merged into a single session, with their properties compared and updated as needed.
     * @param sessions a List of Session objects to merge.
     * @return A List of merged Session objects.
     */
    public List<Session> mergeSessionsBySessionId(List<Session> sessions){
        List<Session> mergedSessions = new ArrayList<>();
        for (Session session : sessions){
            if (!sessionExists(mergedSessions, session.getSessionId())) {
                mergedSessions.add(session);
            } else {
                for (Session merged : mergedSessions) {
                    if (merged.getSessionId().equals(session.getSessionId())) {
                        merged.setClient(compareStrings(merged.getClient(), session.getClient()));
                        merged.setMessageId(compareStrings(merged.getMessageId(), session.getMessageId()));
                        merged.setStatus(compareStrings(merged.getStatus(), session.getStatus()));
                        merged.setAddress(compareAddress(merged.getAddress(), session.getAddress()));
                        merged.setTime(compareTime(merged.getTime(), session.getTime().getStart()));
                    }
                }
            }
        }
        return mergedSessions;
    }

    /**
     * Compare two times and return the updated time object with the earliest start time and the latest end time.
     * @param time The time object to compare against.
     * @param newTime The new time object to compare with.
     * @return The updated time object.
     */
    public Time compareTime (Time time, LocalDateTime newTime){
        Duration duration;
        if (time.getStart().isAfter(newTime)){
            time.setStart(newTime);
        }
        if (time.getEnd().isBefore(newTime)){
            time.setEnd(newTime);
        }
        duration = Duration.between(time.getStart(), time.getEnd());
        time.setDuration(duration);
        return time;
    }

    /**
     * Compare two address objects and return the updated address object with any non-null fields from the new address object.
     * @param a The original address object to compare against.
     * @param b The new address object to compare with.
     * @return The updated address object.
     */
    public Address compareAddress(Address a, Address b){
        if (b.getFrom() != null){
            a.setFrom(b.getFrom());
        }
        if (b.getTo() != null){
            a.setTo(b.getTo());
        }
        return a;
    }

    /**
     * Compare two strings and return the new string if it is not null.
     * @param a The original string to compare against.
     * @param b The new string to compare with.
     * @return The updated string.
     */
    public String compareStrings(String a, String b){
        if (b != null){
            a = b;
        }
        return a;
    }

    /**
     * Check if a session with the given session ID already exists in the merged session list.
     * @param mergedSession The list of merged sessions to search through.
     * @param sessionId The session ID to search for.
     * @return True if the session already exists in the list, false otherwise.
     */
    public boolean sessionExists(List<Session> mergedSession, String sessionId){
        for (Session session : mergedSession){
            if (session.getSessionId().equals(sessionId)){
                return true;
            }
        }
        return false;
    }

    /**
     * Remove any incomplete sessions from the list of sessions.
     * @param sessions The list of sessions to remove incomplete sessions from.
     * @return The list of sessions without any incomplete sessions.
     */
    public List<Session> removeIncompleteSessions(List<Session> sessions){
        List<Session> sessionListWithoutNulls = new ArrayList<>();
        for (Session session : sessions){
            if (session.getClient() !=null && session.getStatus() != null && session.getMessageId() != null && session.getAddress() != null){
                sessionListWithoutNulls.add(session);
            }
        }
        return sessionListWithoutNulls;
    }

}