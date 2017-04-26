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

package org.mobicents.protocols.ss7.cap.api.service.sms.primitive;

import java.io.Serializable;

/**
 *
 * RPCause ::= OCTET STRING (SIZE (1)) -- RP cause according to 3GPP TS 24.011
 * [10] or 3GPP TS 29.002 [11]. -- GsmSCF shall send this cause in the
 * ReleaseSMS operation. -- For a MO-SMS service, the MSC or SGSN shall send the
 * RP Cause to the originating MS. -- It shall be used to overwrite the RP-Cause
 * element in the RP-ERROR RPDU. -- For a MT-SMS service, the MSC or SGSN shall
 * send the RP Cause to the sending SMS-GMSC. -- It shall be used to overwrite
 * the RP-Cause element in the RP-ERROR RPDU.
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface RPCause extends Serializable {

    int getData();

    // RP Cause values based on 24.011

    int _RPCV_UNASSIGNEND_UNALLOCATED_NUMBER = 1;

    int _RPCV_OPERATOR_DETERMINED_BARRING = 8;

    int _RPCV_CALL_BARRED = 10;

    int _RPCV_RESERVED = 11;

    int _RPCV_SHORT_MESSAGE_TRANSFER_REJECTED = 21;

    int _RPCV_MEMORY_CAPACITY_EXCEEDED = 22;

    int _RPCV_DESTINATION_OUT_OF_ORDER = 27;

    int _RPCV_UNIDENTIFIED_SUBSCRIBER = 28;

    int _RPCV_FACILITY_REJECTED = 29;

    int _RPCV_UNKNOWN_SUBSCRIBER = 30;

    int _RPCV_NETWORK_OUT_OF_ORDER = 38;

    int _RPCV_TEMPORARY_FAILURE = 41;

    int _RPCV_CONGESTION = 42;

    int _RPCV_RESOURCES_UNAVAILABLE_UNSPECIFIED = 47;

    int _RPCV_REQUESTED_FACILITY_NOT_SUBSCRIBED = 50;

    int _RPCV_REQUESTED_FACILITY_NOT_IMPLEMENTED = 69;

    int _RPCV_INVALID_SHORT_MESSAGE_TRANSFER_REFERENCE_VALUE = 81;

    int _RPCV_SEMANTICALLY_INCORRECT_MESSAGE = 95;

    int _RPCV_INVALID_MANDATORY_INFORMATION = 96;

    int _RPCV_MESSAGE_TYPE_NON_EXISTENT_OR_NOT_IMPLEMENTED = 97;

    int _RPCV_MESSAGE_NOT_COMPATIBLE_WITH_SHORT_MESSAGE_PROTOCOL_STATE = 98;

    int _RPCV_INFORMATION_ELEMENT_NON_EXISTENT_OR_NOT_IMPLEMENTED = 99;

    int _RPCV_PROTOCOL_ERROR_UNSPECIFIED = 111;

    int _RPCV_INTERWORKING_UNSPECIFIED = 127;

}