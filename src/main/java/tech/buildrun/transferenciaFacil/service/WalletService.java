package tech.buildrun.transferenciaFacil.service;

import org.springframework.stereotype.Service;
import tech.buildrun.transferenciaFacil.controller.dto.CreateWalletDto;
import tech.buildrun.transferenciaFacil.entity.Wallet;
import tech.buildrun.transferenciaFacil.exception.WalletDataAlreadyExistsException;
import tech.buildrun.transferenciaFacil.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if (walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("CpfCnpj or Email already exists");
        }

        return walletRepository.save(dto.toWallet());
    }
}
