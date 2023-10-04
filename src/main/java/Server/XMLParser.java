package Server;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private final String STUDENTS_FILES_FOLDER_PATH = "src/main/resources/students_files";

    private final List<StudentFile> studentFileList = new ArrayList<>();

    public List<StudentFile> getStudentFileList() {
        return studentFileList;
    }

    private final File folderPath = new File(STUDENTS_FILES_FOLDER_PATH);


    private JAXBContext context;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLParser() {
        try {
            context = JAXBContext.newInstance(StudentFile.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = context.createUnmarshaller();

        } catch (JAXBException exception) {
            exception.printStackTrace();
        }
    }

    public void readStudentsFilesFolder() {

        File[] listFiles = folderPath.listFiles((dir, name) -> name.endsWith(".xml"));

        if (listFiles != null) {
            for (File file : listFiles) {
                try {
                    StudentFile studentFile = (StudentFile) unmarshaller.unmarshal(file);
                    studentFile.setFileName(file.getName());
                    studentFileList.add(studentFile);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeStudentsFilesToFolder() {
        for (StudentFile studentFile : studentFileList) {
            String fileName = studentFile.getFileName();
            if (fileName != null) {
                writeToFileUsingFileName(studentFile, fileName);
            } else {
                writeToFileUsingGeneratedName(studentFile);
            }
        }
        studentFileList.clear();
    }

    private void writeToFileUsingFileName(StudentFile studentFile, String fileName) {
        File file = new File(STUDENTS_FILES_FOLDER_PATH, fileName);
        writeStudentFile(studentFile, file);
    }

    private void writeToFileUsingGeneratedName(StudentFile studentFile) {
        String newFileName = studentFile.getName() + "_" + studentFile.getSurname() + ".xml";

        Path filePath = Paths.get(STUDENTS_FILES_FOLDER_PATH, newFileName);

        try (StringWriter stringWriter = new StringWriter()) {
            marshaller.marshal(studentFile, stringWriter);
            String xmlString = stringWriter.toString();
            Files.write(filePath, xmlString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeStudentFile(StudentFile studentFile, File file) {
        try {
            marshaller.marshal(studentFile, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//
//import java.io.File;
//        import java.util.ArrayList;
//        import java.util.List;
//        import javax.xml.bind.JAXBContext;
//        import javax.xml.bind.JAXBException;
//        import javax.xml.bind.Unmarshaller;
//
//public class XmlReader {
//
//    public static void main(String[] args) throws JAXBException {
//        List<Server.StudentFile> studentFiles = new ArrayList<>();
//        //путь к папке resourses
//        String folderPath = "src/main/resources";
//        File folder = new File(folderPath);
//        //читаем каждый файл xml в папке resourses
//        for (File file : folder.listFiles((dir, name) -> name.endsWith(".xml"))) {
//            JAXBContext jaxbContext = JAXBContext.newInstance(Server.StudentFile.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Server.StudentFile studentFile = (Server.StudentFile) jaxbUnmarshaller.unmarshal(file);
//            studentFiles.add(studentFile);
//        }
//        //выводим список объектов Server.StudentFile
//        System.out.println(studentFiles);
//    }
//}

//Шаг 2: Создайте объект класса JAXBContext. Пример:
//
//        JAXBContext context = JAXBContext.newInstance(Employee.class);
//        Шаг 3: Создайте объект Marshaller и установите свойства, если необходимо. Пример:
//
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        Шаг 4: С помощью объекта Marshaller выполните маршализацию объекта Java в XML-документ. Пример:
//
//        Employee employee = new Employee(1, "John", "Doe");
//        marshaller.marshal(employee, new File("employee.xml"));
//        Шаг 5: С помощью объекта Unmarshaller выполните демаршализацию XML-документа в объект Java. Пример:
//
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Employee employee = (Employee) unmarshaller.unmarshal(new File("employee.xml"));
//        Это примерный код, который поможет вам начать использование JAXB в вашем Мавен-проекте. Однако, перед выполнением любых действий, связанных с изменением файлов проекта, всегда рекомендуется сделать резервную копию.

//
//    Для чтения XML-файла в Java с использованием библиотеки JAXB, необходимо использовать классы, сгенерированные из схемы XSD или классов JAXB.
//
//        Пример чтения файла XML с использованием классов JAXB:
//
//        Создайте объект класса JAXBContext, который будет использоваться для чтения XML:
//        JAXBContext context = JAXBContext.newInstance(Server.StudentFile.class);
//        Здесь Server.StudentFile - это класс, соответствующий корневому элементу XML-файла.
//
//        Создайте объект класса Unmarshaller, который будет использоваться для чтения XML-файла:
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        Прочитайте XML-файл с помощью метода unmarshal() у объекта unmarshaller:
//        Server.StudentFile studentFile = (Server.StudentFile) unmarshaller.unmarshal(new File("students.xml"));
//        Здесь students.xml - это название файла XML, который необходимо прочитать, а Server.StudentFile - это тип объекта, который вы хотите прочитать.
//
//        Добавьте новые данные в объект studentFile, используя соответствующие методы для доступа к полям.
//        Student student = new Student();
//// Задайте значения полей объекта student
//        studentFile.getStudents().add(student);
//        В данном примере создается новый объект класса Student и добавляется в список студентов в файле Server.StudentFile.
//
//        Сохраните обновленный studentFile в XML-файл, используя объект marshaller.
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.marshal(studentFile, new File("students.xml"));
//        Здесь students.xml - это название файла, в который будет сохранен обновленный XML.
//
//        Обратите внимание, что при использовании JAXB необходимо указывать аннотации в классах, которые вы хотите сериализовать или десериализовать, используя JAXB.