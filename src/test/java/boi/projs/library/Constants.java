package boi.projs.library;

import boi.projs.library.domain.Author;
import boi.projs.library.domain.User;

import java.util.UUID;

public abstract class Constants {
    
    public final static String userName = "User Name";
    public final static byte[] userPass = "userPass".getBytes();
    public final static UUID userId = UUID.fromString("0f14d0ab-9601-4a62-a9e4-5ed26688389b");
    public final static String authorName = "Author Name";
    public final static UUID authorId = UUID.fromString("0f14d0ab-9602-4a62-a9e4-5ed26688389b");
    public final static String bookTitle = "Book Title";
    public final static int pageNum = 10;
    public final static UUID fileId = UUID.fromString("0f14d0ab-9603-4a62-a9e4-5ed26688389b");
    public final static UUID bookId = UUID.fromString("0f14d0ab-9604-4a62-a9e4-5ed26688389b");

    public final static byte[] text = {1,2,4,5,12,2};

    public static User createUser() {
        return User.builder().login(userName).password(userPass).id(userId).build();
    }

    public static Author createAuthor(User user) {
        return Author.builder().name(authorName).user(user).id(authorId).id(bookId).build();
    }
}
