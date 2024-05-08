/* Proyecto 2, Grupo #1
 * Algoritmos y estructuras de datos [Sección 50]
 * Factory pattern para el tipo de usuario
 */

/**
 * Esta clase contendrá el método que devolverá la instancia correspondiente al tipo de usuario.
 * @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
 * @version 20.0.1, 08/05/2024
 */
public class TipoUsuarioFactory {

    /*
     * Variable numérica que corresponde al estudiante (utilizada para el factory pattern).
     */
    public static final int estudiante = 1;

    /*
     * Variable numérica que corresponde al tutor (utilizada para el factory pattern).
     */
    public static final int tutor = 2;

    /**
     * En base al tipo de perfil del usuario brindado, devuelve la instancia correspondiente al tipo de usuario (factory pattern).
     * @param tipo_perfil El tipo de perfil del usuario.
     * @return ITipoUsuario La instancia correspondiente al tipo de usuario.
     */
    public static ITipoUsuario getTipoUsuarioInstance(int tipo_perfil){
        
        switch (tipo_perfil) {
            case estudiante:
                return new Estudiante();    

            case tutor:
                return new Tutor();
        
            default:
                return null;
        }
    }

}