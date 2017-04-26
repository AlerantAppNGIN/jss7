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
package org.mobicents.protocols.ss7.cap.service.sms.primitive;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.primitives.MonitorMode;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.EventTypeSMS;
import org.testng.annotations.Test;

import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class SMSEventTest {

    public byte[] getData() {
        return new byte[] { 48, 6, -128, 1, 3, -127, 1, 1 };
    };

    @Test(groups = { "functional.decode", "primitives" })
    public void testDecode() throws Exception {
        byte[] data = this.getData();
        AsnInputStream asn = new AsnInputStream(data);
        int tag = asn.readTag();
        SMSEventImpl prim = new SMSEventImpl();
        prim.decodeAll(asn);

        assertEquals(tag, Tag.SEQUENCE);
        assertEquals(asn.getTagClass(), Tag.CLASS_UNIVERSAL);

        assertEquals(prim.getEventTypeSMS(), EventTypeSMS.oSmsSubmission);
        assertEquals(prim.getMonitorMode(), MonitorMode.notifyAndContinue);

    }

    @Test(groups = { "functional.encode", "primitives" })
    public void testEncode() throws Exception {
        SMSEventImpl prim = new SMSEventImpl(EventTypeSMS.oSmsSubmission, MonitorMode.notifyAndContinue);
        AsnOutputStream asn = new AsnOutputStream();
        prim.encodeAll(asn);

        assertTrue(Arrays.equals(asn.toByteArray(), this.getData()));
    }

    @Test(groups = { "functional.xml.serialize", "primitives" })
    public void testXMLSerialize() throws Exception {
        SMSEventImpl original = new SMSEventImpl(EventTypeSMS.oSmsSubmission, MonitorMode.interrupted);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "smsEvent", SMSEventImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        SMSEventImpl copy = reader.read("smsEvent", SMSEventImpl.class);

        assertEquals(copy.getEventTypeSMS(), original.getEventTypeSMS());
        assertEquals(copy.getMonitorMode(), original.getMonitorMode());

        original = new SMSEventImpl(EventTypeSMS.tSmsDelivery, MonitorMode.interrupted);

        // Writes the area to a file.
        baos = new ByteArrayOutputStream();
        writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "smsEvent", SMSEventImpl.class);
        writer.close();

        rawData = baos.toByteArray();
        serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        bais = new ByteArrayInputStream(rawData);
        reader = XMLObjectReader.newInstance(bais);
        copy = reader.read("smsEvent", SMSEventImpl.class);

        assertEquals(copy.getEventTypeSMS(), original.getEventTypeSMS());
        assertEquals(copy.getMonitorMode(), original.getMonitorMode());
    }

}
