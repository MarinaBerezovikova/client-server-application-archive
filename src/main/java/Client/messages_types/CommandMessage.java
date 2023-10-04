package Client.messages_types;

import Client.Flag;
import java.io.Serializable;

public class CommandMessage implements Message, Serializable {
    private final Flag flag;


    public CommandMessage(Flag flag, String content) {
        this.flag = flag;
    }

    @Override
    public Flag getFlag() {
        return flag;
    }
}
