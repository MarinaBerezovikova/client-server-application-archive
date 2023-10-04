//package Client.messages_types;
//
//import Client.ClientRole;
//import Client.Flag;
//
//import java.io.Serializable;
//
//public class Messager implements Serializable {
//
//    Flag flag;
//    String message;
//    Object object;
//
//
//    public class ClientRoleMessage implements Message {
//        private final String content;
//
//        public ClientRoleMessage(String content) {
//            this.content = content;
//        }
//
//        @Override
//        public String getType() {
//            return "clientRole";
//        }
//
//        @Override
//        public Object getContent() {
//            return content;
//        }
//    }
//
//    public Messager(Flag flag, String message) {
//        this.flag = flag;
//        this.message = message;
//        this.object = null;
//    }
//
//    public Messager(Flag flag, Object object) {
//        this.flag = flag;
//        this.message = "";
//        this.object = object;
//    }
//
//    public Messager(Flag flag, String message, Object object) {
//        this.flag = flag;
//        this.message = message;
//        this.object = object;
//    }
//
//    public Flag getFlag() {
//        return flag;
//    }
//
//    public void setFlag(Flag flag) {
//        this.flag = flag;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getObject() {
//        return object;
//    }
//
//    public void setObject(Object object) {
//        this.object = object;
//    }
//    class MessageClientRole implements Messager {
//
//        ClientRole clientRole;
//    }
//}
