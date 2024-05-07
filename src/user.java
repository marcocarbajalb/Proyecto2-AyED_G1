package src;

public abstract class user {
    public String name;
    public String lastname;
    public String gender;
    public int age;
    public int id;
    public String email;
    public String password;

    public user(String name, String lastname, String gender, int age, int id, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.id = id;
        this.email = email;
        this.password = password;
    }
}