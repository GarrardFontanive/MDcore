package br.com.mdcore.core;

import br.com.mdcore.json.MechConfigParser;
import br.com.mdcore.json.ProjectConfig;

/**
 * Classe principal do MDcore.
 *
 * Responsabilidade:
 * - Ser apenas o ponto de entrada da aplicação.
 * - Ler o caminho do arquivo JSON informado pelo usuário.
 * - Acionar o parser e o motor principal do framework.
 *
 * Esta classe não deve concentrar regra de negócio.
 * Toda a lógica principal fica em MDcoreEngine.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Iniciando o MDcore ===");

        try {
            String inputFile = args.length > 0 ? args[0] : "projeto_mdcore.json";

            ProjectConfig config = MechConfigParser.loadProjectConfiguration(inputFile);

            MDcoreEngine engine = new MDcoreEngine();
            engine.run(config);

            System.out.println("=== MDcore finalizado com sucesso ===");

        } catch (Exception e) {
            System.err.println("Erro ao executar o MDcore: " + e.getMessage());
            e.printStackTrace();
        }
    }
}