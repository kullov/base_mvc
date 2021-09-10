package org.ttnga.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ttnga.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
