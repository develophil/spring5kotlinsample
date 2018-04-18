package net.slipp.hkp.repository;

import net.slipp.hkp.racing.RacingResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ResultReactiveRepository extends ReactiveCrudRepository<RacingResult, String> {

}
