package com.example.mindera.service;

import com.example.mindera.dto.CourseCreationDto;
import com.example.mindera.dto.CourseDto;
import com.example.mindera.dto.StudentCreationDto;
import com.example.mindera.dto.StudentDto;
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
class StudentControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        courseService.deleteAllCourses();
        studentService.deleteAllStudents();
    }


    @Nested
    class crudStudents {
        @Test
        void getAllStudentsReturnsEmptyList() {
            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)  //pq status code é 200?
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(0, students.size());
        }

        @Test
        void getAllStudentsReturnsAllStudents() {
            StudentCreationDto student = new StudentCreationDto(
                    "Mindera"
            );

            studentService.addStudent(student);
            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(1, students.size());

        }

        @Test
        void createStudentReturns200AndStudent() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            Assertions.assertEquals(studentDto2.getName(), "Mindera");
            Assertions.assertNotNull(studentDto2.getId());

            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(1, students.size());

        }

        @Test
        void getStudentByIdReturns200AndStudent() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            StudentDto studentDto =
                    given()
                            .when()
                            .get("/api/v1/students/" + studentId)
                            .then()
                            .statusCode(200).extract().body().as(StudentDto.class);

            Assertions.assertEquals(studentDto.getName(), "Mindera");

        }

        @Test
        void updateStudentReturns200AndStudent() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            StudentDto studentDto =
                    given()
                            .when()
                            .get("/api/v1/students/" + studentId)
                            .then()
                            .statusCode(200).extract().body().as(StudentDto.class);

            studentDto.setName("Blip");

            given()
                    .contentType(ContentType.JSON)
                    .body(studentDto)
                    .when()
                    .patch("/api/v1/students/update-student-name/" + studentId)
                    .then()
                    .statusCode(200).extract().body().as(StudentDto.class);

            StudentDto studentDto3 =
                    given()
                            .when()
                            .get("/api/v1/students/" + studentId)
                            .then()
                            .statusCode(200)
                            .extract().body().as(StudentDto.class);

            Assertions.assertEquals(studentDto.getName(), "Blip");
        }

        @Test
        void addCourseReturns200AndStudent() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto2)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            StudentDto studentDto =
                    given()
                            .when()
                            .get("/api/v1/students/" + studentId)
                            .then()
                            .statusCode(200).extract().body().as(StudentDto.class);


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
                            .post("/api/v1/courses")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            CourseDto courseDto1 =
                    given()
                            .when()
                            .get("/api/v1/courses/" + courseId)
                            .then()
                            .statusCode(200).extract().body().as(CourseDto.class);


            studentDto.setCourses(List.of(courseDto1));

            given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .put("/api/v1/students/" + studentId + "/course/" + courseId)
                    .then()
                    .statusCode(200).extract().body().as(StudentDto.class);
            Assertions.assertEquals(studentDto.getCourses().size(), 1);
        }

        @Test
        void deleteStudentReturns200() {
            StudentCreationDto studentCreationDto = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentCreationDto)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);
            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentCreationDto)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .delete("/api/v1/students/delete/" + studentId)
                    .then()
                    .statusCode(204);
        }
    }

    @Nested
    class validation {
        @Test
        void createStudentWithNullName() {
            StudentCreationDto student = new StudentCreationDto(
                    null
            );

            given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students")
                    .then()
                    .statusCode(400);
        }

        @Test
        void createStudentWithEmptyName() {
            StudentCreationDto student = new StudentCreationDto(
                    ""
            );

            given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students")
                    .then()
                    .statusCode(400);
        }

        @Test
        void getStudentByIdWithEmptyId() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            StudentDto studentDto =
                    given()
                            .when()
                            .get("/api/v1/students/" + " ")
                            .then()
                            .statusCode(404).extract().body().as(StudentDto.class);


        }

        @Test
        void getStudentByIdWithNullId() {
            StudentCreationDto studentDto1 = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto2 =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto1)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            StudentDto studentDto =
                    given()
                            .when()
                            .get("/api/v1/students/" + null)
                            .then()
                            .statusCode(400).extract().body().as(StudentDto.class);


        }

     /*   @Test
        void getStudentByIdWithNotExistingId() {
            StudentCreationDto studentCreationDto = new StudentCreationDto(
                    "Mindera"
            );

            StudentDto studentDto =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentCreationDto)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().as(StudentDto.class);

            String studentId =
                    given()
                            .contentType(ContentType.JSON)
                            .body(studentDto)
                            .when()
                            .post("/api/v1/students")
                            .then()
                            .statusCode(201).extract().body().jsonPath().getString("id");

            String studentWithIdThatDoesnExists = "3";

            StudentDto studentDto1 =
                    given()
                            .when()
                            .get("api/v1/students/" + studentWithIdThatDoesnExists)
                            .then()
                            .statusCode(404).extract().body().as(StudentDto.class);
        }*/
    }
}