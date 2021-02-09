public class BinaryTree<E extends Comparable<E>>{
    private int size;
    private Node<E> root;
    private Node<E> grandRoot;

    /**
     * Creates a BinaryTree with size = 0 and 
     * a null root
     */
    public BinaryTree(){
        size = 0;
        root = null;
        grandRoot = new Node<E>(root, null, root);
    }
    
    public BinaryTree(E val){
        grandRoot = new Node<E>(root, null, root);
        add(root, val);
    }

    private static class Node<E>{
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;

        /**
         * Creates a node with a left child, value, and right child
         * @param left the left child of this node
         * @param val the value of this node
         * @param right the right child of this node
         */
        private Node(Node<E> left, E val, Node<E> right){
            value = val;
            leftChild = left;
            rightChild = right;
        }

        /**
         * Creates a node with a given value and sets the 
         * children to null
         * @param val the value to be contained in the node
         */
        private Node(E val){
            this(null, val, null);
        }

        /**
         * Returns the value of this node
         */
        private E getValue(){
            return value;
        }

        /**
         * Sets the nodes value to the specified value
         */
        private void setValue(E newVal){
            value = newVal;
        }

        /**
         * Returns the left child of this node
         */
        private Node<E> getLeft(){
            return leftChild;
        }

        /**
         * Returns the right child of this node
         */
        private Node<E> getRight(){
            return rightChild;
        }

        /**
         * Sets this node's left child to left
         * @param left the new left child
         */
        private void setLeft(Node<E> left){
            leftChild = left;
        }
        
        /**
         * Sets this node's right child to right
         * @param right the new right child of this node
         */
        private void setRight(Node<E> right){
            rightChild = right;
        }
    }

    /**
     * 
     * @return the number of nodes in the tree
     */
    int size(){
        return size;
    }

    /**
     * creates a node and inserts it into the binary tree
     * @param val the value to be added to the tree
     */
    public void add(E val){
        checkValNull(val);

        if(size() == 0){
            //this will be the root node
            root = new Node<E>(val);
            grandRoot.setLeft(root);
            grandRoot.setRight(root);
            size++; 
        }
        else if(get(val) == null){
            //not the root node and not duplicate
            //TODO: better way to check for duplicate val?
            Node<E> temp = root;
            boolean foundParent = false;
            boolean left = false;
            while(!foundParent){
                //until fall out of tree
                int comp = temp.getValue().compareTo(val);
                if(comp > 0){
                    // temp.val > val, go left
                    if(temp.getLeft() == null){
                        foundParent = true;
                    }
                    left = true;
                }
                else if(comp < 0){
                    // temp.val < val, go right
                    if(temp.getRight() == null){
                        foundParent = true;
                    }
                    left = false;
                }
                
                temp = left ? temp.getLeft(); t
            }
            //Link node
            Node<E> newNode = new Node<E>(val);
            if(left){
                temp.setLeft(newNode);
            }
            else{
                temp.setRight(newNode);
            }
            size++;
        }
        else{
            System.out.println("Can't store duplicates");
        }
    }
    
    public Node<E> add(Node<E> root, E val){
        checkValNull(val);

        if(root == null){
            //base case, make the new node
            root = new Node<E>(val);
        }
        else{
            //recursive
            int comp = root.getValue().compareTo(val);
            if(comp > 0){
                //val < root.val, go left
                root.setLeft(add(root.getLeft(), val));
            }
            else{
                //val > root.val, go right
                root.setRight(add(root.getRight(), val));
            }
        }
        return root;
    }

    /**
     * Removes a given value from the tree
     * @param val the val to be removed from the tree
     */
    public void remove(E val){
        checkValNull(val);
    
        Node<E> grandparent = getGrandparent(val);
        boolean parentIsLeftChild = grandparent.getLeft().getValue() == val ? true : false;
        //If the val is in left child of grandparent, parent is left child, right child otherwise
        Node<E> parent = parentIsLeftChild ? grandparent.getLeft() : grandparent.getRight();

        if(parent.getLeft() == null && parent.getRight() == null){
            //no children
            parent = null;
        }
        else if(parent.getLeft() != null ^ parent.getRight() != null){
            //one child
            //set parent to its child
            parent = parent.getLeft() == null ? parent.getRight() : parent.getLeft();
            //set grandparent's child to new parent
            LinkGrandToCorrectChild(parentIsLeftChild, grandparent, parent, parent);
        }
        else{
            //two children
            int comp = parent.getValue().compareTo(root.getValue());
            if(comp < 0){
                //left case
                Node<E> rightChild = parent.getRight();
                Node<E> leftChild = parent.getLeft();
                parent = rightChild;
                while(parent.getLeft() != null){
                    //until bottom is found
                    parent = parent.getLeft();
                }
                parent.setLeft(rightChild);
                LinkGrandToCorrectChild(parentIsLeftChild, grandparent, leftChild, rightChild);
            }
            else{
                //right and root
                //TODO: refactor these declarations?
                Node<E> rightChild = parent.getRight();
                Node<E> leftChild = parent.getLeft();
                parent = leftChild;
                while(parent.getRight() != null){
                    //until bottom is found
                    parent = parent.getRight();
                }
                parent.setRight(rightChild);
                LinkGrandToCorrectChild(parentIsLeftChild, grandparent, leftChild, rightChild);
            }
        }
        size--;
    }

    /**
     * Method that relinks the original grandparent with the new parent (old left or right child)
     * @param whichChild is true if the new parent should be on left, false if right
     * @param grand the grandparent node
     * @param left the left child of the node to be removed
     * @param right the right child of the node to be removed
     */
    private void LinkGrandToCorrectChild(boolean whichChild, Node<E> grand, Node<E> left, Node<E> right){
        if(whichChild){
            //left child is the parent
            grand.setLeft(left);
        }
        else{
            //right child is the parent
            grand.setRight(right);
        }
    }
     
    /**
     * Finds the given node with the given val in the tree
     * @param val the value being searched for
     * @return the node with the given val or 
     * null if the that val is not in the tree
     */
    public Node<E> get(E val){
        checkValNull(val);
        Node<E> temp = root;
        while (temp != null && temp.getValue() != val){
            // until the proper child is found or fell out of tree
            int comp = temp.getValue().compareTo(val);
            if(comp > 0){
                // temp.val > val, go left
                temp = temp.getLeft();
            }
            else if(comp < 0){
                // temp.val < val, go right
                temp = temp.getRight();
            }
        }
        return temp;
    }
    
    /**
     * Gets the node before the one with the given val
     * @param val the value to search for
     */
    private Node<E> getGrandparent(E val){
        Node <E> temp = grandRoot;
        boolean foundParentVal = temp.getLeft().getValue() == val || temp.getRight().getValue() == val;
        while(temp != null && !foundParentVal){
                //until the parentVal is found, or not found (should always be found)
            int comp = temp.getValue().compareTo(val);
            if(comp > 0){
                // temp.val > val, go left
                temp = temp.getLeft();
            }
            else if(comp < 0){
                // temp.val < val, go right
                temp = temp.getRight();
            }
        }
        //grandparent found
        return temp;
    }
 
    /**
     * Creates a preOrder string of the elements in the tree
     */
    public String preOrder(){
        StringBuilder str = new StringBuilder();
        recurPre(str, root);
        return str.toString();
    }

    private void recurPre(StringBuilder str, Node<E> current){
        if(current == null){
            //base case, do not add anything to string
        }
        else{
            //recursive
            str.append(current.getValue().toString());
            str.append(", ");
            recurPre(str, current.getLeft());
            recurPre(str, current.getRight());
        }
    }
    
    /**
     * 
     * @return a string of the elements in the tree inorder
     */
    public String inOrder(){
        StringBuilder str = new StringBuilder();
        recurIn(str, root);
        return str.toString();
    }
    
    private void recurIn(StringBuilder str, Node<E> current){
        if(current == null){
            //base case, do not add anything
        }
        else{
            //recursive
            recurIn(str, current.getLeft());
            str.append(current.getValue().toString());
            str.append(", ");
            recurIn(str, current.getRight());
        }
    }

    /**
     * 
     * @return a string of the values in this tree in postorder
     */
    public String postOrder(){
        StringBuilder str = new StringBuilder();
        recurPost(str, root);
        return str.toString();
    }

    private void recurPost(StringBuilder str, Node<E> current){
        if(current == null){
            //base case, do not add anything
        }
        else{
            //recursive
            recurPost(str, current.getLeft());
            recurPost(str, current.getRight());
            str.append(current.getValue().toString());
            str.append(", ");
        }
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("Pre: " + preOrder() + "\n");
        ret.append("In: " + inOrder() + "\n");
        ret.append("Post: " + postOrder());
        return ret.toString();
    }

    /**
     * Checks to see if the given val is null and throws
     * IAE if it is
     * @param val the value to be checked
     */
    private void checkValNull(E val){
        if(val == null){
            throw new IllegalArgumentException("This tree can not store null values!");
        }
    }
}