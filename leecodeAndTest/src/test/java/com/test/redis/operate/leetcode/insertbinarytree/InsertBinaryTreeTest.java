package com.test.redis.operate.leetcode.insertbinarytree;

import com.test.redis.operate.leetcode.common.TreeNode;
import org.testng.annotations.Test;

public class InsertBinaryTreeTest {


    @Test
    public void testInsertBinaryTreeNode() {
        //1新建一个二叉树
      /*  [40,20,60,10,30,50,70]

        25
        期望结果:[40,20,60,10,30,50,70,null,null,25]
        */

//        TreeNode node = TreeNode.array2TreeNode(4, 2, 7, 1, 3, null, null);
        TreeNode node = TreeNode.array2TreeNode(40, 20, 60, 10, 30, 50, 70);
        //2往里面增增一个变量
        System.out.println(insertValIntoTreeNode(node, 25));
    }


    private TreeNode insertValIntoTreeNode(TreeNode node, Integer val) {

        //1 遍历所有的节点值,假如我是唯一的
//          node.isExist(val);
        //2遍历所有节点,若节点值大于val且右节点为空,val就成为新的右节点,若节点值小于val且右节点为空,val就成为新的左节点

        node.insert(val);
        return node;
    }

    @Test
    public void testIsExit() {
        //1新建一个二叉树
        TreeNode node = TreeNode.array2TreeNode(4, 2, 7, 1, 3, null, null);


        System.out.println(node.isNotExist(5));

    }

}
