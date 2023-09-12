package BackEndWouldYouRather.BackEndWouldYouRather.web.repository;

import java.util.Set;

import BackEndWouldYouRather.BackEndWouldYouRather.support.jpa.CustomJpaRepository;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Book;

/**
 * <b>Book Repository</b><br>
 * You can use NamedQuery or Query annotation here.<br>
 * 
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public interface BookRepository extends CustomJpaRepository<Book, Long> {

	public Book findByBookName(String bookName);

	public Set<Book> findByBookAuthors_Author_Id(Long authorId);
}
