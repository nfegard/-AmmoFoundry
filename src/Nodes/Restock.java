package Nodes;

public class Restock implements ExecutableNode {

    @Override
    public int executeNodeAction() throws InterruptedException {
        // use noted steel bars on bank and interact with banker

        return 1;
    }

    @Override
    public boolean verifyCorrectNode() throws InterruptedException {
        // check for noted steel bars + ammo mould, edgeville bank area contains player
        return true;
    }
}
