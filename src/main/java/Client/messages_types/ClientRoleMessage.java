package Client.messages_types;

import Client.Flag;
import Server.ClientRole;

import java.io.Serializable;

public class ClientRoleMessage implements Message, Serializable {
    private final Flag flag;
    private final ClientRole clientRole;

    public ClientRoleMessage(Flag flag, ClientRole clientRole) {
        this.flag = flag;
        this.clientRole = clientRole;
    }

    @Override
    public Flag getFlag() {
        return flag;
    }

    public ClientRole getClientRole() {
        return clientRole;
    }
}
