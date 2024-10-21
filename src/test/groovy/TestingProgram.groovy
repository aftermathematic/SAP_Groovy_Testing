import com.sap.gateway.ip.core.customdev.util.Message
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.DefaultExchange
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Logger
import org.apache.log4j.Level
import MessageLogFactory

// Configure the logger
BasicConfigurator.configure()
Logger.getRootLogger().setLevel(Level.ERROR)

// Initialize GroovyShell and parse the script
GroovyShell shell = new GroovyShell()
Script script = shell.parse(new File('./src/main/groovy/processData.groovy'))

// Initialize Camel context and exchange
CamelContext context = new DefaultCamelContext()
Exchange exchange = new DefaultExchange(context)
Message msg = new Message(exchange)

// Load the input XML file
def body = new File('./data/in/input.xml')

// Load properties and headers from files
loadPropsHeaders('./data/in/_properties.txt', msg)
loadPropsHeaders('./data/in/_headers.txt', msg)

// Set the body of the exchange
exchange.getIn().setBody(body)
msg.setBody(exchange.getIn().getBody())

// Inject mock messageLogFactory
MessageLogFactory messageLogFactory = new MessageLogFactory(msg)
script.setProperty("messageLogFactory", messageLogFactory)

// Process the message using the script
script.processData(msg)
exchange.getIn().setBody(msg.getBody())

// Write the processed message body to the output file
try {
    new FileWriter('./data/out/output.xml').withWriter { writer ->
        writer.write(msg.getBody(String))
    }
} catch (IOException e) {
    println("An error occurred writing the output.")
    e.printStackTrace()
}

// Print the processed message body, headers, and properties
println("------------------------------------------------------")
//println("\033[1mBody: \033[0m")
printColoredXml(msg.getBody(String))
println("------------------------------------------------------")
println("\033[1mHeaders: \033[0m")
msg.getHeaders().each { k, v -> println("$k = $v") }
println()
println("\033[1mProperties: \033[0m")
msg.getProperties().each { k, v -> println("$k = $v") }
println("------------------------------------------------------")

/**
 * Loads properties or headers from a file and sets them in the message.
 * @param fileName the name of the file containing properties or headers
 * @param msg the message to set the properties or headers in
 */
static void loadPropsHeaders(String fileName, Message msg) {
    try {
        new FileReader(fileName).withReader { reader ->
            reader.eachLine { line ->
                def (prop, val) = line.split("=")
                if (fileName.contains("headers")) {
                    msg.setHeader(prop, val)
                } else if (fileName.contains("properties")) {
                    msg.setProperty(prop, val)
                }
            }
        }
    } catch (IOException e) {
        println("An error occurred fetching properties & headers.")
        e.printStackTrace()
    }
}

/**
 * Prints XML with colored tags and attributes for better readability.
 * @param xml the XML string to be printed
 */
static void printColoredXml(String xml) {
    // Process each line of the XML
    xml.eachLine { line ->
        // Colorize opening tags and their attributes
        line = line.replaceAll(/<([^\s>\/]+)(.*?)>/) { match, tagName, attributes ->
            // Colorize attribute names and equal signs
            attributes = attributes.replaceAll(/(\w+)(=)/, "\033[1;37m\$1\033[0m\033[1;37m=\033[0m")
            // Colorize attribute values
            attributes = attributes.replaceAll(/(")/, "\033[1;37m\"\033[0m")
            // Return the colorized opening tag
            "\033[1;37m<${tagName}\033[0m$attributes\033[1;37m>\033[0m"
        }
        // Colorize closing tags
        line = line.replaceAll(/<\/([^\s>\/]+)>/, "\033[1;37m</\$1>\033[0m")
        // Print the colorized line
        println(line)
    }
}