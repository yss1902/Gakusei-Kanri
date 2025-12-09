package net.datasa.Gakusei_Kanri.repository;

import net.datasa.Gakusei_Kanri.domain.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
