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

import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.MSRadioAccessCapability;
import org.mobicents.protocols.ss7.map.primitives.OctetStringBase;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 * @author amit bhayani
 * @author sergey vetyutnev
 *
 */
public class MSRadioAccessCapabilityImpl extends OctetStringBase implements MSRadioAccessCapability {

    private static final String DATA = "data";

    public MSRadioAccessCapabilityImpl() {
        super(1, 50, "MSRadioAccessCapability");
    }

    public MSRadioAccessCapabilityImpl(byte[] data) {
        super(1, 50, "MSRadioAccessCapability", data);
    }

    public byte[] getData() {
        return data;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<MSRadioAccessCapabilityImpl> MS_RADIO_ACCESS_CAPABILITY_XML = new XMLFormat<MSRadioAccessCapabilityImpl>(
            MSRadioAccessCapabilityImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                MSRadioAccessCapabilityImpl mSRadioAccessCapabilityImpl) throws XMLStreamException {
            mSRadioAccessCapabilityImpl.data = OctetStringBase.hexToBytes(xml.getAttribute(DATA, (String) null));
        }

        @Override
        public void write(MSRadioAccessCapabilityImpl mSRadioAccessCapabilityImpl,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringBase.bytesToHex(mSRadioAccessCapabilityImpl.data));
        }

    };
}
