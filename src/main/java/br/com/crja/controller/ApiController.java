package br.com.crja.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.crja.model.CalculoMediaHoras;
import br.com.crja.model.Departamento;
import br.com.crja.model.mongodb.PessoaMongo;
import br.com.crja.model.mongodb.TarefaMongo;
import br.com.crja.service.DepartamentoService;
import br.com.crja.service.PessoaService;
import br.com.crja.service.TarefaService;

@RestController
@RequestMapping("/api_gerenciamento")
public class ApiController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/pessoas")
	public ResponseEntity<List<Map<String,?>>> pegarPessoas() {
		return ResponseEntity.ok(pessoaService.pegarPessoas());
	}
	
	@GetMapping("/departamentos")
	public ResponseEntity<List<Departamento>> pegarDepartamentos() {
		return ResponseEntity.ok(departamentoService.pegarDepartamentos(pessoaService.pegarTodos(), tarefaService.pegarTodos()));
	}
	
	@GetMapping("/tarefas/pendentes")
    public ResponseEntity<List<TarefaMongo>> listarTarefasPendentes() {
		List<TarefaMongo> tarefasPendentes = tarefaService.listarTarefasPendentesMaisAntigas(3);
		
		if (tarefasPendentes.isEmpty())
            return ResponseEntity.noContent().build();
		
        return ResponseEntity.ok(tarefasPendentes);
    }
	
	@GetMapping("/pessoas/gastos")
	@ResponseBody
	public ResponseEntity<Double> calcularMediaHorasPorTarefa(@RequestBody CalculoMediaHoras calculoMediaHoras) {
	    double mediaHoras = tarefaService.calcularMediaDaPessoaPorTarefa(pessoaService
    		.pegarPorNome(
				calculoMediaHoras.getNome()), 
    			LocalDate.parse(calculoMediaHoras.getDataInicio()), 
    			LocalDate.parse(calculoMediaHoras.getDataFim())
			);
	    return ResponseEntity.ok(mediaHoras);
	}
	
	@GetMapping("/tarefas")
	public ResponseEntity<List<TarefaMongo>> pegarTodasTarefas() {
		return ResponseEntity.ok(tarefaService.pegarTodos());
	}
	
	@PostMapping("/pessoas")
	@ResponseBody
	public ResponseEntity<Object> criarPessoa(@RequestBody PessoaMongo pessoa) {
		try {
			if(pessoa != null) {
					PessoaMongo pessoaFinal = pessoaService.criar(pessoa);
		            return ResponseEntity.created(pessoaService.pegarUri(pessoaFinal)).body(pessoaFinal);
			} else {
				return ResponseEntity.badRequest().body("JSON não é valido para essa solicitação!");
			}
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@PostMapping("/tarefas")
	@ResponseBody
	public ResponseEntity<Object> criarTarefa(@RequestBody TarefaMongo tarefa) {
		try {
			if(tarefa != null) {
	            	TarefaMongo tarefaFinal = tarefaService.criar(tarefa);
		            return ResponseEntity.created(tarefaService.pegarUri(tarefaFinal)).body(tarefaFinal);
			} else {
				return ResponseEntity.badRequest().body("JSON não é valido para essa solicitação!");
			}
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@PutMapping(path = "/pessoas/{id}", produces = { "application/json" })
	public ResponseEntity<PessoaMongo> atualizarPessoa(@PathVariable("id") long id, @RequestBody PessoaMongo pessoaJSON) {
		try {
			if(pessoaJSON != null) {

				PessoaMongo pessoa = pessoaService.pegarPorId(id);
				
				if(pessoa == null)
					return ResponseEntity.notFound().build();
					
				PessoaMongo pessoaAlterada = pessoaService.alterar(pessoaJSON, pessoa);
	            	return ResponseEntity.ok(pessoaAlterada);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@PutMapping(path = "/tarefas/alocar/{id}", produces = { "application/json" })
	public ResponseEntity<TarefaMongo> incluirPessoaNaTarefa(@PathVariable("id") long id, @RequestBody TarefaMongo idTarefa) {

		try {
			
			int idTarefaInt = idTarefa.getIdTarefa();
			
			if(idTarefaInt > 0) {

				PessoaMongo pessoa = pessoaService.pegarPorId(id);
				TarefaMongo tarefa = tarefaService.pegarPorId(idTarefaInt);
				
				
				if(pessoa == null || tarefa == null)
					return ResponseEntity.notFound().build();
					
					if(pessoa.getIdDepartamento() == tarefa.getIdDepartamento() && 
							tarefa.getIdPessoa() == 0 &&
							!(pessoa.getIdPessoa() == tarefa.getIdPessoa())) {
						tarefa.setIdPessoa(pessoa.getIdPessoa());
						TarefaMongo tarefaAlterada = tarefaService.alterar(tarefa, tarefa);
		            	return ResponseEntity.ok(tarefaAlterada);
					} else {
						return ResponseEntity.badRequest().body(null);
					}
				
			} else {
				return ResponseEntity.badRequest().body(null);
			}
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		} 
	}
	
	@PutMapping(path = "/tarefas/finalizar/{id}", produces = { "application/json" })
	public ResponseEntity<TarefaMongo> finalizarTarefa(@PathVariable("id") long id) {
		try {

			TarefaMongo tarefa = tarefaService.pegarPorId(id);
			
			if(tarefa == null)
				return ResponseEntity.notFound().build();
				
				if(tarefa.isFinalizado())
					return ResponseEntity.badRequest().body(null);
				
				tarefa.setFinalizado(true);
				TarefaMongo tarefaAlterada = tarefaService.alterar(tarefa, tarefa);
            	return ResponseEntity.ok(tarefaAlterada);
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		} 
	}
	
	
	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<Boolean> deletarPessoa(@PathVariable("id") long id) {
		try {
			Object pessoa = pessoaService.pegarPorId(id);
			if(pessoa == null) {
				return ResponseEntity.notFound().build(); 
			} else {
				pessoaService.deletar(id);
				return ResponseEntity.noContent().build();
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	
	

}
