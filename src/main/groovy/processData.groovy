import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder

Message processData(Message message) {

    /*
    // Some much used code snippets

    def body = message.getBody(String) as String
    def root = new XmlParser().parseText(body)

    //Iterate childnodes
    root.Person.each { node ->
        //Remove node
        if (node.Name.text() == 'xxxxxxxxxxxxx') {
            node.parent().remove(node)
        }
    }

    //Remove node, lambda version
    root.Person.findAll { it.Name.text() == 'John Doe' }.each { it.parent().remove(it) }

    //Add new node to root
    def nodeBuilder = new NodeBuilder()
    def node = nodeBuilder.Person {
        Name('Jan Vermeerbergen')
        Age('42')
        City('Wilrijk')
    }
    root.append(node)

    //Sort the xml by attribute
    root.Person.sort(true) { it.City.text() }

    def outxml = XmlUtil.serialize(root)
    message.setBody(outxml)
*/


    return message
}
