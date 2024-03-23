package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import exception.PessoaException;
import model.entity.VacinacaoEntity;

public class VacinacaoRepository implements BaseRepository<VacinacaoEntity> {

	@Override
	public VacinacaoEntity salvar(VacinacaoEntity novaVacinacao) {
		String query = "INSERT INTO vacina.aplicacao_vacina (idPessoa, vacina, dataVacina, avaliacao) values (?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, novaVacinacao.getIdpessoa());
			pstmt.setObject(2, novaVacinacao.getVacina());
			pstmt.setDate(3, Date.valueOf(novaVacinacao.getDataVacina()));
			pstmt.setInt(4, novaVacinacao.getAvaliacao());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				novaVacinacao.setIdVacinacao(resultado.getInt(1));
			}

		} catch (SQLException e) {
			System.out.println("ERRO AO CADASTRAR UMA APLICAÇÃO!");
			System.out.println("ERRO: " + e.getMessage());

		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaVacinacao;
	}

	@Override
	public boolean excluir(int id) {
		String query = "SELECT aplicacao_vacina where id = " + id;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean excluiu = false;
		
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				excluiu = true;
			}
			
		} catch (SQLException e) {
			System.out.println("ERRO AO EXCLUIR!");
			System.out.println("ERRO: " + e.getMessage());
		}finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return false;
	}

	@Override
	public boolean alterar(VacinacaoEntity vacinaEditada) {
		boolean alterou = false;
		String query = " UPDATE aplicacao_vacina "
					 + " SET id_pessoa=?, id_vacina=?, data_aplicacao=?, avaliacao=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			
			pstmt.setInt(1, vacinaEditada.getIdpessoa());
			pstmt.setInt(2, vacinaEditada.getVacina().getId());
			pstmt.setDate(3, Date.valueOf(vacinaEditada.getDataVacina()));
			pstmt.setInt(4, vacinaEditada.getAvaliacao());
			pstmt.setInt(5, vacinaEditada.getIdVacinacao());
			alterou = pstmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar aplicação de vacina");
			System.out.println("Erro: " + e.getMessage());
		}finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public VacinacaoEntity consultarPorId(int id) {
		String query = "SELECT * FROM aplicacao_vacina WHERE id = " + id;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		VacinacaoEntity vacinacao = null;
		ResultSet resultado = null;

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				VacinaRepository repository = new VacinaRepository();
				vacinacao = new VacinacaoEntity();
				vacinacao.setIdVacinacao(resultado.getInt("id"));
				vacinacao.setIdpessoa(resultado.getInt("id_pessoa"));
				vacinacao.setVacina(repository.consultarPorId(resultado.getInt("id_vacina")));
				vacinacao.setDataVacina(resultado.getDate("data_aplicacao").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("avaliacao"));

			}

		} catch (SQLException e) {
			System.out.println("ERRO AO CONSULTAR POR ID VACINACAO!");
			System.out.println("ERRO: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return vacinacao;
	}

	@Override
	public ArrayList<VacinacaoEntity> consultarTodos() {
		ArrayList<VacinacaoEntity> listaVacinacao = new ArrayList<VacinacaoEntity>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM aplicacao_vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			while(resultado.next()){
				VacinacaoEntity vacinacao = new VacinacaoEntity();
				vacinacao.setIdVacinacao(resultado.getInt("id"));
				vacinacao.setIdpessoa(resultado.getInt("id_pessoa"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("id_vacina")));
				vacinacao.setDataVacina(resultado.getDate("data_aplicacao").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("avaliacao"));
				listaVacinacao.add(vacinacao);
			}
		} catch (SQLException erro){
			System.out.println("Erro consultar todas as aplicações de vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaVacinacao;
	}
	
	

}