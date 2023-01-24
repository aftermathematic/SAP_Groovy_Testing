import com.sap.gateway.ip.core.customdev.util.Message

class MessageLogFactory {

    private Message msg

    MessageLog getMessageLog(Object context){
        MessageLog ml = new MessageLog(msg)
        return ml
    }

    MessageLogFactory(Message message) {
        msg = message
    }
}