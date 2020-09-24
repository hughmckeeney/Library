package ibtwlb.ibtwlb.UserService;

import ibtwlb.ibtwlb.models.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}