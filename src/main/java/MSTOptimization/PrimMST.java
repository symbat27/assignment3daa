package MSTOptimization;

import java.util.*;

public class PrimMST {
    public static Map<String, Object> runPrim(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        Set<String> visited = new HashSet<>();
        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;

        String startNode = graph.nodes.get(0);
        visited.add(startNode);

        while (visited.size() < graph.nodes.size()) {
            Edge minEdge = null;
            for (Edge e : graph.edges) {
                operations++;
                boolean oneVisited = visited.contains(e.from) ^ visited.contains(e.to);
                if (oneVisited) {
                    if (minEdge == null || e.weight < minEdge.weight) {
                        minEdge = e;
                    }
                }
            }
            if (minEdge == null) break;
            mst.add(minEdge);
            totalCost += minEdge.weight;
            visited.add(minEdge.from);
            visited.add(minEdge.to);
        }

        double timeMs = (System.nanoTime() - start) / 1e6;
        Map<String, Object> result = new HashMap<>();
        result.put("mst_edges", mst);
        result.put("total_cost", totalCost);
        result.put("operations_count", operations);
        result.put("execution_time_ms", timeMs);
        return result;
    }
}





