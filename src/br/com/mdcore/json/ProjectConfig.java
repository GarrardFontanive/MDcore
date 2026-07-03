package br.com.mdcore.json;

import java.util.ArrayList;
import java.util.List;

public class ProjectConfig {

    public String projeto;
    public String material;
    public Impressora impressora;
    public double baseGap;
    public String outputDir;
    public List<PartSpec> objetos;
    public List<PartSpec> pecas;

    public void normalizeDefaults() {
        if (projeto == null || projeto.isBlank()) {
            projeto = "MDcore_Project";
        }
        if (material == null || material.isBlank()) {
            material = "PLA";
        }
        if (impressora == null) {
            impressora = new Impressora();
        }
        if (impressora.nozzle <= 0) {
            impressora.nozzle = 0.4;
        }
        if (baseGap <= 0) {
            baseGap = 0.2;
        }
        if (outputDir == null || outputDir.isBlank()) {
            outputDir = "src/PecasGeradas";
        }
        if (objetos == null && pecas != null) {
            objetos = pecas;
        }
        if (objetos == null) {
            objetos = new ArrayList<>();
        }
    }

    public double getNozzleSize() {
        return impressora != null && impressora.nozzle > 0 ? impressora.nozzle : 0.4;
    }

    public static class Impressora {
        public double nozzle;
    }

    public static class PartSpec {
        public String tipo;
        public String nome;

        public double largura_mm;
        public double comprimento_mm;
        public double altura_mm;
        public double espessura_parede_mm;
        public double espessura_mm;

        public String tipo_parafuso;
        public int quantidade;
        public double raio_mm;
        public double comprimento_parafuso_mm;

        public double altura_suporte_mm;
        public double diametro_furo_mm;

        public int colunas;
        public int linhas;
        public double largura_abertura_mm;
        public double espacamento_mm;

        public String perfil;
        public double raio_base_mm;
        public double raio_topo_mm;
        public double amplitude_mm;
        public int segmentos_radiais;
        public int segmentos_altura;
    }
}
