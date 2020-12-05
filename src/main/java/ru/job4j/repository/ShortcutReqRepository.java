package ru.job4j.repository;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.ShortcutReq;
@Repository
public interface ShortcutReqRepository extends CrudRepository<ShortcutReq, Long> {
    @Procedure
    String getUrlByIdAndIncrementTotal(int idByCode);
}
