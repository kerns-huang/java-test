package tree;

/**
 * 默克尔树
 *
 * 在最底层，和哈希列表一样，我们把数据分成小的数据块，有相应地哈希和它对应。但是往上走，并不是直接去运算根哈希，
 * 而是把相邻的两个哈希合并成一个字符串，然后运算这个字符串的哈希，这样每两个哈希就结婚生子，得到了一个”子哈希“。
 * 如果最底层的哈希总数是单数，那到最后必然出现一个单身哈希，这种情况就直接对它进行哈希运算，所以也能得到它的子哈希。
 * 于是往上推，依然是一样的方式，可以得到数目更少的新一级哈希，最终必然形成一棵倒挂的树，到了树根的这个位置，这一代就剩下一个根哈希了，我们把它叫做 Merkle Root。
 * 主要用于p2p 数据下载
 *
 * 1:MT是一种树，大多数是二叉树，也可以多叉树，无论是几叉树，它都具有树结构的所有特点；
 * 2:Merkle Tree的叶子节点的value是数据集合的单元数据或者单元数据HASH。
 * 3:非叶子节点的value是根据它下面所有的叶子节点值，然后按照Hash算法计算而得出的
 * @author xiaohei
 * @create 2020-04-27 下午2:55
 **/
public class MerkleTree {
}