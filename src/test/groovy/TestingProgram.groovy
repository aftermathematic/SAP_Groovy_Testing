import com.sap.gateway.ip.core.customdev.util.Message
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultExchange
import org.apache.log4j.BasicConfigurator

BasicConfigurator.configure()

// Load Groovy Script
GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File('./src/main/groovy/processData.groovy'))

// Initialize CamelContext and exchange for the message
CamelContext context = new DefaultCamelContext()
Exchange exchange = new DefaultExchange(context)
Message msg = new Message(exchange)

// Initialize the message body with the input file
def body = new File('./data/in/input.xml')

//Initialize properties and headers
getPropsHeaders('./data/in/_properties.txt', msg)
getPropsHeaders('./data/in/_headers.txt', msg)

// Set exchange body in case Type Conversion is required
exchange.getIn().setBody(body)
msg.setBody(exchange.getIn().getBody())

// Inject mock messageLogFactory
MessageLogFactory messageLogFactory = new MessageLogFactory(msg)
script.setProperty("messageLogFactory", messageLogFactory)

// Execute script
script.processData(msg)
exchange.getIn().setBody(msg.getBody())

try {
    FileWriter writer = new FileWriter('./data/out/output.xml')
    writer.write(msg.getBody(String))
    writer.close()
} catch (IOException e) {
    System.out.println("An error occurred writing the output.")
    e.printStackTrace()
}

// Display results of script in console
println()
println("\033[1mBody: \033[0m")
println(msg.getBody(String))
println()
println("\033[1mHeaders: \033[0m")
msg.getHeaders().each { k, v -> println("$k = $v") }
println()
println("\033[1mProperties: \033[0m")
msg.getProperties().each { k, v -> println("$k = $v") }

static void getPropsHeaders(String fileName, Message msg) {

   try {
        FileReader reader = new FileReader(fileName)
        BufferedReader bufferedReader = new BufferedReader(reader)

        String line
        while ((line = bufferedReader.readLine()) != null) {

            String[] parts = line.split("=")
            String prop = parts[0]
            String val = parts[1]

            if (fileName.contains("headers")) {
                msg.setHeader(prop, val)
            }
            if (fileName.contains("properties")) {
                msg.setProperty(prop, val)
            }

        }
        reader.close()

    } catch (IOException e) {
       System.out.println("An error occurred fetching properties & headers.")
       e.printStackTrace()
    }
}