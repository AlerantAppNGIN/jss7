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

package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import javolution.text.CharArray;
import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.FreeFormatData;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author Lasith Waruna Perera
 * @author alerant appngin
 */
@SuppressWarnings("serial")
public class FreeFormatDataImpl extends OctetStringBase implements FreeFormatData {

    public FreeFormatDataImpl() {
        super(1, 160, "FreeFormatData");
    }

    public FreeFormatDataImpl(byte[] data) {
        super(1, 160, "FreeFormatData", data);
    }

    @Override
    public byte[] getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<FreeFormatDataImpl> FREE_FORMAT_DATA_XML = new XMLFormat<FreeFormatDataImpl>(
            FreeFormatDataImpl.class) {

        // serialize as simple text content

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, FreeFormatDataImpl obj) throws XMLStreamException {

            CharArray arr = xml.getText();
            obj.data = hexToBytes(arr.array(), arr.offset(), arr.length());
        }

        @Override
        public void write(FreeFormatDataImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {

            xml.addText(bytesToHex(obj.data));
        }
    };

}
