package rest;

import entities.BugReport;
import entities.DifficultyChecker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DifficultyCheckerTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static DifficultyChecker dc1;
    private static DifficultyChecker dc2;

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
        dc1 = new DifficultyChecker("country1","flag1", 6);
        dc2 = new DifficultyChecker("country2","flag2", 1);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("DifficultyChecker.deleteAllRows").executeUpdate();
            em.persist(dc1);
            em.persist(dc2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    //This test the endpoint /difficulty, which returns a list of all difficultyChecker objects
    @Test
    public void testGetDFs() {
        given()
                .contentType(ContentType.JSON)
                .get("/difficulty").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(2));
    }

    //This test the endpoint /difficulty/create, which creates a new difficultyChecker object
    @Test
    public void testCreateDF() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"countryName\":\"country3\",\"flagURL\":\"flag3\",\"timesNotAnswered\":3}")
                .when()
                .post("/difficulty/create")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("countryName", equalTo("country3"));
    }

    //This tests the endpoint /difficulty/edit, which edits a difficultyChecker object
    @Test
    public void testEditDF() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"id\": "+ dc1.getId()+", \"countryName\":\"country1\",\"flagURL\":\"flag1\",\"timesNotAnswered\":3}")
                .when()
                .put("/difficulty/edit")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("timesNotAnswered", equalTo(3));
    }
}
