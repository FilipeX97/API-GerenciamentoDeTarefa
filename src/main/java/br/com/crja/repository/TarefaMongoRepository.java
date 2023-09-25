package br.com.crja.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.crja.model.mongodb.TarefaMongo;


public interface TarefaMongoRepository extends MongoRepository<TarefaMongo, String> {
	public TarefaMongo getByIdTarefa(long idTarefa);
	public List<TarefaMongo> getAllByIdPessoa(long idPessoa);
	public void deleteByIdPessoa(long id);
	List<TarefaMongo> findByIdPessoaIsNullOrderByPrazoAsc();
}
