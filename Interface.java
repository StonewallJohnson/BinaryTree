
public class Interface {
    public static void main(String[] args){
        test();
        System.out.println();
        stressTest();
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

    public static void stressTest(){
        BinaryTree<Integer> tree = new BinaryTree<Integer>();
        int runfor = 10000000;
        System.out.println("Adding and removing " + runfor + " times");
        for(int i = 0; i < runfor; i++){
            tree.add(i);
            tree.remove(i);
        }
        System.out.println("Done.");
    }
}
