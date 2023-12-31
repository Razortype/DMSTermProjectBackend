package DatabaseManagementSystem.termproject.user;

import DatabaseManagementSystem.termproject.entities.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE lower(SUBSTRING(u.email, 1, locate('@', u.email) - 1)) LIKE %:text% " +
            "OR lower(u.firstname) LIKE %:text% " +
            "OR lower(u.lastname) LIKE %:text%")
    List<User> searchUserByFilter(
            @Param("text") String text);

    @Query("SELECT u.userId FROM User u")
    List<Integer> getUserIdList();

    List<User> findAllByUserIdIn(List<Integer> ids);

}
