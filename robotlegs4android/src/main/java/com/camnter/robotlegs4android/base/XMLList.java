package com.camnter.robotlegs4android.base;

import java.util.ArrayList;

/**
 * Description：XMLList
 * Created by：CaMnter
 */
public class XMLList extends ArrayList<XML> {
    private static final long serialVersionUID = 1L;

    /**
     * Obtain XMLList node under each child node in an XML list This is a
     * recursive process, XMLList -  XML -  XMLList -  XML...
     * 取得XMLList节点下每一个XML中的子节点列表
     * 这是一个递归的过程，XMLList - XML - XMLList - XML...
     *
     * @return new an XMLList of adding all the nodes new 一个添加所有子节点的 XMLList
     */
    public XMLList children() {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            result.addAll(this.get(i).children());
        }
        return result;
    }

    /**
     * Search for XML name attribute of the same XML all child nodes
     * 查找XML的name属性相同的所有XML子节点
     *
     * @param name The XML's name attribute XML的name属性
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     */
    public XMLList findXMLListByName(String name) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).name.equals(name)) {
                result.add(this.get(i));
            }
        }
        return result;
    }

    /**
     * Search for the prototype in a XML key and the value of the same XML all
     * child nodes
     * 查找XML中prototype的key和value相同的所有XML子节点
     *
     * @param key   The key to the prototype of XML XML的prototype的key
     * @param value The value to the prototype of XML XML的prototype的value
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     */
    public XMLList findXMLListByKeyValue(String key, String value) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getValue(key).equals(value)) {
                result.add(this.get(i));
            }
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get all the same name attribute node (all nodes and child nodes)
     * 获取所有name属性相同节点(所有的节点和子节点)
     *
     * @param name The XML's name attribute XML的name属性
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByName(String name) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            XML tempXml = this.get(i);
            // XML class getXMLListByName(String name)
            result.addAll(tempXml.getXMLListByName(name));
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get all the prototype property at the same key nodes (all nodes and child
     * nodes)
     * 获取所有prototype属性的key相同节点(所有的节点和子节点)
     *
     * @param key The key of the prototype properties prototype属性的key
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByKey(String key) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            XML tempXml = this.get(i);
            // XML class getXMLListByKey(String key)
            result.addAll(tempXml.getXMLListByKey(key));
        }
        return result;
    }

    /**
     * *************************************************************************
     * Obtain all share the same name and prototype property key nodes (all
     * nodes and child nodes)
     * 获取所有name相同 和 prototype属性的key相同节点(所有的节点和子节点)
     *
     * @param name The XML's name attribute XML的name属性
     * @param key  The key of the prototype properties prototype属性的key
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByNameAndKey(String name, String key) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            XML tempXml = this.get(i);
            // XML class getXMLListByNameAndKey(String name, String key)
            result.addAll(tempXml.getXMLListByNameAndKey(name, key));
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get all the attributes of the prototype and prototype same properties key
     * value of the same node (all nodes and child nodes)
     * 获取所有 prototype属性的key相同 和 prototype属性的value相同节点(所有的节点和子节点)
     *
     * @param key   The key of the prototype properties prototype属性的key
     * @param value The value of the prototype properties prototype属性的value
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByKeyValue(String key, String value) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            XML tempXml = this.get(i);
            // XML class getXMLListByKeyValue(String key, String value)
            result.addAll(tempXml.getXMLListByKeyValue(key, value));
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get all the attributes of the same name and prototype key value
     * corresponding to the node (all nodes and child nodes)
     * 获取所有name相同 和 prototype属性的key value对应的节点(所有的节点和子节点)
     *
     * @param name  The XML's name attribute XML的name属性
     * @param key   The key of the prototype properties prototype属性的key
     * @param value The value of the prototype properties prototype属性的value
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByNameAndKeyValue(String name, String key, String value) {
        XMLList result = new XMLList();
        for (int i = 0; i < this.size(); i++) {
            XML tempXml = this.get(i);
            /*
             * XML class getXMLListByNameAndKeyValue(String name, String key,
             * String value)
             */
            result.addAll(tempXml.getXMLListByNameAndKeyValue(name, key, value));
        }
        return result;
    }

}