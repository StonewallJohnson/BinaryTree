
public class Interface {
    public static void main(String[] args){
        test();
    }

    public static void test(){
        BinaryTree<Integer> myTree = new BinaryTree<Integer>();
        int[] items = new int[] {5, 4, 7, 0, 6, 20, 3, 10, 30, 9, 15, 21, 33};
        
        for(int i : items){
            System.out.println("Adding: " + i);
            myTree.add(i);
            System.out.println(myTree.toString());
            System.out.println("Size: " + myTree.size());
        }

        System.out.println();
        
        for(int i : items){
            System.out.println("Getting " + i);
            System.out.println(myTree.get(i));
        }

        System.out.println();

        for(int i : items){
            System.out.println("Removing " + i);    
            myTree.remove(i);
            System.out.println(myTree.toString());
        }


    }
}
