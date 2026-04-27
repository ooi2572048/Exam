package bean;

import java.io.Serializable;

public class Subject implements Serializable {
    private String cd;
    private String name;
    private School school;

    // Getter for cd
    public String getCd() {
        return cd;
    }

    // Setter for cd
    public void setCd(String cd) {
        this.cd = cd;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for school
    public School getSchool() {
        return school;
    }

    // Setter for school
    public void setSchool(School school) {
        this.school = school;
    }
}