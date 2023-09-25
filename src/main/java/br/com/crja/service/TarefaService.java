package br.com.crja.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.crja.model.mongodb.PessoaMongo;
import br.com.crja.model.mongodb.TarefaMongo;
import br.com.crja.repository.TarefaMongoRepository;

@Service
public class TarefaService {
	
	@Autowired
	private TarefaMongoRepository tarefaMongoRepository;
	
	public TarefaMongo pegarPorId(long id) {
			return tarefaMongoRepository.getByIdTarefa(id);
    }
	
	public List<TarefaMongo> pegarTodos() {
			return tarefaMongoRepository.findAll();
	}
	
	public List<TarefaMongo> pegarTodosPorIdPessoa(Long idPessoa) {
			return tarefaMongoRepository.getAllByIdPessoa(idPessoa);
	}
	
	public double calcularMediaDaPessoaPorTarefa(PessoaMongo pessoaJSON, LocalDate dataInicio, LocalDate dataFim) {
		 int totalHoras = 0;
		 int totalTarefasNoPeriodo = 0;
		    
		
			PessoaMongo pessoa =  pessoaJSON;
			List<TarefaMongo> tarefasDaPessoa = tarefaMongoRepository.getAllByIdPessoa(pessoa.getIdPessoa());
			
			if (tarefasDaPessoa.isEmpty()) {
		        return 0;
		    }
			
			for (TarefaMongo tarefa : tarefasDaPessoa) {
		        LocalDate dataTarefa = LocalDate.parse(tarefa.getPrazo());

		        if (dataTarefa != null && dataTarefa.isAfter(dataInicio) && dataTarefa.isBefore(dataFim)) {
		            totalHoras += tarefa.getDuracao();
		            totalTarefasNoPeriodo++;
		        }
		    }

		    if (totalTarefasNoPeriodo == 0) {
		        return 0.0;
		    }

	    return (double) totalHoras / totalTarefasNoPeriodo;
	}
	
	public TarefaMongo criar(TarefaMongo tarefa) {
		TarefaMongo tarefaMongo = new TarefaMongo();
		List<TarefaMongo> listaTarefas = tarefaMongoRepository.findAll();
		tarefaMongo.setIdTarefa(listaTarefas.get(listaTarefas.size() - 1).getIdTarefa() + 1);
		tarefaMongo.setTitulo(tarefa.getTitulo());
		tarefaMongo.setDescricao(tarefa.getDescricao());
		tarefaMongo.setPrazo(tarefa.getPrazo());
		tarefaMongo.setIdDepartamento(tarefa.getIdDepartamento());
		tarefaMongo.setDuracao(tarefa.getDuracao());
		tarefaMongo.setIdPessoa(tarefa.getIdPessoa());
		tarefaMongo.setFinalizado(tarefa.isFinalizado());
		tarefaMongoRepository.insert(tarefaMongo);
		return tarefaMongo;
		
	}
	
	public TarefaMongo alterar(TarefaMongo tarefaJSON, TarefaMongo tarefa) {
		String titulo = tarefaJSON.getTitulo();
		String descricao = tarefaJSON.getDescricao();
		String prazo = tarefaJSON.getPrazo();
		int idDepartamento = tarefaJSON.getIdDepartamento();
		int duracao = tarefaJSON.getDuracao();
		int idPessoa = tarefaJSON.getIdPessoa();
		boolean finalizado = tarefaJSON.isFinalizado();
		
		TarefaMongo t = tarefaMongoRepository.getByIdTarefa(tarefa.getIdTarefa());
		t.set_id(t.get_id().isEmpty() ? null : t.get_id());
		t.setTitulo(titulo.isEmpty() ? t.getTitulo() : titulo);
		t.setDescricao(descricao.isEmpty() ? t.getDescricao() : descricao);
		t.setPrazo(prazo == null ? t.getPrazo() : prazo);
		t.setIdDepartamento(idDepartamento == 0 ? t.getIdDepartamento() : idDepartamento);
		t.setDuracao(duracao <= 0 ? t.getDuracao() : duracao);
		t.setIdPessoa(idPessoa == 0 ? t.getIdPessoa() : idPessoa);
		t.setTitulo(titulo.isEmpty() ? t.getTitulo() : titulo);
		t.setFinalizado(!finalizado ? t.isFinalizado() : finalizado);
		
		
		tarefaMongoRepository.save(t);
		
		return t;
				
	}
	public void deletar(long id) {
			tarefaMongoRepository.deleteByIdPessoa(id);
	}
	
	public URI pegarUri(TarefaMongo t) {
		URI uri = null;
			TarefaMongo pessoaMongo = t;
		    uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pessoaMongo.getIdPessoa())
                    .toUri();
		    return uri;
	}

	public List<TarefaMongo> listarTarefasPendentesMaisAntigas(int i) {
			return tarefaMongoRepository.findByIdPessoaIsNullOrderByPrazoAsc().stream()
	                .limit(i)
	                .collect(Collectors.toList());
	}
	

}
