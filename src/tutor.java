/* Proyecto 2, Grupo #1
 * Algoritmos y estructuras de datos [Sección 50]
 * Tutor (tipo de usuario)
 */

//Importar las librerías que harán falta para el programa
import java.util.ArrayList;
import java.util.Scanner;

/**
* Esta clase representará a un tutor, y heredará de la superclase ITipoUsuario.
* @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
* @version 20.0.1, 08/05/2024
*/
public class Tutor extends ITipoUsuario {

    /*
     * Nombre completo del usuario.
     */
    public String nombre_completo;

    /*
     * Número de carné del usuario.
     */
    public int carnet;

    /*
     * Nombre de usuario (único) del usuario.
     */
    public String username;

    /*
     * Contraseña del ususario.
     */
    public String password;

    /*
     * Edad del usuario.
     */
    public int edad;

    /*
     * Género del usuario.
     * True corresponderá a masculino y false a femenino.
     */
    public boolean gender;

    /*
     * String con los días disponibles del tutor.
     * Serán 7 caracteres en cadena, donde 1 corresponderá a un día disponible y 0 a un día no disponible.
     */
    public String dias_disponibles;

    /*
     * Dominio que tiene el tutor en los cursos.
     */
    public int calculo, algebra, fisica, quimica, estadistica, programacion;

    /*
     * Modalidad preferida del tutor.
     * True corresponderá a presencial y false a virtual.
     */
    public boolean modalidad;

    /*
     * Mínimo y máximo que el tutor está dispuesto a cobrar.
     */
    public int min, max;

    /*
     * Variable numérica que indica el tipo de perfil del usuario (utilizada para el factory pattern).
     * El 1 corresponde a Estudiante y el 2 a Tutor.
     */
    public static void main(String[] args) {
        
    } final int tipo_perfil = 2;

    /**
	 * Establece el nombre de usuario (único) del usuario.
	 * @return Este método no devuelve nada.
	 */
    @Override
    public void crearUsername() {
        String carnet = this.carnet + "";
        String apellido = this.nombre_completo.split(" ")[1];
        String username = "";
        if (apellido.length() < 3){
            username = apellido.toLowerCase() + carnet + "@uvg.edu.gt";
        } else {
            username = apellido.substring(0, 3).toLowerCase() + carnet + "@uvg.edu.gt";
        }
        this.username = username;}

    public void setUsername(String username) {
        this.username = username;}  
    
    public void setEdad(int edad) {
        this.edad = edad;}

    public void setGender(boolean gender) {
        this.gender = gender;}

    public void setDias_disponibles(String dias_disponibles) {
        this.dias_disponibles = dias_disponibles;}

    public void setCalculo(int calculo) {
        this.calculo = calculo;}

    public void setAlgebra(int algebra) {
        this.algebra = algebra;}

    public void setFisica(int fisica) {
        this.fisica = fisica;}

    public void setQuimica(int quimica) {
        this.quimica = quimica;}

    public void setEstadistica(int estadistica) {
        this.estadistica = estadistica;}

    public void setProgramacion(int programacion) {
        this.programacion = programacion;}

    public void setModalidad(boolean modalidad) {
        this.modalidad = modalidad;}

    public void setMin(int min) {
        this.min = min;}

    public void setMax(int max) {
        this.max = max;}

    /**
	 * Establece la contraseña del usuario.
	 * @return Este método no devuelve nada.
	 */
    @Override
    public void setPassword(String password) {
        this.password = password;}

    /**
	 * Establece el nombre completo del usuario.
	 * @return Este método no devuelve nada.
	 */
    @Override
    public void setNombreCompleto(String nombre_completo) {
        this.nombre_completo = nombre_completo;}

    /**
	 * Establece el número de carné del usuario.
	 * @return Este método no devuelve nada.
	 */
    @Override
    public void setCarnet(int carnet) {
        this.carnet = carnet;}
    
    /**
	 * Obtiene la contraseña del usuario.
	 * @return String La contraseña del usuario.
	 */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
	 * Obtiene el nombre de usuario (único) del usuario.
	 * @return String El nombre de usuario (único) del usuario.
	 */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
	 * Obtiene el nombre completo del usuario.
	 * @return String El nombre completo del usuario.
	 */
    @Override
    public String getNombreCompleto() {
        return this.nombre_completo;
    }

