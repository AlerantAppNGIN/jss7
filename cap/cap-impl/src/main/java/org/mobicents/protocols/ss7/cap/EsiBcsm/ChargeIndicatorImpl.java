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
package org.mobicents.protocols.ss7.cap.EsiBcsm;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.cap.api.EsiBcsm.ChargeIndicator;
import org.mobicents.protocols.ss7.cap.api.EsiBcsm.ChargeIndicatorValue;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringLength1Base;

/**
 *
 * @author alerant appngin
 *
 */
@SuppressWarnings("serial")
public class ChargeIndicatorImpl extends OctetStringLength1Base implements ChargeIndicator {

    /** Package private constructor for deserialization only. */
    ChargeIndicatorImpl() {
        super("ChargeIndicator");
    }

    public ChargeIndicatorImpl(ChargeIndicatorValue value) {
        super("ChargeIndicator", value.getCode());
    }

    @Override
    public ChargeIndicatorValue getData() {
        return ChargeIndicatorValue.getInstance(data);
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<ChargeIndicatorImpl> CHARGE_INDICATOR_XML = new XMLFormat<ChargeIndicatorImpl>(
            ChargeIndicatorImpl.class) {

        // using meaningful name of the data enum type in the XML

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, ChargeIndicatorImpl chargeIndicator)
                throws XMLStreamException {

            chargeIndicator.data = ChargeIndicatorValue.valueOf(xml.getAttribute("data", (String) null)).getCode();
        }

        @Override
        public void write(ChargeIndicatorImpl chargeIndicator, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            xml.setAttribute("data", chargeIndicator.getData().name());
        }
    };
}
