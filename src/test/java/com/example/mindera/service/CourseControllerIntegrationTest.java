package com.example.mindera.service;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CourseControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        courseService.deleteAllCourses();
    }

    @Nested
    class crudCourses {
        @Test
        void createCourseReturns200AndCourse() {
            CourseCreationDto course = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(course)
                            .when()
                            .post("/api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            String courseId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            CourseDto courseDto1 =
                    given()
                            .when()
                            .get("/api/v1/courses/" + courseId)
                            .then()
                            .statusCode(200).extract().body().as(CourseDto.class);

            Assertions.assertEquals(courseDto.getName(), "Java");
        }

        @Test
        void getAllCoursesReturnsAll() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            List<CourseDto> courseDtoList =
                    given()
                            .when()
                            .get("api/v1/courses/all")
                            .then()
                            .statusCode(200).extract().body().jsonPath().getList(".", CourseDto.class);

            Assertions.assertEquals(1, courseDtoList.size());
        }

        @Test
        void gettAllStudentsReturnsEmptyList() {
            List<CourseDto> courseDtoList =
                    given()
                            .when()
                            .get("api/v1/courses/all")
                            .then()
                            .statusCode(200).extract().body().jsonPath().getList(".", CourseDto.class);
        }

        @Test
        void getCourseByIdReturns200AndCourse() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            String courseId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            CourseDto courseDto1 =
                    given()
                            .when()
                            .get("api/v1/courses/" + courseId)
                            .then()
                            .statusCode(200).extract().body().as(CourseDto.class);

            Assertions.assertEquals(courseDto.getName(), "Java");
        }

        @Test
        void updateCourseReturns200andCourse() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            String courseId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            CourseDto courseDto1 = given()
                    .when()
                    .get("api/v1/courses/" + courseId)
                    .then()
                    .statusCode(200).extract().body().as(CourseDto.class);

            courseDto.setName("Python");

            given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .put("api/v1/courses/update/" + courseId)
                    .then()
                    .statusCode(200).extract().body().as(CourseDto.class);

            CourseDto courseDto2 =
                    given()
                            .when()
                            .get("api/v1/courses/" + courseId)
                            .then()
                            .statusCode(200).extract().body().as(CourseDto.class);


            Assertions.assertEquals(courseDto.getName(), "Python");
        }

        @Test
        void deleteCourseReturns200() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            String courseId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .delete("api/v1/courses/delete/" + courseId)
                    .then()
                    .statusCode(204);
        }

    }

    @Nested
    class validation {
        @Test
        void createCourseWithNullName() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    null
            );
            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(400).extract().body().as(CourseDto.class);
        }

        @Test
        void createCourseWiyhEmptyName() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    " "
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(400).extract().body().as(CourseDto.class);
        }

        @Test
        void getCourseByIdWithEmptyId() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .get("api/v1/courses/" + " ")
                    .then()
                    .statusCode(404).extract().body().as(CourseDto.class);
        }

        @Test
        void getCourseByIdWithNullId() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .get("api/v1/courses/" + null)
                    .then()
                    .statusCode(400).extract().body().as(CourseDto.class);
        }

    /*    @Test
        void getCourseByIdThatDoesntExists() {
            CourseCreationDto courseCreationDto = new CourseCreationDto(
                    "Java"
            );

            CourseDto courseDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().as(CourseDto.class);

            String courseId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(courseDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            String idThatDoenstExists = "2";

            given()

                    .when()
                    .get("api/v1/courses/" + idThatDoenstExists)
                    .then()
                    .statusCode(404).extract().body().as(CourseDto.class);
        }*/
    }
}
