import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder

Message processData(Message message) {
    //Body
    def body = message.getBody(String)as String
    def parseXML = new XmlParser().parseText(body)
    def varStringWriter = new StringWriter()
    def varXMLBuilder   = new MarkupBuilder(varStringWriter)

    String name
    String email
    int numberOfNodes = parseXML.Child.size()

    for(int i=0;i < numberOfNodes ;i++)
    {
        name = "${parseXML.Child[i].node1.text().toString()}"
        email = "${parseXML.Child[i].node5.text().toString()}"

        varXMLBuilder.Employee{
            Name(name)
            Email(email)
        }
    }

    def xml = varStringWriter.toString()
    xml="<Employees>\n"+xml+"\n</Employees>"
    message.setBody(xml)

    return message
}