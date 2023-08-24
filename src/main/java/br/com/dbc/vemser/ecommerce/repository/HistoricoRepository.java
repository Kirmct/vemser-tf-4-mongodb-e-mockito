package br.com.dbc.vemser.ecommerce.repository;

import br.com.dbc.vemser.ecommerce.dto.historico.HistoricoContadorDTO;
import br.com.dbc.vemser.ecommerce.entity.Historico;
import br.com.dbc.vemser.ecommerce.entity.enums.Cargo;
import br.com.dbc.vemser.ecommerce.entity.enums.Setor;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends MongoRepository<Historico, String> {

    List<Historico> findAllByCargo(Cargo cargo);

    @Aggregation(pipeline = {
            "{'$unwind': '$cargo' }",
            "{'$group': { '_id': '$cargo', 'quantidade': { '$sum': 1 } } }"
    }
    )
    List<HistoricoContadorDTO> groupByCargoAndCount();

    List<Historico> findAllBySetor(Setor setor);

}
