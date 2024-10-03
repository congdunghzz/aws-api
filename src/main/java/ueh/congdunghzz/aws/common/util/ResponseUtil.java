package ueh.congdunghzz.aws.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ueh.congdunghzz.aws.model.response.Response;

public class ResponseUtil {
    public static ResponseEntity<Response> createResponse(HttpStatus status, String message , Object data){
        return ResponseEntity
                .status(status)
                .body(new Response(status.value(),message, data));
    }
    public static ResponseEntity<Response> successResponse(Object data){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(200,"", data));
    }

    public static ResponseEntity<Response> successResponse(String message, Object data){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response(200,message, data));
    }
}
