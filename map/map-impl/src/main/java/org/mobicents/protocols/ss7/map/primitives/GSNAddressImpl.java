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

package org.mobicents.protocols.ss7.map.primitives;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.mobicents.protocols.ss7.map.api.primitives.GSNAddress;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class GSNAddressImpl extends OctetStringBase implements GSNAddress {

    public GSNAddressImpl() {
        super(5, 17, "GSNAddress");
    }

    public GSNAddressImpl(byte[] data) {
        super(5, 17, "GSNAddress", data);
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public Type getAddressType() {
        if (data == null || data.length < 1)
            return null;
        if (data[0] == (0 << 6 | 4) && data.length == 5)
            return Type.IPV4;
        else if (data[0] == ((1 << 6) | 16) && data.length == 17)
            return Type.IPV6;
        else
            throw new IllegalStateException(
                    "Invalid type + length combination: " + data[0] + ", octet count: " + data.length);
    }

    @Override
    public InetAddress getIpAddress() {
        try {
            return InetAddress.getByAddress(Arrays.copyOfRange(data, 1, data.length));
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    /** XML serialization */
    protected static final XMLFormat<GSNAddressImpl> GSN_ADDRESS_XML = new XMLFormat<GSNAddressImpl>(
            GSNAddressImpl.class) {

        private static final String DATA = "data";
        private static final String ADDRESS_TYPE = "addressType";
        private static final String ADDRESS = "address";

        @Override
        public void write(GSNAddressImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {

            xml.setAttribute(DATA, OctetStringBase.bytesToHex(obj.data));
            try { // also include human-readable, decoded version
                xml.setAttribute(ADDRESS_TYPE, obj.getAddressType().toString());
                xml.setAttribute(ADDRESS, obj.getIpAddress().getHostAddress());
            } catch (Exception e) {
                throw new XMLStreamException(e);
            }
        }

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, GSNAddressImpl obj) throws XMLStreamException {
            obj.data = OctetStringBase.hexToBytes(xml.getAttribute(DATA, (String) null));
        }

    };
}
