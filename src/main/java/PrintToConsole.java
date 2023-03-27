import dto.InitialData;
import dto.Session;

import java.util.List;

/**
 * Prints data to a console
 */
public class PrintToConsole {

    /**
     * Takes a list of InitialData objects and prints them to the console with an index number.
     * It is used for debugging and displaying the initial data.
     * @param dataList Initial data from file
     */
    public void printInitialData(List<InitialData> dataList){
        int k = 0;
        System.out.println(" -- Initial Data -- ");
        for (InitialData data : dataList){
            System.out.println(k + " " + data);
            k++;
        }
        System.out.println(" ");
    }

    /**
     * Takes a list of Session objects and prints them to the console with an index number.
     * It is used for debugging and displaying the initial data.
     * @param sessionList List of Session objects
     * @param string A short comment before printing
     */
    public void printSessions(List<Session> sessionList, String string){
        int k = 0;
        System.out.println(string);
        for (Session session : sessionList){
            System.out.println(k + " " + session);
            k++;
        }
        System.out.println(" ");
    }

}
