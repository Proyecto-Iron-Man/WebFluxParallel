package pe.ironman.parallel.expose.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ironman.parallel.application.dtos.StudentCourseDto;
import pe.ironman.parallel.application.services.ParallelStudentService;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("parallel-student")
public class ParallelStudentController {

    private final ParallelStudentService parallelStudentService;

    @GetMapping("{documentNumber}")
    public Mono<StudentCourseDto> getStudentCourses(@PathVariable("documentNumber") String documentNumber) {
        return parallelStudentService.getStudentCourses(documentNumber);
    }

}
