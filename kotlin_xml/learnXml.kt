import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import java.io.StringWriter


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Book(@XmlElement val name: String, @XmlElement val author: String, @XmlElement val description: String) {
    constructor() : this("No name", "No author", "No description")
}

val book = Book("Bookie", "Bob", "Yup")

val jaxbContext = JAXBContext.newInstance(Book::class.java)
val marshaller = jaxbContext.createMarshaller()
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

val stringWriter = StringWriter()
stringWriter.use {
    marshaller.marshal(book, stringWriter)
}

println(stringWriter)
val s = stringWriter
