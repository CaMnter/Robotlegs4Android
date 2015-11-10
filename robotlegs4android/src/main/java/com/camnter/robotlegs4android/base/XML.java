package com.camnter.robotlegs4android.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：XML
 * Created by：CaMnter
 */
public class XML {
    public String name = "";
    public Map<String, String> prototype = new HashMap<>();
    public Object child = new XMLList();
    public XML parent;

    /**
     * Add an XML node, the XML node's parent node points to the corresponding
     * XMLList
     * 添加一个XML节点，该XML节点的父节点指向对应的XMLList
     *
     * @param childXml To add an XML node 要添加的一个XML节点
     * @return XML node is added 被添加的XML节点
     */
    public XML appendChild(XML childXml) {
        this.children().add(childXml);
        childXml.parent = this;
        return this;
    }

    /**
     * According to the name for the first child (XML)
     * If so, it returns the corresponding XML node, not return a new XML
     * 根据name获取第一个子节点(XML)
     * 如果有，则返回对应的XML节点，没有则返回一个new XML
     *
     * @param name XML's name XML的name
     * @return Corresponding to the name of the XML 对应name的XML
     */
    public XML getXMLByName(String name) {
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if (childList.get(i).name.equals(name))
                    return childList.get(i);
            }
        }
        return new XML();
    }

    /**
     * Whether child for XMLList instance, is converted to XMLList, will Object
     * type of child and return
     * 判断child是否为XMLList实例，再将Object类型的child转换成XMLList，并返回
     *
     * @return <code>XMLList</code> this.child object after conversion
     * this.child转换后的XMLList
     */
    public XMLList children() {
        XMLList result = new XMLList();
        if (XMLList.class.isInstance(this.child)) {
            result = (XMLList) this.child;
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get an XMLList contains all child nodes
     * Here is the child node traverse all the nodes, it is a recursive process
     * 获取一个包含所有子节点的XMLList
     * 这里是遍历所有节点的子节点，这是一个递归的过程
     *
     * @return new an XMLList of adding all the nodes new 一个添加所有子节点的 XMLList
     * *****************************************************************
     */
    public XMLList getAllChildren() {
        XMLList result = new XMLList();
        XMLList children = this.children();
        result.addAll(children);
        for (int i = 0; i < children.size(); i++) {
            result.addAll(children.get(i).getAllChildren());
        }
        return result;

    }

    /**
     * Set the value of the this.prototype
     * 设置this.prototype的值
     *
     * @param key   To add the key 要添加的key
     * @param value To add the value 要添加的value
     * @return The <code>XML</code> 这个XML
     */
    public XML setValue(String key, String value) {
        this.prototype.put(key, value);
        return this;
    }

    /**
     * 根据属性key获取属性值
     *
     * @param key this.prototype's key this.prototype的key
     * @return The key value corresponding to the value key值对应的value
     */
    public String getValue(String key) {
        String result = this.prototype.get(key);
        if (result == null) {
            result = "";
        }
        return result;
    }

    /**
     * Set the XML name attribute
     * 设置XML的name属性
     *
     * @param name To set the name of the XML 要设置XML的name
     * @return The <code>XML</code> 这个XML
     */
    public XML setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * According to the name list of child nodes
     * 根据name,获取相同name子节点的列表
     *
     * @param name XML's name XML节点的name
     * @return The same name a list of child nodes 相同name子节点的列表
     */
    public XMLList getXMLListByName(String name) {
        XMLList result = new XMLList();
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if (childList.get(i).name.equals(name)) {
                    result.add(childList.get(i));
                }
            }
        }
        return result;
    }

    /**
     * *************************************************************************
     * Get the child node list according to the prototype of the key
     * 根据prototype的key获取子节点列表
     *
     * @param key this.prototype's key this.prototype的key
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByKey(String key) {
        XMLList result = new XMLList();
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if (childList.get(i).prototype.containsKey(key)) {
                    result.add(childList.get(i));
                }
            }
        }
        return result;
    }

    /**
     * *************************************************************************
     * According to the name attribute of XML and XML key access list of child
     * nodes of the prototype
     * 根据XML的name属性和XML的prototype的key获取子节点列表
     *
     * @param name XML's name XML节点的name
     * @param key  this.prototype's key this.prototype的key
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByNameAndKey(String name, String key) {
        XMLList result = new XMLList();
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if (childList.get(i).name.equals(name)
                        && (childList.get(i).prototype.containsKey(key))) {
                    result.add(childList.get(i));
                }
            }
        }
        return result;
    }

    /**
     * *************************************************************************
     * According to the prototype of XML key and the value for the list of child
     * nodes
     * 根据XML的prototype的key和value获取子节点列表
     *
     * @param key   this.prototype's key this.prototype的key
     * @param value this.prototype's value this.prototype的value
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByKeyValue(String key, String value) {
        XMLList result = new XMLList();
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if ((childList.get(i).prototype.containsKey(key))
                        && childList.get(i).prototype.get(key).equals(value)) {
                    result.add(childList.get(i));
                }
            }
        }
        return result;
    }

    /**
     * *************************************************************************
     * According to the name attribute XML, XML, the prototype of the key and
     * the value of access list of child nodes
     * 根据XML的name属性、 XML的prototype的key和value获取子节点列表
     *
     * @param name  XML's name XML节点的name
     * @param key   this.prototype's key this.prototype的key
     * @param value this.prototype's value this.prototype的value
     * @return Meet the conditions of the child nodes of the list 满足条件的子节点列表
     * *****************************************************************
     */
    public XMLList getXMLListByNameAndKeyValue(String name, String key, String value) {
        XMLList result = new XMLList();
        if (ArrayList.class.isInstance(this.child)) {
            XMLList childList = (XMLList) this.child;
            for (int i = 0; i < childList.size(); i++) {
                if (childList.get(i).name.equals(name)
                        && (childList.get(i).prototype.containsKey(key))
                        && childList.get(i).prototype.get(key).equals(value)) {
                    result.add(childList.get(i));
                }
            }
        }
        return result;
    }

}