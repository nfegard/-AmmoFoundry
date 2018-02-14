package Nodes;

public class SmeltCannonBalls implements ExecutableNode {

    @Override
    public int executeNodeAction() throws InterruptedException {
        // use steel bars on furnace, interact with widget

        return 1;
    }

    @Override
    public boolean verifyCorrectNode() throws InterruptedException {
        // check for steel bars + ammo mould, edgeville furnace area contains player
        return true;
    }
}
