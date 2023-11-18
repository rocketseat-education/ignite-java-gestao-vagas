package br.com.rocketseat.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {
    
    @Test
    public void deve_ser_possivel_calcular_dois_numeros() {
        var result = calculate(2, 3);
        assertEquals(result, 5);
    }

    @Test
    public void validar_valor_incorreto(){
        var result = calculate(2, 3);
        assertNotEquals(result, 4);
    }

    public static int calculate(int num1, int num2){
        return num1 + num2;
    }

    
}
