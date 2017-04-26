/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2013, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.mobicents.protocols.ss7.cap.service.sms.primitive;

import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.TPDataCodingScheme;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringLength1Base;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class TPDataCodingSchemeImpl extends OctetStringLength1Base implements TPDataCodingScheme {

    private static final String DATA = "data";

    public TPDataCodingSchemeImpl() {
        super("TPDataCodingScheme");
    }

    public TPDataCodingSchemeImpl(int data) {
        super("TPDataCodingScheme", data);
    }

    @Override
    public int getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<TPDataCodingSchemeImpl> TP_DATA_CODINGSCHEME_XML = new XMLFormat<TPDataCodingSchemeImpl>(
            TPDataCodingSchemeImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, TPDataCodingSchemeImpl tPDataCodingSchemeImpl)
                throws XMLStreamException {
            tPDataCodingSchemeImpl.data = OctetStringLength1Base
                    .octetStringLength1ToInt(xml.getAttribute(DATA, (String) null));
        }

        @Override
        public void write(TPDataCodingSchemeImpl tPDataCodingSchemeImpl, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringLength1Base.intToOctetStringLength1(tPDataCodingSchemeImpl.getData()));
        }

    };

}
