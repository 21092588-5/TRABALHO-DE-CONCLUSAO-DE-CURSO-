package tech.buildrun.transferenciaFacil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.transferenciaFacil.entity.Transfer;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
