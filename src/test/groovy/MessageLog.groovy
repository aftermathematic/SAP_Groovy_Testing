import com.sap.gateway.ip.core.customdev.util.Message

class MessageLog {
    private Message msg

    void setStringProperty(String p0, String p1) {
        msg.setProperty(p0, p1)
    }

    void setIntegerProperty(String p0, Integer p1) {
        msg.setProperty(p0, p1)
    }

    void setLongProperty(String p0, Long p1) {
        msg.setProperty(p0, p1)
    }

    void setBooleanProperty(String p0, Boolean p1) {
        msg.setProperty(p0, p1)
    }

    void setFloatProperty(String p0, Float p1) {
        msg.setProperty(p0, p1)
    }

    void setDoubleProperty(String p0, Double p1) {
        msg.setProperty(p0, p1)
    }

    void setDateProperty(String p0, Date p1) {
        msg.setProperty(p0, p1)
    }

    void addAttachmentAsString(String p0, String p1, String p2) {
        msg.setProperty(p0, p1)
    }

    void addCustomHeaderProperty(String p0, String p1) {
        msg.setProperty(p0, p1)
    }

    MessageLog(Message message) {
        msg = message
    }
}