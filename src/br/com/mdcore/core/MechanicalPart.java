package br.com.mdcore.core;

/**
 * Contrato base para qualquer peça mecânica gerada pelo MDcore.
 *
 * Toda peça do framework precisa implementar esta interface.
 *
 * Responsabilidade:
 * - Informar o nome da peça.
 * - Receber uma tolerância dimensional.
 * - Gerar sua geometria em formato STL textual.
 *
 * Essa interface permite que o motor principal trabalhe com qualquer tipo
 * de peça sem conhecer os detalhes de implementação.
 *
 * Exemplos de implementações:
 * - EnclosureBox
 * - RevolutionSolid
 * - outras peças futuras
 */
public interface MechanicalPart {

    /**
     * Retorna o nome da peça.
     *
     * Esse nome também pode ser usado para formar o nome do arquivo STL.
     */
    String getPartName();

    /**
     * Aplica uma tolerância dimensional à peça.
     *
     * A tolerância é calculada de acordo com o material e parâmetros de impressão.
     * Algumas peças podem alterar largura, raio ou encaixes.
     * Outras podem simplesmente ignorar esse ajuste.
     */
    void applyTolerance(double gap);

    /**
     * Gera a geometria da peça em formato STL textual.
     *
     * O retorno é uma String contendo a malha triangular da peça.
     */
    String buildGeometry();
}