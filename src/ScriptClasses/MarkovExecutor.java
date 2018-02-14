package ScriptClasses;

import Nodes.ExecutableNode;
//import Nodes.TestNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class MarkovExecutor {
    private class NodeEdge {
        ExecutableNode u; //source node
        ExecutableNode v; //edge to some other node
        int edgeExecutionWeight; //how often do we randomly traverse to this node, higher = more frequent. Relative to edgeExecutionWeights of sibling nodes.
        //ex: if node A had outgoing edges with weights 2, 3, 5. Then edge with weight 2 will be executed 20% of the time, 3 -> 30%, and 5 -> 50%.

        NodeEdge(ExecutableNode u, ExecutableNode v, int edgeExecutionWeight) {
            this.u = u;
            this.v = v;
            this.edgeExecutionWeight = edgeExecutionWeight;
        }
    }

    private HashMap<ExecutableNode, LinkedList<NodeEdge>> adjMap;
    private ExecutableNode current;

    public MarkovExecutor(ExecutableNode startingNode){
        adjMap = new HashMap<>();
        current = startingNode;
    }

    public void addEdgeToNode(ExecutableNode u, ExecutableNode v, int edgeExecutionWeight){
        if(adjMap.containsKey(u)){ //adjMap has u stored inside
            LinkedList<NodeEdge> edges = adjMap.get(u);
            if(edges == null){ //check if list of edges for u is instantiated, if not do so.
                edges = new LinkedList<>();

            }
            //add new edge
            edges.add(new NodeEdge(u, v, edgeExecutionWeight));
            adjMap.put(u, edges);
        }
        else{
            LinkedList<NodeEdge> edges = new LinkedList<>();
            edges.add(new NodeEdge(u, v, edgeExecutionWeight));
            adjMap.put(u, edges);
        }
    }

    public void deleteEdgeForNode(ExecutableNode u, ExecutableNode v){
        if(adjMap.containsKey(u)){
            LinkedList<NodeEdge> edges = adjMap.get(u);
            edges.forEach(edge -> {
                if(edge.v == v){
                    edges.remove(edge);
                }
            });
        }
    }

    public int executeNodeThenTraverse() throws InterruptedException {
        int onLoopSleepTime = current.executeNodeAction();
        traverseToNextNode();
        return onLoopSleepTime;
    }

    private void traverseToNextNode() {
        // Coding pattern for random percentage branching
        // https://stackoverflow.com/questions/45836397/coding-pattern-for-random-percentage-branching?noredirect=1&lq=1
        if (current != null) {
            LinkedList<NodeEdge> edges = adjMap.get(current);
            if (edges.size() == 0) {
                return;
            }
            int combinedWeight = edges.stream().mapToInt(edge -> edge.edgeExecutionWeight).sum();
            int sum = 0;
            int roll = ThreadLocalRandom.current().nextInt(1, combinedWeight + 1);
            NodeEdge selectedEdge = null;
            for (NodeEdge edge : edges) {
                sum += edge.edgeExecutionWeight;
                if (sum >= roll) {
                    selectedEdge = edge;
                    break;
                }
            }
            if (selectedEdge == null) {
                selectedEdge = edges.getLast();
            }
            current = selectedEdge.v;
        }
    }
}