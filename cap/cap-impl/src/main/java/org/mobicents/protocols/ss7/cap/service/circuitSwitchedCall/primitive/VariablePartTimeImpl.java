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

import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariablePartTime;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class VariablePartTimeImpl extends OctetStringBase implements
        VariablePartTime, CAPAsnPrimitive {

    private static final String DATA = "data";

    // public static final String _PrimitiveName = "VariablePartTime";

    // private byte[] data;

    public VariablePartTimeImpl() {
        super(2, 2, "VariablePartTime");
    }

    public VariablePartTimeImpl(byte[] data) {
        // this.data = data;
        super(2, 2, "VariablePartTime", data);
    }

    public VariablePartTimeImpl(int hour, int minute) {
        super(2, 2, "VariablePartTime");
        this.data = new byte[2];

        this.data[0] = (byte) this.encodeByte(hour);
        this.data[1] = (byte) this.encodeByte(minute);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public int getHour() {

        if (this.data == null || this.data.length != 2)
            return 0;

        return this.decodeByte(data[0]);
    }

    @Override
    public int getMinute() {

        if (this.data == null || this.data.length != 2)
            return 0;

        return this.decodeByte(data[1]);
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

        if (this.data != null && this.data.length == 2) {
            sb.append("hour=");
            sb.append(this.getHour());
            sb.append(", minute=");
            sb.append(this.getMinute());
        }

        sb.append("]");

        return sb.toString();
    }

    protected static final XMLFormat<VariablePartTimeImpl> VARIABLE_PART_TIME_XML = new XMLFormat<VariablePartTimeImpl>(
            VariablePartTimeImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                VariablePartTimeImpl variablePartTime)
                throws XMLStreamException {
            variablePartTime.data = OctetStringBase.hexToBytes(xml
                    .getAttribute(DATA, (String) null));

        }

        @Override
        public void write(VariablePartTimeImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(DATA, OctetStringBase.bytesToHex(obj.data));

        }

    };

    /*
     * TODO: move this code into the appropriate test class
    public static void main(String[] args) throws UnsupportedEncodingException,
            XMLStreamException {
        XMLObjectWriter x = new XMLObjectWriter().setBinding(new XMLBinding())
                .setOutput(System.out).setIndentation(" ");
        x.write(new VariablePartTimeImpl(15, 47), "variablePartTimeImpl");
        x.flush();

        String xml = "<variablePartTimeImpl data=\"5174\"/>";

        XMLObjectReader r = new XMLObjectReader().setInput(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8
                        .name()))).setBinding(new XMLBinding());
        VariablePartTimeImpl readHere = null;// = new VariablePartPriceImpl();
        if (r.hasNext()) {
            readHere = r.read("variablePartTimeImpl",
                    VariablePartTimeImpl.class);
        }
        System.out.println("");
        System.out.println(readHere.toString());

    }
    */

}
