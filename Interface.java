
public class Interface {
    public static void main(String[] args){
        test();
    }

    public static void test(){
        BinaryTree<Integer> myTree = new BinaryTree<Integer>();

        myTree.add(5);
        myTree.add(4);
        myTree.add(7);
        myTree.add(0);
        System.out.println(myTree.toString());
        
        
    }
}
