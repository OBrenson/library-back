package boi.projs.library.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static boi.projs.library.Constants.*;

public class DomainTests {

    User user = createUser();
    Author author = createAuthor(user);

    @Test
    public void testUser() {
        user.setId(userId);
        assertEquals(userId, user.getId());
        assertEquals(userName, user.getLogin());
        assertEquals(userPass, user.getPassword());
        assertEquals(String.format("boi.projs.library.domain.User: %s %s", userId, userName), user.toString());
    }

    @Test
    public void testAuthor() {
        author.setId(authorId);
        assertEquals(author.getId(), authorId);
        assertEquals(author.getName(), authorName);
        assertEquals(user, author.getUser());
        assertEquals(String.format("boi.projs.library.domain.Author: %s %s", authorId, authorName), author.toString());
    }

    @Test
    public void testBook() {
        Book book = Book.builder().title(bookTitle).author(author).fileId(fileId).page(pageNum).id(bookId).build();
        assertEquals(bookTitle, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(fileId, book.getFileId());
        assertEquals(pageNum, book.getPage());
        assertEquals(String.format("boi.projs.library.domain.Book: %s %s",bookId, bookTitle), book.toString());
    }

    @Test
    public void testTextFile() {
        TextFile textFile = TextFile.builder().id(fileId).bookId(bookId).content(text).build();
        assertEquals(textFile.getId(), fileId);
        assertEquals(textFile.getBookId(), bookId);
        assertEquals(textFile.getContent(), text);
        assertEquals(String.format("boi.projs.library.domain.TextFile: %s %s", fileId, bookId), textFile.toString());
    }

}
