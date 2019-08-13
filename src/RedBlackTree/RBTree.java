package RedBlackTree;

import java.util.Iterator;

/**
 * Creat by ConfusedCat
 * 2019.8.5
 * 红黑树的性质
 * 1.每个结点或是红色的，或是黑色的
 * 2.根结点是黑色的
 * 3.每个叶节结点（null）是黑色的
 * 4.如果一个结点是红色的，则它的两个子节点都是黑色的
 * 5.对每个结点，从该结点到其所有的后代叶结点的简单路径上，均包含相同数目的黑色结点
 */

public class RBTree {
    //1表示红色结点
    private final static int RED=1;
    //0表示蓝色结点
    private final static int BLACK=0;
    //树的叶节点
    private final static TreeNode T_NULL=new TreeNode(BLACK);
    //红黑树中结点的数量
    private int size=0;
    //红黑树的根节点
    private TreeNode root;
    //红黑树中值最靠前的结点
    private TreeNode firstNode;

    public RBTree(){
        root=T_NULL;
    }
    public void add(int value){
        TreeNode x=new TreeNode(value,null,T_NULL,T_NULL,RED);
        insert(x);
    }
    public TreeNode getNode(int value){
        TreeNode node=root;
        while(node!=T_NULL){
            if(node.value==value)
                return node;
            if(node.value>value){
                node=node.right;
            }else node=node.left;
        }
        return null;
    }
    public void delete(TreeNode z){RBDelete(z);}
    public Iterator iterator(){return new Iter();}
    public TreeNode getMinNode(){return firstNode;}
    public TreeNode getMaxNode(){return firstNode.last;}
    /**
     * 左旋
     * @param x
     */
    private void leftRotate(TreeNode x){
        TreeNode y=x.right;
        x.right=y.left;
        if(y.left!=T_NULL){
            y.left.parent=x;
        }
        y.parent=x.parent;
        if(x==root){
            root=y;
        }else if(x==x.parent.left){
            x.parent.left=y;
        }else{
            x.parent.right=y;
        }
        y.left=x;
        x.parent=y;
    }

    /**
     * 右旋
     * @param x
     */
    private void rightRotate(TreeNode x){
        TreeNode y=x.left;
        x.left=y.right;
        if(y.right!=T_NULL){
            y.right.parent=x;
        }
        y.parent=x.parent;
        if(x==root){
            root=y;
        }else if(x==x.parent.left){
            x.parent.left=y;
        }else{
            x.parent.right=y;
        }
        y.right=x;
        x.parent=y;
    }

    /**
     * 将结点z插入到红黑树中
     * @param z
     */
    private void insert(TreeNode z){
        size++;
        TreeNode y=T_NULL;
        TreeNode x=root;
        while(x!=T_NULL){
            y=x;
            if(z.value<=x.value){
                x=x.left;
            }else x=x.right;
        }
        z.parent=y;
        if(y==T_NULL){
            root=z;
            firstNode=root;
            root.last=root;
            root.next=root;
        }else if(z.value<=y.value){
            y.left=z;
            z.next=y;
            z.last=y.last;
            y.last.next=z;
            y.last=z;
            //如果y.last的值比z的值大，说明y.last这个结点的值在红黑树中是最大的
            if(z.last.value>z.value)
                firstNode=z;
        }else {
            y.right=z;
            z.next=y.next;
            z.last=y;
            y.next.last=z;
            y.next=z;
        }
        insertFixup(z);
    }

