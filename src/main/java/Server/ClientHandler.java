package Server;

import Client.messages_types.ClientRoleMessage;
import Client.messages_types.CommandMessage;
import Client.messages_types.ObjectMessage;

import java.io.*;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        ClientHandler clientHandler = new ClientHandler(clientSocket);
        try {
            // получаем объект роль клиента
            ClientRoleMessage messageRole = (ClientRoleMessage) clientHandler.inputStream.readObject();

            ClientMenu clientMenu = new ClientMenu(messageRole.getClientRole());
            clientMenu.executeRoleStrategy();

            //конец работы после выбора клиентом 0. закрыть программу
            closeConnection();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибок
        }
    }

    public void sendObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveObject() {
        try {
            return inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientMenu {

        private final RoleStrategy roleStrategy;

        public ClientMenu(ClientRole clientRole) {
            switch (clientRole) {
                case USER:
                    this.roleStrategy = new UserRoleStrategy();
                    break;
                case ADMIN:
                    this.roleStrategy = new AdminRoleStrategy();
                    break;
                default:
                    this.roleStrategy = new UndefinedRoleStrategy();
            }
        }

        public void executeRoleStrategy() {
            roleStrategy.executeMenu();
        }

    }
    class UndefinedRoleStrategy implements RoleStrategy {

        PrintWriter writer;
        StudentFileManager studentFileManager;

        @Override
        public void executeMenu() {
            boolean isWorking = true;
            writer.println("Роль клиента не определена.\n");
            while (isWorking) {
                writer.println("Доступные действия:\n1. Показать список студентов\n" +
                        "0. Закрыть программу");
                CommandMessage commandMessage = (CommandMessage) receiveObject();

                switch (commandMessage.getFlag()) {
                    case CLOSE_PROGRAM:
                        isWorking = false;
                        break;
                    case VIEW_STUDENTS:
                        writer.println(studentFileManager.viewStudentFiles());
                        break;
                }
            }
        }

        @Override
        public ClientRole getClientRole() {
            return ClientRole.UNDEFINED;
        }
    }

    class UserRoleStrategy implements RoleStrategy {

        PrintWriter writer;
        StudentFileManager studentFileManager;

        @Override
        public void executeMenu() {
            boolean isWorking = true;
            writer.println("Вы используете программу в роли пользователя.");
            while (isWorking) {
                writer.println("Доступные действия:\n1. Показать список студентов" +
                        "\n2. Добавить файл студента\n0. Закрыть программу");
                CommandMessage commandMessage = (CommandMessage) receiveObject();

                switch (commandMessage.getFlag()) {
                    case CLOSE_PROGRAM:
                        isWorking = false;
                    case VIEW_STUDENTS:
                        writer.println(studentFileManager.viewStudentFiles());
                        break;
                    case ADD_STUDENT:
                        ObjectMessage objectMessage = (ObjectMessage) receiveObject();
                        studentFileManager.createStudentFile(objectMessage.getContent());
                        break;
                }
            }
        }

        @Override
        public ClientRole getClientRole() {
            return ClientRole.USER;
        }
    }

    class AdminRoleStrategy implements RoleStrategy {

        PrintWriter writer;
        StudentFileManager studentFileManager;

        @Override
        public void executeMenu() {
            boolean isWorking = true;
            writer.println("Вы используете программу в роли администратора.");
            while (isWorking) {
                writer.println("Доступные действия:\n1. Показать список студентов" +
                        "\n2. Добавить файл студента\n3.Редактировать файл студента\n0. Закрыть программу");
                CommandMessage commandMessage = (CommandMessage) receiveObject();

                switch (commandMessage.getFlag()) {
                    case CLOSE_PROGRAM:
                        isWorking = false;
                    case VIEW_STUDENTS:
                        writer.println(studentFileManager.viewStudentFiles());
                        break;
                    case ADD_STUDENT:
                        ObjectMessage objectMessageAdd = (ObjectMessage) receiveObject();
                        studentFileManager.createStudentFile(objectMessageAdd.getContent());
                        break;
                    case EDIT_STUDENT:
                        writer.println(studentFileManager.viewStudentFiles());
                        Integer numberFile = (Integer) receiveObject();
                        sendObject(studentFileManager.getStudentFile(numberFile));
                        ObjectMessage objectMessageEdited = (ObjectMessage) receiveObject();
                        studentFileManager.updateStudentFile(numberFile, objectMessageEdited.getContent());
                }
            }
        }

        @Override
        public ClientRole getClientRole() {
            return ClientRole.ADMIN;
        }
    }
}


// улучши этот код используя эти пункты:
//        Рефакторинг и оптимизация кода: Например, у вас повторяется код для вывода списка действий и обработки команд в разных ролях пользователя (UNDEFINED, USER, ADMIN). Этот код можно вынести в отдельные методы или даже классы, которые бы обрабатывали конкретные команды.
//
//        Использование паттернов проектирования: К примеру, конкретные действия, которые доступны для разных ролей, могут быть реализованы через паттерн Стратегия.

//    private void switchFunctionByRole() {
//        //распределение функционала по роли
//        switch (role) {
//            case admin:
//
//        }
//        case user: {
//        }
//        case undefined: {
//        }
//    }

//public class ClientThread extends Thread {
//    private final Socket clientSocket;
//
//    public ClientThread(Socket socket) {
//        this.clientSocket = socket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//// создаем объектный поток для чтения объектов из сокета клиента
//
//            Client client = (Client) objectInputStream.readObject();
//// сначала получаем объект client
//            System.out.println("Получен объект Client: " + client);
//
//            Messager message = (Messager) objectInputStream.readObject();
//// затем получаем объект message
//            System.out.println("Получено сообщение: " + message);
//
//            // здесь можно обработать сообщение от клиента
//
//            clientSocket.close(); // закрываем сокет клиента
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace(); // обработка ошибок
//        }
//    }
//}
//
//       try {
//        // Получение входного и выходного потоков для обмена данными с клиентом
//        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
//
//        writer.println("Введите роль пользователя: ");
//
//        String inputLine;
//        while ((inputLine = reader.readLine()) != null.xml) {
//            System.out.println("Клиент: " + inputLine);
//
//            // Отправка ответа клиенту
//            writer.println("Сервер получил ваше сообщение: " + inputLine);
//        }
//
//        // Закрытие соединения с клиентом
//        clientSocket.close();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}