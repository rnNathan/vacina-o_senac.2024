package controller;

import java.util.ArrayList;
import java.util.List;

import exception.PessoaException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.VacinacaoEntity;
import service.VacinacaoService;

@Path("/vacinacao")
public class VacinacaoController {
	
	private VacinacaoService service = new VacinacaoService();
	
	@POST
	@Path("/inserir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VacinacaoEntity inserir (VacinacaoEntity novaVacinacao) throws PessoaException {
		return service.inserir(novaVacinacao);
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean excluir (@PathParam("id")int id) {
		return service.excluir(id);
	}
	
	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean alterar (VacinacaoEntity entity) throws PessoaException {
		return service.alterar(entity);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public VacinacaoEntity consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@GET
	@Path("/listarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<VacinacaoEntity> consultarTodos(){
		return service.consultarTodos();
	}
	
	@GET
	@Path("/pessoa/{idPessoa}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VacinacaoEntity> consultarVacinaPorIdPessoa(@PathParam("idPessoa")int id){
		return service.consultarTodasPessoaPorId(id);
	}
	
	
	
	@GET
	@Path("/vacinas/{idVacina}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VacinacaoEntity> consultarTodasVacinasPorId(@PathParam("idVacina")int id){
		return service.consultarTodasVacinaPorId(id);
	}
	
	
	
	

	

}
