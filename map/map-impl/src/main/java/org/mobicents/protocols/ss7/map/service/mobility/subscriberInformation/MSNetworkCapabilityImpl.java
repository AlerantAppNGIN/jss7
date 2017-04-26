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

package org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation;

import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.MSNetworkCapability;
import org.mobicents.protocols.ss7.map.primitives.OctetStringBase;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 * @author amit bhayani
 * @author sergey vetyutnev
 *
 */
public class MSNetworkCapabilityImpl extends OctetStringBase implements MSNetworkCapability {

    private static final String DATA = "data";

    public MSNetworkCapabilityImpl() {
        super(1, 8, "MSNetworkCapability");
    }

    public MSNetworkCapabilityImpl(byte[] data) {
        super(1, 8, "MSNetworkCapability", data);
    }

    public byte[] getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<MSNetworkCapabilityImpl> MS_NETWORK_CAPABILITY_XML = new XMLFormat<MSNetworkCapabilityImpl>(
            MSNetworkCapabilityImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, MSNetworkCapabilityImpl mSNetworkCapabilityImpl)
                throws XMLStreamException {
            mSNetworkCapabilityImpl.data = OctetStringBase.hexToBytes(xml.getAttribute(DATA, (String) null));
        }

        @Override
        public void write(MSNetworkCapabilityImpl mSNetworkCapabilityImpl, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringBase.bytesToHex(mSNetworkCapabilityImpl.data));
        }

    };
}
