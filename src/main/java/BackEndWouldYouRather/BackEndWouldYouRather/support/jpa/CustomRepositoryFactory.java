package BackEndWouldYouRather.BackEndWouldYouRather.support.jpa;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;
import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * <b>Custom Repository Factory</b> <BR>
 * The factory help to product the custom repository implementation.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public class CustomRepositoryFactory extends JpaRepositoryFactory {

	private final EntityManager entityManager;

	public CustomRepositoryFactory(EntityManager entityManager) {
		super(entityManager);

		this.entityManager = entityManager;
	}

	@Override
	protected Object getTargetRepository(RepositoryInformation information) {

		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
		return getTargetRepositoryViaReflection(information, entityInformation, entityManager);

	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface())) {
			return QueryDslJpaRepository.class;
		} else {
			return CustomJpaRepositoryImpl.class;
		}
	}

}
