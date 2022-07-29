package sber.ru.terminal.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class FraudException extends BaseException{

    public FraudException(String message) {
        super(BAD_REQUEST, message);
    }
}
