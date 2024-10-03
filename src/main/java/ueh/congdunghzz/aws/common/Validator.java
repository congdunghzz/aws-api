package ueh.congdunghzz.aws.common;

import org.springframework.http.HttpStatus;
import ueh.congdunghzz.aws.common.exception.ApplicationException;

public class Validator {
    public static void notNull(Object object, HttpStatus status, String message){
        if (object == null){
            throw new ApplicationException(status, message);
        }
    }
    public static void notNull(Object object, HttpStatus status){
        if (object == null){
            throw new ApplicationException(status);
        }
    }
    public static void mustNull(Object object, HttpStatus status, String message){
        if (object != null){
            throw new ApplicationException(status, message);
        }
    }
}
