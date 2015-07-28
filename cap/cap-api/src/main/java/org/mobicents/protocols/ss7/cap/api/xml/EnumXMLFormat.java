package org.mobicents.protocols.ss7.cap.api.xml;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 * XMLFormat class for serializing enum types.
 *
 * @author tamas gyorgyey
 */
public class EnumXMLFormat<T extends Enum<T>> extends XMLFormat<T> {

    public EnumXMLFormat(Class<T> forClass) {
        super(forClass);
    }

    @Override
    public void write(T obj, javolution.xml.XMLFormat.OutputElement xml)
            throws XMLStreamException {
        xml.setAttribute("value", obj.name());
    }

    @Override
    public void read(javolution.xml.XMLFormat.InputElement xml, T obj)
            throws XMLStreamException {
        // newInstance did the job, nothing to do here
    }

    @Override
    public T newInstance(Class<T> cls, javolution.xml.XMLFormat.InputElement xml)
            throws XMLStreamException {
        return Enum.valueOf(cls, xml.getAttribute("value").toString());
    }
}