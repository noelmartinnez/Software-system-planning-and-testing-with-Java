package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integracion-fase1")
class AlumnoDAOIT {
    IAlumnoDAO alumnoDAO; //SUT

    private IDatabaseTester databaseTester;
    private IDatabaseConnection connection;

    @BeforeEach
    void setUp() throws Exception {
        String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?useSSL=false";
        databaseTester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver", cadena_conexionDB, "root", "ppss");
        //obtenemos la conexión con la BD
        connection = databaseTester.getConnection();

        alumnoDAO = new FactoriaDAO().getAlumnoDAO();
    }

    @Test
    void testA1() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, Month.FEBRUARY, 22));

        //Inicializamos el dataSet con los datos iniciales de la tabla cliente
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla3-init.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();

        // SUT a probar
        assertDoesNotThrow(() -> alumnoDAO.addAlumno(alumno));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        //Recuperamos los datos de la tabla cliente
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla3.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(actualTable, expectedTable);
    }

    @Test
    void testA2() {
        String excepcionEsperada = "Error al conectar con BD";
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        DAOException excepcionReal = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        Assertions.assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void testA3() {
        String excepcionEsperada = "Error al conectar con BD";
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("44444444D");
        alumno.setNombre(null);
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        DAOException excepcionReal = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        Assertions.assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void testA4() {
        String excepcionEsperada = "Alumno nulo";
        AlumnoTO alumno = null;

        DAOException excepcionReal = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        Assertions.assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void testA5() {
        String excepcionEsperada = "Error al conectar con BD";
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif(null);
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        DAOException excepcionReal = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        Assertions.assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }

    @Test
    void testB1() throws Exception {
        String nif =  "11111111A";

        //Inicializamos el dataSet con los datos iniciales de la tabla cliente
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        //Inyectamos el dataset en el objeto databaseTester
        databaseTester.setDataSet(dataSet);
        //inicializamos la base de datos con los contenidos del dataset
        databaseTester.onSetup();

        assertDoesNotThrow(() -> alumnoDAO.delAlumno(nif));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        //Recuperamos los datos de la tabla cliente
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla4.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(actualTable, expectedTable);
    }

    @Test
    void testB2() {
        String nif = "33333333C";
        String excepcionEsperada = "No se ha borrado ningun alumno";

        DAOException excepcionReal = assertThrows(DAOException.class, () -> alumnoDAO.delAlumno(nif));
        assertEquals(excepcionEsperada, excepcionReal.getMessage());
    }
}
