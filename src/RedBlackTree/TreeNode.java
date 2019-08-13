package RedBlackTree;

public class TreeNode{
    int value;
    //父结点
    TreeNode parent;
    //左孩子
    TreeNode left;
    //右孩子
    TreeNode right;
    //在红黑树中的结点排列顺序中排在这个结点前的结点
    TreeNode last;
    //在红黑树中的结点排列顺序中排在这个结点后的结点
    TreeNode next;
    int color;
    public TreeNode(){
        color=0;
    }
    public TreeNode(int color){
        this.color=color;
    }
    public TreeNode(int value,TreeNode parent,TreeNode left,TreeNode right,int color){
        this.value=value;
        this.parent=parent;
        this.left=left;
        this.right=right;
        this.color=color;
    }

    @Override
    public String toString() {
        return "value:"+this.value+"  color:"+this.color;
    }
}
