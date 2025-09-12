
package main;

import beans.Clientes;
import beans.Sorvetes;
import beans.Vendas;
import java.sql.Connection;
import conexao.Conexao;
import dao.ClientesDao;
import dao.FornecedoresDao;
import dao.FuncionariosDao;
import dao.SorvetesDao;
import dao.VendasDao;

/**
 *
 * @author Karol
 */
public class Main {

    public static void main(String[] args) {
        
        Conexao conexao = new Conexao();
        Connection conn = conexao.connectDB();
        
        // Testando a classe ClientesDao
        ClientesDao clientesDao = new ClientesDao(conn);
        Clientes cliente = clientesDao.buscarNomeCliente("Mariana");
        
        System.out.println("Teste 1 - Clientes:");
        if(cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNome());   
        } else {
            System.out.println("Cliente não encontrado.");
        }
        
        // Testando a classe FornecedoresDao
        System.out.println("\nTeste 2 - Fornecedores:");
        FornecedoresDao fornecedoresDao = new FornecedoresDao(conn);
        System.out.println("Lista de fornecedores: " + fornecedoresDao.listarFornecedores());
        
        // Testando a classe FuncionariosDao
        System.out.println("\nTeste 3 - Funcionários:");
        FuncionariosDao funcionariosDao = new FuncionariosDao(conn);
        System.out.println("Lista de dados do funcionário: " + funcionariosDao.buscarNomeFuncionario("Viktor"));
        
        // Testando a classe SorvetesDao
        System.out.println("\nTeste 4 - Sorvetes:");
        SorvetesDao sorvetesDao = new SorvetesDao(conn);
        Sorvetes sorvete = sorvetesDao.buscarSaborSorvete("Cookies");
        
        if (sorvete != null) {
            System.out.println("Sabor de sorvete encontrado: " + sorvete.getSaborSorvete());
        } else {
            System.out.println("Sabor de sorvete não encontrado.");
        }
        
        // Testando a classe VendasDao
        System.out.println("\nTeste 5 - Vendas:");
        VendasDao vendasDao = new VendasDao(conn);
        
        Vendas venda = vendasDao.buscarVendasPorId(1);
        
        if (venda != null) {
            System.out.println("Venda encontrada: " + venda);
        } else {
            System.out.println("Venda não encontrada.");
        }
    }
    
}
