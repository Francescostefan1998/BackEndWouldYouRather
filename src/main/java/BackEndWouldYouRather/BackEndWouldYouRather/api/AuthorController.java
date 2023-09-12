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

import BackEndWouldYouRather.BackEndWouldYouRather.api.assembler.AuthorResourceAssembler;
import BackEndWouldYouRather.BackEndWouldYouRather.api.resource.AuthorResource;
import BackEndWouldYouRather.BackEndWouldYouRather.support.constant.MessageCode;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.BadParamException;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.DuplicateException;
import BackEndWouldYouRather.BackEndWouldYouRather.support.exception.NotFoundException;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Author;
import BackEndWouldYouRather.BackEndWouldYouRather.web.entity.Book;
import BackEndWouldYouRather.BackEndWouldYouRather.web.service.AuthorService;

/**
 * <b>Author API</b><br>
 * 
 * @author Wenbo Wang (jackie-1685@163.com)
 */
@RestController
@RequestMapping(path="/api/authors")
public class AuthorController {

	private @Autowired AuthorService authorService;
	private @Autowired MessageSource messageSource;

	/**
	 * Create a author
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AuthorResource> createAuthor(@RequestBody Author author) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		// Verify
		if (null == author || !StringUtils.hasText(author.getAuthorName())) {
			throw new BadParamException(messageSource.getMessage(MessageCode.AUTHOR_NAME_REQUIRED, null, locale));
		}

		if (author.getAuthorName().length() > 64) {
			throw new BadParamException(messageSource.getMessage(MessageCode.AUTHOR_NAME_TOO_LONG, null, locale));
		}

		// Create
		Author newAuthor = null;
		try {
			newAuthor = authorService.createAuthor(author.getAuthorName());
		} catch (DuplicateException e) {
			throw new DuplicateException(
					messageSource.getMessage(e.getMessage(), new Object[] { author.getAuthorName() }, locale));
		}

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		AuthorResource resource = assembler.toResource(newAuthor);
		return new ResponseEntity<AuthorResource>(resource, HttpStatus.CREATED);
	}

	/**
	 * Get a author
	 * 
	 */
	@RequestMapping(path = "/{authorId}", method = RequestMethod.GET)
	public ResponseEntity<AuthorResource> getAuthor(@PathVariable Long authorId) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		Author author = null;
		try {
			author = authorService.getAuthor(authorId);
		} catch (NotFoundException e) {
			throw new NotFoundException(messageSource.getMessage(e.getMessage(), new Object[] { authorId }, locale));
		}

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		AuthorResource resource = assembler.toResource(author);
		return new ResponseEntity<AuthorResource>(resource, HttpStatus.OK);
	}

	/**
	 * Delete a author
	 * 
	 */
	@RequestMapping(path = "/{authorId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {

		authorService.deleteAuthor(authorId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Assign author a book
	 * 
	 */
	@RequestMapping(path = "/{authorId}/books", method = RequestMethod.PUT)
	public ResponseEntity<AuthorResource> assignBook(@PathVariable Long authorId, @RequestBody Book book) {
		// Get request locale
		Locale locale = LocaleContextHolder.getLocale();

		if (null == book || null == book.getId()) {
			throw new BadParamException(messageSource.getMessage(MessageCode.ASSIGN_BOOK_REQUIRE_BOOK_ID,
					new Object[] { authorId }, locale));
		}

		Author author = null;
		try {
			author = authorService.assignBook(authorId, book.getId());
		} catch (NotFoundException e) {
			if (e.getMessage().equalsIgnoreCase(MessageCode.AUTHOR_NOT_FOUND)) {
				throw new NotFoundException(
						messageSource.getMessage(e.getMessage(), new Object[] { authorId }, locale));
			}

			if (e.getMessage().equalsIgnoreCase(MessageCode.BOOK_NOT_FOUND)) {
				throw new NotFoundException(
						messageSource.getMessage(e.getMessage(), new Object[] { book.getId() }, locale));
			}
		}

		AuthorResourceAssembler assembler = new AuthorResourceAssembler();
		AuthorResource resource = assembler.toResource(author);
		return new ResponseEntity<AuthorResource>(resource, HttpStatus.OK);
	}

}
