package br.com.mdcore;

import br.com.mdcore.core.MechanicalPart;
import br.com.mdcore.json.ProjectConfig;
import br.com.mdcore.parts.PartFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartFactoryTest {

    @Test
    void deveCriarCaixaAPartirDoJsonInterno() {
        ProjectConfig.PartSpec spec = new ProjectConfig.PartSpec();

        spec.tipo = "caixa";
        spec.nome = "caixa_teste";
        spec.largura_mm = 80;
        spec.comprimento_mm = 120;
        spec.altura_mm = 40;
        spec.espessura_parede_mm = 3;

        MechanicalPart part = PartFactory.create(spec);

        assertNotNull(part);
        assertTrue(part.getPartName().contains("caixa"));
        assertTrue(part.buildGeometry().contains("solid"));
    }

    @Test
    void deveCriarSolidoDeRevolucaoAPartirDoJsonInterno() {
        ProjectConfig.PartSpec spec = new ProjectConfig.PartSpec();

        spec.tipo = "solido_revolucao";
        spec.nome = "bocal_teste";
        spec.perfil = "linear";
        spec.altura_mm = 60;
        spec.raio_base_mm = 20;
        spec.raio_topo_mm = 8;
        spec.segmentos_radiais = 16;
        spec.segmentos_altura = 10;

        MechanicalPart part = PartFactory.create(spec);

        assertNotNull(part);
        assertTrue(part.getPartName().contains("bocal"));
        assertTrue(part.buildGeometry().contains("facet normal"));
    }
}