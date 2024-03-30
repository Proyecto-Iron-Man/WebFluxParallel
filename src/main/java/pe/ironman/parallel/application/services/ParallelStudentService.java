package pe.ironman.parallel.application.services;

import pe.ironman.parallel.application.dtos.StudentCourseDto;
import reactor.core.publisher.Mono;

public interface ParallelStudentService {
    Mono<StudentCourseDto> getParallelStudentCourses(String documentNumber);
}
