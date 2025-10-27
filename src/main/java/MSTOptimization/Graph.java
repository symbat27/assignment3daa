package MSTOptimization;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Graph {
    public List<String> nodes;
    public List<Edge> edges;

    public static List<Graph> readGraphs(String filename) throws IOException {
        Gson gson = new Gson();
        InputStream in = Graph.class.getResourceAsStream("/" + filename);
        Reader reader = new InputStreamReader(in);
        JsonObject obj = gson.fromJson(reader, JsonObject.class);
        reader.close();

        List<Graph> graphs = new ArrayList<>();
        JsonArray arr = obj.getAsJsonArray("graphs");

        for (JsonElement el : arr) {
            JsonObject g = el.getAsJsonObject();
            Graph graph = new Graph();
            graph.nodes = new ArrayList<>();
            for (JsonElement n : g.getAsJsonArray("nodes"))
                graph.nodes.add(n.getAsString());
            graph.edges = new ArrayList<>();
            for (JsonElement e : g.getAsJsonArray("edges")) {
                JsonObject edge = e.getAsJsonObject();
                graph.edges.add(new Edge(
                        edge.get("from").getAsString(),
                        edge.get("to").getAsString(),
                        edge.get("weight").getAsInt()
                ));
            }
            graphs.add(graph);
        }
        return graphs;
    }
}




