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

import javolution.text.CharArray;
import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.FreeFormatDataSMS;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author Lasith Waruna Perera
 * @author alerant appngin
 */
@SuppressWarnings("serial")
public class FreeFormatDataSMSImpl extends OctetStringBase implements FreeFormatDataSMS {

    public FreeFormatDataSMSImpl() {
        super(1, 160, "FreeFormatDataSMS");
    }

    public FreeFormatDataSMSImpl(byte[] data) {
        super(1, 160, "FreeFormatDataSMS", data);
    }

    @Override
    public byte[] getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<FreeFormatDataSMSImpl> FREE_FORMAT_DATA_SMS_XML = new XMLFormat<FreeFormatDataSMSImpl>(
            FreeFormatDataSMSImpl.class) {

        // serialize as simple text content

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, FreeFormatDataSMSImpl obj) throws XMLStreamException {

            CharArray arr = xml.getText();
            obj.data = hexToBytes(arr.array(), arr.offset(), arr.length());
        }

        @Override
        public void write(FreeFormatDataSMSImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {

            xml.addText(bytesToHex(obj.data));
        }
    };
}
