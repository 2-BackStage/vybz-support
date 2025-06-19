package back.vybz.support_service.support.infrastructure.mongo;

import back.vybz.support_service.support.domain.mongo.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonationMongoRepository extends MongoRepository<Donation, String> {

    boolean existsByUserUuid(String userUuid);

    boolean existsByBuskerUuid(String buskerUuid);

    Page<Donation> findByUserUuid(String userUuid, Pageable pageable);

    Page<Donation> findByBuskerUuid(String buskerUuid, Pageable pageable);
}
