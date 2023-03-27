import dto.InitialData;
import dto.Session;

import java.util.List;

public class PrintToConsole {

    public void printInitialData(List<InitialData> dataList){
        int k = 0;
        System.out.println(" -- Initial Data -- ");
        for (InitialData data : dataList){
            System.out.println(k + " " + data);
            k++;
        }
        System.out.println(" ");
    }

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
