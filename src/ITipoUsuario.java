/* Proyecto 2, Grupo #1
 * Algoritmos y estructuras de datos [Sección 50]
 * Superclase para el tipo de usuario
 */

//Importar las librerías que harán falta para el programa
import java.util.ArrayList;
import java.util.Scanner;

/**
* Esta superclase contendrá los métodos que los tipos de usuarios deben implementar, y ayudará al funcionamiento del factory pattern.
* @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
* @version 20.0.1, 08/05/2024
*/
public abstract class ITipoUsuario {
    
	/**
	 * Establece la contraseña del usuario.
	 * @return Este método no devuelve nada.
	 */
	public abstract void setPassword(String password);

    /**
	 * Establece el nombre completo del usuario.
	 * @return Este método no devuelve nada.
	 */
    public abstract void setNombreCompleto(String nombre_completo);

    /**
	 * Establece el número de carné (único) del usuario.
	 * @return Este método no devuelve nada.
	 */
    public abstract void setCarnet(int carnet);

    public abstract void setUsername(String username);

    public abstract void setEdad(int edad);

    public abstract void setGender(boolean gender);

    public abstract void setDias_disponibles(String dias_disponibles);

    public abstract void setCalculo(int calculo);

    public abstract void setAlgebra(int algebra);

    public abstract void setFisica(int fisica);

    public abstract void setQuimica(int quimica);

    public abstract void setEstadistica(int estadistica);

    public abstract void setProgramacion(int programacion);

    public abstract void setModalidad(boolean modalidad);

    public abstract void setMin(int min);

    public abstract void setMax(int max);

    /**
	 * Crea el username del usuario.
	 * @return Este método no devuelve nada.
	 */
	public abstract void crearUsername();
    
    /**
	 * Obtiene el nombre de usuario (único) del usuario.
	 * @return String El nombre de usuario (único) del usuario.
	 */
    public abstract String getUsername();

	/**
	 * Obtiene la contraseña del usuario.
	 * @return String La contraseña del usuario.
	 */
    public abstract String getPassword();
    
    /**
	 * Obtiene el nombre completo del usuario.
	 * @return String El nombre completo del usuario.
	 */
    public abstract String getNombreCompleto();

    /**
	 * Obtiene el número correspondiente al tipo de perfil.
	 * @return int El número correspondiente al tipo de perfil.
	 */
    public abstract int getTipo_perfil();

    /**
	 * Formatea la información del usuario al formato reconocido por el archivo csv.
	 * @return String La información del usuario en el formato reconocido por el archivo csv.
	 */
    public abstract String formatoCSV();

	/**
	 * Menú para los usuarios de determinado tipo.
	 * @param usuario_activo El usuario activo en el sistema (ya ha iniciado sesión).
     * @param lista_usuarios La lista de usuarios del sistema.
     * @param lista_usernames La lista de nombres de usuario de los usuarios del sistema.
	 * @param scanString El scanner para registrar los textos ingresados por el usuario.
	 * @param scanInt El scanner para registrar los números enteros ingresados por el usuario.
	 * @return Este método no devuelve nada.
	 */	
	public abstract void menuIndividual(ITipoUsuario usuario_activo, ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames, Scanner scanString, Scanner scanInt, String boltURL, String username_neo4j, String password_neo4j);

}