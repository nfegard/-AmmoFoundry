package Nodes;

public class WalkToEdgeFurnace implements ExecutableNode {

    @Override
    public int executeNodeAction() throws InterruptedException {
        // use normal walk if possible, then webwalk to edge furnace.

        return 1;
    }

    @Override
    public boolean verifyCorrectNode() throws InterruptedException {
        // check for steel bars + ammo mould, edgeville furnace area contains player
        return true;
    }
}