package br.com.mdcore.tolerance;

/**
 * Classe responsável por calcular a folga dimensional
 * de acordo com o material utilizado na impressão 3D.
 *
 * Esta versão substitui várias classes pequenas de tolerância
 * por uma única classe simples e direta.
 *
 * A ideia continua sendo a mesma do padrão Strategy:
 * materiais diferentes recebem comportamentos diferentes.
 */
public class MaterialTolerance {

    private MaterialTolerance() {
        // Classe utilitária.
    }

    public static double calculateGap(String material, double nozzleSize, double baseGap) {
        double base = baseGap > 0 ? baseGap : 0.2;
        double nozzle = nozzleSize > 0 ? nozzleSize : 0.4;

        if (material == null) {
            return base + nozzle * 0.10;
        }

        return switch (material.trim().toUpperCase()) {
            case "PLA" -> base + nozzle * 0.10;
            case "PETG" -> base + nozzle * 0.15;
            case "ABS" -> base + nozzle * 0.20;
            case "TPU" -> base + nozzle * 0.25;
            default -> base + nozzle * 0.10;
        };
    }
}