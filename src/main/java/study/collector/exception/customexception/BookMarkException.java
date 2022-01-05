package study.collector.exception.customexception;

public class BookMarkException extends RuntimeException{
    public BookMarkException() {
        super();
    }

    public BookMarkException(String message) {
        super(message);
    }

    public BookMarkException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookMarkException(Throwable cause) {
        super(cause);
    }

    protected BookMarkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
