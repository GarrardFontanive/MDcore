package br.com.mdcore.parts;

import br.com.mdcore.core.MechanicalPart;
import br.com.mdcore.json.ProjectConfig;

/**
 * Factory responsável por transformar uma especificação declarada no JSON
 * em uma peça concreta do MDcore.
 *
 * Esta classe evita que o motor principal precise conhecer detalhes
 * de cada tipo de peça.
 *
 * Tipos suportados nesta versão limpa:
 * - caixa
 * - solido_revolucao
 */
public class PartFactory {

    public static MechanicalPart create(ProjectConfig.PartSpec spec) {
        if (spec == null) {
            throw new IllegalArgumentException("Especificação da peça não pode ser nula.");
        }

        if (spec.tipo == null || spec.tipo.isBlank()) {
            throw new IllegalArgumentException("Toda peça precisa ter um campo 'tipo'.");
        }

        String type = spec.tipo.trim().toLowerCase();

        return switch (type) {
            case "caixa", "box", "enclosurebox" -> new EnclosureBox(
                    firstNonBlank(spec.nome, "caixa_parametrica"),
                    valueOr(spec.largura_mm, 80),
                    valueOr(spec.comprimento_mm, 120),
                    valueOr(spec.altura_mm, 40),
                    valueOr(spec.espessura_parede_mm, 3)
            );

            case "solido_revolucao", "revolucao", "revolution", "revolve" -> new RevolutionSolid(
                    firstNonBlank(spec.nome, "solido_revolucao"),
                    firstNonBlank(spec.perfil, "linear"),
                    valueOr(spec.altura_mm, 60),
                    valueOr(spec.raio_base_mm, spec.raio_mm, 20),
                    valueOr(spec.raio_topo_mm, spec.raio_mm, 8),
                    valueOr(spec.amplitude_mm, 3),
                    spec.segmentos_radiais > 0 ? spec.segmentos_radiais : 64,
                    spec.segmentos_altura > 0 ? spec.segmentos_altura : 40
            );

            default -> throw new IllegalArgumentException("Tipo de peça não suportado: " + spec.tipo);
        };
    }

    private static String firstNonBlank(String value, String fallback) {
        return value != null && !value.isBlank() ? value : fallback;
    }

    private static double valueOr(double value, double fallback) {
        return value > 0 ? value : fallback;
    }

    private static double valueOr(double first, double second, double fallback) {
        if (first > 0) {
            return first;
        }

        if (second > 0) {
            return second;
        }

        return fallback;
    }
}