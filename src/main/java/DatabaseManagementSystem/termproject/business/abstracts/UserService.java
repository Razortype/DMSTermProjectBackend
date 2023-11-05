package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.core.enums.Role;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.user.User;

import java.util.List;

public interface UserService {

    DataResult<List<User>> getAllUser();
    DataResult<User> getUserById(int userId);
    DataResult<User> getUserByEmail(String email);
    void saveUser(User user);

}
