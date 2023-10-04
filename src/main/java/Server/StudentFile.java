package Server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

@XmlRootElement
public class StudentFile {

//    private static final long serialVersionUID = 1123140291701573943L;

    private String fileName;
    private String name;
    private String surname;
    private int numberGroup;
    private int yearOfStudy;
    private String note;

    public StudentFile() {
        name = "UNDEFINED";
        surname = "UNDEFINED";
        fileName = null;
        numberGroup = 0;
        yearOfStudy = 0;
        note = "";
    }

    public StudentFile(String name, String surname, int numberGroup, int yearOfStudy, String note) {
        this.name = name;
        this.surname = surname;
        fileName = null;
        this.numberGroup = numberGroup;
        this.yearOfStudy = yearOfStudy;
        this.note = note;
    }

    @XmlTransient
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement
    public int getNumberGroup() {
        return numberGroup;
    }

    public void setNumberGroup(int numberGroup) {
        this.numberGroup = numberGroup;
    }

    @XmlElement
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    @XmlElement
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentFile student = (StudentFile) o;
        return numberGroup == student.numberGroup &&
                yearOfStudy == student.yearOfStudy &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(note, student.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }

    @Override
    public String toString() {
        return "\nStudent name: " + name +
                "\nStudent surname: " + surname +
                "\nNumber of group: " + numberGroup +
                "\nYear of study: " + yearOfStudy +
                "\nNote: " + note;
    }

//    public static class GradeEntry {
//
//        @XmlElement
//        private String subject;
//
//        @XmlElement
//        private int grade;
//
//        @Override
//        public String toString() {
//            return "\nSubject: " + subject + " Grade: " + grade;
//        }
//    }
}