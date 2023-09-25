package br.com.crja.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crja.model.Departamento;
import br.com.crja.model.mongodb.DepartamentoMongo;
import br.com.crja.model.mongodb.PessoaMongo;
import br.com.crja.model.mongodb.TarefaMongo;
import br.com.crja.repository.DepartamentoMongoRepository;

@Service
public class DepartamentoService {
	
	@Autowired
	private DepartamentoMongoRepository departamentoMongoRepository;

	public List<Departamento> pegarDepartamentos(List<PessoaMongo> pessoa, List<TarefaMongo> tarefa) {
		
			List<DepartamentoMongo> departamentos = departamentoMongoRepository.findAll();

	        return departamentos.stream().map(departamento -> {
	            Departamento departamentoComContagem = new Departamento();
	            departamentoComContagem.setNome(departamento.getTitulo());
	            int contTarefa = 0;
	            int contPessoa = 0;

	            List<TarefaMongo> tarefaMongo = tarefa;
	            List<PessoaMongo> pessoaMongo = pessoa;
	            
	            for (TarefaMongo t : tarefaMongo) {
	            	if(t.getIdDepartamento() == departamento.getIdDepartamento())
	            		contTarefa++;
                }
	            
	            for (PessoaMongo p : pessoaMongo) {
	            	if(p.getIdDepartamento() == departamento.getIdDepartamento())
	            		contPessoa++;
	            }
	            	
            	departamentoComContagem.setQuantidadeTarefas(contTarefa);
            	departamentoComContagem.setQuantidadePessoas(contPessoa);

	            return departamentoComContagem;
	        }).collect(Collectors.toList());
		
		}
	}
