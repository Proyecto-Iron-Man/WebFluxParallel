package pe.ironman.parallel.data.clientapi.students.services.impl;

import org.springframework.stereotype.Service;
import pe.ironman.parallel.data.clientapi.students.models.ApiStudent;
import pe.ironman.parallel.data.clientapi.students.services.ApiStudentService;
import reactor.core.publisher.Mono;

@Service
public class ApiStudentServiceImpl implements ApiStudentService {
    @Override
    public Mono<ApiStudent> getStudentByDocumentNumber(String documentNumber) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var id = 1000L;
        if (documentNumber.matches("\\d+")) {
             id = Long.parseLong(documentNumber);
        }

        var student = ApiStudent.builder()
                .id(id)
                .documentNumber(documentNumber)
                .name("John Doe")
                .email("jdoe@gmail.com")
                .phone("123456789")
                .address("123 Main St")
                .build();

        return Mono.just(student);
    }
}

