package BackEndWouldYouRather.BackEndWouldYouRather.api;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import BackEndWouldYouRather.BackEndWouldYouRather.api.assembler.BookResourceAssembler;
import BackEndWouldYouRather.BackEndWouldYouRather.api.resource.BookResource;
import BackEndWouldYouRather.BackEndWouldYouRather.support.constant.MessageCode;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.BadParamException;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.DuplicateException;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.NotFoundException;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Author;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Book;
import BackEndWouldYouRather.BackEndWouldYouRather.web.service.BookService;

/**
 * <b>Book API</b><br>
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

	private @Autowired MessageSource messageSource;
	private @Autowired BookService bookService;

	/**
	 * Create a book
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BookResource> createBook(@RequestBody Book book) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		// Verify
		if (null == book || !StringUtils.hasText(book.getBookName())) {
			throw new BadParamException(messageSource.getMessage(MessageCode.BOOK_NAME_REQUIRED, null, locale));
		}

		if (book.getBookName().length() > 64) {
			throw new BadParamException(messageSource.getMessage(MessageCode.BOOK_NAME_TOO_LONG, null, locale));
		}

		// Create
		Book newBook = null;
		try {
			newBook = bookService.createBook(book.getBookName());
		} catch (DuplicateException e) {
			throw new DuplicateException(
					messageSource.getMessage(e.getMessage(), new Object[] { book.getBookName() }, locale));
		}

		BookResourceAssembler assembler = new BookResourceAssembler();
		BookResource resource = assembler.toResource(newBook);
		return new ResponseEntity<BookResource>(resource, HttpStatus.CREATED);
	}

	/**
	 * Get a book
	 * 
	 */
	@RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
	public ResponseEntity<BookResource> getBook(@PathVariable Long bookId) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		Book book = null;
		try {
			book = bookService.getBook(bookId);
		} catch (NotFoundException e) {
			throw new NotFoundException(messageSource.getMessage(e.getMessage(), new Object[] { bookId }, locale));
		}

		BookResourceAssembler assembler = new BookResourceAssembler();
		BookResource resource = assembler.toResource(book);
		return new ResponseEntity<BookResource>(resource, HttpStatus.OK);
	}

	/**
	 * Delete a book
	 * 
	 */
	@RequestMapping(path = "/{bookId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {

		bookService.deleteBook(bookId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Assign book a author
	 * 
	 */
	@RequestMapping(path = "/{bookId}/authors", method = RequestMethod.PUT)
	public ResponseEntity<BookResource> assignAuthor(@PathVariable Long bookId, @RequestBody Author author) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		if (null == author || null == author.getId()) {
			throw new BadParamException(messageSource.getMessage(MessageCode.ASSIGN_AUTHOR_REQUIRE_AUTHOR_ID,
					new Object[] { bookId }, locale));
		}

		Book book = null;
		try {
			book = bookService.assignAuthor(bookId, author.getId());
		} catch (NotFoundException e) {
			if (e.getMessage().equalsIgnoreCase(MessageCode.BOOK_NOT_FOUND)) {
				throw new NotFoundException(messageSource.getMessage(e.getMessage(), new Object[] { bookId }, locale));
			}

			if (e.getMessage().equalsIgnoreCase(MessageCode.AUTHOR_NOT_FOUND)) {
				throw new NotFoundException(
						messageSource.getMessage(e.getMessage(), new Object[] { author.getId() }, locale));
			}
		}

		BookResourceAssembler assembler = new BookResourceAssembler();
		BookResource resource = assembler.toResource(book);
		return new ResponseEntity<BookResource>(resource, HttpStatus.OK);
	}
}
