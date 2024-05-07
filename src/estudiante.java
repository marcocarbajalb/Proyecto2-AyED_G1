package src;

public class estudiante extends user {
    public int Calculo;
    public int Algebra;
    public int Fisica;
    public int Quimica;
    public int Estadistica;
    public int Programacion;
    public int Min;
    public int Max;
    public boolean Presencial;

    public estudiante(String name, String lastname, String gender, int age, int id, String email, String password, int Calculo, int Algebra, int Fisica, int Quimica, int Estadistica, int Programacion,
    int Min, int Max, boolean Presencial) {
        super(name, lastname, gender, age, id, email, password);
        this.Calculo = Calculo;
        this.Algebra = Algebra;
        this.Fisica = Fisica;
        this.Quimica = Quimica;
        this.Estadistica = Estadistica;
        this.Programacion = Programacion;
        this.Min = Min;
        this.Max = Max;
        this.Presencial = Presencial;
    }
}