    /**
     * 插入结点后平衡红黑树结构
     * @param z
     */
    private void insertFixup(TreeNode z){
        while (T_NULL!=z.parent&&T_NULL!=z.parent.parent&&z.parent.color==RED){
            //z的父结点为左孩子时
            if(z.parent==z.parent.parent.left){
                TreeNode y=z.parent.parent.right;
                //情况一：z的叔结点的颜色为红色
                if(y.color==RED){
                    z.parent.color=BLACK;
                    y.color=BLACK;
                    z.parent.parent.color=RED;
                    z=z.parent.parent;
                } else{
                    //情况二：z的叔结点的颜色为黑色其z为左孩子
                    if(z==z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    //情况三：情况二之后一定为情况三，z的叔结点的颜色为黑色其z为右孩子
                    z.parent.color=BLACK;
                    z.parent.parent.color=RED;
                    rightRotate(z.parent.parent);
                }
            //z的父结点为右孩子
            }else{
                TreeNode y=z.parent.parent.left;
                //情况一：z的叔结点的颜色为红色
                if(y.color==RED){
                    z.parent.color=BLACK;
                    y.color=BLACK;
                    z.parent.parent.color=RED;
                    z=z.parent.parent;
                } else{
                    //情况二：z的叔结点的颜色为黑色其z为右孩子
                    if(z==z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    //情况三：情况二之后一定为情况三，z的叔结点的颜色为黑色其z为左孩子
                    z.parent.color=BLACK;
                    z.parent.parent.color=RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color=BLACK;
    }
    /**
     * 使用v结点替换u结点
     * @param u
     * @param v
     */
    private void transplant(TreeNode u,TreeNode v){
        if(u.parent==T_NULL){
            root=v;
        }else if(u==u.parent.left){
            u.parent.left=v;
        }else u.parent.right=v;
        v.parent=u.parent;
    }

    /**
     * 找到以node结点为根结点的子树中的结点的值最小的结点
     * @param node
     * @return
     */
    private TreeNode getMiniMum(TreeNode node){
        while(node.left!=T_NULL){
            node=node.left;
        }
        return node;
    }

    /**
     * 删除红黑树中的结点
     * @param z 需要删除的结点
     */
    private void RBDelete(TreeNode z){
        size--;
        if(size==0) {
            firstNode = T_NULL;
        }else{
            if(firstNode==z)
                firstNode=z.next;
            z.next.last=z.last;
            z.last.next=z.next;
        }
        TreeNode y=z;
        int originalColor=y.color;
        TreeNode x;
        //x的父结点，x为T_NULL时，从红黑树中得不到父结点，所以需要记录
        TreeNode parent;
        //删除结点的左孩子为 T_NULL 时，直接用删除结点的右孩子代替
        if(z.left==T_NULL){
            x=z.right;
            parent=z.parent;
            transplant(z,x);
        //删除结点的右孩子为 T_NULL 时，直接用删除结点的左孩子代替
        }else if(z.right==T_NULL){
            x=z.left;
            parent=z.parent;
            transplant(z,x);
        //删除结点的左右孩子都不为 T_NULL 时，使用删除结点右子树上最小的结点代替
        }else{
            y=getMiniMum(z.right);
            originalColor=y.color;
            //由y的左结点为T_NULL可知，y的右结点为T_NULL或者结点颜色为红色，否则不满足红黑树性质
            x=y.right;
            if(y.parent==z){
                parent=y;
            }else{
                parent=y.parent;
                transplant(y,y.right);
                y.right=z.right;
                y.right.parent=y;
            }
            transplant(z,y);
            y.left=z.left;
            y.left.parent=y;
            y.color=z.color;
        }
        //如果x的结点颜色为BLACK，那么x==T_NULL
        if(originalColor==BLACK)
            deleteFixup(x,parent);
    }

    /**
     * 删除树中的结点后，使树重新具有红黑树的性质
     * @param x 删除的结点的左孩子
     * @param parent 删除结点后，x在树中的父结点
     */
    private void deleteFixup(TreeNode x,TreeNode parent){
        while(x!=root && x.color==BLACK){
            if(x==parent.left){
                TreeNode w=parent.right;
                //情况一：x的兄弟结点w是红色
                if(w.color==RED){
                    parent.color=RED;
                    w.color=BLACK;
                    leftRotate(x.parent);
                    w=parent.right;
                }
                //情况二：x的兄弟结点是黑色的，x的两个孩子也是黑色的
                if(w.left.color==BLACK&&w.right.color==BLACK){
                    w.color=RED;
                    x=parent;
                    parent=parent.parent;
                }else{
                    //情况三：x的兄弟w是黑色的，w的左孩子是红色的，w的右孩子是黑色的
                    if(w.right.color==BLACK){
                        w.left.color=BLACK;
                        w.color=RED;
                        rightRotate(w);
                        w=parent.right;
                    }
                    //情况四：x的兄弟w是黑色的，w的右孩子是红色的
                    w.color=parent.color;
                    parent.color=BLACK;
                    w.right.color=BLACK;
                    leftRotate(x.parent);
                    x=root;
                }
            }else{
                TreeNode w=parent.left;
                //情况一：x的兄弟结点w是红色
                if(w.color==RED){
                    w.color=BLACK;
                    parent.color=RED;
                    rightRotate(parent);
                    w=parent.left;
                }
                //情况二：x的兄弟结点是黑色的，x的两个孩子也是黑色的
                if(w.left.color==BLACK&&w.right.color==BLACK){
                    w.color=RED;
                    x=parent;
                    parent=parent.parent;
                }else{
                    //情况三：x的兄弟w是黑色的，w的右孩子是红色的，w的左孩子是黑色的
                    if(x.left.color==BLACK){
                        w.right.color=BLACK;
                        w.color=RED;
                        leftRotate(w);
                        w=parent.left;
                    }
                    //情况四：x的兄弟w是黑色的，w的左孩子是红色的
                    w.color=parent.color;
                    parent.color=BLACK;
                    w.left.color=BLACK;
                    rightRotate(parent);
                    x=root;
                }
            }
        }
        if(x!=T_NULL)
            x.color=BLACK;
    }
    class Iter implements Iterator<TreeNode>{
        //记录当前遍历的位置
        int loca=0;
        //记录当前遍历的结点
        TreeNode nowNode=firstNode;
        //标识是否调用了next()方法
        boolean mark=false;

        Iter(){}

        @Override
        public boolean hasNext() {
            if(loca<size)
                return true;
            return false;
        }
        @Override
        public TreeNode next() {
            loca++;
            nowNode=nowNode.next;
            mark=true;
            return nowNode.last;
        }
        @Override
        public void remove() {
            if(mark) {
                mark=false;
                RBDelete(nowNode.last);
            }else{
                System.out.println("错误的操作");
            }
        }
    }
}
