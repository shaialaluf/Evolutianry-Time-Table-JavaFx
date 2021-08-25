package exception;

public class FileErrorException extends RuntimeException {
    String illegalFilePath;

    public FileErrorException(String msg, String illegalFilePath) {
        super(msg);
        this.illegalFilePath=illegalFilePath;
    }
}
