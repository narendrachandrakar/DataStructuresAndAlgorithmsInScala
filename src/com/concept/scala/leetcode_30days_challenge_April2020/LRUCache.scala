package com.concept.scala.leetcode_30days_challenge_April2020

import scala.collection.mutable.{HashMap, ListBuffer}

/** *
  * Day 24
  *
  * @see https://leetcode.com/explore/challenge/card/30-day-leetcoding-challenge/531/week-4/3309/
  *      OR
  *      https://leetcode.com/problems/lru-cache/
  *
  *
  *      Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
  *
  *      get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
  *      put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
  *
  *      The cache is initialized with a positive capacity.
  *
  *      Follow up:
  *      Could you do both operations in O(1) time complexity?
  *
  *      Example:
  *
  *      LRUCache cache = new LRUCache( 2 /* capacity */ );
  *
  * cache.put(1, 1);
  * cache.put(2, 2);
  * cache.get(1);       // returns 1
  * cache.put(3, 3);    // evicts key 2
  * cache.get(2);       // returns -1 (not found)
  * cache.put(4, 4);    // evicts key 1
  * cache.get(1);       // returns -1 (not found)
  * cache.get(3);       // returns 3
  * cache.get(4);       // returns 4
  *
  *
  * 18 / 18 test cases passed.
  * Status: Accepted
  * Runtime: 1504 ms
  * Memory Usage: 79.2 MB
  *
  * Runtime: 1576 ms, faster than 28.57% of Scala online submissions for LRU Cache.
  * Memory Usage: 84.4 MB, less than 100.00% of Scala online submissions for LRU Cache.
  */
object LRUCache {


  def main(args: Array[String]): Unit = {

    // Your LRUCache object will be instantiated and called as such:

    val cache = new LRUCache(4 /* capacity */);

    println(s"cache.put(1, 1) ==> ${cache.put(1, 1)}\t ${cache.listbuffer}")
    println(s"cache.put(2, 2) ==> ${cache.put(2, 2)}\t ${cache.listbuffer}")
    println(s"cache.get(1)    ==> ${cache.get(1)}   \t ${cache.listbuffer}") // returns 1
    println(s"cache.put(3, 3) ==> ${cache.put(3, 3)}\t ${cache.listbuffer}") // evicts key 2
    println(s"cache.get(2)    ==> ${cache.get(2)}   \t ${cache.listbuffer}") // returns -1 (not found)
    println(s"cache.put(4, 4) ==> ${cache.put(4, 4)}\t ${cache.listbuffer}") // evicts key 1
    println(s"cache.get(1)    ==> ${cache.get(1)}   \t ${cache.listbuffer}") // returns -1 (not found)
    println(s"cache.get(3)    ==> ${cache.get(3)}   \t ${cache.listbuffer}") // returns 3
    println(s"cache.get(4)    ==> ${cache.get(4)}   \t ${cache.listbuffer}") // returns 4
    /*
    Output:

    cache.put(1, 1) ==> ()	 ListBuffer(1)
    cache.put(2, 2) ==> ()	 ListBuffer(1, 2)
    cache.get(1)    ==> 1 	 ListBuffer(2, 1)
    cache.put(3, 3) ==> ()	 ListBuffer(2, 1, 3)
    cache.get(2)    ==> 2 	 ListBuffer(1, 3, 2)
    cache.put(4, 4) ==> ()	 ListBuffer(1, 3, 2, 4)
    cache.get(1)    ==> 1 	 ListBuffer(3, 2, 4, 1)
    cache.get(3)    ==> 3 	 ListBuffer(2, 4, 1, 3)
    cache.get(4)    ==> 4 	 ListBuffer(2, 1, 3, 4)

     */
  }

}

class LRUCache(_capacity: Int) {

  val hashmap = HashMap[Int, Int]() // to store the value for it's respective key
  val listbuffer = ListBuffer[Int]() // To store the order

  val capacityOfMap = _capacity //given size with which we can control the size of HashMap (mutable HashMap size is dynamic so we need an additional variable to track the size)
  var sizeOfListBuffer = 0 //same as above line ,size of listBuffer is dynamic so we have to track it's size to see if we are exceeding _capacity value

  def get(key: Int): Int = {
    var result = -1 //the value to return, if the key is not found in the hashmap
    if (hashmap.contains(key)) {
      //if the key you are looking for is already in the hashmap
      result = hashmap(key) // then get the value of that key
      //move the key to the end of listbuffer
      addKeyAtTheEnd(key)


    }
    result
  }

  def put(key: Int, value: Int) {

    if (hashmap.contains(key)) {
      // update the key's value in hashmap
      hashmap(key) = value
      // add  the key at the very end of listbuffer
      addKeyAtTheEnd(key)
    } else {
      //since key is not present in  hashmap we have to make a decision for the edge case, i.e
      // i.e "What if adding one more element would exceed the capacity of hashmap?"
      if (capacityOfMap == sizeOfListBuffer) {
        //you cannot PUT an additonal value into hashmap when it is already at it's full capacity.
        // So decide the last recent used value. {It present at the begining of the listbuffer}
        val lastRecentlyUsedIndex = listbuffer.head

        //so remove lastRecentlyUsedIndex from the begining . i.e remove the head from list   {REMOVE THE INDEX}
        listbuffer -= lastRecentlyUsedIndex

        //remove LRU key (listbuffer head's key ) and it's value {REMOVE THE KEY VALUE PAIR}
        hashmap.remove(lastRecentlyUsedIndex)

      } else {
        sizeOfListBuffer += 1 // we are increasing the buffer size as we are about add one more value to it.
      }
      //add the index in listbuffer
      listbuffer.append(key)
    }
    // add/update(upsert) the key with new value //  "hashMap(key)= value" will create a keyValue pair if key is not present else update the key's value
    hashmap(key) = value

  }

  def addKeyAtTheEnd(key: Int) = {
    //add Key At The End by removing the listbuffer index of the key
    listbuffer.remove(listbuffer.indexOf(key))
    // then appending the key to listbuffer at the end
    listbuffer.append(key)
  }

}
