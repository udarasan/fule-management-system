package lk.esoft.fulemanagementsystem.repository;

import lk.esoft.fulemanagementsystem.entity.AuditSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditSection,Integer> {
}
