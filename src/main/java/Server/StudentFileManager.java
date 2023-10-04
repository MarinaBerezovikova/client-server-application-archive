package Server;

import java.util.List;

public class StudentFileManager {

    static XMLParser xmlParser;

    public String viewStudentFiles() {
        List<StudentFile> temp = xmlParser.getStudentFileList();
        String result = "";
        int number = 0;
        for (StudentFile studentFile : temp) {
            result = "Номер файла: " + number + studentFile.toString();
            number++;
        }
        return result;
    }

    public void createStudentFile(StudentFile content) {
        xmlParser.getStudentFileList().add(content);
    }

    public StudentFile getStudentFile(Integer number) {
        return xmlParser.getStudentFileList().get(number);
    }

    public void updateStudentFile(Integer number, StudentFile studentFile) {
        xmlParser.getStudentFileList().set(number, studentFile);
    }
}
    //    public StudentFile createStudentFile(String name, String surname, int numberGroup,
//                                         int yearOfStudy,String note) {
//        return new StudentFile(name, surname, numberGroup, yearOfStudy, note);
//    }
//
//    public StudentFile editStudentFile(StudentFile studentFile) {
//        studentFile.setName();
//
//        return studentFile;
//    }
