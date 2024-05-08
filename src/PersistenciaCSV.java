//Importar las librerías que harán falta para el programa
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;

/**
* Esta clase se encargará de manejar la lectura y escritura de datos en los archivos csv en los que se almacenarán los datos del sistema de recomendación de tutorías (persistencia).
* @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
* @version 20.0.1, 08/05/2024
*/
public class PersistenciaCSV {
    
    private static final String[] encabezados_usuarios = {"nombre_completo", "carnet", "username", "password", "edad", "gender", "dias_disponibles", "calculo", "algebra", "fisica", "quimica", "estadistica", "programacion", "modalidad", "min", "max", "tipo_perfil"};

    /**
	* Escribe los datos de los usuarios brindados en el archivo csv.
	* @param lista_usuarios La lista de usuarios a escribir en el archivo csv.
	* @return Este método no devuelve nada.
	* @throws IOException Si ocurre un error al escribir (o encontrar) el archivo.
	*/
    public void guardarUsuariosCSV(ArrayList<ITipoUsuario> lista_usuarios) throws IOException {
    	
    	//Abrir el escritor y darle la ruta
    	FileWriter fileName = new FileWriter("usuarios.csv");
    	BufferedWriter escritura = new BufferedWriter(fileName);
    			
    	//Escribir los encabezados en el csv
    	String encabezado = String.join(",", encabezados_usuarios);
    	escritura.write(encabezado);
    	escritura.newLine();
    			
    	//Escribir los datos del los tickets de la lista al csv
    	for(int i=0;i<lista_usuarios.size();i++) {
    		String linea = lista_usuarios.get(i).formatoCSV();
    		escritura.write(linea);
    		escritura.newLine();}
    			
    	//Cerrar el escritor
    	escritura.close();}
	
    /**
	 * Lee los datos de los usuarios desde el archivo csv.
	 * @return La lista de usuarios leída del archivo csv.
	 */
	public ArrayList<ITipoUsuario> cargarUsuariosCSV() {
		
        String filename = "usuarios.csv";
		ArrayList<ITipoUsuario> lista_usuarios = new ArrayList<>();
        BufferedReader fileReader = null;
		
		try {
			String line = "";
			fileReader = new BufferedReader(new FileReader(filename));
			fileReader.readLine();
			
			while ((line = fileReader.readLine()) != null) {
				String[] datos = line.split(",");
				
            ITipoUsuario usuario = TipoUsuarioFactory.getTipoUsuarioInstance(Integer.parseInt(datos[16]));
            usuario.setUsername(datos[2]);
            usuario.setPassword(datos[3]);
            usuario.setNombreCompleto(datos[0]);
            usuario.setCarnet(Integer.parseInt(datos[1]));
            usuario.setEdad(Integer.parseInt(datos[4]));
            usuario.setGender(Boolean.parseBoolean(datos[5]));
            usuario.setDias_disponibles(datos[6]);
            usuario.setCalculo(Integer.parseInt(datos[7]));
            usuario.setAlgebra(Integer.parseInt(datos[8]));
            usuario.setFisica(Integer.parseInt(datos[9]));
            usuario.setQuimica(Integer.parseInt(datos[10]));
            usuario.setEstadistica(Integer.parseInt(datos[11]));
            usuario.setProgramacion(Integer.parseInt(datos[12]));
            usuario.setModalidad(Boolean.parseBoolean(datos[13]));
            usuario.setMin(Integer.parseInt(datos[14]));
            usuario.setMax(Integer.parseInt(datos[15]));
            lista_usuarios.add(usuario);}} 
		
		catch(FileNotFoundException ex) {}//No se debe hacer nada, ya que solo sucederá cuando sea la primera vez que se corre el programa y aún no se ha creado el archivo.
		
		catch (IOException e) {
			System.out.println("Error al cargar los datos del archivo CSV.");}
		
		finally {
			try {
				if (fileReader != null) {
					fileReader.close();}}
				
				catch (IOException e) {
				System.out.println("Error al cerrar el lector de archivos.");}}
		
		return lista_usuarios;}

	/**
	 * Genera una lista con los usernames de los usuarios brindados.
	 * @param lista_usuarios La lista de usuarios de los que se desea extraer el username.
	 * @return La lista de usernames de los usuarios brindados.
	 */
	public ArrayList<String> extraerUsernamesUsuarios(ArrayList<ITipoUsuario> lista_usuarios) {
		ArrayList<String> lista_usernames = new ArrayList<>();
		
		for (ITipoUsuario usuario : lista_usuarios) {
			lista_usernames.add(usuario.getUsername());}
		
		return lista_usernames;}   
}
