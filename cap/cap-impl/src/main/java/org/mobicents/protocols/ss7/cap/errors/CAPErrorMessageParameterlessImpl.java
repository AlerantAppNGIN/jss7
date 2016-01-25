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

package org.mobicents.protocols.ss7.cap.errors;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.errors.CAPErrorCode;
import org.mobicents.protocols.ss7.cap.api.errors.CAPErrorMessageParameterless;

/**
 * The CAP ReturnError message without any parameters
 *
 * @author sergey vetyutnev
 *
 */
public class CAPErrorMessageParameterlessImpl extends CAPErrorMessageImpl implements CAPErrorMessageParameterless {

    public CAPErrorMessageParameterlessImpl(Long errorCode) {
        super(errorCode);
    }

    @Override
    public boolean isEmParameterless() {
        return true;
    }

    @Override
    public CAPErrorMessageParameterless getEmParameterless() {
        return this;
    }

    @Override
    public int getTag() throws CAPException {
        throw new CAPException("CAPErrorMessageParameterless does not support encoding");
    }

    @Override
    public int getTagClass() {
        return 0;
    }

    @Override
    public boolean getIsPrimitive() {
        return false;
    }

    @Override
    public void decodeAll(AsnInputStream ansIS) throws CAPParsingComponentException {
    }

    @Override
    public void decodeData(AsnInputStream ansIS, int length) throws CAPParsingComponentException {
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs) throws CAPException {
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag) throws CAPException {
    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {
    }

    @Override
    public String toString() {
        return "CAPErrorMessageParameterless [errorCode=" + capErrorCodeName() + "]";
    }

    private String capErrorCodeName() {
        if (errorCode == null)
            return "N/A";
        switch (errorCode.intValue()) {
            case CAPErrorCode.canceled:
                return "canceled";
            case CAPErrorCode.cancelFailed:
                return "cancelFailed";
            case CAPErrorCode.eTCFailed:
                return "eTCFailed";
            case CAPErrorCode.improperCallerResponse:
                return "improperCallerResponse";
            case CAPErrorCode.missingCustomerRecord:
                return "missingCustomerRecord";
            case CAPErrorCode.missingParameter:
                return "missingParameter";
            case CAPErrorCode.parameterOutOfRange:
                return "parameterOutOfRange";
            case CAPErrorCode.requestedInfoError:
                return "requestedInfoError";
            case CAPErrorCode.systemFailure:
                return "systemFailure";
            case CAPErrorCode.taskRefused:
                return "taskRefused";
            case CAPErrorCode.unavailableResource:
                return "unavailableResource";
            case CAPErrorCode.unexpectedComponentSequence:
                return "unexpectedComponentSequence";
            case CAPErrorCode.unexpectedDataValue:
                return "unexpectedDataValue";
            case CAPErrorCode.unexpectedParameter:
                return "unexpectedParameter";
            case CAPErrorCode.unknownCSID:
                return "unknownCSID";
            case CAPErrorCode.unknownLegID:
                return "unknownLegID";
            case CAPErrorCode.unknownPDPID:
                return "unknownPDPID";
            default:
                return errorCode.toString();
        }
    }
}
