import java.io.BufferedWriter;
import java.io.IOException;

public class redBlackTree {
    Node root;
//initializing the rbt, by setting root to null.
    public redBlackTree() {
        this.root = null;
    }
//parent function returns the parent node of the given node ( 1 level prior)
    public Node parent(Node node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }
//grandparent function gives out the node that is the parent of the parent node( 2 levels prior)
    public Node grandparent(Node node) {
        if (node != null && node.parent != null) {
            return node.parent.parent;
        } else {
            return null;
        }
    }
// sibling node gives out the node that is in the same level of the given node and has the same parent of the given node.
    public Node sibling(Node node) {
        if (node != null && node.parent != null) {
            if (node == node.parent.left) {
                return node.parent.right;
            } else {
                return node.parent.left;
            }
        } else {
            return null;
        }
    }
// to rotate the node left inorder to balance the tree
    public void rotateLeft(Node node) {
        Node newnode = node.right;
        node.right = newnode.left;
        //case 1 where right child has left child
        if (newnode.left != null) {
            newnode.left.parent = node;
        }
        newnode.parent = node.parent;
        // case 2 where the node is root
        if (node.parent == null) {
            this.root = newnode;
            //case 3 where right child doesn't have left child
        } else if (node.parent.left == node) {
            node.parent.left = newnode;
        } else {
            node.parent.right = newnode;
        }

        newnode.left = node;
        node.parent = newnode;

    }
    // to rotate the node right inorder to balance the tree
    public void rotateRight(Node node) {
        Node newnode2 = node.left;
        node.left = newnode2.right;
        //case 1 where the left child has a right child
        if (newnode2.right != null) {
            newnode2.right.parent = node;
        }
        newnode2.parent = node.parent;
        //case 2 where the node is the root node
        if (node.parent == null) {
            this.root = newnode2;
            // case 3 where the node doesn't have right child
        } else if (node.parent.left == node) {
            node.parent.left = newnode2;
        } else {
            node.parent.right = newnode2;
        }
        newnode2.right = node;
        node.parent = newnode2;
    }

// the below function used to flip colors if both parent and child node have red color.
    public void colorFlip(Node node) {
        // iteratively doing the color flip from the new node to the root ( bottom-up method)
        while (node.parent != null && node.parent.color == Color.RED) {
            Node parentNode = parent(node);
            Node gpNode = grandparent(node);
            // it is done with 2-level process, so no gpnode means break the loop
            if (gpNode == null) {
                break;
            }
            Node pSibNode = sibling(parentNode);
//if the parent's sibling is also red, change grandparent node and their child's color
            if (pSibNode != null && pSibNode.color == Color.RED) {
                parentNode.color = Color.BLACK;
                pSibNode.color = Color.BLACK;
                gpNode.color = Color.RED;
                node = gpNode; //Continue iteration with grandparent node ( 2 level above)
            } else {
                // 2nd case: parent is re and sibling is black or null
                if (node == parentNode.right && parentNode == gpNode.left) {
                    // do LR in this case
                    rotateLeft(parentNode);
                    node = node.left;
                } else if (node == parentNode.left && parentNode == gpNode.right) {
                    // do RL in this case
                    rotateRight(parentNode);
                    node = node.right;
                }
// for LL or RR rotation case ( parent is red , it's sibling is black or null)
                parentNode.color = Color.BLACK;
                gpNode.color = Color.RED;
                if (node == parentNode.left) {
                    rotateRight(gpNode);
                } else {
                    rotateLeft(gpNode);
                }
            }
        }
        //root should always be black
        root.color = Color.BLACK;
    }

    public void insert(Ride ride) {
        Node newnode = new Node(ride);
        //If rb-tree is empty, insert new node as root and color it black
        if (this.root == null) {
            root = newnode;
            newnode.color = Color.BLACK;
            return;
        }
        // ELSE TRAVERSE THE TREE, to find the appropriate position to insert
        Node node = root;
        while (true) {
            //it does a binary search and insert the new node to where the traversing falls off from the tree.
            //if the ride number is smaller traverse to the left side.
            if (newnode.ride.rideNumber < node.ride.rideNumber) {
                if (node.left == null) {
                    node.left = newnode;
                    newnode.parent = node;
                } else {
                    node = node.left;
                }
//if ride number is greater traverse to right child
            } else if (newnode.ride.rideNumber > node.ride.rideNumber) {
                if (node.right == null) {
                    node.right = newnode;
                    newnode.parent = node;
                } else {
                    node = node.right;
                }
            } else {
                // If node already exists in tree, break
                break;
            }

        }
        // After inserting new node, check and fix any red and red color violations
        colorFlip(newnode);
    }
// this function is used to determine if the ride exists in the data structure.
    //If it does, then it results out the node where the respective ride is stored in.
    public Node findNode(int rideNumber) {
        Node node = root;
//initiate a traversal starting from root and proceed based on the conditions.
        while (node != null) {
            if (node.ride.rideNumber == rideNumber) {
                return node;
            } else if (node.ride.rideNumber > rideNumber) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
//if not found it returns null
        return null;
    }
// the below function is used to delete a ride based on it's ridenumber
    public void delete(int rideNumber) {
        Node node = findNode(rideNumber);
        //no node (for leaf node)
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            //if the deleted node is root
            if (node == root) {
                root = null;
            }
            else {
                //if the deleted node's color is black, then the number of black nodes to the external node changes.
                //thus it requires balancing
                if (node.color == Color.BLACK) {
                    balanceFlip(node);
                }
                //remove the pointers from the parent node to remove the node.
            if (node == node.parent.left) {
                    node.parent.left = null;
                }
            else{
                    node.parent.right = null;
                }


            }
        }
        // one node ( node having 1 child)
        else if (node.left == null || node.right == null) {
            //create child node that contains either one of the lc or rc(whichever one is not null)
            Node child = (node.left != null) ? node.left : node.right;
            //if the node is root then change the root pointer to it's child
            if (node == root) {
                root = child;
                child.parent = null;
                child.color = Color.BLACK;// root color should always be black
//if node is left child and color is black (remove it assign the parent pointer accordingly
            } else if (node == node.parent.left) {
                node.parent.left = child;
                child.parent = node.parent;
                if (node.color == Color.BLACK) {// if the removed node is black
                    balanceFlip(child);
                } else {
                    child.color = Color.BLACK;
                }
            } else {// if the node is a right child
                node.parent.right = child;
                child.parent = node.parent;
                if (node.color == Color.BLACK) {// if node color is black
                    balanceFlip(child);
                } else {
                    child.color = Color.BLACK;
                }
            }

        }
        // 2 nodes( if it contains 2 child)
        else {
            // get replacement node( the left subtree's right most child)
            // and swap it with the desired deletion node.
            Node rNode = node.left;
            while (rNode.right != null) {
                rNode = rNode.right;
            }
            //delete the replecement node form the tree
            delete(rNode.ride.rideNumber);
            node.ride = rNode.ride;
        }

    }


