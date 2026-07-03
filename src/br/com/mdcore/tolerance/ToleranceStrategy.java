package br.com.mdcore.tolerance;

/**
 * Define uma estratégia para cálculo de tolerância dimensional.
 *
 * <p>Cada material pode possuir uma regra própria de compensação,
 * considerando comportamento térmico, retração e características
 * comuns de impressão 3D.</p>
 */
public interface ToleranceStrategy {

    /**
     * Calcula a folga final aplicada às peças geradas pelo framework.
     *
     * @param baseGap folga base definida pelo sistema.
     * @param nozzleSize tamanho do bico da impressora em milímetros.
     * @return folga final calculada para o material.
     */
    double calculateGap(double baseGap, double nozzleSize);
}