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

package org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive;

import org.mobicents.protocols.ss7.cap.api.xml.EnumXMLFormat;

import javolution.xml.XMLFormat;

/**
 *
 ConnectedNumberTreatmentInd ::= ENUMERATED { noINImpact (0), presentationRestricted (1), presentCalledINNumber (2),
 * presentCallINNumberRestricted (3) } -- This parameter is used to suppress or to display the connected number.
 *
 * @author sergey vetyutnev
 * @author tamas gyorgyey
 */
public enum ConnectedNumberTreatmentInd {
    noINImpact(0), presentationRestricted(1), presentCalledINNumber(2), presentCallINNumberRestricted(3);

    private final int code;

    private ConnectedNumberTreatmentInd(int code) {
        this.code = code;
    }

    public static ConnectedNumberTreatmentInd getInstance(int code) {
        switch (code) {
            case 0:
                return ConnectedNumberTreatmentInd.noINImpact;
            case 1:
                return ConnectedNumberTreatmentInd.presentationRestricted;
            case 2:
                return ConnectedNumberTreatmentInd.presentCalledINNumber;
            case 3:
                return ConnectedNumberTreatmentInd.presentCallINNumberRestricted;
            default:
                return null;
        }
    }

    public int getCode() {
        return this.code;
    }

    protected static final XMLFormat<ConnectedNumberTreatmentInd> XML = new EnumXMLFormat<ConnectedNumberTreatmentInd>(
            ConnectedNumberTreatmentInd.class);
}
