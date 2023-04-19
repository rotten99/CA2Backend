package rest;

import entities.BugReport;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BugReportRessourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static BugReport br1;
    private static BugReport br2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        br1 = new BugReport("Bug1", true);
        br2 = new BugReport("Bug2", true);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("BugReport.deleteAllRows").executeUpdate();
            em.persist(br1);
            em.persist(br2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This tests the endpoint /bugreport/edit, which should edit a bugreport
    @Order(1)
    @Test
    public void testEditBugReport() {
        given()
                .contentType("application/json")
                .body("{\"id\": "+ br1.getId()+", \"description\": \"Bug1\", \"isFixed\": false}")
                .when().put("/bugreport/edit").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("isFixed", equalTo(false));
    }

    //This tests the endpoint /bugreport/all, which should return a list of all bugreports
    @Order(2)
    @Test
    public void testGetAllBugReports() {
        given()
                .contentType("application/json")
                .when().get("/bugreport/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }


    //This tests the endpoint /bugreport/create, which should create a bugreport
    @Order(3)
    @Test
    public void testCreateBugReport() {
        given()
                .contentType("application/json")
                .body("{\"description\": \"Bug3\", \"isFixed\": false}")
                .when().post("/bugreport/create").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("description", equalTo("Bug3"));
    }


}
