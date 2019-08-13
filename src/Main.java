import RedBlackTree.RBTree;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        RBTree tree=new RBTree();
        Queue queue=new PriorityQueue();
        for(int i=10;i>0;i--){
            System.out.println(i);
            tree.add(i);
        }
        tree.add(12);
        System.out.println(tree.getMaxNode());
        System.out.println(tree.getMinNode());

        Iterator iter=tree.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
        tree.delete(tree.getNode(7));
        iter=tree.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
        System.out.println("Hello World!");
    }
}
