
import dto.Address;
import dto.InitialData;
import dto.Session;
import dto.Time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionService {
    /**
     * The method generates a list of all sessions(objects) from initial data:
     * assigns null or 'clear' values
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
     * The method takes 'clear' values from the log
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

    public List<Session> mergeSessionsBySessionId(List<Session> sessions){
        List<Session> mergedSessions = new ArrayList<>();
        for (Session session : sessions){
            if (!sessionExists(mergedSessions, session.getSessionId())) {
                mergedSessions.add(session);
            } else {
                for (Session merged : mergedSessions) {
                    if (merged.getSessionId().equals(session.getSessionId())) {
                        merged.setClient(compareStrings(
                                merged.getClient(), session.getClient()));
                        merged.setMessageId(compareStrings(
                                merged.getMessageId(), session.getMessageId()));
                        merged.setStatus(compareStrings(
                                merged.getStatus(), session.getStatus()));
                        merged.setAddress(compareAddress(
                                merged.getAddress(), session.getAddress()));
                        merged.setTime(compareTime(
                                merged.getTime(), session.getTime().getStart()));
                    }
                }
            }
        }
        return mergedSessions;
    }

    /**
     * The method finds min and max values of session times and calculates a duration of the session
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
     * Session has addresses 'from' and 'to'.
     * We are calling this method during merging of the session.
     * This method compares sessions address values, and leaves only not null values.
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
     * We are calling this method during merging of the session.
     * This method compares sessions string values, and leaves only not null values.
     */
    public String compareStrings(String a, String b){
        if (b != null){
            a = b;
        }
        return a;
    }

    public boolean sessionExists(List<Session> mergedSession, String sessionId){
        for (Session session : mergedSession){
            if (session.getSessionId().equals(sessionId)){
                return true;
            }
        }
        return false;
    }

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