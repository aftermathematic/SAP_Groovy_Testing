import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.XmlUtil

Message processData(Message message) {
    def body = message.getBody(String) as String
    def root = new XmlParser().parseText(body)

    // iterate over every 'row' node
    root.'**'.row.each { row ->

        // sort all elements in the 'row' node alphabetically
        row.children().sort { it.name() }
    }


    message.setBody(serializeXml(root))
    return message
}

// Function to serialize the root and prepend the XML declaration
String serializeXml(def root) {
    return XmlUtil.serialize(root).replaceAll(/<\?xml\b[^>]*\?>/,
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
}