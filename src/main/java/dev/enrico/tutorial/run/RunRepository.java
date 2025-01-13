package dev.enrico.tutorial.run;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return this.jdbcClient
                .sql("SELECT * FROM run")
                .query(Run.class)
                .list();
    }

    Optional<Run> findById(Integer id) {
        return this.jdbcClient
                .sql("SELECT * FROM Run WHERE id=:id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    void create(Run run) {
        int created = this.jdbcClient
                .sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(),
                        run.location().toString()))
                .update();
        Assert.state(created == 1, "Failed to create run " + run.title());
    }

    void update(Run run, Integer id) {
        int updated = this.jdbcClient
                .sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(),
                        id))
                .update();
        Assert.state(updated == 1, "Failed to update run " + id);
    }

    void delete(Integer id) {
        int deleted = this.jdbcClient
                .sql("DELETE FROM run WHERE id=:id")
                .param("id", id)
                .update();
        Assert.state(deleted == 1, "Dailed to delete run " + id);
    }

    /*
     * In memory database
     */
    // private List<Run> runs = new ArrayList<>();

    // List<Run> findAll() {
    // return runs;
    // }

    // Optional<Run> findById(Integer id) {
    // return runs.stream()
    // .filter(run -> run.id() == id)
    // .findFirst();
    // }

    // void create(Run run) {
    // runs.add(run);
    // }

    // void update(Run run, Integer id) {
    // Optional<Run> existingRun = findById(id);
    // if (existingRun.isPresent()) {
    // runs.set(runs.indexOf(existingRun.get()), run);
    // }
    // }

    // void delete(Integer id) {
    // runs.removeIf(run -> run.id().equals(id));
    // }

    // @PostConstruct
    // private void init() {
    // runs.add(new Run(
    // 1,
    // "Monday Morning Run",
    // LocalDateTime.now(),
    // LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
    // 3,
    // Location.INDOOR));
    // runs.add(new Run(
    // 2,
    // "Wednesday Morning Run",
    // LocalDateTime.now(),
    // LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
    // 6,
    // Location.INDOOR));
    // }
}
