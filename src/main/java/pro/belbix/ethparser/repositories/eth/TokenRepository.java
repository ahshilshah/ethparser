package pro.belbix.ethparser.repositories.eth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.belbix.ethparser.entity.eth.ContractEntity;
import pro.belbix.ethparser.entity.eth.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    @Query("select t from TokenEntity t "
        + "left join fetch t.contract f1 "
        + "left join fetch f1.type f1f "
        + "where t.contract = :contract")
    TokenEntity findFirstByContract(@Param("contract") ContractEntity tokenContract);
}
