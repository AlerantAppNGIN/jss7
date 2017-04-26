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
package org.mobicents.protocols.ss7.cap.EsiSms;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.EsiSms.TSmsDeliverySpecificInfo;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.inap.api.INAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class TSmsDeliverySpecificInfoImpl extends SequenceBase implements TSmsDeliverySpecificInfo {

    public TSmsDeliverySpecificInfoImpl() {
        super("TSmsDeliverySpecificInfo");
    }

    @Override
    protected void _decode(AsnInputStream asnIS, int length) throws CAPParsingComponentException, IOException,
            AsnException, MAPParsingComponentException, INAPParsingComponentException {

    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {

    }

    protected static final XMLFormat<TSmsDeliverySpecificInfoImpl> T_SMS_DELIVERY_SPECIFIC_INFO_XML = new XMLFormat<TSmsDeliverySpecificInfoImpl>(
            TSmsDeliverySpecificInfoImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                TSmsDeliverySpecificInfoImpl tSmsDeliverySpecificInfoImpl) throws XMLStreamException {
        }

        @Override
        public void write(TSmsDeliverySpecificInfoImpl tSmsDeliverySpecificInfoImpl,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
        }
    };

}
