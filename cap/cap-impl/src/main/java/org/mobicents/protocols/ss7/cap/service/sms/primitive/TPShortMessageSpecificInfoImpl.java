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

import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.TPShortMessageSpecificInfo;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringLength1Base;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class TPShortMessageSpecificInfoImpl extends OctetStringLength1Base implements TPShortMessageSpecificInfo {

    private static final String DATA = "data";

    public TPShortMessageSpecificInfoImpl() {
        super("TPShortMessageSpecificInfo");
    }

    public TPShortMessageSpecificInfoImpl(int data) {
        super("TPShortMessageSpecificInfo", data);
    }

    @Override
    public int getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<TPShortMessageSpecificInfoImpl> TP_SHORT_MESSAGE_SPECIFIC_INFO_XML = new XMLFormat<TPShortMessageSpecificInfoImpl>(
            TPShortMessageSpecificInfoImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                TPShortMessageSpecificInfoImpl tPShortMessageSpecificInfo) throws XMLStreamException {
            tPShortMessageSpecificInfo.data = OctetStringLength1Base
                    .octetStringLength1ToInt(xml.getAttribute(DATA, (String) null));
        }

        @Override
        public void write(TPShortMessageSpecificInfoImpl tPShortMessageSpecificInfo,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            xml.setAttribute(DATA,
                    OctetStringLength1Base.intToOctetStringLength1(tPShortMessageSpecificInfo.getData()));
        }

    };
}
