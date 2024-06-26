package controller;

import java.util.ArrayList;
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
import model.entity.VacinaEntity;
import model.seletor.VacinaSeletor;
import service.VacinaService;

@Path("/vacina")
public class VacinaController {
	
	VacinaService service = new VacinaService();
	
	
	@Path("/inserir")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VacinaEntity inserir(VacinaEntity vacina) throws PessoaException {
		return service.inserir(vacina);
	}
	
	@Path("/excluir/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean excluir(@PathParam("id") int id) throws PessoaException {
		return service.excluir(id);
	}
	
	@Path("/alterar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar (VacinaEntity altera) {
		return service.alterar(altera);
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VacinaEntity consultarPorId(@PathParam("id") int id) {
		return service.consultarPorId(id);
	}
	
	@Path("/listarTodos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<VacinaEntity> listaTodas(){
		return service.listarTodas();
	}
	
	@Path("/filtro")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<VacinaEntity> consultarPorFiltro(VacinaSeletor seletor){
		return service.consultarPorFiltro(seletor);
	}
	
	@Path("/contar-pagina")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int contarPaginas(VacinaSeletor seletor) {
		return this.service.contarPaginas(seletor);
	}
	
	@Path("/contar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int contarTotalDeRegistro(VacinaSeletor seletor) {
		return this.service.contarTodosRegistro(seletor);
	}
	
	

}
