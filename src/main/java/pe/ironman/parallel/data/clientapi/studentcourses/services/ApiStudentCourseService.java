package pe.ironman.parallel.data.clientapi.studentcourses.services;

import pe.ironman.parallel.data.clientapi.studentcourses.models.ApiStudentCourse;
import reactor.core.publisher.Flux;

public interface ApiStudentCourseService {
    Flux<ApiStudentCourse> getStudentCoursesByStudentId(Long studentId);
}
