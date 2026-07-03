package br.com.mdcore.core;

import br.com.mdcore.adapter.StlDirectAdapter;
import br.com.mdcore.json.ProjectConfig;
import br.com.mdcore.parts.PartFactory;
import br.com.mdcore.tolerance.MaterialTolerance;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor principal do framework MDcore.
 *
 * Responsabilidade:
 * - Receber uma configuração de projeto vinda do JSON.
 * - Normalizar valores padrão.
 * - Calcular a tolerância de fabricação conforme o material.
 * - Criar as peças por meio da PartFactory.
 * - Aplicar tolerância nas peças.
 * - Exportar cada peça para STL.
 *
 * Fluxo principal:
 *
 * JSON -> ProjectConfig -> MDcoreEngine -> MechanicalPart -> STL
 */
public class MDcoreEngine {

    private final EngineExporter exporter;

    public MDcoreEngine() {
        this.exporter = new StlDirectAdapter();
    }

    public MDcoreEngine(EngineExporter exporter) {
        this.exporter = exporter;
    }

    public void run(ProjectConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Configuração do projeto não pode ser nula.");
        }

        config.normalizeDefaults();

        System.out.println("Material: " + config.material);
        System.out.println("Diretório de saída: " + config.outputDir);

        if (config.objetos == null || config.objetos.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma peça foi declarada no projeto.");
        }

        double nozzleSize = config.getNozzleSize();
        double toleranceGap = MaterialTolerance.calculateGap(
                config.material,
                nozzleSize,
                config.baseGap
        );

        System.out.printf("Tolerância aplicada: %.3f mm%n", toleranceGap);
        System.out.println("Peças declaradas: " + config.objetos.size());

        List<MechanicalPart> generatedParts = new ArrayList<>();

        for (ProjectConfig.PartSpec spec : config.objetos) {
            MechanicalPart part = PartFactory.create(spec);
            part.applyTolerance(toleranceGap);
            generatedParts.add(part);
        }

        for (MechanicalPart part : generatedParts) {
            String outputPath = config.outputDir + "/" + part.getPartName() + ".stl";
            exporter.exportPart(part, outputPath);
        }

        System.out.println("Arquivos STL gerados: " + generatedParts.size());
    }
}