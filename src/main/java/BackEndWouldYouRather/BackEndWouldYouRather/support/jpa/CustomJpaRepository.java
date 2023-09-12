package BackEndWouldYouRather.BackEndWouldYouRather.support.jpa;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <b>Custom JPA repository</b> <BR>
 * Sometimes, The default JPA repository behavior can't meet all our
 * requirements. We can extend the default JPA repository behavior in this
 * class. Please be aware of that the extension is for all your repositories.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	public T sharedCustomMethod(ID id);
}
