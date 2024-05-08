//Importar las librerías que harán falta para el programa
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* Esta clase es el driver program. Es decir, la clase que conectará todas las demás clases y hará funcionar el sistema de recomendación de tutorías.
* @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
* @version 20.0.1, 08/05/2024
*/
public class TutoriasUI {

    /**
	* Método principal.
	* En este método se desarrollará todo el programa.
	* @param String[] args
	* @return Este método no devuelve nada
	*/
    public static void main(String[] args) {
        
        //Instanciar el gestor que permitirá la persistencia de los datos
		PersistenciaCSV gestor = new PersistenciaCSV();
        
        //Lista en la que se almacenarán los usuarios del programa
		ArrayList<ITipoUsuario> lista_usuarios = new ArrayList<ITipoUsuario>();
		lista_usuarios = gestor.cargarUsuariosCSV();

        //Lista en la que se almacenarán los usernames de los usuarios del programa
		ArrayList<String> lista_usernames = new ArrayList<String>();
		lista_usernames = gestor.extraerUsernamesUsuarios(lista_usuarios);

		//Crear los scanners que registrarán los datos ingresados por el ususario
		Scanner scanInt = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		
		boolean menu_principal = true;
		while(menu_principal) {
			
			//Menú que se le mostrará al usuario
			System.out.println("\n\n----------------BIENVENIDO/A AL SISTEMA DE RECOMENDACIÓN DE TUTORÍAS----------------");
			System.out.println("\nIngrese el numero correspondiente a la opcion que desea realizar:\n1. Registrarse.\n2. Iniciar sesión.\n3. Salir del programa.");
			
			int decision_principal = 0;
			try {decision_principal = scanInt.nextInt();}

			catch(Exception e) {//En caso de que el usuario ingrese texto en lugar de un número 
				System.out.println("\n**ERROR** La decision ingresada debe ser un numero.");
				scanInt.nextLine();
				continue;}
				
			switch(decision_principal) {
				case 1:{//Registrarse
					System.out.println("\n----------------------------REGISTRARSE----------------------------");
					registrarUsuario(lista_usuarios,lista_usernames,scanString,scanInt);
					break;}
				
				case 2:{//Iniciar sesión
					System.out.println("\n---------------------------INICIAR SESION---------------------------");
					if(lista_usuarios.size()>0) {
						iniciarSesion(lista_usuarios,lista_usernames,scanString,scanInt,gestor);}
					
					else {
						System.out.println("\nOPCION NO DISPONIBLE.\nPor el momento, no hay ningun usuario registrado en el sistema de la universidad.");}
					break;}
				
				case 3:{//Salir del programa
					//Terminar el bucle del menú principal
					menu_principal = false;
					
					//Mostrar al ususario que ha abandonado el programa
					System.out.println("\nHa abandonado el programa exitosamente.");
					
					//Cerrar todos los scanners
					scanString.close();
					scanInt.close();
										
					// Guardar información de los nuevos usuarios añadidos al programa
					try {gestor.guardarUsuariosCSV(lista_usuarios);} 
					catch (IOException e) {
						System.out.println("\n**ERROR**\nSe produjo un error al guardar la información de los usuarios del sistema.");}
					break;}
				
				default:{//Opción no disponible (programación defensiva)
					System.out.println("\n**ERROR**\nEl numero ingresado no se encuentra entre las opciones disponibles.");}
			}
		}
    }

