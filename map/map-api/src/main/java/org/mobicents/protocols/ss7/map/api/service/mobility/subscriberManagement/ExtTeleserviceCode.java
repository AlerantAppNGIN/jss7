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

package org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement;

import java.io.Serializable;

/**
 *
 Ext-TeleserviceCode ::= OCTET STRING (SIZE (1..5)) -- This type is used to represent the code identifying a single --
 * teleservice, a group of teleservices, or all teleservices. The -- services are defined in TS GSM 22.003 [4]. -- The internal
 * structure is defined as follows:
 *
 * -- OCTET 1: -- bits 87654321: group (bits 8765) and specific service -- (bits 4321)
 *
 * -- OCTETS 2-5: reserved for future use. If received the -- Ext-TeleserviceCode shall be -- treated according to the exception
 * handling defined for the -- operation that uses this type.
 *
 * -- Ext-TeleserviceCode includes all values defined for TeleserviceCode.
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface ExtTeleserviceCode extends Serializable {

    byte[] getData();

    TeleserviceCodeValue getTeleserviceCodeValue();

}
