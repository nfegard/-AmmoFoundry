package Nodes;

public interface ExecutableNode {
    int executeNodeAction() throws InterruptedException;
    boolean verifyCorrectNode() throws InterruptedException;
}