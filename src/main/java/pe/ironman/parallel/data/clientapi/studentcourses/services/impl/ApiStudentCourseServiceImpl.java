package pe.ironman.parallel.data.clientapi.studentcourses.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.ironman.parallel.data.clientapi.studentcourses.models.ApiStudentCourse;
import pe.ironman.parallel.data.clientapi.studentcourses.services.ApiStudentCourseService;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
public class ApiStudentCourseServiceImpl implements ApiStudentCourseService {
    @Override
    public Flux<ApiStudentCourse> getStudentCoursesByStudentId(Long studentId) {
        if(222 == studentId) {
            return Flux.empty();
        }

        if(2222 == studentId) {
            return Flux.error(new RuntimeException("Error"));
        }


        return Flux.fromIterable(getStudentCourses(studentId))
                .doOnNext(item -> log.info("getStudentCoursesByStudentId executed on thread: " + Thread.currentThread().getName()))
                .delayElements(ofSeconds(1))
                ;
    }

    private List<ApiStudentCourse> getStudentCourses(Long studentId) {
        return List.of(
                ApiStudentCourse.builder()
                        .courseId(1L)
                        .studentId(studentId)
                        .courseName("Math")
                        .courseCredits(5)
                        .courseDuration(40)
                        .build(),
                ApiStudentCourse.builder()
                        .courseId(2L)
                        .studentId(studentId)
                        .courseName("Science")
                        .courseCredits(4)
                        .courseDuration(36)
                        .build(),
                ApiStudentCourse.builder()
                        .courseId(3L)
                        .studentId(studentId)
                        .courseName("English")
                        .courseCredits(3)
                        .courseDuration(32)
                        .build()
        );
    }

}
