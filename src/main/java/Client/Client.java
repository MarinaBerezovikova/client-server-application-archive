package Client;

import Server.ClientRole;

import java.io.Serializable;
import java.util.Objects;

public class Client  implements Serializable {

//    private static final long serialVersionUID = 2123150291706573946L;

    private ClientRole clientRole;
    private String clientName;
    private String clientSurname;

    public ClientRole getClientRole() {
        return clientRole;
    }

    public void setClientRole(ClientRole clientRole) {
        this.clientRole = clientRole;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public Client() {
        this.clientRole = ClientRole.UNDEFINED;
        this.clientName = "UNDEFINED";
        this.clientSurname = "UNDEFINED";
    }

    public Client(ClientRole clientRole, String clientName, String clientSurname) {
        this.clientRole = clientRole;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(getClientName(), client.getClientName()) &&
                Objects.equals(getClientSurname(), client.getClientSurname()) &&
                getClientRole() == client.getClientRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientRole(),getClientName(),getClientSurname());
    }

    @Override
    public String toString() {
        return "Client.Client's role: " + clientRole +
                "\nName and surname: " + clientName + " " + clientSurname;
    }
}