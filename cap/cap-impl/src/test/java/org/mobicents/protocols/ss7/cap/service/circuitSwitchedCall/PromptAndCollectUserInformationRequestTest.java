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

package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
import org.mobicents.protocols.ss7.cap.api.primitives.ErrorTreatment;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariablePart;
import org.mobicents.protocols.ss7.cap.primitives.CAPExtensionsTest;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.CollectedDigitsImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.CollectedInfoImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.InbandInfoImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.InformationToSendImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.MessageIDImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.ToneImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.VariableMessageImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.VariablePartDateImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.VariablePartImpl;
import org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive.VariablePartTimeImpl;
import org.testng.annotations.Test;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class PromptAndCollectUserInformationRequestTest {

    public byte[] getData1() {
        return new byte[] { 48, 47, (byte) 160, 5, (byte) 160, 3, (byte) 129,
                1, 10, (byte) 129, 1, 0, (byte) 162, 8, (byte) 161, 6,
                (byte) 128, 1, 10, (byte) 129, 1, 100, (byte) 163, 18, 48, 5,
                2, 1, 2, (byte) 129, 0, 48, 9, 2, 1, 3, 10, 1, 1, (byte) 129,
                1, (byte) 255, (byte) 132, 1, 22, (byte) 159, 51, 1, 0 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall" })
    public void testDecode() throws Exception {

        byte[] data = this.getData1();
        AsnInputStream ais = new AsnInputStream(data);
        PromptAndCollectUserInformationRequestImpl elem = new PromptAndCollectUserInformationRequestImpl();
        int tag = ais.readTag();
        assertEquals(tag, Tag.SEQUENCE);
        elem.decodeAll(ais);
        assertEquals(elem.getCollectedInfo().getCollectedDigits()
                .getMaximumNbOfDigits(), 10);
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getMinimumNbOfDigits());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getEndOfReplyDigit());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getCancelDigit());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getMinimumNbOfDigits());
        assertNull(elem.getCollectedInfo().getCollectedDigits().getStartDigit());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getFirstDigitTimeOut());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getInterDigitTimeOut());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getErrorTreatment());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getInterruptableAnnInd());
        assertNull(elem.getCollectedInfo().getCollectedDigits()
                .getVoiceInformation());
        assertNull(elem.getCollectedInfo().getCollectedDigits().getVoiceBack());
        assertFalse(elem.getDisconnectFromIPForbidden());
        assertEquals(elem.getInformationToSend().getTone().getToneID(), 10);
        assertEquals((int) elem.getInformationToSend().getTone().getDuration(),
                100);
        assertTrue(CAPExtensionsTest.checkTestCAPExtensions(elem
                .getExtensions()));
        assertEquals((int) elem.getCallSegmentID(), 22);
        assertFalse(elem.getRequestAnnouncementStartedNotification());
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall" })
    public void testEncode() throws Exception {

        CollectedDigitsImpl collectedDigits = new CollectedDigitsImpl(null, 10,
                null, null, null, null, null, null, null, null, null);
        // Integer minimumNbOfDigits, int maximumNbOfDigits, byte[]
        // endOfReplyDigit, byte[] cancelDigit, byte[] startDigit,
        // Integer firstDigitTimeOut, Integer interDigitTimeOut, ErrorTreatment
        // errorTreatment, Boolean interruptableAnnInd,
        // Boolean voiceInformation,
        // Boolean voiceBack
        CollectedInfoImpl collectedInfo = new CollectedInfoImpl(collectedDigits);

        ToneImpl tone = new ToneImpl(10, 100);
        // int toneID, Integer duration
        InformationToSendImpl informationToSend = new InformationToSendImpl(
                tone);

        PromptAndCollectUserInformationRequestImpl elem = new PromptAndCollectUserInformationRequestImpl(
                collectedInfo, false, informationToSend,
                CAPExtensionsTest.createTestCAPExtensions(), 22, false);
        AsnOutputStream aos = new AsnOutputStream();
        elem.encodeAll(aos);
        assertTrue(Arrays.equals(aos.toByteArray(), this.getData1()));

        // CollectedInfo collectedInfo, Boolean disconnectFromIPForbidden,
        // InformationToSend informationToSend, CAPExtensions extensions,
        // Integer callSegmentID, Boolean
        // requestAnnouncementStartedNotification }
    }

    @Test(groups = { "functional.xml.serialize", "circuitSwitchedCall" })
    public void testXMLSerialize() throws Exception {

        ArrayList<VariablePart> aL = new ArrayList<VariablePart>();
        aL.add(new VariablePartImpl(new VariablePartDateImpl(2015, 6, 27)));
        aL.add(new VariablePartImpl(new VariablePartTimeImpl(15, 10)));
        aL.add(new VariablePartImpl(new Integer(145)));
        VariableMessageImpl vm = new VariableMessageImpl(145, aL);
        MessageIDImpl mi = new MessageIDImpl(vm);
        InbandInfoImpl inbandInfoImpl = new InbandInfoImpl(mi, new Integer(5),
                new Integer(8), new Integer(2));

        CollectedDigitsImpl collectedDigits = new CollectedDigitsImpl(10, 20,
                "#".getBytes(), "*".getBytes(), "1".getBytes(), 5, 10,
                ErrorTreatment.stdErrorAndInfo, false, false, false);

        PromptAndCollectUserInformationRequestImpl original = new PromptAndCollectUserInformationRequestImpl(
                new CollectedInfoImpl(collectedDigits), true,
                new InformationToSendImpl(inbandInfoImpl), null, 1, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "promptAndCollect",
                PromptAndCollectUserInformationRequestImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        PromptAndCollectUserInformationRequestImpl copy = reader.read(
                "promptAndCollect",
                PromptAndCollectUserInformationRequestImpl.class);

        assertEquals(original, copy);
    }
}
