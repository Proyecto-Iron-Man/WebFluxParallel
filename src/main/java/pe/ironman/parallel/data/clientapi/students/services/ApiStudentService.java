package pe.ironman.parallel.data.clientapi.students.services;

import pe.ironman.parallel.data.clientapi.students.models.ApiStudent;
import reactor.core.publisher.Mono;

public interface ApiStudentService {

    Mono<ApiStudent> getStudentByDocumentNumber(String documentNumber);
}
