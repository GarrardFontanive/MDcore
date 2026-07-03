package br.com.mdcore;

import br.com.mdcore.tolerance.MaterialTolerance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaterialToleranceTest {

    @Test
    void deveCalcularToleranciaParaPla() {
        double gap = MaterialTolerance.calculateGap("PLA", 0.4, 0.2);

        assertTrue(gap > 0.0);
    }

    @Test
    void absDeveTerToleranciaMaiorQuePla() {
        double pla = MaterialTolerance.calculateGap("PLA", 0.4, 0.2);
        double abs = MaterialTolerance.calculateGap("ABS", 0.4, 0.2);

        assertTrue(abs > pla);
    }

    @Test
    void materialDesconhecidoDeveGerarToleranciaPadrao() {
        double gap = MaterialTolerance.calculateGap("MATERIAL_X", 0.4, 0.2);

        assertTrue(gap > 0.0);
    }
}