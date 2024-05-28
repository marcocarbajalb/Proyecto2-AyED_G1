 //Importar todas las librerías que harán falta para el programa
import java.lang.AutoCloseable;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.summary.ResultSummary;
import static org.neo4j.driver.Values.parameters;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.Value;


/**
 * Clase que se encargará de manejar la base de datos de Neo4j.
 * @author Marco Carbajal, Carlos Aldana, Carlos Angel y Diego Monroy
 * @version 20.0.1, 08/05/2024
 */
public class EmbeddedNeo4j implements AutoCloseable {

    private final Driver driver;
    
    public EmbeddedNeo4j( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public String insertarEstudiante(Estudiante estudiante) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "CREATE (Test:Estudiante {correo:'" + estudiante.username + "', carné:"+ estudiante.carnet +", nombre_completo:'"+ estudiante.nombre_completo +"'})");                    
                    return "OK";
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

    public String insertarTutor(Tutor tutor) {
    try ( Session session = driver.session() ) {
        String result = session.writeTransaction( new TransactionWork<String>() {
            @Override
            public String execute( Transaction tx ) 
                {
                    tx.run( "CREATE (Test:Tutor {correo:'" + tutor.username + "', carné:"+ tutor.carnet +", nombre_completo:'"+ tutor.nombre_completo +"'})");
                    return "OK";
                }
            }
        
        );
        return result;
        } catch (Exception e) {
        return e.getMessage();
        }
    }

    public Map<String, String> generarTablaHash() {
        Map<String, String> tablaHash = new HashMap<>();
    
        // Obtener todos los nodos
        List<Record> nodos = obtenerTodosLosNodos();
    
        // Leer el archivo CSV
        List<List<String>> datosCsv = leerCsv("usuarios.csv");
    
        // Llenar la tabla hash
        for (Record nodo : nodos) {
            String usernameNodo = nodo.get("username").asString();
    
            for (List<String> filaCsv : datosCsv) {
                String usernameCsv = filaCsv.get(2);
    
                if (usernameNodo.equals(usernameCsv)) {
                    tablaHash.put(usernameNodo, usernameCsv);
                    break;
                }
            }
        }
    
        return tablaHash;
    }
    
    public List<Record> obtenerTodosLosNodos() {
        try ( Session session = driver.session() ) {
            List<Record> result = session.readTransaction( new TransactionWork<List<Record>>() {
                @Override
                public List<Record> execute( Transaction tx ) {
                    Result result = tx.run( "MATCH (n) RETURN n");
                    return result.list();
                }
            });
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<List<String>> leerCsv(String rutaArchivo) {
        List<List<String>> datos = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                datos.add(Arrays.asList(linea.split(",")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return datos;
    }

    public static int calcularPonderacion(Tutor tutor, Estudiante estudiante) {
        int ponderacion = 200;

        // 1. Dominio acerca del tema
        ponderacion -= (Math.abs(tutor.calculo - estudiante.calculo));
        ponderacion -= (Math.abs(tutor.algebra - estudiante.algebra));
        ponderacion -= (Math.abs(tutor.fisica - estudiante.fisica));
        ponderacion -= (Math.abs(tutor.quimica - estudiante.quimica));
        ponderacion -= (Math.abs(tutor.estadistica - estudiante.estadistica));
        ponderacion -= (Math.abs(tutor.programacion - estudiante.programacion));

        // 2. Precio
        if (tutor.min <= estudiante.max || tutor.max >= estudiante.min) {
            ponderacion -= 15;
        }

        // 3. Compatibilidad de horarios
        int disponibilidadScore = 0;
        for (int i = 0; i < 7; i++) {
            if (tutor.dias_disponibles.charAt(i) == '1' && estudiante.dias_disponibles.charAt(i) == '1') {
                disponibilidadScore++;
            }
        }
        ponderacion -= disponibilidadScore * 5;

        // 4. Preferencia sobre método
        if (tutor.modalidad == estudiante.modalidad) {
            ponderacion -= 10;
        }

        // 5. Edad (muy poco importante)
        if (tutor.edad > estudiante.edad) {
            ponderacion -= 2;
        }

        // 6. Sexo (casi irrelevante)
        if (tutor.gender == estudiante.gender) {
            ponderacion -= 1;
        }

        return ponderacion;
    }
}