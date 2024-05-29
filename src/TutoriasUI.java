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

        //-----[Variables importantes de la base de datos]-------
        String username_neo4j = "neo4j";
		String password_neo4j = "builder-deliveries-harnesses";
		String boltURL = "bolt://34.237.124.48:7687";
        //--------------------------------------------------------

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
			System.out.println("\n\n----------------BIENVENIDO/A AL SISTEMA DE RECOMENDACION DE TUTORIAS----------------");
			System.out.println("\nIngrese el numero correspondiente a la opcion que desea realizar:\n1. Registrarse.\n2. Iniciar sesión.\n3. Administrar base de datos. \n4. Salir del programa.");
			
			int decision_principal = 0;
			try {decision_principal = scanInt.nextInt();}

			catch(Exception e) {//En caso de que el usuario ingrese texto en lugar de un número 
				System.out.println("\n**ERROR** La decision ingresada debe ser un numero.");
				scanInt.nextLine();
				continue;}
				
			switch(decision_principal) {
				case 1:{//Registrarse
					System.out.println("\n----------------------------REGISTRARSE----------------------------");
					registrarUsuario(lista_usuarios,lista_usernames,scanString,scanInt,username_neo4j,password_neo4j,boltURL,gestor);
					break;}
				
				case 2:{//Iniciar sesión
					System.out.println("\n---------------------------INICIAR SESION---------------------------");
					if(lista_usuarios.size()>0) {
						iniciarSesion(lista_usuarios,lista_usernames,scanString,scanInt,gestor,username_neo4j,password_neo4j,boltURL);}
					
					else {
						System.out.println("\nOPCION NO DISPONIBLE.\nPor el momento, no hay ningun usuario registrado en el sistema de la universidad.");}
					break;}
				
                case 3:{//Administrar base de datos
                    System.out.println("\n--------------------ADMINISTRAR BASE DE DATOS (NEO4J)--------------------");
                    System.out.println("\nIngrese su usuario de administrador: ");
                    String username_ingresado = scanString.nextLine().trim().toLowerCase();

                    if((username_ingresado.equals("marcocar")||username_ingresado.equals("carlosald")||username_ingresado.equals("carlosang")||username_ingresado.equals("diegomon")||username_ingresado.equals("moisesalo")||username_ingresado.equals("joaquinpue"))){

                        System.out.println("Ingrese su contraseña de administrador: ");
                        String password_ingresado = scanString.nextLine().trim();

                        if(password_ingresado.equals("admin1234")) {
                            administrarBaseDeDatosNeo4j(username_neo4j,password_neo4j,boltURL,scanString,scanInt,lista_usernames,lista_usuarios);}

                        else {
                            System.out.println("\nCONTRASEÑA INCORRECTA.\nLa contraseña ingresada no es correcta.");
                            break;}}

                    else {
                        System.out.println("\nADMINISTRADOR NO ENCONTRADO.\nNo hay ningun administrador con el usuario ingresado. Verifique que este sea correcto.");}

                    break;}

				case 4:{//Salir del programa
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
     * @param username_neo4j El nombre de usuario de la base de datos Neo4j.
     * @param password_neo4j La contraseña de la base de datos Neo4j.
     * @param boltURL La URL de la base de datos Neo4j.
     * @param gestor El gestor (instancia de la clase PersistenciaCSV) para guardar y leer los datos del csv.
	 * @return Este método no devuelve nada.
	 */
	public static void registrarUsuario(ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames, Scanner scanString, Scanner scanInt, String username_neo4j, String password_neo4j, String boltURL, PersistenciaCSV gestor) {
		
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
	            System.out.println("\n**ERROR** La decision ingresada debe ser un numero.\n");
	            scanInt.nextLine();
	            continue;}
	        
	        if((decision_perfil>=0)&&(decision_perfil<=tipos_de_perfiles.length)) {
	            seleccion_perfil = false;} 
	        
	        else {
	            System.out.println("\n**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.\n");}}
	    
        int tipo_perfil = decision_perfil;

	    //Aplicar el factory design para instanciar el usuario adecuado
        ITipoUsuario usuario = TipoUsuarioFactory.getTipoUsuarioInstance(tipo_perfil);
        
        //Atributos necesarios para la creación del usuario
	    String password,nombre_completo="", dias_disponibles = "";
        int carnet=0,edad=0, min=0, max=0, calculo=0, algebra=0, fisica=0, quimica=0, estadistica=0, programacion=0;
        boolean gender = false, modalidad = false;
        
        //[Registro de datos para crear el usuario]

        System.out.println("\n[Ingreso de datos personales]");
 		//Ciclo para validar el nombre completo del usuario
        boolean validar_nombre = true;
        while(validar_nombre) {
            System.out.println("Ingrese su primer nombre y apellido separados por un espacio (ej. 'Nombre Apellido'):");
            nombre_completo = scanString.nextLine();
            String[] nombre_completo_usuario = nombre_completo.trim().split(" ");
            
            if(nombre_completo_usuario.length==2) {//Si el nombre ingresado tiene 2 palabras
                validar_nombre = false;} //Salir del ciclo
            
            else {//Solicitar que lo vuelva a introducir
                System.out.println("\n**ERROR** Ingrese sus datos en el formato solicitado.\n");}}

        //Ciclo para validar el carnet del usuario
        boolean validar_carnet = true;
        while(validar_carnet) {
            System.out.println("Ingrese su carnet (sin guiones ni espacios):");
            try {
                carnet = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** El carnet ingresado debe ser un numero entero.\n");
                scanInt.nextLine();
                continue;}
            
            if((carnet>0)&&(carnet<999999)) {
                validar_carnet = false;}
            
            else {
                System.out.println("\n**ERROR** El carnet ingresado no es válido.\n");}}

        //Ciclo para validar la edad del usuario
        boolean validar_edad = true;
        while(validar_edad) {
            System.out.println("Ingrese su edad:");
            try {
                edad = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La edad ingresada debe ser un numero entero.\n");
                scanInt.nextLine();
                continue;}
            
            if((edad>0)&&(edad<101)) {
                validar_edad = false;}
            
            else {
                System.out.println("\n**ERROR** La edad ingresada no es válida.\n");}}

        //Ciclo para validar el género del usuario
        boolean validar_genero = true;
        while(validar_genero) {
            System.out.println("Seleccione el número correspondiente a su género: \n1. Masculino\n2. Femenino");
            int decision_genero = 0;
            try {
                decision_genero = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decision ingresada debe ser un numero.\n");
                scanInt.nextLine();
                continue;}
            
            if((decision_genero>0)&&(decision_genero<3)) {
                validar_genero = false;
                gender = (decision_genero==1);}
            
            else {
                System.out.println("\n**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.\n");}}
        	        
		//Solicitar contraseña
		System.out.println("Ingrese su password:");
		password = scanString.nextLine().trim();

        //Ciclo para validar la calificación en los cursos
        System.out.println("\n[Dominio de los cursos]");
        boolean validar_calificaciones = true;
        while(validar_calificaciones) {
            System.out.println("Ingrese una calificación que corresponda al dominio que considera tener en los siguientes cursos (siendo 1 lo más bajo y 10 lo más alto):");
            System.out.println("Cálculo:");
            try {
                calculo = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Algebra Lineal:");
            try {
                algebra = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Física:");
            try {
                fisica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Química:");
            try {
                quimica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Estadística:");
            try {
                estadistica = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Programación:");
            try {
                programacion = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La calificación ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            if((calculo>=1)&&(calculo<=10)&&(algebra>=1)&&(algebra<=10)&&(fisica>=1)&&(fisica<=10)&&(quimica>=1)&&(quimica<=10)&&(estadistica>=1)&&(estadistica<=10)&&(programacion>=1)&&(programacion<=10)) {
                validar_calificaciones = false;}
            
            else {
                System.out.println("\n**ERROR** Las calificaciones ingresadas no son válidas (todas ellas deben ser valores numéricos entre 1 y 10).\n");}}

        System.out.println("\n[Preferencias para las tutorías]");
        //Ciclo para validar la modalidad de tutoría
        boolean validar_modalidad = true;
        while(validar_modalidad) {
            System.out.println("Seleccione el número correspondiente a la modalidad de tutoría que prefiere: \n1. Presencial\n2. Virtual");
            int decision_modalidad = 0;
            try {
                decision_modalidad = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decision ingresada debe ser un numero.\n");
                scanInt.nextLine();
                continue;}
            
            if((decision_modalidad>0)&&(decision_modalidad<3)) {
                validar_modalidad = false;
                modalidad = (decision_modalidad==1);}
            
            else {
                System.out.println("\n**ERROR** El numero ingresado no se encuentra entre las opciones disponibles.\n");}}

        //Ciclo para validar el rango de precios
        boolean validar_rango = true;
        while(validar_rango) {
            System.out.println("Ingrese los límites enteros del rango de precios que desea pagar/cobrar por hora de tutoría (en Q):");
            System.out.println("Mínimo:");
            try {
                min = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** El valor monetario ingresado debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            System.out.println("Máximo:");
            try {
                max = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** El valor monetario ingresado debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            if((min>0)&&(max>0)&&(min<=max)) {
                validar_rango = false;}
            
            else {
                System.out.println("\n**ERROR** Los límites del rango ingresado no son válidos.\n");}}
            
        System.out.println("\n[Disponibilidad de días]");
        //Ciclo para validar los días disponibles
        boolean validar_dias = true;
        while(validar_dias) {
            System.out.println("Para cada día que se le presentará a continuación, ingrese 1 si lo tiene disponible o 0 si no lo tiene disponible.");
            System.out.println("Lunes:");
            int lunes = 0;
            try {
                lunes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int martes = 0;
            System.out.println("Martes:");
            try {
                martes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int miercoles = 0;
            System.out.println("Miércoles:");
            try {
                miercoles = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int jueves = 0;
            System.out.println("Jueves:");
            try {
                jueves = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int viernes = 0;
            System.out.println("Viernes:");
            try {
                viernes = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int sabado = 0;
            System.out.println("Sábado:");
            try {
                sabado = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            int domingo = 0;
            System.out.println("Domingo:");
            try {
                domingo = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decisión ingresada debe ser un número entero.\n");
                scanInt.nextLine();
                continue;}
            
            if((lunes==0||lunes==1)&&(martes==0||martes==1)&&(miercoles==0||miercoles==1)&&(jueves==0||jueves==1)&&(viernes==0||viernes==1)&&(sabado==0||sabado==1)&&(domingo==0||domingo==1)) {
                dias_disponibles = ""+lunes+martes+miercoles+jueves+viernes+sabado+domingo;
                validar_dias = false;}
            
            else {
                System.out.println("\n**ERROR** Las decisiones ingresadas no son válidas (los valores numéricos de cada día deben ser 0 o 1).\n");}}

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
        System.out.println(nombre_completo + " (" + tipos_de_perfiles[tipo_perfil-1] + "), su nombre de usuario es: " + usuario.getUsername() + ", y su password es: " + password + ".\n");
    
        // Guardar la información de los usuarios existentes en el archivo CSV
        try {gestor.guardarUsuariosCSV(lista_usuarios);} 
        catch (IOException e) {
            System.out.println("\n**ERROR**\nSe produjo un error al guardar la información de los usuarios del sistema.");}

        //Agregar usuario a la base de datos
        agregarUsuarioNeo4j(tipo_perfil, usuario, username_neo4j, password_neo4j, boltURL);}

    /**
	 * Acceso a un usuario y todas las opciones dentro de este.
	 * @param lista_usuarios La lista de usuarios del sistema. 
	 * @param lista_usernames La lista de los nombres de usuario de los usuarios del sistema.
	 * @param scanString El scanner para registrar los textos ingresados por el usuario.
	 * @param scanInt El scanner para registrar los números enteros ingresados por el usuario.
	 * @param gestor El gestor (instancia de la clase PersistenciaCSV) para guardar y leer los datos del csv.
     * @param username_neo4j El nombre de usuario de la base de datos Neo4j.
     * @param password_neo4j La contraseña de la base de datos Neo4j.
     * @param boltURL La URL de la base de datos Neo4j.
	 * @return Este método no devuelve nada.
	 */
	public static void iniciarSesion(ArrayList<ITipoUsuario> lista_usuarios, ArrayList<String> lista_usernames, Scanner scanString, Scanner scanInt, PersistenciaCSV gestor, String username_neo4j, String password_neo4j, String boltURL) {
	    	
        //En esta variable se registrará el nombre de usuario ingresado por el ususario
	    String username_ingresado = "";
	   
	    System.out.println("\nIngrese su correo electrónico institucional: ");
	    username_ingresado = scanString.nextLine().trim().toLowerCase();
	        	
		if(lista_usernames.contains(username_ingresado)) {//Si el username ingresado se encuentra en el sistema
			
			//Obtener y validar la contraseña del usuario
			System.out.println("Ingrese su password: ");
	    	String password = scanString.nextLine().trim();

			ITipoUsuario usuario_activo = lista_usuarios.get(lista_usernames.indexOf(username_ingresado));

			if(usuario_activo.getPassword().equals(password)) {//Si la contraseña ingresada es correcta 
		    
			usuario_activo.menuIndividual(usuario_activo,lista_usuarios,lista_usernames,scanString,scanInt,boltURL,username_neo4j,password_neo4j);}
			    
	    else {//Si la contraseña ingresada es incorrecta
			System.out.println("\nCONTRASEÑA INCORRECTA.\nLa contraseña ingresada no es correcta.");}}
	    
	    else {//Si el nombre de usuario ingresado no se encuentra entre los usernames del sistema
			System.out.println("\nUSUARIO NO ENCONTRADO.\nNo hay ningun usuario con el correo electrónico institucional ingresado. Verifique que el correo sea correcto o que su usuario ya exista.");}}

    public static void administrarBaseDeDatosNeo4j(String username_neo4j, String password_neo4j, String boltURL, Scanner scanString, Scanner scanInt, ArrayList<String> lista_usernames, ArrayList<ITipoUsuario> lista_usuarios) {
        
        boolean menu_adiminstracion = true;
        while(menu_adiminstracion){
            System.out.println("\nIngrese el numero correspondiente a la opcion que desea realizar:\n1. Eliminar usuario de la base de datos. \n2. Ver nodos y conexiones de la base de datos.\n3. Salir del menú de administración.");

            int decision_administracion = 0;
            try {decision_administracion = scanInt.nextInt();}
            catch(Exception e) {
                System.out.println("\n**ERROR** La decision ingresada debe ser un numero.");
                scanInt.nextLine();
                continue;}
            
            switch(decision_administracion) {

                case 1:{//Eliminar usuario de la base de datos
                    System.out.println("\n----------------[Eliminar usuario de la base de datos]----------------\n");
                    System.out.println("Ingrese el correo institucional del usuario que desea eliminar de la base de datos:");
                    String username = scanString.nextLine();

                    if(lista_usernames.contains(username)) {
                        int index = lista_usernames.indexOf(username);
                        try ( EmbeddedNeo4j db = new EmbeddedNeo4j(boltURL, username_neo4j, password_neo4j) )
                        {
                            db.eliminarUsuario(username);
                            System.out.println("\nOPERACION EXITOSA.\nEl usuario con el correo institucional " + username + " ha sido eliminado de la base de datos correctamente.");
                            lista_usernames.remove(index);
                            lista_usuarios.remove(index);
                            
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();}}
            
                    else {
                        System.out.println("\n**ERROR** El usuario con el correo ingresado no se encuentra en la base de datos.\n");}

                    break;}

                case 2:{//Ver nodos y conexiones de la base de datos
                    System.out.println("\n--------------------[Nodos y conexiones en Neo4j]--------------------\n");
                    try ( EmbeddedNeo4j db = new EmbeddedNeo4j(boltURL, username_neo4j, password_neo4j) )
                    {
                        db.printAllRelationshipsAndNodes();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();}
                    break;}

                case 3:{//Salir del menú de administración
                    menu_adiminstracion = false;
                    break;}
                
                default:{//Opción no disponible (programación defensiva)
                    System.out.println("\n**ERROR**\nEl numero ingresado no se encuentra entre las opciones disponibles.");}}}}

    public static void agregarUsuarioNeo4j(int tipo_perfil, ITipoUsuario usuario, String username_neo4j, String password_neo4j, String boltURL) {
        
        if(tipo_perfil==1) //Estudiante
        {
            try ( EmbeddedNeo4j db = new EmbeddedNeo4j(boltURL, username_neo4j, password_neo4j) )
        {   
		 	Estudiante estudiante = (Estudiante) usuario;
            String result = db.insertarEstudiante(estudiante);
		 	
		 	if (result.equalsIgnoreCase("OK")) {
		 		System.out.println("Estudiante insertado correctamente");
		 	}
        	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

        if(tipo_perfil==2) //Tutor
        {
            try ( EmbeddedNeo4j db = new EmbeddedNeo4j(boltURL, username_neo4j, password_neo4j) )
        {
		 	Tutor tutor = (Tutor) usuario;
            String result = db.insertarTutor(tutor);
		 	
		 	if (result.equalsIgnoreCase("OK")) {
		 		System.out.println("Tutor insertado correctamente");
		 	}
        	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
    }
    
}