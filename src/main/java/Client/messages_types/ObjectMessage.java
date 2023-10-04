package Client.messages_types;

import Client.Flag;
import Server.StudentFile;

import java.io.Serializable;


public class ObjectMessage implements Message, Serializable {

    private final Flag flag;
    private final StudentFile content;

    public ObjectMessage(Flag flag, StudentFile content) {
        this.flag = flag;
        this.content = content;
    }

    @Override
    public Flag getFlag() {
        return flag;
    }

    public StudentFile getContent() {
        return content;
    }
}
