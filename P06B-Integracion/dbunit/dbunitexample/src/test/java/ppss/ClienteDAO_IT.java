package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
//import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/* IMPORTANTE:
    Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
    vamos a usar "throws Esception" en los métodos, para que el código quede más
    legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
    con un assertDoesNotThrow()
    Es decir, que vamos a primar la legibilidad de los tests.
    Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
    invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
*/
public class ClienteDAO_IT {

    private ClienteDAO clienteDAO; //SUT
    private IDatabaseTester databaseTester; // Para acceder a la BD
    private IDatabaseConnection connection;

    @BeforeEach
    public void setUp() throws Exception {

        String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false";
        databaseTester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver", cadena_conexionDB, "root", "ppss");
        connection = databaseTester.getConnection(); // Obtenemos la conexión con la BD

        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testInsert() throws Exception {
        // Datos de entrada
        Cliente cliente = new Cliente(1,"John", "Smith");
        cliente.setDireccion("1 Main Street");
        cliente.setCiudad("Anycity");

        // Inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml"); // Dataset inicial: VACÍO
        // INyectamos el dataset
        databaseTester.setDataSet(dataSet);
        // Inicializamos la BD con el dataset inicial
        databaseTester.onSetup();

        // Invocamos a la sut
        Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

        // Recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        // Recuperamos los datos de la tabla "cliente" -> Resueltado real
        ITable actualTable = databaseDataSet.getTable("cliente");

        // Creamos el dataset con el resultado esperado -> Resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
        ITable expectedTable = expectedDataSet.getTable("cliente");

        Assertion.assertEquals(expectedTable, actualTable);

    }

    @Test
    public void testDelete() throws Exception {
        Cliente cliente =  new Cliente(1,"John", "Smith");
        cliente.setDireccion("1 Main Street");
        cliente.setCiudad("Anycity");

        //inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //invocamos a la SUT
        Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("cliente");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
        ITable expectedTable = expectedDataSet.getTable("cliente");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void test_insert2() throws Exception {
        Cliente cliente = new Cliente(3,"Carl", "Martinez");
        cliente.setDireccion("3 Main Street");
        cliente.setCiudad("Crevillente");

        //inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-insert2.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        //invocamos a la sut
        Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("cliente");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init-insert2-esperado.xml");
        ITable expectedTable = expectedDataSet.getTable("cliente");

        Assertion.assertEquals(expectedTable, actualTable);

    }

    @Test
    public void testUpdate() throws Exception {
        Cliente cliente =  new Cliente(1,"John", "Smith");
        cliente.setDireccion("Other Street");
        cliente.setCiudad("NewCity");

        //inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        // Se usa el update para modificar los datos de un cliente  ya existente
        Assertions.assertDoesNotThrow(()->clienteDAO.update(cliente));

        IDataSet databaseDataSet = connection.createDataSet();
        //Recuperamos los datos de la tabla cliente
        ITable actualTable = databaseDataSet.getTable("cliente");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-updated.xml");
        ITable expectedTable = expectedDataSet.getTable("cliente");

        Assertion.assertEquals(expectedTable, actualTable);

    }

    @Test
    public void testRetrieve() throws Exception {
        Cliente clienteEsperado =  new Cliente(1,"John", "Smith");
        clienteEsperado.setDireccion("1 Main Street");
        clienteEsperado.setCiudad("Anycity");

        //inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();

        // ClienteDAO tiene un método retrieve que dado un id, devuelve el cliente con dicho id.
        Cliente clienteReal = Assertions.assertDoesNotThrow(()->clienteDAO.retrieve(1));

        assertAll("Cliente",
                () -> assertEquals(clienteEsperado.getNombre(), clienteReal.getNombre()),
                () -> assertEquals(clienteEsperado.getApellido(), clienteReal.getApellido()),
                () -> assertEquals(clienteEsperado.getCiudad(), clienteReal.getCiudad()),
                () -> assertEquals(clienteEsperado.getDireccion(), clienteReal.getDireccion()),
                () -> assertEquals(clienteEsperado.getId(), clienteReal.getId())
        );
    }

}
