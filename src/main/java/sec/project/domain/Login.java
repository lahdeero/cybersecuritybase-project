package sec.project.domain;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;

@Entity
public class Login extends AbstractPersistable<Long> {

    private String name;
    private String password;

    public Login() {
        super();
    }

    public Login(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
