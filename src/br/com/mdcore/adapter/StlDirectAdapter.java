package br.com.mdcore.adapter;

import br.com.mdcore.core.EngineExporter;
import br.com.mdcore.core.MechanicalPart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Adapter responsável por exportar uma MechanicalPart para arquivo STL.
 *
 * Ele adapta a geometria textual gerada pela peça para um arquivo físico .stl.
 */
public class StlDirectAdapter implements EngineExporter {

    @Override
    public void exportPart(MechanicalPart part, String outputPath) {
        try {
            File file = new File(outputPath);

            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(part.buildGeometry());
            }

            System.out.println("Sucesso! Ficheiro STL gerado para: " + outputPath);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao exportar STL: " + outputPath, e);
        }
    }
}