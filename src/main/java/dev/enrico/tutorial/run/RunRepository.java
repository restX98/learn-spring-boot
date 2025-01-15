package dev.enrico.tutorial.run;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

    public List<Run> findAllByLocation(String location);

    public List<Run> findAllByMiles(Integer miles);
}
