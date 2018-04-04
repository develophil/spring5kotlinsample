package net.slipp.hkp.repository;

import net.slipp.hkp.racing.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ResultReactiveRepository extends ReactiveCrudRepository<Car, String> {

	@Query("{ name: { $exists: true }}")
	Flux<Car> retrieveAllCarPaged(final Pageable page);
}
