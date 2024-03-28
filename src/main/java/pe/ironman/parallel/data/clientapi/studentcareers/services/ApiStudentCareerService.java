package pe.ironman.parallel.data.clientapi.studentcareers.services;

import pe.ironman.parallel.data.clientapi.studentcareers.models.ApiStudentCareer;
import reactor.core.publisher.Mono;

public interface ApiStudentCareerService {
    Mono<ApiStudentCareer> getStudentCareerByStudentId(Long studentId);
}
