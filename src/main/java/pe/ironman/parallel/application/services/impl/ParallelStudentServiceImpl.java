package pe.ironman.parallel.application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.ironman.parallel.application.dtos.CourseDto;
import pe.ironman.parallel.application.dtos.StudentCourseDto;
import pe.ironman.parallel.application.dtos.StudentDto;
import pe.ironman.parallel.application.services.ParallelStudentService;
import pe.ironman.parallel.data.clientapi.studentcareers.models.ApiStudentCareer;
import pe.ironman.parallel.data.clientapi.studentcareers.services.ApiStudentCareerService;
import pe.ironman.parallel.data.clientapi.studentcourses.services.ApiStudentCourseService;
import pe.ironman.parallel.data.clientapi.students.services.ApiStudentService;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ParallelStudentServiceImpl implements ParallelStudentService {
    private final ApiStudentService apiStudentService;
    private final ApiStudentCareerService apiStudentCareerService;
    private final ApiStudentCourseService apiStudentCourseService;


    @Override
    public Mono<StudentCourseDto> getStudentCourses(String documentNumber) {
        var response = apiStudentService.getStudentByDocumentNumber(documentNumber)
                .flatMap(apiStudent ->{

                    var prepareApiStudentCareer = apiStudentCareerService
                            .getStudentCareerByStudentId(apiStudent.getId())
                            .onErrorResume(e -> Mono.empty())
                            .switchIfEmpty(Mono.just(ApiStudentCareer.builder().build()));

                    var prepareApiStudentCourse = apiStudentCourseService
                            .getStudentCoursesByStudentId(apiStudent.getId())
                            .collectList()
                            .onErrorResume(e -> Mono.just(new ArrayList<>()));

                    return Mono.zip(prepareApiStudentCareer, prepareApiStudentCourse, (apiStudentCareer, apiStudentCourses) -> {
                        var student = StudentDto.builder()
                                .documentNumber(apiStudent.getDocumentNumber())
                                .name(apiStudent.getName())
                                .email(apiStudent.getEmail())
                                .careerName(apiStudentCareer.getCareerName())
                                .careerCredits(apiStudentCareer.getCareerCredits())
                                .percentageCompleted(apiStudentCareer.getPercentageCompleted())
                                .build();

                        var courses = apiStudentCourses.stream()
                                .map(apiStudentCourse -> CourseDto.builder()
                                        .name(apiStudentCourse.getCourseName())
                                        .credits(apiStudentCourse.getCourseCredits())
                                        .duration(apiStudentCourse.getCourseDuration())
                                        .build())
                                .toList();

                        return StudentCourseDto.builder()
                                .student(student)
                                .courses(courses)
                                .build();
                    });
                });

        return response;
    }
}
