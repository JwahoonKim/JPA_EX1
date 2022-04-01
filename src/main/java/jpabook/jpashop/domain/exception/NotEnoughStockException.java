package jpabook.jpashop.domain.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {}
    public NotEnoughStockException(String message) { super(message); }
}
