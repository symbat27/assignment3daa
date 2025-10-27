package MSTOptimization;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Graph> graphs = Graph.readGraphs("input_example.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray results = new JsonArray();

        int id = 1;
        for (Graph g : graphs) {
            JsonObject item = new JsonObject();
            item.addProperty("graph_id", id);

            JsonObject inputStats = new JsonObject();
            inputStats.addProperty("vertices", g.nodes.size());
            inputStats.addProperty("edges", g.edges.size());
            item.add("input_stats", inputStats);

            Map<String, Object> prim = PrimMST.runPrim(g);
            Map<String, Object> kruskal = KruskalMST.runKruskal(g);

            item.add("prim", toJsonObj(prim, gson));
            item.add("kruskal", toJsonObj(kruskal, gson));
            results.add(item);

            System.out.printf(
                    "Graph %d -> Prim: cost=%d, Kruskal: cost=%d%n",
                    id,
                    ((Number) prim.get("total_cost")).intValue(),
                    ((Number) kruskal.get("total_cost")).intValue()
            );

            id++;
        }

        JsonObject output = new JsonObject();
        output.add("results", results);
        try (Writer w = new FileWriter("output.json")) {
            gson.toJson(output, w);
        }
        System.out.println("Results saved to output.json");
    }

    private static JsonObject toJsonObj(Map<String, Object> map, Gson gson) {
        JsonObject obj = new JsonObject();
        obj.add("mst_edges", gson.toJsonTree(map.get("mst_edges")));
        obj.addProperty("total_cost", (Number) map.get("total_cost"));
        obj.addProperty("operations_count", (Number) map.get("operations_count"));
        obj.addProperty("execution_time_ms", (Number) map.get("execution_time_ms"));
        return obj;
    }
}








