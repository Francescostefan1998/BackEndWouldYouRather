package BackEndWouldYouRather.BackEndWouldYouRather.web.service;

import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.DuplicateException;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.NotFoundException;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Book;

/**
 * Book service Implementation refer to {@link BookServiceImpl}
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
public interface BookService {

	/**
	 * Get a book by its ID
	 * 
	 * @param bookId
	 *            long required
	 * 
	 */
	public Book getBook(Long bookId) throws NotFoundException;

	/**
	 * Create a book by its name
	 * 
	 * @param bookName
	 *            String required
	 * 
	 */
	public Book createBook(String bookName) throws DuplicateException;

	/**
	 * Delete a book by its ID
	 * 
	 * @param bookId
	 *            long required
	 * 
	 */
	public void deleteBook(Long bookId) throws NotFoundException;

	/**
	 * Assign book a author
	 * 
	 * 
	 * @param bookId
	 *            long required
	 * @param authorId
	 *            long required
	 * 
	 * 
	 */
	public Book assignAuthor(Long bookId, Long authorId) throws NotFoundException;
}
