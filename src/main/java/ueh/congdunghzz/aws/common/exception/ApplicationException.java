package ueh.congdunghzz.aws.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationException extends RuntimeException{
    private HttpStatus status;
    private int code;

    public ApplicationException(HttpStatus status, int code, String message){
        super(message);
        this.status = status;
        this.code = code;
    }

    public ApplicationException(HttpStatus status){
        super(status.getReasonPhrase());
        this.status = status;
        this.code = status.value();
    }
    public ApplicationException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.code = status.value();
    }

}
