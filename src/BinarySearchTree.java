public class BinarySearchTree <E extends Comparable> {
    // private class for the Tree Node creates a tree node with value and left + right reference
    private class TreeNode <E> {
        E value;
        TreeNode<E> left;
        TreeNode<E> right;

        public TreeNode(E value) {
            this.value = value;
        }
    }

    // root tree node
    private TreeNode<E> root;

    // performs an in order traversal and displays the value at each node uses message as a title
    public void display(String message) {
        System.out.println(message);
        displayInOrder();
    }

    // displays the binary tree in order
    public void displayInOrder() {
        displayInOrder(root);
    }

    // displays in order the binary tree starting at a specified tree node using recursion
    private void displayInOrder(TreeNode<E> node) {
        if (node == null) return;

        displayInOrder(node.left);
        System.out.println(node.value);
        displayInOrder(node.right);
    }


    // returns the count of nodes recursion kicker method
    public int numberNodes() {
        return numberNodes(root);
    }

    // actual recursive method to count the number of nodes
    private int numberNodes(TreeNode<E> node) {
        // base case of a null node
        if ( node == null) {
            return 0;
        }

        return 1 + numberNodes(node.left) + numberNodes(node.right);
    }

    // kicker method for counting the number of leaf nodes
    public int numberLeafNodes() {
        return numberLeafNodes(root);
    }

    // counts the number of leaf nodes through recursion
    private int numberLeafNodes(TreeNode<E> node) {
        // base case
        if(node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }

        return numberLeafNodes(node.left) + numberLeafNodes(node.right);

    }

    // returns the height of the tree kicker method
    public int height() {
        return height(root);
    }

    // returns the height of a binary tree through recursion
    private int height(TreeNode<E> node) {
        // base case empty tree has height of -1
         if (node == null) {
             return -1;
         }
         // recursive step
         return Math.max(height(node.left), height(node.right)) + 1;
    }

    // removes a value from the binary search tree
    public boolean remove(E value) {
        // boolean to return if a value is deleted
        boolean wasDeleted = true;
        if (search(value)) {
            TreeNode<E> parent = null;
            TreeNode<E> node = root;
            boolean done = false;
            while (!done) {
                if (value.compareTo(node.value) > 0) {
                    parent = node;
                    node = node.right;
                } else if (value.compareTo(node.value) < 0) {
                    parent = node;
                    node = node.left;
                } else {
                    done = true;
                }
            }
            // Case for no left child
            if (node.left == null) {
                if (parent == null) {
                    root = node.right;
                } else {
                    if (value.compareTo(parent.value) > 0) {
                        parent.right = node.right;
                    } else {
                        parent.left = node.right;
                    }
                }
            } else { // Case for left child
                TreeNode<E> parentOfRight = node;
                TreeNode<E> rightMost = node.left;
                while (rightMost.right != null) {
                    parentOfRight = rightMost;
                    rightMost = rightMost.right;
                }
                node.value = rightMost.value;
                if (parentOfRight.right == rightMost) {
                    parentOfRight.right = rightMost.left;
                } else {
                    parentOfRight.left = rightMost.left;
                }
            }
        } else {
            wasDeleted = false;
        }
       return wasDeleted;
    }

    // inserts a value into the binary search tree
    public boolean insert(E value) {
        boolean duplicate = true;
        // finds where to insert the value into the tree with the parent reference
        if (root == null) {
            root = new TreeNode<E>(value);
        } else if (search(value)) {
            duplicate = false;
        } else {
            TreeNode<E> parent = null;
            TreeNode<E> node = root;
            while (node != null) {
                parent = node;
                if (value.compareTo(node.value) > 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            // actually inserts the value into the tree at the parent reference
            TreeNode<E> newNode = new TreeNode<E>(value);
            if (value.compareTo(parent.value) > 0) {
                parent.right = newNode;
            } else {
                parent.left = newNode;
            }
        }
        return duplicate;
    }

    // searches the binary tree for a specific value
    public boolean search(E value) {
        boolean found = false;
        TreeNode<E> node = root;

        while (!found && node != null) {
            if (value.compareTo(node.value) == 0) {
                found = true;
            } else if (value.compareTo(node.value) > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        return found;
    }

}