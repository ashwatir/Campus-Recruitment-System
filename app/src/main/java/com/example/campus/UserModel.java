package com.example.campus;

public class UserModel {
    private String Name;
    private String CompanyName;
    private String CGPA;
    private String Resume;

    public UserModel(String Name, String CompanyName, String CGPA, String Resume) {
        this.Name = Name;
        this.CompanyName = CompanyName;
        this.CGPA = CGPA;
        this.Resume=Resume;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCompany() {
        return CompanyName;
    }

    public void setCompany(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getCGPA() {
        return CGPA;
    }

    public void setCGPA(String CGPA) {
        this.CGPA = CGPA;
    }

    public String getResume() {
        return Resume;
    }

    public void setResume(String Resume) {
        this.Resume = Resume;
    }
}
