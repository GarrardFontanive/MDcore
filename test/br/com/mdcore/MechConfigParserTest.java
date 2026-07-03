package br.com.mdcore;

import br.com.mdcore.json.MechConfigParser;
import br.com.mdcore.json.ProjectConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MechConfigParserTest {

    @Test
    void deveLerArquivoProjetoMdcoreJson() throws IOException {
        ProjectConfig config = MechConfigParser.loadProjectConfiguration("projeto_mdcore.json");

        config.normalizeDefaults();

        assertNotNull(config);
        assertNotNull(config.material);
        assertNotNull(config.objetos);
        assertFalse(config.objetos.isEmpty());
    }
}