package mr.cell.routesticker.usersservice;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UsersRepository extends PagingAndSortingRepository<User, UUID> {

    User findByUsername(String username);
}