    /**
	 * Obtiene el número correspondiente al tipo de perfil.
	 * @return int El número correspondiente al tipo de perfil.
	 */
    @Override
     public int getTipo_perfil() {
        return this.tipo_perfil;
    }

    /**
	 * Formatea la información del usuario al formato reconocido por el archivo csv.
	 * @return String La información del usuario en el formato reconocido por el archivo csv.
	 */
	@Override
    public String formatoCSV() {
		
		//Crear un array de strings (cada uno corresponderá a un encabezado del csv de usuarios)
		String[] datos = new String[17];
						
		//Guardar cada atributo en su posición correspondiente en el array (convertir los atributos numéricos o booleanos a strings)
        datos[0] = nombre_completo;
        datos[1] = Integer.toString(carnet);
        datos[2] = username;
        datos[3] = password;
        datos[4] = Integer.toString(edad);
        datos[5] = Boolean.toString(gender);
        datos[6] = dias_disponibles;
        datos[7] = Integer.toString(calculo);
        datos[8] = Integer.toString(algebra);
        datos[9] = Integer.toString(fisica);
        datos[10] = Integer.toString(quimica);
        datos[11] = Integer.toString(estadistica);
        datos[12] = Integer.toString(programacion);
        datos[13] = Boolean.toString(modalidad);
        datos[14] = Integer.toString(min);
        datos[15] = Integer.toString(max);
        datos[16] = Integer.toString(tipo_perfil);
				
		//Unificar todos los elementos del array en un solo string, separados por comas (,)
		String linea_datos = String.join(",", datos);
				
		return linea_datos;}

    /**
	 * Menú para los usuarios de tipo tutor.
	 * @param usuario_activo El usuario activo en el sistema (ya ha iniciado sesión).
     * @param lista_usuarios La lista de usuarios del sistema.
     * @param lista_usernames La lista de nombres de usuario de los usuarios del sistema.
	 * @param scanString El scanner para registrar los textos ingresados por el usuario.
	 * @param scanInt El scanner para registrar los números enteros ingresados por el usuario.
	 * @return Este método no devuelve nada.
	 */			
    @Override
    public void menuIndividual(ITipoUsuario usuario_activo, ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames, Scanner scanString, Scanner scanInt) {
        boolean menu_secundario = true;
		    while(menu_secundario) {
		        System.out.println("\n-------------------------------------------------------------------------");
                System.out.println("\n[TUTOR]\nBienvenido/a, "+ usuario_activo.getNombreCompleto());
		        System.out.println("\nIngrese el numero correspondiente a la opcion que desea realizar:\n1. Calificar estudiante.\n2. Obtener recomendación.\n3. Cerrar sesión.");

				int decision_secundaria = 0;
				try {decision_secundaria = scanInt.nextInt();}

				catch(Exception e) {//En caso de que el usuario ingrese texto en lugar de un número 
					System.out.println("\n**ERROR** La decision ingresada debe ser un numero.");
					scanInt.nextLine();
					continue;}
				
				switch(decision_secundaria) {
					
					case 1:{//Calificar estudiante
						System.out.println("\n----------------CALIFICAR ESTUDIANTE----------------");
						
                        System.out.println("\nIngrese el correo institucional del estudiante que desea calificar:");
                        String correo_estudiante = scanString.nextLine().toLowerCase().trim();
                        
                        System.out.println("\nIngrese la calificación que le daría al estudiante " + correo_estudiante + " (del 1 al 10):");
                        int calificacion = 0;
                        try {calificacion = scanInt.nextInt();}
                        catch(Exception e) {
                            System.out.println("\n**ERROR** La calificación ingresada debe ser un número.");
                            scanInt.nextLine();
                            continue;}
                        
                        if(calificacion<1 || calificacion>10) {
                            System.out.println("\n**ERROR** La calificación ingresada debe ser un número entre 1 y 10.");
                            continue;}
						
						break;}
					
					case 2:{//Obtener recomendación
						System.out.println("\n----------------OBTENER RECOMENDACIÓN----------------");
						
                        //Usar Neo4j para obtener la recomendación (grafos)

						break;}
					
					case 3:{//Cerrar sesión
						menu_secundario = false;
						break;}
					
					default:{//Opción no disponible (programación defensiva)
						System.out.println("\n**ERROR**\nEl numero ingresado no se encuentra entre las opciones disponibles.");}}}
    }
}