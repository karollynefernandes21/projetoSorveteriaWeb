
package service;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VendasServiceTest {
    
    private VendasService vendasService;
    
    public VendasServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vendasService = new VendasService();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    // Este teste testa o método que calcula o total de um pedido
    public void testTotalPedido() {
        System.out.println("| ---- Testando ---- |");
        double valorSorvete = 7.5;
        int quantidadeSorvete = 2;
        double esperado = 15.0;
        double resultado = vendasService.totalPedido(valorSorvete, quantidadeSorvete);
        assertEquals(esperado, resultado, 0.0001);
    }
    
}
