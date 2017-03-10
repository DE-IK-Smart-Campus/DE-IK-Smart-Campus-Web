package hu.unideb.smartcampus.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.unideb.smartcampus.persistence.entity.SampleEntity;

/**
 * Sample repository which provides operations for {@link SampleEntity}.
 */
@Repository
@Transactional
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {

}
