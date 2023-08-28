package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.entity.FinanceiroEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceiroRepository extends MongoRepository<FinanceiroEntity, String> {



}
