package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.business.abstracts.UserService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.ErrorDataResult;
import DatabaseManagementSystem.termproject.core.utils.results.SuccessDataResult;
import DatabaseManagementSystem.termproject.user.User;
import DatabaseManagementSystem.termproject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepo;

    @Override
    public DataResult<List<User>> getAllUser() {
        List<User> users = userRepo.findAll();
        return new SuccessDataResult<>(users, "All User fetched");
    }

    @Override
    public DataResult<User> getUserById(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("User not found: " + userId);
        }
        return new SuccessDataResult<>(user, "User fetched");
    }

    @Override
    public DataResult<User> getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            return new ErrorDataResult<>("User not found: " + email);
        }
        return new SuccessDataResult<>(user, "User fetched");
    }

    @Override
    public DataResult<User> getUserByToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserByEmail(authentication.getName());
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public DataResult<List<User>> searchUserByFilter(String text) {
        if (text.isBlank()) {
            return new ErrorDataResult<>("text cannot be blank");
        }
        List<User> users = userRepo.searchUserByFilter(text.toLowerCase());
        return new SuccessDataResult<>(users, "Users fetched by filter, text="+text);
    }

    @Override
    public DataResult<List<User>> getNRandomUser(int n) {

        List<Integer> usersIdList = userRepo.getUserIdList();
        if (n > usersIdList.size() || n <= 0) {
            return new ErrorDataResult<>("random amount error: " + n + "/" + usersIdList.size());
        }

        Collections.shuffle(usersIdList);
        List<Integer> randomNUserIds = usersIdList.subList(0, n);

        List<User> users = userRepo.findAllByUserIdIn(randomNUserIds);
        return new SuccessDataResult<>(users, "Users random element fetched: " + n);

    }
}
