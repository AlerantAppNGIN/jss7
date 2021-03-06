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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariablePart;
import org.testng.annotations.Test;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class InbandInfoTest {

    public byte[] getData1() {
        return new byte[] { 48, 5, (byte) 160, 3, (byte) 128, 1, 11 };
    }

    public byte[] getData2() {
        return new byte[] { 48, 19, (byte) 160, 8, (byte) 161, 6, (byte) 128,
                4, 73, 110, 102, 111, (byte) 129, 1, 3, (byte) 130, 1, 2,
                (byte) 131, 1, 1 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {

        byte[] data = this.getData1();
        AsnInputStream ais = new AsnInputStream(data);
        InbandInfoImpl elem = new InbandInfoImpl();
        int tag = ais.readTag();
        assertEquals(tag, Tag.SEQUENCE);
        elem.decodeAll(ais);
        assertEquals((int) elem.getMessageID().getElementaryMessageID(), 11);
        assertNull(elem.getNumberOfRepetitions());
        assertNull(elem.getDuration());
        assertNull(elem.getInterval());

        data = this.getData2();
        ais = new AsnInputStream(data);
        elem = new InbandInfoImpl();
        tag = ais.readTag();
        assertEquals(tag, Tag.SEQUENCE);
        elem.decodeAll(ais);
        assertTrue(elem.getMessageID().getText().getMessageContent()
                .equals("Info"));
        assertEquals((int) elem.getNumberOfRepetitions(), 3);
        assertEquals((int) elem.getDuration(), 2);
        assertEquals((int) elem.getInterval(), 1);
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {

        MessageIDImpl messageID = new MessageIDImpl(11);
        InbandInfoImpl elem = new InbandInfoImpl(messageID, null, null, null);
        AsnOutputStream aos = new AsnOutputStream();
        elem.encodeAll(aos);
        assertTrue(Arrays.equals(aos.toByteArray(), this.getData1()));

        MessageIDTextImpl text = new MessageIDTextImpl("Info", null);
        messageID = new MessageIDImpl(text);
        elem = new InbandInfoImpl(messageID, 3, 2, 1);
        aos = new AsnOutputStream();
        elem.encodeAll(aos);
        assertTrue(Arrays.equals(aos.toByteArray(), this.getData2()));

        // MessageID messageID, Integer numberOfRepetitions, Integer duration,
        // Integer interval
    }

    @Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        ArrayList<VariablePart> aL = new ArrayList<VariablePart>();
        aL.add(new VariablePartImpl(new VariablePartDateImpl(2015, 6, 27)));
        aL.add(new VariablePartImpl(new VariablePartTimeImpl(15, 10)));
        aL.add(new VariablePartImpl(new Integer(145)));
        VariableMessageImpl vm = new VariableMessageImpl(145, aL);
        MessageIDImpl mi = new MessageIDImpl(vm);

        InbandInfoImpl original = new InbandInfoImpl(mi, new Integer(5),
                new Integer(8), new Integer(2));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "inbandInfo", InbandInfoImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        InbandInfoImpl copy = reader.read("inbandInfo", InbandInfoImpl.class);

        assertEquals(original, copy);
    }
}
