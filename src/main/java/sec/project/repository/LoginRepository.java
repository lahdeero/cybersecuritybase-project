package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sec.project.domain.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {


}

