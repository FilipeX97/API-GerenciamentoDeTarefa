package br.com.crja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.crja.model.mongodb.DepartamentoMongo;

public interface DepartamentoMongoRepository extends MongoRepository<DepartamentoMongo, String> {

}
