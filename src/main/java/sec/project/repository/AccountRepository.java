package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Login;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(Long id);
    Account findByUsername(String username);
    List<Account> findAllByOrderByIdAsc();
    List<Account> findByUsernameIgnoreCaseContaining(String keyword);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Account c WHERE c.password = :password")
    boolean checkPassword(@Param("password") String password);

}
