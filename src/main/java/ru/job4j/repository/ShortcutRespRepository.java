package ru.job4j.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.ShortcutResp;
@Repository
public interface ShortcutRespRepository extends CrudRepository<ShortcutResp, Long> {
}
