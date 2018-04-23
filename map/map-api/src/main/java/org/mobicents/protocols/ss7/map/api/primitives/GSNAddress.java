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

package org.mobicents.protocols.ss7.map.api.primitives;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * GSN-Address ::= OCTET STRING (SIZE (5..17)) -- Octets are coded according to TS 3GPP TS 23.003 [17]
 *
 * From TS 23.003:
 *
 * The GSN Address is composed of the following elements: 1) The Address Type, which is a fixed length code (of 2 bits)
 * identifying the type of address that is used in the Address field. 2) The Address Length, which is a fixed length
 * code (of 6 bits) identifying the length of the Address field. 3) The Address, which is a variable length field which
 * contains either an IPv4 address or an IPv6 address. Address Type 0 and Address Length 4 are used when Address is an
 * IPv4 address. Address Type 1 and Address Length 16 are used when Address is an IPv6 address. The IP v4 address
 * structure is defined in RFC 791 [14]. The IP v6 address structure is defined in RFC 2373 [15].
 *
 * @author sergey vetyutnev
 * @author alerant appngin
 *
 */
public interface GSNAddress extends Serializable {

    public static enum Type {
        IPV4, IPV6
    }

    /** Returns the unparsed octet string data. */
    byte[] getData();

    Type getAddressType();

    /**
     * Returns a {@link java.net.Inet4Address} or a {@link java.net.Inet6Address}, according to
     * {@link #getAddressType()}.
     */
    InetAddress getIpAddress();

}
