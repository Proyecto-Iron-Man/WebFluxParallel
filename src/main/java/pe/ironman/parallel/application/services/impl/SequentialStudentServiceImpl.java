package pe.ironman.parallel.application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ironman.parallel.application.dtos.CourseDto;
import pe.ironman.parallel.application.dtos.StudentCourseDto;
import pe.ironman.parallel.application.dtos.StudentDto;
import pe.ironman.parallel.application.services.SequentialStudentService;
import pe.ironman.parallel.data.clientapi.studentcareers.models.ApiStudentCareer;
import pe.ironman.parallel.data.clientapi.studentcareers.services.ApiStudentCareerService;
import pe.ironman.parallel.data.clientapi.studentcourses.models.ApiStudentCourse;
import pe.ironman.parallel.data.clientapi.studentcourses.services.ApiStudentCourseService;
import pe.ironman.parallel.data.clientapi.students.models.ApiStudent;
import pe.ironman.parallel.data.clientapi.students.services.ApiStudentService;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SequentialStudentServiceImpl implements SequentialStudentService {
    private final ApiStudentService apiStudentService;
    private final ApiStudentCareerService apiStudentCareerService;
    private final ApiStudentCourseService apiStudentCourseService;

    @Override
    public Mono<StudentCourseDto> getStudentCourses(String documentNumber) {
        return apiStudentService.getStudentByDocumentNumber(documentNumber)
                .flatMap(apiStudent -> {
                    return apiStudentCareerService
                            .getStudentCareerByStudentId(apiStudent.getId())
                            .flatMap(apiStudentCareer -> getStudentCourse(apiStudent, apiStudentCareer));
                });
    }

    private Mono<StudentCourseDto> getStudentCourse(ApiStudent apiStudent, ApiStudentCareer apiStudentCareer) {
        var student = studentBuild(apiStudent, apiStudentCareer);

        return apiStudentCourseService
                .getStudentCoursesByStudentId(apiStudent.getId())
                .map(this::courseBuild)
                .collectList()
                .map(courses -> studentCourseBuild(courses, student));
    }

    private StudentCourseDto studentCourseBuild(List<CourseDto> courses, StudentDto student) {
        return StudentCourseDto.builder()
                .student(student)
                .courses(courses)
                .build();
    }

    private StudentDto studentBuild(ApiStudent apiStudent, ApiStudentCareer apiStudentCareer) {
        return StudentDto.builder()
                .documentNumber(apiStudent.getDocumentNumber())
                .name(apiStudent.getName())
                .email(apiStudent.getEmail())
                .careerName(apiStudentCareer.getCareerName())
                .careerCredits(apiStudentCareer.getCareerCredits())
                .percentageCompleted(apiStudentCareer.getPercentageCompleted())
                .build();
    }

    private CourseDto courseBuild(ApiStudentCourse apiStudentCourse) {
        return CourseDto.builder()
                .name(apiStudentCourse.getCourseName())
                .credits(apiStudentCourse.getCourseCredits())
                .duration(apiStudentCourse.getCourseDuration())
                .build();
    }
}