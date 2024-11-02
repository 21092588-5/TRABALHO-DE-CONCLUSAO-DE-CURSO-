package tech.buildrun.transferenciaFacil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransFException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("TransFacil internal server error");

        return pb;
    }
}
