package BackEndWouldYouRather.BackEndWouldYouRather.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {
	private static final long serialVersionUID = -405064709161586189L;

	private Long id;
	private String bookName;
	private Set<BookAuthor> bookAuthors;

	private User createBy;
	private Date createDate;
	private User lastModifiedBy;
	private Date lastModifiedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BOOK_NAME", unique = true, nullable = false, length = 64)
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@OneToMany(mappedBy = "book")
	public Set<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(Set<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	@CreatedBy
	@ManyToOne
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@CreatedDate
	@Column(name = "CREATE_DATE", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@LastModifiedBy
	@ManyToOne
	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@LastModifiedDate
	@Column(name = "LAST_MODIFIED_DATE")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Book entity = (Book) o;

		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
