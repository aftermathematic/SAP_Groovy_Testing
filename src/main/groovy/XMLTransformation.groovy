import com.sap.gateway.ip.core.customdev.util.Message

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

Message processData(Message message) throws ParseException {

    List<String> expiringEntries = []

    def body = message.getBody(Reader)
    def Root = new XmlSlurper().parse(body)

    int expirationDays = 100
    if(message.getProperty("ExpirationDays") != null){
        expirationDays = message.getProperty("ExpirationDays") as int
    }

    Date today = new Date()
    Calendar cal = Calendar.getInstance()
    cal.setTime(today)
    cal.add(Calendar.DATE, expirationDays)
    Date expiryDate = cal.getTime()

    Root.KeystoreEntry.each{

        String ValidNotAfterEDM = it."ValidNotAfter".text()
        String stringDate = ValidNotAfterEDM.substring(0, 10)
        Date itDate=new SimpleDateFormat("yyyy-MM-dd").parse(stringDate)

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy")
        String expDate = formatter.format(itDate)

        if(itDate.before(expiryDate)){
            String entry = " " + it.Alias.toString() + " (Expires: " + expDate + ")"
            //add keystore entry to list
            expiringEntries.add(entry)
        }

    }

    String ExpEntries = String.join(",", expiringEntries)
    message.setProperty("ExpiringEntries", ExpEntries)

    return message
}
