package tw.idv.rainbow.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.rainbow.web.entity.StatusRef;

public interface StatusRefRepository extends JpaRepository<StatusRef,Integer> {
    StatusRef findByStatus(String status);
}
