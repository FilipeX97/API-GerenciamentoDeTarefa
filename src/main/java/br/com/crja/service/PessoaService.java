package br.com.crja.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.crja.model.mongodb.PessoaMongo;
import br.com.crja.model.mongodb.TarefaMongo;
import br.com.crja.repository.PessoaMongoRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaMongoRepository pessoaMongoRepository;
	
	@Autowired
	private TarefaService tarefaService;
    
    public PessoaMongo pegarPorId(long id) {
			return pessoaMongoRepository.getByIdPessoa(id);
    }
    
    public PessoaMongo pegarPorNome(String nome) {
			return pessoaMongoRepository.getByNome(nome);
    }
    
    public List<PessoaMongo> pegarTodos() {
			return pessoaMongoRepository.findAll();
    }
    
	public List<Map<String,?>> pegarPessoas() {
			List<Map<String,?>> listaPersonalizada = new ArrayList<Map<String,?>>();
			
			pessoaMongoRepository.findAll()
            .forEach(p -> {
                int horasGastasNaTarefa = 0;

                List<TarefaMongo> tarefasDaPessoa = (List<TarefaMongo>) tarefaService.pegarTodosPorIdPessoa((long) p.getIdPessoa());
                for (TarefaMongo t : tarefasDaPessoa) {
                    horasGastasNaTarefa += t.getDuracao();
                }

                LinkedHashMap<String, Object> pessoaInfo = new LinkedHashMap<>();
                pessoaInfo.put("nome", p.getNome());
                pessoaInfo.put("departamento", p.getIdDepartamento());
                pessoaInfo.put("horasGastasNaTarefa", horasGastasNaTarefa);

                listaPersonalizada.add(pessoaInfo);
            });

			return listaPersonalizada;
	}
	
	public PessoaMongo criar(PessoaMongo pessoa) {
			PessoaMongo pessoaMongo = new PessoaMongo();
			pessoaMongo.setIdPessoa(pegarTodos().size() + 1);
			pessoaMongo.setNome(pessoa.getNome());
			pessoaMongo.setIdDepartamento(pessoa.getIdDepartamento());
			pessoaMongoRepository.insert(pessoaMongo);
			return pessoaMongo;
	}
	
	public PessoaMongo alterar(PessoaMongo pessoaJSON, PessoaMongo pessoa) {
		String nome = pessoaJSON.getNome();
		int idDepartamento = pessoaJSON.getIdDepartamento();
		

			int contador = pegarTodos().size() + 1;
			PessoaMongo pDados = pessoa;
			PessoaMongo p = pessoaMongoRepository.getByIdPessoa(pDados.getIdPessoa());
			p.setIdPessoa(p.getIdPessoa() == 0 ? contador : p.getIdPessoa());
			p.setNome(nome.isEmpty() ? p.getNome() : nome);
			p.setIdDepartamento(idDepartamento == 0 ? p.getIdDepartamento() : idDepartamento);
			
			pessoaMongoRepository.save(p);
			return p;
	}
	
	public void deletar(long id) {
			pessoaMongoRepository.deleteByIdPessoa(id);
	}
	
	public URI pegarUri(PessoaMongo p) {
		URI uri = null;
		
		    PessoaMongo pessoaMongo = p;
		    uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pessoaMongo.getIdPessoa())
                    .toUri();
		    return uri;
	}
	
	

}
