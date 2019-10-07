package mr.cell.usersservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersMongoRepository extends UsersRepository, MongoRepository<User, UUID> {

}
