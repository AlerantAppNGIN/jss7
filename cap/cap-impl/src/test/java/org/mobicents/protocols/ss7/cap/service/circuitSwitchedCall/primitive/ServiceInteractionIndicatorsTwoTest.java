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
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallCompletionTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallDiversionTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallingPartyRestrictionIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ConferenceTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ConnectedNumberTreatmentInd;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CwTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.EctTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.HoldTreatmentIndicator;
import org.mobicents.protocols.ss7.inap.api.primitives.BothwayThroughConnectionInd;
import org.testng.annotations.Test;

/**
 *
 * @author sergey vetyutnev
 * @author tamas gyorgyey
 */
public class ServiceInteractionIndicatorsTwoTest {

    public byte[] getData1() {
        return new byte[] { 48, 3, (byte) 130, 1, 0 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {

        byte[] data = this.getData1();
        AsnInputStream ais = new AsnInputStream(data);
        ServiceInteractionIndicatorsTwoImpl elem = new ServiceInteractionIndicatorsTwoImpl();
        int tag = ais.readTag();
        elem.decodeAll(ais);
        assertEquals(elem.getBothwayThroughConnectionInd(), BothwayThroughConnectionInd.bothwayPathRequired);

        // TODO: implement full testing for CAP V4
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {

        ServiceInteractionIndicatorsTwoImpl elem = new ServiceInteractionIndicatorsTwoImpl(null, null,
                BothwayThroughConnectionInd.bothwayPathRequired, null, false, null, null, null);
        AsnOutputStream aos = new AsnOutputStream();
        elem.encodeAll(aos);
        assertTrue(Arrays.equals(aos.toByteArray(), this.getData1()));

        // ForwardServiceInteractionInd forwardServiceInteractionInd,
        // BackwardServiceInteractionInd backwardServiceInteractionInd, BothwayThroughConnectionInd
        // bothwayThroughConnectionInd,
        // ConnectedNumberTreatmentInd connectedNumberTreatmentInd, boolean nonCUGCall, HoldTreatmentIndicator
        // holdTreatmentIndicator,
        // CwTreatmentIndicator cwTreatmentIndicator, EctTreatmentIndicator ectTreatmentIndicator

        // TODO: implement full testing for CAP V4
    }

    private static final <T> ArrayList<T> listValues(T... values) {
        ArrayList<T> ret = new ArrayList<T>(Arrays.asList(values));
        ret.add(null);
        return ret;
    }

    @Test(groups = { "functional.encode", "functional.decode", "functional.xml", "circuitSwitchedCall.primitive" })
    public void testAsnAndXml() throws Throwable {

        // test method: ensure that the value stays the same after POJO->XML->POJO->ASN.1->POJO conversion
        // note: this does not test the correctness of the ASN.1 encoding, just the consistency of encoding/decoding.

        // generate and test all possible parameter combinations, since there are "only" 226800 of them...

        // 28 elements
        List<ForwardServiceInteractionIndImpl> fsiiList = new ArrayList<ForwardServiceInteractionIndImpl>(28);
        fsiiList.add(null);
        for (ConferenceTreatmentIndicator conferenceTreatmentIndicator : listValues(ConferenceTreatmentIndicator
                .values())) {
            for (CallDiversionTreatmentIndicator callDiversionTreatmentIndicator : listValues(CallDiversionTreatmentIndicator
                    .values())) {
                for (CallingPartyRestrictionIndicator callingPartyRestrictionIndicator : listValues(CallingPartyRestrictionIndicator
                        .values())) {

                    fsiiList.add(new ForwardServiceInteractionIndImpl(conferenceTreatmentIndicator,
                            callDiversionTreatmentIndicator, callingPartyRestrictionIndicator));
                }
            }
        }

        // 10 elements
        List<BackwardServiceInteractionIndImpl> bsiiList = new ArrayList<BackwardServiceInteractionIndImpl>(10);
        bsiiList.add(null);
        for (ConferenceTreatmentIndicator conferenceTreatmentIndicator2 : listValues(ConferenceTreatmentIndicator
                .values())) {
            for (CallCompletionTreatmentIndicator callCompletionTreatmentIndicator : listValues(CallCompletionTreatmentIndicator
                    .values())) {

                bsiiList.add(new BackwardServiceInteractionIndImpl(conferenceTreatmentIndicator2,
                        callCompletionTreatmentIndicator));

            }
        }

        final List<ServiceInteractionIndicatorsTwoImpl> testCases = new ArrayList<ServiceInteractionIndicatorsTwoImpl>();

        // 28 × 10 × 810 elements = 226800 combinations
        for (ForwardServiceInteractionIndImpl forwardServiceInteractionInd : fsiiList) {
            for (BackwardServiceInteractionIndImpl backwardServiceInteractionInd : bsiiList) {
                // 810 combinations below
                for (boolean nonCUGCall : Arrays.asList(true, false)) {
                    for (BothwayThroughConnectionInd bothwayThroughConnectionInd : listValues(BothwayThroughConnectionInd
                            .values())) {
                        for (ConnectedNumberTreatmentInd connectedNumberTreatmentInd : listValues(ConnectedNumberTreatmentInd
                                .values())) {
                            for (HoldTreatmentIndicator holdTreatmentIndicator : listValues(HoldTreatmentIndicator
                                    .values())) {
                                for (CwTreatmentIndicator cwTreatmentIndicator : listValues(CwTreatmentIndicator
                                        .values())) {
                                    for (EctTreatmentIndicator ectTreatmentIndicator : listValues(EctTreatmentIndicator
                                            .values())) {
                                        testCases.add(new ServiceInteractionIndicatorsTwoImpl(
                                                forwardServiceInteractionInd, backwardServiceInteractionInd,
                                                bothwayThroughConnectionInd, connectedNumberTreatmentInd, nonCUGCall,
                                                holdTreatmentIndicator, cwTreatmentIndicator, ectTreatmentIndicator));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int cpus = Runtime.getRuntime().availableProcessors();
        System.out.println("Running " + testCases.size() + " tests on " + cpus + " threads...");
        Thread[] threads = new Thread[cpus];
        final Object[] result = new Object[testCases.size()];
        final AtomicInteger index = new AtomicInteger(0);
        for (int i = 0; i < cpus; i++) {
            threads[i] = new Thread() {

                @Override
                public void run() {
                    final String tag = "serviceInteractionIndicatorsTwo";
                    XMLBinding b = new XMLBinding();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    XMLObjectWriter w;
                    try {
                        w = new XMLObjectWriter().setBinding(b).setIndentation(" ").setOutput(baos);
                    } catch (XMLStreamException e1) {
                        e1.printStackTrace();
                        return;
                    }
                    AsnOutputStream aos = new AsnOutputStream();

                    int idx;
                    while ((idx = index.getAndIncrement()) < testCases.size()) {
                        try {
                            ServiceInteractionIndicatorsTwoImpl siit = testCases.get(idx);
                            baos.reset();
                            w.write(siit, tag, ServiceInteractionIndicatorsTwoImpl.class);
                            w.flush();

                            XMLObjectReader reader = new XMLObjectReader().setBinding(b).setInput(
                                    new ByteArrayInputStream(baos.toByteArray()));
                            ServiceInteractionIndicatorsTwoImpl xmlRead = reader.read(tag,
                                    ServiceInteractionIndicatorsTwoImpl.class);

                            assertEquals(xmlRead, siit);

                            aos.reset();
                            xmlRead.encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC, 7);

                            AsnInputStream ais = new AsnInputStream(aos.toByteArray());
                            assertEquals(ais.readTag(), 7);

                            ServiceInteractionIndicatorsTwoImpl asnRead = new ServiceInteractionIndicatorsTwoImpl();
                            asnRead.decodeAll(ais);
                            assertEquals(asnRead, siit);
                            result[idx] = Boolean.TRUE;
                        } catch (Throwable e) {
                            result[idx] = e;
                        }
                    }
                }
            };

            threads[i].start();
        }
        for (int i = 0; i < cpus; i++) {
            threads[i].join();
        }
        // wait for all threads to join before throwing an exception in the main test thread
        boolean fail = false;
        for (int i = 0; i < result.length; i++) {
            if (result[i] instanceof Throwable) {
                System.err.println("Error in testcase " + testCases.get(i));
                ((Throwable) result[i]).printStackTrace(System.err);
                fail = true;
            } else if (result[i] == null) {
                System.err.println("No result for testcase " + testCases.get(i));
                fail = true;
            }
        }
        if (fail) {
            throw new AssertionError("Failure(s) occurred, see the logs");
        }
    }
}
