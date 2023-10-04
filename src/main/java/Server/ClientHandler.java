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
            ClientRoleMessage message = (ClientRoleMessage) clientHandler.inputStream.readObject();

            ClientMenu clientMenu = new ClientMenu();
            clientMenu.defineClientMenu(message, clientSocket);
            // управление переходит в меню по опред клиенту

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

        Socket clientSocket;
        ClientRole clientRole;
        Flag flag;
        PrintWriter writer;
        StudentFileManager studentFileManager;

        private void defineClientMenu(ClientRoleMessage message, Socket clientSocket) {

            this.clientSocket = clientSocket;

            clientRole = validateClientRole(message.getClientRole());

            switch (clientRole) {
                case UNDEFINED:
                    System.out.println("Подключился клиент " + ClientRole.UNDEFINED);
                    startMenuForUndefinedRole();
                    break;
                case USER:
                    System.out.println("Подключился клиент " + ClientRole.USER);
                    startMenuForUserRole();
                    break;
                case ADMIN:
                    System.out.println("Подключился клиент " + ClientRole.ADMIN);
                    startMenuForAdminRole();
//                default: // сюда неопределенного пользователя и при получении невалид значения?
//                    throw new IllegalStateException("Unexpected value: " + clientRole);
            }
        }

        private ClientRole validateClientRole(ClientRole clientRole) {
            if (clientRole == ClientRole.USER || clientRole == ClientRole.ADMIN) {
                return clientRole;
            }
            return ClientRole.UNDEFINED;

        }

        private void startMenuForUndefinedRole() {
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

        private void startMenuForUserRole() {
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

        private void startMenuForAdminRole() {
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
    }
    class UndefinedRoleStrategy implements RoleStrategy {
        @Override
        public void executeMenu() {
            // код для выполнения для роли UNDEFINED
        }

        @Override
        public ClientRole getClientRole() {
            return ClientRole.UNDEFINED;
        }
    }

    class UserRoleStrategy implements RoleStrategy {
        @Override
        public void executeMenu() {
            // код для выполнения для роли USER
        }

        @Override
        public ClientRole getClientRole() {
            return ClientRole.USER;
        }
    }

    class AdminRoleStrategy implements RoleStrategy {
        @Override
        public void executeMenu() {
            // код для выполнения для роли ADMIN
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