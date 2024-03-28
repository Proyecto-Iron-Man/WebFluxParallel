package pe.ironman.parallel.data.clientapi.studentcareers.services.impl;

import org.springframework.stereotype.Service;
import pe.ironman.parallel.data.clientapi.studentcareers.models.ApiStudentCareer;
import pe.ironman.parallel.data.clientapi.studentcareers.services.ApiStudentCareerService;
import reactor.core.publisher.Mono;

@Service
public class ApiStudentCareerServiceImpl implements ApiStudentCareerService {
    @Override
    public Mono<ApiStudentCareer> getStudentCareerByStudentId(Long studentId) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(111 == studentId) {
            return Mono.empty();
        }

        if(1111 == studentId) {
            return Mono.error(new RuntimeException("Error getting student career"));
        }


        var career = ApiStudentCareer.builder()
                .careerId(10L)
                .studentId(studentId)
                .careerName("Computer Science")
                .careerCredits(200)
                .percentageCompleted(50)
                .build();

        return Mono.just(career);
    }
}
