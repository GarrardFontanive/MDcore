package br.com.mdcore.parts;

import br.com.mdcore.core.MechanicalPart;

/**
 * Gera um sólido de revolução parametrizado.
 *
 * Esta peça representa a parte matemática do MDcore:
 * um perfil 2D é rotacionado ao redor do eixo Z,
 * formando uma malha triangular exportável para STL.
 *
 * Perfis suportados:
 * - linear
 * - senoidal
 * - cilindro
 *
 * O objetivo é mostrar que o framework não depende apenas
 * de um catálogo de peças, mas pode gerar geometria a partir
 * de regras matemáticas.
 */
public class RevolutionSolid implements MechanicalPart {

    private final String name;
    private final String profile;

    private double height;
    private double baseRadius;
    private double topRadius;
    private double amplitude;

    private final int radialSegments;
    private final int heightSegments;

    public RevolutionSolid(String name,
                           String profile,
                           double height,
                           double baseRadius,
                           double topRadius,
                           double amplitude,
                           int radialSegments,
                           int heightSegments) {

        this.name = name;
        this.profile = profile;
        this.height = height;
        this.baseRadius = baseRadius;
        this.topRadius = topRadius;
        this.amplitude = amplitude;
        this.radialSegments = radialSegments;
        this.heightSegments = heightSegments;
    }

    @Override
    public String getPartName() {
        return name;
    }

    @Override
    public void applyTolerance(double gap) {
        this.baseRadius += gap;
        this.topRadius += gap;
    }

    @Override
    public String buildGeometry() {
        StringBuilder stl = new StringBuilder();

        stl.append("solid ").append(getPartName()).append("\n");

        for (int i = 0; i < heightSegments; i++) {
            double z1 = height * i / heightSegments;
            double z2 = height * (i + 1) / heightSegments;

            double r1 = radiusAt(z1);
            double r2 = radiusAt(z2);

            for (int j = 0; j < radialSegments; j++) {
                double a1 = 2.0 * Math.PI * j / radialSegments;
                double a2 = 2.0 * Math.PI * (j + 1) / radialSegments;

                double[] p1 = point(r1, a1, z1);
                double[] p2 = point(r1, a2, z1);
                double[] p3 = point(r2, a1, z2);
                double[] p4 = point(r2, a2, z2);

                appendTriangle(stl, p1, p3, p2);
                appendTriangle(stl, p2, p3, p4);
            }
        }

        stl.append("endsolid ").append(getPartName()).append("\n");

        return stl.toString();
    }

    private double radiusAt(double z) {
        String normalizedProfile = profile == null ? "linear" : profile.trim().toLowerCase();

        return switch (normalizedProfile) {
            case "cilindro", "cylinder" -> baseRadius;

            case "senoidal", "sine", "sin" -> {
                double middle = (baseRadius + topRadius) / 2.0;
                double wave = amplitude * Math.sin(2.0 * Math.PI * z / height);
                yield middle + wave;
            }

            case "linear" -> {
                double t = z / height;
                yield baseRadius + (topRadius - baseRadius) * t;
            }

            default -> {
                double t = z / height;
                yield baseRadius + (topRadius - baseRadius) * t;
            }
        };
    }

    private double[] point(double radius, double angle, double z) {
        return new double[]{
                radius * Math.cos(angle),
                radius * Math.sin(angle),
                z
        };
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