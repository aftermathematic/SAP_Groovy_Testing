import com.sap.gateway.ip.core.customdev.util.Message
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultExchange

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

BasicConfigurator.configure();


// Load Groovy Script
GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File('../../../src/main/groovy/XMLTransformation.groovy'))

// Initialize CamelContext and exchange for the message
CamelContext context = new DefaultCamelContext()
Exchange exchange = new DefaultExchange(context)
Message msg = new Message(exchange)

// Initialize the message body with the input file
def body = new File('../../../data/in/input1.xml')

// Set exchange body in case Type Conversion is required
exchange.getIn().setBody(body)
msg.setBody(exchange.getIn().getBody())
//msg.setHeader("TestHeader", "This is a dummy test header")
//msg.setProperty("TestHeader", "This is a dummy test property")

// Execute script
script.processData(msg)
exchange.getIn().setBody(msg.getBody())

try {
    FileWriter myWriter = new FileWriter('../../../data/out/output1.xml');
    myWriter.write(msg.getBody(String));
    myWriter.close();
    //System.out.println("Successfully wrote to the file.");
} catch (IOException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
}


// Display results of script in console
println("Body:\r\n\r\n${msg.getBody(String)}")
println()
println('Headers:')
msg.getHeaders().each { k, v -> println("$k = $v") }
println('Properties:')
msg.getProperties().each { k, v -> println("$k = $v") }
