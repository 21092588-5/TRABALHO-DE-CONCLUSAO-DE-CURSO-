package tech.buildrun.transferenciaFacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.transferenciaFacil.entity.WalletType;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
