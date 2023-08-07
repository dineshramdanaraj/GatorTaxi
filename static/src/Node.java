// create a node that is stored in rbt
// the node contains attributes regarding parents, left child, right child, value it stores and color of it
public class Node {
    Ride ride;
    Node left;
    Node right;//right child
    Node parent;// left child
    Color color;

    public Node(Ride ride) {
        this.ride = ride;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = Color.RED;//in rbt the color of the new node is always red
        // (later color is flipped according to the position of it in the tree)

    }

}