    /**
	 * Registro de datos y creación de usuario.
	 * @param lista_usuarios La lista de usuarios en la que se deben almacenar los usuarios creados.
	 * @param lista_usernames La lista en la que se deben almacenar los usernames de los usuarios creados.
	 * @param scanString El scanner para registrar los textos ingresados por el usuario.
	 * @param scanInt El scanner para registrar los números enteros ingresados por el usuario.
	 * @return Este método no devuelve nada.
	 */
	public static void registrarUsuario(ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames, Scanner scanString, Scanner scanInt) {
		
		//Tipos de usuarios disponibles
		String [] tipos_de_perfiles = {"Estudiante","Tutor"};

		int decision_perfil = 0;
	    boolean seleccion_perfil = true;
	    while(seleccion_perfil) {
	        System.out.println("\nIngrese el numero correspondiente a su categoria de perfil: ");
	        for(int i=0;i<tipos_de_perfiles.length;i++) {
	            System.out.println((i+1) + ". " + tipos_de_perfiles[i]);}
	        
	        try {
				decision_perfil = scanInt.nextInt();} 
	        catch(Exception e) {
	            System.out.println("\n**ERROR** La decision ingresada debe ser un numero.");
	            scanInt.nextLine();
	            continue;}
	        
	        if((decision_perfil>=0)&&(decision_perfil<=tipos_de_perfiles.length)) {
	            seleccion_perfil = false;} 
	        
	        else {
	            System.out.println("\n**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.");}}
	    
        int tipo_perfil = decision_perfil;

	    //Aplicar el factory design para instanciar el usuario adecuado
        ITipoUsuario usuario = TipoUsuarioFactory.getTipoUsuarioInstance(tipo_perfil);
        
        //Atributos necesarios para la creación del usuario
	    String password,nombre_completo="", dias_disponibles = "";
        int carnet=0,edad=0, min=0, max=0, calculo=0, algebra=0, fisica=0, quimica=0, estadistica=0, programacion=0;
        boolean gender = false, modalidad = false;
        
        //[Registro de datos para crear el usuario]

        System.out.println("[Ingreso de datos personales]");
 		//Ciclo para validar el nombre completo del usuario
        boolean validar_nombre = true;
        while(validar_nombre) {
            System.out.println("\nIngrese su primer nombre y apellido separados por un espacio (ej. 'Nombre Apellido'):");
            nombre_completo = scanString.nextLine();
            String[] nombre_completo_usuario = nombre_completo.trim().split(" ");
            
            if(nombre_completo_usuario.length==2) {//Si el nombre ingresado tiene 2 palabras
                validar_nombre = false;} //Salir del ciclo
            
            else {//Solicitar que lo vuelva a introducir
                System.out.println("**ERROR** Ingrese sus datos en el formato solicitado.\n");}}

        //Ciclo para validar el carnet del usuario
        boolean validar_carnet = true;
        while(validar_carnet) {
            System.out.println("Ingrese su carnet (sin guiones ni espacios):");
            try {
                carnet = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** El carnet ingresado debe ser un numero entero.");
                scanInt.nextLine();
                continue;}
            
            if((carnet>0)&&(carnet<999999)) {
                validar_carnet = false;}
            
            else {
                System.out.println("**ERROR** El carnet ingresado no es válido.");}}

        //Ciclo para validar la edad del usuario
        boolean validar_edad = true;
        while(validar_edad) {
            System.out.println("Ingrese su edad:");
            try {
                edad = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La edad ingresada debe ser un numero entero.");
                scanInt.nextLine();
                continue;}
            
            if((edad>0)&&(edad<101)) {
                validar_edad = false;}
            
            else {
                System.out.println("**ERROR** La edad ingresada no es válida.");}}

        //Ciclo para validar el género del usuario
        boolean validar_genero = true;
        while(validar_genero) {
            System.out.println("Seleccione el número correspondiente a su género: \n1. Masculino\n2. Femenino");
            int decision_genero = 0;
            try {
                decision_genero = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decision ingresada debe ser un numero.");
                scanInt.nextLine();
                continue;}
            
            if((decision_genero>0)&&(decision_genero<3)) {
                validar_genero = false;
                gender = (decision_genero==1);}
            
            else {
                System.out.println("**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.");}}
        	        
		//Solicitar contraseña
		System.out.println("Ingrese su password:");
		password = scanString.nextLine().trim();

        //Ciclo para validar la calificación en los cursos
        System.out.println("[Dominio de los cursos]");
        boolean validar_calificaciones = true;
        while(validar_calificaciones) {
            System.out.println("Ingrese la calificación que se da en los siguientes cursos (del 1 al 10):");
            System.out.println("Cálculo:");
            try {
                calculo = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Álgebra:");
            try {
                algebra = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Física:");
            try {
                fisica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Química:");
            try {
                quimica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Estadística:");
            try {
                estadistica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Programación:");
            try {
                programacion = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La calificación ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            if((calculo>=1)&&(calculo<=10)&&(algebra>=1)&&(algebra<=10)&&(fisica>=1)&&(fisica<=10)&&(quimica>=1)&&(quimica<=10)&&(estadistica>=1)&&(estadistica<=10)&&(programacion>=1)&&(programacion<=10)) {
                validar_calificaciones = false;}
            
            else {
                System.out.println("**ERROR** Las calificaciones ingresadas no son válidas (deben ser valores numéricos entre 1 y 10).");}}

        System.out.println("[Preferencias para las tutorías]");
        //Ciclo para validar la modalidad de tutoría
        boolean validar_modalidad = true;
        while(validar_modalidad) {
            System.out.println("Seleccione el número correspondiente a la modalidad de tutoría que desea ofrecer: \n1. Presencial\n2. Virtual");
            int decision_modalidad = 0;
            try {
                decision_modalidad = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decision ingresada debe ser un numero.");
                scanInt.nextLine();
                continue;}
            
            if((decision_modalidad>0)&&(decision_modalidad<3)) {
                validar_modalidad = false;}
            
            else {
                System.out.println("**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.");}}

        //Ciclo para validar el rango de precios
        boolean validar_rango = true;
        while(validar_rango) {
            System.out.println("Ingrese el rango de precios que desea cobrar por hora de tutoría (en Q):");
            System.out.println("Mínimo:");
            try {
                min = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** El rango ingresado debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Máximo:");
            try {
                max = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** El rango ingresado debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            if((min>0)&&(max>0)&&(min<=max)) {
                validar_rango = false;}
            
            else {
                System.out.println("**ERROR** El rango ingresado no es válido.");}}
            
        System.out.println("[Disponibilidad de días]");
        //Ciclo para validar los días disponibles
        boolean validar_dias = true;
        while(validar_dias) {
            System.out.println("Para cada día que se le presentará a continuación, ingrese 1 si lo tiene disponible o 0 si no lo tiene disponible.");
            System.out.println("Lunes:");
            int lunes = 0;
            try {
                lunes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int martes = 0;
            System.out.println("Martes:");
            try {
                martes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int miercoles = 0;
            System.out.println("Miércoles:");
            try {
                miercoles = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int jueves = 0;
            System.out.println("Jueves:");
            try {
                jueves = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int viernes = 0;
            System.out.println("Viernes:");
            try {
                viernes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int sabado = 0;
            System.out.println("Sábado:");
            try {
                sabado = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            int domingo = 0;
            System.out.println("Domingo:");
            try {
                domingo = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("**ERROR** La decisión ingresada debe ser un número.");
                scanInt.nextLine();
                continue;}
            
            if((lunes==0||lunes==1)&&(martes==0||martes==1)&&(miercoles==0||miercoles==1)&&(jueves==0||jueves==1)&&(viernes==0||viernes==1)&&(sabado==0||sabado==1)&&(domingo==0||domingo==1)) {
                dias_disponibles = lunes + "," + martes + "," + miercoles + "," + jueves + "," + viernes + "," + sabado + "," + domingo;
                validar_dias = false;}
            
            else {
                System.out.println("**ERROR** Las decisiones ingresadas no son válidas (deben ser valores numéricos entre 0 y 1).");}}

        //Agregar los datos al usuario y agregarlo a la lista de usuarios (también agregar su username de la lista de usernames).
        usuario.setNombreCompleto(nombre_completo);
        usuario.setCarnet(carnet);
        usuario.setEdad(edad);
        usuario.crearUsername();
        usuario.setCalculo(calculo);
        usuario.setAlgebra(algebra);
        usuario.setFisica(fisica);
        usuario.setQuimica(quimica);
        usuario.setEstadistica(estadistica);
        usuario.setProgramacion(programacion);
        usuario.setGender(gender);
        usuario.setDias_disponibles(dias_disponibles);
        usuario.setModalidad(modalidad);
        usuario.setMin(min);
        usuario.setMax(max);
        usuario.setPassword(password);

        lista_usuarios.add(usuario);
        lista_usernames.add(usuario.getUsername());
        
        //Notificar al usuario y brindarle su información de registro.
        System.out.println("\nUSUARIO CREADO EXITOSAMENTE.");
        System.out.println(nombre_completo + " (" + tipos_de_perfiles[tipo_perfil-1] + "), su nombre de usuario es: " + usuario.getUsername() + ", y su password es: " + password + ".");}

    /**
	 * Acceso a un usuario y todas las opciones dentro de este.
	 * @param lista_usuarios La lista de usuarios del sistema. 
	 * @param lista_usernames La lista de los nombres de usuario de los usuarios del sistema.
	 * @param scanString El scanner para registrar los textos ingresados por el usuario.
	 * @param scanInt El scanner para registrar los números enteros ingresados por el usuario.
	 * @param gestor El gestor (instancia de la clase PersistenciaCSV) para guardar y leer los datos del csv.
	 * @return Este método no devuelve nada.
	 */
	public static void iniciarSesion(ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames,Scanner scanString, Scanner scanInt, PersistenciaCSV gestor) {
	    	
        //En esta variable se registrará el nombre de usuario ingresado por el ususario
	    String username_ingresado = "";
	   
	    System.out.println("\nIngrese su correo institucional: ");
	    username_ingresado = scanString.nextLine().trim().toLowerCase();
	        	
		if(lista_usernames.contains(username_ingresado)) {//Si el username ingresado se encuentra en el sistema
			
			//Obtener y validar la contraseña del usuario
			System.out.println("Ingrese su password: ");
	    	String password = scanString.nextLine().trim();

			ITipoUsuario usuario_activo = lista_usuarios.get(lista_usernames.indexOf(username_ingresado));

			if(usuario_activo.getPassword().equals(password)) {//Si la contraseña ingresada es correcta 
		    
			usuario_activo.menuIndividual(usuario_activo,lista_usuarios,lista_usernames,scanString,scanInt);}
			    
	    else {//Si la contraseña ingresada es incorrecta
			System.out.println("\nCONTRASEÑA INCORRECTA.\nLa contraseña ingresada no es correcta.");}}
	    
	    else {//Si el nombre de usuario ingresado no se encuentra entre los usernames del sistema universitario
			System.out.println("\nUSUARIO NO ENCONTRADO.\nNo hay ningun usuario con el username ingresado. Verifique que el username sea correcto o que su usuario ya exista.");}}
    
}