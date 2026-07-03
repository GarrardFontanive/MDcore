package br.com.mdcore.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class MechConfigParser {

    public static EnclosureConfig loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, EnclosureConfig.class);
        }
    }

    public static ProjectConfig loadProjectConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, ProjectConfig.class);
        }
    }

    public static boolean isProjectConfiguration(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            return root.has("objetos") || root.has("pecas") || root.has("projeto");
        }
    }
}