    private void balanceFlip(Node node) {
        while (node != root && node.color == Color.BLACK) {
            Node parent = parent(node);
            Node grandparent = grandparent(node);
            Node sibling = sibling(node);

            if (sibling == null) {
                // node has no sibling, move up to the parent
                node = parent;
            }
            else if (node == parent.left) {
                if (sibling.color == Color.RED) {
                    // if sibling is red and node is left child of the parent, rotate the parent towards left
                    //to make the sib as the new parent and assign new sib's color black and parent to red
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    rotateLeft(parent);
                    sibling = parent.right;//if exists, update the new sibling node to be the new right child of the parent
                    if (sibling == null){return; }
                }

                if (sibling.left == null || sibling.right == null) {
                    // sibling has no child, move up to the parent
                    node = parent;
                }

                else if (sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK) {
                    // If both children of the sibling are black, sibling color becomes red and move to the parent
                    sibling.color = Color.RED;
                    node = parent;
                } else {
                    if (sibling.right.color == Color.BLACK) {
                        // right child of the sibling is black, rotate sibling to the right
                        // color the sibling's left child to black and the color of the sibling to red

                        sibling.left.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotateRight(sibling);
                        sibling = parent.right;
                        if (sibling == null){return; }// if exists, Update the sibling to be the new left child of the parent
                    }

                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    sibling.right.color = Color.BLACK;
                    rotateLeft(parent);
                    node = root;// to terminate iteration
                }
            } else {
                if (sibling.color == Color.RED) {
                    // if sibling is red and node is right child of the parent, rotate the parent towards right
                    //to make the sib as the new parent and assign new sib's color black and parent to red
                    sibling.color = Color.BLACK;
                    parent.color = Color.RED;
                    rotateRight(parent);
                    sibling = parent.left;
                    if (sibling == null){return;};//if exists, update the new sibling node to be the new right child of the parent.
                }


                if (sibling.left == null || sibling.right == null) {
                    // sibling has no child, move up to the parent
                    node = parent;
                }

                else if (sibling.left.color == Color.BLACK && sibling.right.color == Color.BLACK) {
                    // If both children of the sibling are black, sibling color becomes red and move to the parent
                    sibling.color = Color.RED;
                    node = parent;
                } else {
                    if (sibling.left.color == Color.BLACK) {
                        // left child of the sibling is black, rotate sibling to the right
                        // color the sibling's right child to black and the color of the sibling to red
                        sibling.right.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotateLeft(sibling);
                        sibling = parent.left;
                        if (sibling == null){return; }//if exists, Update the sibling to be the new left child of the parent
                    }

                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    sibling.left.color = Color.BLACK;
                    rotateRight(parent);
                    node = root; // to terminate iteration
                }
            }
        }

        node.color = Color.BLACK;
    }
    // the below function acts as a helper function to print the rides between 2 ride numbers.
    //it takes buffer writter arguement to write the output in the output file.
    // a start boolean is used to indicate if the ride is starting ride for the given range, to eliminate "," from printing.
    public void printRange(Node node, int rn1, int rn2, BufferedWriter bw, boolean start) throws IOException {
        if (node == null) {
            return;
        }
// recursively calling the printrange function (similar to in-oreder traversal)
        if (node.ride.rideNumber > rn2) {

            this.printRange(node.left, rn1, rn2, bw, false);
        } else if (node.ride.rideNumber < rn1) {
            this.printRange(node.right, rn1, rn2, bw, true);
        } else {
            printRange(node.left, rn1, rn2, bw, start);
            if (node.ride.rideNumber >= rn1 && node.ride.rideNumber <= rn2) {
                //start is used to check if it is the starting ride
                if (!start) {
                    //if not the commas are written to the output
                    bw.write(",");
                    bw.write("(" + node.ride.rideNumber + "," + node.ride.rideCost + "," + node.ride.tripDuration + ")");
                    start = false;
                } else {
                    start = false;
                    bw.write("(" + node.ride.rideNumber + "," + node.ride.rideCost + "," + node.ride.tripDuration + ")");

                }
            }
            printRange(node.right, rn1, rn2, bw, false);
        }
    }
}
