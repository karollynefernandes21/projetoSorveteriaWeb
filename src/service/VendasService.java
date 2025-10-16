
package service;

public class VendasService {
    
    // Calcula o total de um pedido multiplicando o valor do sorvete pela quantidade de sorvetes pedidos
    public double totalPedido(double valorSorvete, int quantidadeSorvete) {
        double totalPedido = valorSorvete * quantidadeSorvete;
        return totalPedido;
    }
}
