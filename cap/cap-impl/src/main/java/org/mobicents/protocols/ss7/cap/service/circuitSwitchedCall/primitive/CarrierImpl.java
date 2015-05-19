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

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.Carrier;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author sergey vetyutnev
 * @author alerant appngin
 *
 */
@SuppressWarnings("serial")
public class CarrierImpl extends OctetStringBase implements Carrier {
    private static final String DATA = "data";

    public CarrierImpl() {
        super(4, 4, "Carrier");
    }

    public CarrierImpl(byte[] data) {
        super(4, 4, "Carrier", data);
    }

    @Override
    public byte[] getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<CarrierImpl> CARRIER_XML = new XMLFormat<CarrierImpl>(CarrierImpl.class) {

        @Override
        public void write(CarrierImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringBase.bytesToHex(obj.data));
        }

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, CarrierImpl obj) throws XMLStreamException {
            obj.data = OctetStringBase.hexToBytes(xml.getAttribute(DATA, (String) null));
        }

    };
}
