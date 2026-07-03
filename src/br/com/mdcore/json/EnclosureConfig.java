package br.com.mdcore.json;

/**
 * Configuração do modo legado do MDcore.
 *
 * Esta classe representa o arquivo antigo caixa.json, usado na primeira
 * versão do framework para gerar uma caixa e uma tampa diretamente.
 *
 * Mesmo na versão mais limpa do MDcore, ela pode ser mantida para
 * compatibilidade com o fluxo inicial do projeto.
 */
public class EnclosureConfig {

    private String peca;
    private String material;

    private double nozzleSize;
    private double nozzle_size;

    private Dimensoes dimensoes;

    public String getPeca() {
        if (peca == null || peca.isBlank()) {
            return "EnclosureBox";
        }

        return peca;
    }

    public String getMaterial() {
        if (material == null || material.isBlank()) {
            return "PLA";
        }

        return material;
    }

    public double getNozzleSize() {
        if (nozzleSize > 0) {
            return nozzleSize;
        }

        if (nozzle_size > 0) {
            return nozzle_size;
        }

        return 0.4;
    }

    public Dimensoes getDimensoes() {
        if (dimensoes == null) {
            dimensoes = new Dimensoes();
        }

        return dimensoes;
    }

    /**
     * Dimensões principais da caixa no modo legado.
     *
     * Os nomes seguem o padrão usado no caixa.json antigo.
     */
    public static class Dimensoes {

        public double largura_mm = 80.0;
        public double comprimento_mm = 120.0;
        public double altura_mm = 40.0;
        public double espessura_parede_mm = 3.0;
    }
}