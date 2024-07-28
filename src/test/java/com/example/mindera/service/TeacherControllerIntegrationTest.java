package com.example.mindera.service;


import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.dto.TeacherCreationDto;
import com.example.mindera.dto.TeacherDto;
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
public class TeacherControllerIntegrationTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        teacherService.deleteAllTeachers();
    }

    @Nested
    class crudTeacher {
        @Test
        void getAllTeachersReturnsEmptyList() {
            List<TeacherDto> teacherDtoList =
                    given()
                            .when()
                            .get("api/v1/teachers/all")
                            .then()
                            .statusCode(200).extract().body().jsonPath().getList(".", TeacherDto.class);

            Assertions.assertEquals(0, teacherDtoList.size());
        }

        @Test
        void getAllTeachersReturnsAllTeachers() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            List<TeacherDto> teacherDtoList =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherDto)
                            .when()
                            .get("api/v1/teachers/all")
                            .then()
                            .statusCode(200).extract().body().jsonPath().getList(".", TeacherDto.class);

            Assertions.assertEquals(1, teacherDtoList.size());
        }

        @Test
        void createTeacherReturns201() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);
        }

        @Test
        void getTeacherByIdReturns200() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .get("api/v1/teachers/" + teacherId)
                    .then()
                    .statusCode(200);
        }

        @Test
        void updateTeachersNameReturn200() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            teacherDto.setName("Maria");

            given()
                    .contentType(ContentType.JSON)
                    .body(teacherDto)
                    .when()
                    .patch("api/v1/teachers/update/" + teacherId)
                    .then()
                    .statusCode(200).extract().body().as(TeacherDto.class);

            given()
                    .when()
                    .get("api/v1/teachers/" + teacherId)
                    .then()
                    .statusCode(200);

            Assertions.assertEquals("Maria", teacherDto.getName());
        }

        @Test
        void addCourseReturns200() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

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
                            .body(courseCreationDto)
                            .when()
                            .post("api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            teacherDto.setCourses(List.of(courseDto));

            given()
                    .when()
                    .put("api/v1/teachers/" + teacherId + "/course/" + courseId)
                    .then()
                    .statusCode(200);

            Assertions.assertEquals(teacherDto.getCourses().size(), 1);
        }

        @Test
        void deleteTeacherReturns204() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .delete("api/v1/teachers/delete/" + teacherId)
                    .then()
                    .statusCode(204);
        }
    }

    @Nested
    class validation {
        @Test
        void createTeacherWithNullName() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    null
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(400).extract().body().as(TeacherDto.class);

        }

        @Test
        void createTeacherWithEmptyName() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    " "
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(400).extract().body().as(TeacherDto.class);

        }

        @Test
        void getTeacherByIdWithEmptyId() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .get("api/v1/teachers/" + " ")
                    .then()
                    .statusCode(404);

        }

        @Test
        void getTeacherByIdWithNullId() {
            TeacherCreationDto teacherCreationDto = new TeacherCreationDto(
                    "Ana"
            );
            TeacherDto teacherDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().as(TeacherDto.class);

            String teacherId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(teacherCreationDto)
                            .when()
                            .post("api/v1/teachers")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .get("api/v1/teachers/" + null)
                    .then()
                    .statusCode(400);

        }
    }
}
