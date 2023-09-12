package BackEndWouldYouRather.BackEndWouldYouRather.support.jpa;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * <b>Custom JPA repository implementation</b> <BR>
 * We implement the custom JPA repository interface here. Please be aware of
 * that the extension is for all your repositories.
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public class CustomJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements CustomJpaRepository<T, ID> {

	private final EntityManager entityManager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public T sharedCustomMethod(ID id) {
		Assert.notNull(id, "The given id must not be null!");

		Class<T> domainType = getDomainClass();

		if (getRepositoryMethodMetadata() == null) {
			return entityManager.find(domainType, id);
		}

		LockModeType type = getRepositoryMethodMetadata().getLockModeType();

		Map<String, Object> hints = getQueryHints();

		return type == null ? entityManager.find(domainType, id, hints)
				: entityManager.find(domainType, id, type, hints);
	}
}
