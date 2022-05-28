package alfaisal.naif.naif_200031;

class Student {
    private String student_ID;
    private String first_Name;
    private String surname;
    private String father_Name;
    private String national_ID;
    private String date_of_birth;
    private String gender;

    public Student(){}

    public Student(String student_ID, String first_Name, String father_Name, String surname, String national_ID, String date_of_birth, String gender) {
        this.student_ID = student_ID;
        this.first_Name = first_Name;
        this.father_Name = father_Name;
        this.surname = surname;
        this.national_ID = national_ID;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getFather_Name() {
        return father_Name;
    }

    public void setFather_Name(String father_Name) {
        this.father_Name = father_Name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNational_ID() {
        return national_ID;
    }

    public void setNational_ID(String national_ID) {
        this.national_ID = national_ID;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
