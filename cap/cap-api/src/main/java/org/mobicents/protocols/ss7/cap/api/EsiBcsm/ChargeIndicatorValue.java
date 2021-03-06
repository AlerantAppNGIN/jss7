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

package org.mobicents.protocols.ss7.cap.api.EsiBcsm;

/**
 *
 -- As specified in ITU-T Recommendation Q.763 as follows: -- no indication 'xxxx xx00'B -- no charge 'xxxx xx01'B -- charge
 * 'xxxx xx10'B -- spare 'xxxx xx11'B -- Sending entity shall fill the upper six bits with '0's. -- Receiving entity shall
 * ignore the upper six bits.
 *
 *
 * @author sergey vetyutnev
 *
 */
public enum ChargeIndicatorValue {
    noIndication(0), noCharge(1), charge(2), spare(3);

    private int code;

    private ChargeIndicatorValue(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static ChargeIndicatorValue getInstance(int code) {
        switch (code) {
            case 0:
                return ChargeIndicatorValue.noIndication;
            case 1:
                return ChargeIndicatorValue.noCharge;
            case 2:
                return ChargeIndicatorValue.charge;
            case 3:
                return ChargeIndicatorValue.spare;
            default:
                return null;
        }
    }
}
