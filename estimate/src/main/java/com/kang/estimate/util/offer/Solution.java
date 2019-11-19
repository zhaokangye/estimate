package com.kang.estimate.util.offer;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Solution {

    /**
     * 在一个二维数组中（每个一维数组的长度相同），
     * 每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public boolean Find(int target, int [][] array) {
        int max_row=array.length-1;
        int max_col=array[0].length-1;
        int row=max_row;
        int col=0;
        while(row>=0&&col<=max_col){
            if(target==array[row][col]){
                return true;
            }else if(target>array[row][col]){
                col++;
            }else{
                row--;
            }
        }
        return false;
    }

    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list=new ArrayList<>();
        if(listNode==null){
            return list;
        }
        readEveryNode(list,listNode);
        return list;
    }

    public void readEveryNode (ArrayList<Integer> list,ListNode listNode){
        if(listNode.next!=null){
            readEveryNode(list,listNode.next);
        }
        list.add(listNode.val);
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
     * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     * @param pre
     * @param in
     * @return
     */
    public static TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if(pre.length==0||in.length==0){
            return null;
        }
        int rootVal=pre[0];
        TreeNode root=new TreeNode(rootVal);
        if(pre.length==1){
            return root;
        }
        int rootIndex=0;
        for(int i=0;i<in.length;i++){
            if(rootVal==in[i]){
                rootIndex=i;
                break;
            }
        }
        root.left=reConstructBinaryTree(Arrays.copyOfRange(pre,1,rootIndex+1),Arrays.copyOfRange(in,0,rootIndex));
        root.right=reConstructBinaryTree(Arrays.copyOfRange(pre,rootIndex+1,pre.length),Arrays.copyOfRange(in,rootIndex,in.length));
        return root;
    }

    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.empty()){
            while(!stack1.empty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if(array.length==0){
            return 0;
        }
        int smallest=array[0];
        for(int val:array){
            if(val<smallest){
                return val;
            }
        }
        return smallest;
    }

    /**
     * 同一题的二分查找解法
     * @param array
     * @return
     */
    public static int minNumberInRotateArrayInBinary(int [] array) {
        if(array.length==0){
            return 0;
        }
        int low=0;
        int high=array.length-1;
        while(low<=high){
            int mid=low+(high-low)/2;
            if(array[mid]>=array[0]){
                low=mid+1;
            }else if(array[mid]<array[0]){
                high=mid-1;
            }else{
                return array[mid];
            }
        }
        return array[low];
    }

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。n<=39
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     * @param n
     * @return
     */
    public int Fibonacci(int n) {
        if(n==1){
            return 1;
        }
        int fn1=0;
        int fn2=1;
        int sum=0;
        for(int i=2;i<=n;i++){
            int fn=fn1+fn2;
            sum=fn;
            fn1=fn2;
            fn2=fn;
        }
        return sum;
    }

    public static void main(String[] args) {

    }
}
