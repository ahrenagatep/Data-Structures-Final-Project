class BinarySearchTree {
    // Node class for the tree
    class Node {
        String exercise;
        Node left, right;

        public Node(String exercise) {
            this.exercise = exercise;
            left = right = null;
        }
    }
    private Node root; // root of tree

    // Constructor initializes the root to Mountain Climbers (m is middle of alphabet)
    public BinarySearchTree() {
        root = new Node("Mountain Climbers");
    }

    // Method to insert a new value into the tree
    public void insert(String exercise) {
        root = insertRec(root, exercise);
    }

    private Node insertRec(Node root, String exercise) {
        if (root == null) {
            root = new Node(exercise);
            return root;
        }
        if (exercise.compareTo(root.exercise) < 0) {        // compares alphabetical order
            root.left = insertRec(root.left, exercise);     // inserts left if first alphabetically
        } else if (exercise.compareTo(root.exercise) > 0) { // inserts right if second alphabetically
            root.right = insertRec(root.right, exercise);
        }
        return root;
    }

    // Method to search for a value in the tree
    public boolean search(String exercise) {
        return searchRec(root, exercise);
    }
    private boolean searchRec(Node root, String exercise) {
        if (root == null) {
            return false;
        }
        if (root.exercise.equalsIgnoreCase(exercise)) {
            return true;
        }
        // ternary, if true return left of root, if false return right of root
        return exercise.compareTo(root.exercise) < 0 ? searchRec(root.left, exercise) : searchRec(root.right, exercise);
    }
}