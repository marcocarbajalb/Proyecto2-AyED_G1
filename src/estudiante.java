package src;

public class estudiante extends user {

    public estudiante(String name, String lastname, String gender, int age, int id, String email, String password,
            String days, int calculo, int algebra, int fisica, int quimica, int estadistica, int programacion, int min,
            int max, boolean presencial) {
        super(name, lastname, gender, age, id, email, password, days, calculo, algebra, fisica, quimica, estadistica,
                programacion, min, max, presencial);
    }
}