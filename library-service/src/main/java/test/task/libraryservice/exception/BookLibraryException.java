package test.task.libraryservice.exception;


public class BookLibraryException extends RuntimeException {

    private Long bookId;

    public BookLibraryException(String message, Long bookId) {
        super(message);
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

}