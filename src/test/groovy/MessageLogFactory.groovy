import com.sap.gateway.ip.core.customdev.util.Message
import MessageLog

class MessageLogFactory {
    private Message msg

    MessageLogFactory(Message message) {
        msg = message
    }

    MessageLog getMessageLog(Object context){
        MessageLog ml = new MessageLog(msg)
        return ml
    }
}