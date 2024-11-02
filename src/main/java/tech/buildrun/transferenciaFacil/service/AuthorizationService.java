package tech.buildrun.transferenciaFacil.service;

import org.springframework.stereotype.Service;
import tech.buildrun.transferenciaFacil.client.AuthorizationClient;
import tech.buildrun.transferenciaFacil.controller.dto.TransferDto;
import tech.buildrun.transferenciaFacil.exception.TransFException;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transfer) {

        var resp = authorizationClient.isAuthorized();

        if (resp.getStatusCode().isError()) {
            throw new TransFException();
        }

        return resp.getBody().authorized();
    }
}
