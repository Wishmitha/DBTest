package mysql;

/**
 * Created by wishmitha on 7/14/17.
 */
public class Message {

    private int id;
    private String payload;

    public Message(int id, String payload){
        this.id=id;
        this.payload=payload;
    }

    public String getPayload(){
        return payload;
    }

    public int getId(){
        return this.id;
    }


}
