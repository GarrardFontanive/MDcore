package br.com.mdcore.parts;

import br.com.mdcore.core.MechanicalPart;

/**
 * Representa uma caixa técnica parametrizada.
 *
 * Esta peça demonstra o uso funcional do MDcore:
 * o usuário informa dimensões e espessura de parede,
 * e o framework gera uma geometria STL correspondente.
 *
 * A geometria é formada por cinco cuboides:
 * - fundo;
 * - parede frontal;
 * - parede traseira;
 * - parede esquerda;
 * - parede direita.
 */
public class EnclosureBox implements MechanicalPart {

    private final String name;

    private double width;
    private double length;
    private double height;
    private double wall;

    public EnclosureBox(String name, double width, double length, double height, double wall) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.wall = wall;
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public void applyTolerance(double gap) {
        this.width += gap;
        this.length += gap;
    }

    @Override
    public String buildGeometry() {
        StringBuilder stl = new StringBuilder();

        stl.append("solid ").append(getPartName()).append("\n");

        // Fundo
        appendCuboid(stl, 0, 0, 0, width, length, wall);

        // Parede frontal
        appendCuboid(stl, 0, 0, wall, width, wall, height);

        // Parede traseira
        appendCuboid(stl, 0, length - wall, wall, width, length, height);

        // Parede esquerda
        appendCuboid(stl, 0, wall, wall, wall, length - wall, height);

        // Parede direita
        appendCuboid(stl, width - wall, wall, wall, width, length - wall, height);

        stl.append("endsolid ").append(getPartName()).append("\n");

        return stl.toString();
    }

    private void appendCuboid(StringBuilder stl,
                              double x1, double y1, double z1,
                              double x2, double y2, double z2) {

        double[][] v = {
                {x1, y1, z1},
                {x2, y1, z1},
                {x2, y2, z1},
                {x1, y2, z1},
                {x1, y1, z2},
                {x2, y1, z2},
                {x2, y2, z2},
                {x1, y2, z2}
        };

        int[][] faces = {
                {0, 1, 2}, {0, 2, 3},
                {4, 6, 5}, {4, 7, 6},
                {0, 4, 5}, {0, 5, 1},
                {1, 5, 6}, {1, 6, 2},
                {2, 6, 7}, {2, 7, 3},
                {3, 7, 4}, {3, 4, 0}
        };

        for (int[] face : faces) {
            appendTriangle(stl, v[face[0]], v[face[1]], v[face[2]]);
        }
    }

    private void appendTriangle(StringBuilder stl, double[] a, double[] b, double[] c) {
        stl.append("  facet normal 0 0 0\n");
        stl.append("    outer loop\n");
        appendVertex(stl, a);
        appendVertex(stl, b);
        appendVertex(stl, c);
        stl.append("    endloop\n");
        stl.append("  endfacet\n");
    }

    private void appendVertex(StringBuilder stl, double[] v) {
        stl.append("      vertex ")
                .append(v[0]).append(" ")
                .append(v[1]).append(" ")
                .append(v[2]).append("\n");
    }
}