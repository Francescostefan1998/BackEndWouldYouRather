package BackEndWouldYouRather.BackEndWouldYouRather.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * We do not use the union primary key because of the extended demand. Actually,
 * spring data jpa not provider us a good support for union primary key
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "BOOK_ID", "AUTHOR_ID" }))
public class BookAuthor implements Serializable {
	private static final long serialVersionUID = 3330193491362488868L;

	private Long id;
	private Book book;
	private Author author;

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

	@ManyToOne
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@ManyToOne
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
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

		BookAuthor entity = (BookAuthor) o;

		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
