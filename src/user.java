package src;
public abstract class user {
    public String name;
    public String lastname;
    public String gender;
    public int age;
    public int id;
    public String email;
    public String password;
    public String days;
    public int Calculo;
    public int Algebra;
    public int Fisica;
    public int Quimica;
    public int Estadistica;
    public int Programacion;
    public int Min;
    public int Max;
    public boolean Presencial;
    
    public user(String name, String lastname, String gender, int age, int id, String email, String password,
            String days, int calculo, int algebra, int fisica, int quimica, int estadistica, int programacion, int min,
            int max, boolean presencial) {
        this.name = name;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.id = id;
        this.email = email;
        this.password = password;
        this.days = days;
        this.Calculo = calculo;
        this.Algebra = algebra;
        this.Fisica = fisica;
        this.Quimica = quimica;
        this.Estadistica = estadistica;
        this.Programacion = programacion;
        this.Min = min;
        this.Max = max;
        this.Presencial = presencial;
    }
}