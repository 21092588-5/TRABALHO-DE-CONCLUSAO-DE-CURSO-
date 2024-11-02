package tech.buildrun.transferenciaFacil.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.buildrun.transferenciaFacil.controller.dto.TransferDto;
import tech.buildrun.transferenciaFacil.entity.Transfer;
import tech.buildrun.transferenciaFacil.entity.Wallet;
import tech.buildrun.transferenciaFacil.exception.InsufficientBalanceException;
import tech.buildrun.transferenciaFacil.exception.TransferNotAllowedForWalletTypeException;
import tech.buildrun.transferenciaFacil.exception.TransferNotAuthorizedException;
import tech.buildrun.transferenciaFacil.exception.WalletNotFoundException;
import tech.buildrun.transferenciaFacil.repository.TransferRepository;
import tech.buildrun.transferenciaFacil.repository.WalletRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository,
                           AuthorizationService authorizationService,
                           NotificationService notificationService,
                           WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto transferDto) {

        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());

        var transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDto transferDto, Wallet sender) {

        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if (!sender.isBalancerEqualOrGreatherThan(transferDto.value())) {
            throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }

    }
}
