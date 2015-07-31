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

package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariablePartDate;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class VariablePartDateImpl extends OctetStringBase implements
        VariablePartDate, CAPAsnPrimitive {

    // public static final String _PrimitiveName = "VariablePartDate";

    private static final String DATA = "data";

    // private byte[] data;

    public VariablePartDateImpl() {
        super(4, 4, "VariablePartDate");
    }

    public VariablePartDateImpl(byte[] data) {
        // this.data = data;
        super(4, 4, "VariablePartDate", data);
    }

    public VariablePartDateImpl(int year, int month, int day) {
        super(4, 4, "VariablePartDate");
        this.data = new byte[4];

        this.data[0] = (byte) this.encodeByte(year / 100);
        this.data[1] = (byte) this.encodeByte(year % 100);
        this.data[2] = (byte) this.encodeByte(month);
        this.data[3] = (byte) this.encodeByte(day);
    }

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public int getYear() {

        if (this.data == null || this.data.length != 4)
            return 0;

        return this.decodeByte(data[0]) * 100 + this.decodeByte(data[1]);
    }

    @Override
    public int getMonth() {

        if (this.data == null || this.data.length != 4)
            return 0;

        return this.decodeByte(data[2]);
    }

    @Override
    public int getDay() {

        if (this.data == null || this.data.length != 4)
            return 0;

        return this.decodeByte(data[3]);
    }

    private int decodeByte(int bt) {
        return (bt & 0x0F) * 10 + ((bt & 0xF0) >> 4);
    }

    private int encodeByte(int val) {
        return (val / 10) | (val % 10) << 4;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.data != null && this.data.length == 4) {
            sb.append("year=");
            sb.append(this.getYear());
            sb.append(", month=");
            sb.append(this.getMonth());
            sb.append(", day=");
            sb.append(this.getDay());
        }

        sb.append("]");

        return sb.toString();
    }

    protected static final XMLFormat<VariablePartDateImpl> VARIABLE_PART_DATE_XML = new XMLFormat<VariablePartDateImpl>(
            VariablePartDateImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                VariablePartDateImpl variablePartDate)
                throws XMLStreamException {
            variablePartDate.data = OctetStringBase.hexToBytes(xml
                    .getAttribute(DATA, (String) null));

        }

        @Override
        public void write(VariablePartDateImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringBase.bytesToHex(obj.data));

        }

    };

}
