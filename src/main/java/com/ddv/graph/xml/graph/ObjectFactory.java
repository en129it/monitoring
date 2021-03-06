//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.14 at 11:48:09 PM CEST 
//


package com.ddv.graph.xml.graph;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ddv.graph.xml.graph package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Graph_QNAME = new QName("http://ddv.com/graph/", "graph");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ddv.graph.xml.graph
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GraphType }
     * 
     */
    public GraphType createGraphType() {
        return new GraphType();
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link DependenciesType }
     * 
     */
    public DependenciesType createDependenciesType() {
        return new DependenciesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GraphType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ddv.com/graph/", name = "graph")
    public JAXBElement<GraphType> createGraph(GraphType value) {
        return new JAXBElement<GraphType>(_Graph_QNAME, GraphType.class, null, value);
    }

}
