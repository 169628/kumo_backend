package tw.idv.rainbow.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.rainbow.web.entity.Devices;

public interface DevicesRepository extends JpaRepository<Devices,Long> {

    Devices findBySn(String sn);
}
