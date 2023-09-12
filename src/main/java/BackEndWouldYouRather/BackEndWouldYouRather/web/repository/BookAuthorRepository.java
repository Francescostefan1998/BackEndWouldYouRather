package BackEndWouldYouRather.BackEndWouldYouRather.web.repository;

import BackEndWouldYouRather.BackEndWouldYouRather.support.jpa.CustomJpaRepository;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Author;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Book;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.BookAuthor;

/**
 * <b>BookAuthor Repository</b><br>
 * You can use NamedQuery or Query annotation here.<br>
 * 
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public interface BookAuthorRepository extends CustomJpaRepository<BookAuthor, Long> {

	public BookAuthor findByBookAndAuthor(Book book, Author author);

	public void deleteByAuthor(Author author);

	public void deleteByBook(Book book);
}
