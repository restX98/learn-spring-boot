package dev.enrico.tutorial.run;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return this.runRepository.findAll();
    }

    @GetMapping("{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run = this.runRepository.findById(id);
        if (run.isEmpty()) {
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            throw new RunNotFoundException();
        }
        return run.get();
    }

    @GetMapping("location/{location}")
    List<Run> findByLocation(@PathVariable String location) {
        List<Run> runs = this.runRepository.findAllByLocation(location);
        return runs;
    }

    @GetMapping("miles/{miles}")
    List<Run> findByMiles(@PathVariable Integer miles) {
        List<Run> runs = this.runRepository.findAllByMiles(miles);
        return runs;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Run create(@Valid @RequestBody Run run) {
        this.runRepository.save(run);
        return run;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        Optional<Run> optionalRun = this.runRepository.findById(id);
        if (optionalRun.isPresent()) {
            this.runRepository.save(new Run(
                    id,
                    run.title(),
                    run.startedOn(),
                    run.completedOn(),
                    run.miles(),
                    run.location(),
                    run.version()));
        } else {
            throw new RunNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        Optional<Run> optionalRun = runRepository.findById(id);
        if (optionalRun.isPresent()) {
            this.runRepository.delete(optionalRun.get());
        } else {
            throw new RunNotFoundException();
        }
    }
}
