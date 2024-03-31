package pe.ironman.parallel.data.clientapi.studentcareers.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.ironman.parallel.data.clientapi.studentcareers.models.ApiStudentCareer;
import pe.ironman.parallel.data.clientapi.studentcareers.services.ApiStudentCareerService;
import reactor.core.publisher.Mono;

import static java.time.Duration.ofSeconds;

@Slf4j
@Service
public class ApiStudentCareerServiceImpl implements ApiStudentCareerService {
    @Override
    public Mono<ApiStudentCareer> getStudentCareerByStudentId(Long studentId) {
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

        return Mono.just(career)
                .doOnNext(item -> log.info("getStudentCareerByStudentId executed on thread: " + Thread.currentThread().getName()))
                .delayElement(ofSeconds(1))
                ;
    }
}
