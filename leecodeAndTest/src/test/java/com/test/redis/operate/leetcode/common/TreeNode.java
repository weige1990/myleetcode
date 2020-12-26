package com.test.redis.operate.leetcode.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode {

    public TreeNode right;
    public TreeNode left;
    private boolean isRoot;

    private Object val;


    public TreeNode(int val) {
        this.val = val;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }


    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode(Object val) {

        this.val = val;
    }

    public static TreeNode array2TreeNode(Integer... arr) {


        final int lastNum = arr.length - 1 + 1;//2最后一个数字的编号,应为索引+1

        TreeNode root = new TreeNode(arr[0]);
        List<TreeNode> lastNodes = new LinkedList<>();//4上一层的节点
        lastNodes.add(root);
        int lastMaxIndex = 0;
        root.setRoot(true);
        root.setRight(null);
        int notf = 1;//3numOfNodeOfThisFloor


        //------------------------
        List<TreeNode> thisNodes;
        Object rightValueTemp = null;
        Object leftValueTemp = null;
        TreeNode lastNodeTemp = null;
        outter:
        while (lastMaxIndex + 1 < lastNum) {
            notf *= 2;
//            System.out.println(notf);
            thisNodes = new LinkedList<>();
            Iterator<TreeNode> treeNodeIterator = lastNodes.iterator();
            inner:
            for (int i = 0; i <= lastNodes.size() - 1; i++) {
                lastNodeTemp = treeNodeIterator.next();

                while (lastNodeTemp == null && treeNodeIterator.hasNext())//1迭代所有的上一层的节点,为空的就下一个
                {
                    lastNodeTemp = treeNodeIterator.next();

                }

                if (lastMaxIndex + 1 <= arr.length - 1) {//2给上一层的节点加入左节点
                    leftValueTemp = arr[lastMaxIndex + 1];
                    if (lastNodeTemp != null) {

                        lastNodeTemp.setLeft(leftValueTemp != null ? new TreeNode(leftValueTemp) : null);
                        thisNodes.add(lastNodeTemp.getLeft());
                    }
                    if (lastMaxIndex + 1 == arr.length - 1) {
                        break outter;
                    }
                } else {

                    break outter;
                }
                if (lastMaxIndex + 2 <= arr.length - 1) {//3给上一层节点加上右节点

                    rightValueTemp = arr[lastMaxIndex + 2];
                    if (lastNodeTemp != null) {


                        lastNodeTemp.setRight(rightValueTemp != null ? new TreeNode(rightValueTemp) : null);
                        thisNodes.add(lastNodeTemp.getRight());
                    }
                    if (lastMaxIndex + 2 == arr.length - 1) {
                        break outter;
                    }
                } else {
                    break outter;
                }
                lastMaxIndex += 2;
            }

            lastNodes = thisNodes;
        }

        return root;
    }


    public Boolean isExist(Integer val) {
        Boolean isExist = null;


        isExist = this.getVal() == val || (this.getLeft() != null && this.getLeft().isExist(val)) || (this.getRight() != null && this.getRight().isExist(val));

        return isExist;
    }

    public Boolean isNotExist(Integer val) {
        Boolean isNotExist = null;


        isNotExist = this.getVal() != val || (this.getLeft() == null || this.getLeft().isNotExist(val)) && (this.getRight() == null || this.getRight().isNotExist(val));

        return isNotExist;
    }

    public TreeNode insert(Comparable val) {

        if (((Comparable) this.getVal()).compareTo(val) < 0) {

            if (this.getRight() == null) {

                this.setRight(new TreeNode(val));
            } else {
                this.right.insert(val);
            }

        } else if (((Comparable) this.getVal()).compareTo(val) > 0 ) {

            if (this.left == null) {

                this.setLeft(new TreeNode(val));
            } else {
                this.left.insert(val);
            }
        }
        return this;
    }

    public String toString() {
        return (this.left != null ? this.left.toString() : "") + this.val.toString() + "," + (this.right != null ? this.right.toString() : "");
    }


}