package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    private Time time = new Time();
    private String sessionId;
    private String client;
    private String messageId;
    private Address address = new Address();
    private String status;

    public Session(String sessionId, String client, String messageId, String status, Time time, Address address){
        this.sessionId = sessionId;
        this.client = client;
        this.messageId = messageId;
        this.status = status;
        this.time = time;
        this.address = address;
    }

}
