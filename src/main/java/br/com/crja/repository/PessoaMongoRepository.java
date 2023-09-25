package br.com.crja.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.crja.model.mongodb.PessoaMongo;



public interface PessoaMongoRepository extends MongoRepository<PessoaMongo, String> {
	
	public PessoaMongo getByIdPessoa(long id);
	public PessoaMongo getByNome(String Nome);
	public void deleteByIdPessoa(long id);

}
