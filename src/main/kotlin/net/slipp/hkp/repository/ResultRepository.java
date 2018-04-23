package net.slipp.hkp.repository;

import net.slipp.hkp.racing.RacingResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultRepository extends MongoRepository<RacingResult, String> {

}
