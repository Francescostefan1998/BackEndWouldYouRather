package BackEndWouldYouRather.BackEndWouldYouRather.api.resource;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

/**
 * <b>Book Resource</b><br>
 * Here we can set which part is available to user
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public class BookResource extends ResourceSupport {
	private String bookName;
	private String createBy;
	private Date createDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
