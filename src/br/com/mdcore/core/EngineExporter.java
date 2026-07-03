package br.com.mdcore.core;

/**
 * Contrato para exportadores do MDcore.
 *
 * Responsabilidade:
 * - Definir como uma peça mecânica será exportada.
 *
 * Hoje o framework usa STL por meio de StlDirectAdapter.
 * No futuro, poderiam existir outros exportadores, como:
 * - OBJ
 * - STEP
 * - relatório visual
 * - integração com slicer
 *
 * Essa interface ajuda a separar o núcleo do framework
 * do formato final de saída.
 */
public interface EngineExporter {

    /**
     * Exporta uma peça para um caminho de saída.
     *
     * @param part peça mecânica a ser exportada
     * @param outputPath caminho do arquivo de saída
     */
    void exportPart(MechanicalPart part, String outputPath);
}