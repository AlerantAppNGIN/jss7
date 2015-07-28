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

import java.io.IOException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.BackwardServiceInteractionInd;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ConnectedNumberTreatmentInd;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CwTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.EctTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ForwardServiceInteractionInd;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.HoldTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ServiceInteractionIndicatorsTwo;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.inap.api.primitives.BothwayThroughConnectionInd;

/**
 * Implementation class for {@link ServiceInteractionIndicatorsTwo}.
 *
 * @author sergey vetyutnev
 * @author tamas gyorgyey
 */
@SuppressWarnings("serial")
public class ServiceInteractionIndicatorsTwoImpl extends SequenceBase implements ServiceInteractionIndicatorsTwo,
        CAPAsnPrimitive {

    public static final int _ID_forwardServiceInteractionInd = 0;
    public static final int _ID_backwardServiceInteractionInd = 1;
    public static final int _ID_bothwayThroughConnectionInd = 2;
    public static final int _ID_connectedNumberTreatmentInd = 4;
    public static final int _ID_nonCUGCall = 13;
    public static final int _ID_holdTreatmentIndicator = 50;
    public static final int _ID_cwTreatmentIndicator = 51;
    public static final int _ID_ectTreatmentIndicator = 52;

    private static final String FORWARD_SERVICE_INTERACTION_IND = "forwardServiceInteractionInd";
    private static final String BACKWARD_SERVICE_INTERACTION_IND = "backwardServiceInteractionInd";
    private static final String BOTHWAY_THROUGH_CONNECTION_IND = "bothwayThroughConnectionInd";
    private static final String CONNECTED_NUMBER_TREATMENT_IND = "connectedNumberTreatmentInd";
    private static final String NON_CUG_CALL = "nonCUGCall";
    private static final String HOLD_TREATMENT_INDICATOR = "holdTreatmentIndicator";
    private static final String CW_TREATMENT_INDICATOR = "cwTreatmentIndicator";
    private static final String ECT_TREATMENT_INDICATOR = "ectTreatmentIndicator";

    public static final String _PrimitiveName = "ServiceInteractionIndicatorsTwo";

    private ForwardServiceInteractionInd forwardServiceInteractionInd;
    private BackwardServiceInteractionInd backwardServiceInteractionInd;
    private BothwayThroughConnectionInd bothwayThroughConnectionInd;
    private ConnectedNumberTreatmentInd connectedNumberTreatmentInd;
    private boolean nonCUGCall;
    private HoldTreatmentIndicator holdTreatmentIndicator;
    private CwTreatmentIndicator cwTreatmentIndicator;
    private EctTreatmentIndicator ectTreatmentIndicator;

    public ServiceInteractionIndicatorsTwoImpl() {
        super(_PrimitiveName);
    }

    public ServiceInteractionIndicatorsTwoImpl(ForwardServiceInteractionInd forwardServiceInteractionInd,
            BackwardServiceInteractionInd backwardServiceInteractionInd,
            BothwayThroughConnectionInd bothwayThroughConnectionInd,
            ConnectedNumberTreatmentInd connectedNumberTreatmentInd, boolean nonCUGCall,
            HoldTreatmentIndicator holdTreatmentIndicator, CwTreatmentIndicator cwTreatmentIndicator,
            EctTreatmentIndicator ectTreatmentIndicator) {
        super(_PrimitiveName);
        this.forwardServiceInteractionInd = forwardServiceInteractionInd;
        this.backwardServiceInteractionInd = backwardServiceInteractionInd;
        this.bothwayThroughConnectionInd = bothwayThroughConnectionInd;
        this.connectedNumberTreatmentInd = connectedNumberTreatmentInd;
        this.nonCUGCall = nonCUGCall;
        this.holdTreatmentIndicator = holdTreatmentIndicator;
        this.cwTreatmentIndicator = cwTreatmentIndicator;
        this.ectTreatmentIndicator = ectTreatmentIndicator;
    }

    @Override
    public ForwardServiceInteractionInd getForwardServiceInteractionInd() {
        return forwardServiceInteractionInd;
    }

    @Override
    public BackwardServiceInteractionInd getBackwardServiceInteractionInd() {
        return backwardServiceInteractionInd;
    }

    @Override
    public BothwayThroughConnectionInd getBothwayThroughConnectionInd() {
        return bothwayThroughConnectionInd;
    }

    @Override
    public ConnectedNumberTreatmentInd getConnectedNumberTreatmentInd() {
        return connectedNumberTreatmentInd;
    }

    @Override
    public boolean getNonCUGCall() {
        return nonCUGCall;
    }

    @Override
    public HoldTreatmentIndicator getHoldTreatmentIndicator() {
        return holdTreatmentIndicator;
    }

    @Override
    public CwTreatmentIndicator getCwTreatmentIndicator() {
        return cwTreatmentIndicator;
    }

    @Override
    public EctTreatmentIndicator getEctTreatmentIndicator() {
        return ectTreatmentIndicator;
    }

    protected void _decode(AsnInputStream ansIS, int length) throws CAPParsingComponentException, IOException,
            AsnException {

        this.forwardServiceInteractionInd = null;
        this.backwardServiceInteractionInd = null;
        this.bothwayThroughConnectionInd = null;
        this.connectedNumberTreatmentInd = null;
        this.nonCUGCall = false;
        this.holdTreatmentIndicator = null;
        this.cwTreatmentIndicator = null;
        this.ectTreatmentIndicator = null;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                    case _ID_forwardServiceInteractionInd:
                        ForwardServiceInteractionIndImpl fsii = new ForwardServiceInteractionIndImpl();
                        fsii.decodeAll(ais);
                        this.forwardServiceInteractionInd = fsii;
                        break;
                    case _ID_backwardServiceInteractionInd:
                        BackwardServiceInteractionIndImpl bsii = new BackwardServiceInteractionIndImpl();
                        bsii.decodeAll(ais);
                        this.backwardServiceInteractionInd = bsii;
                        break;
                    case _ID_bothwayThroughConnectionInd:
                        this.bothwayThroughConnectionInd = BothwayThroughConnectionInd.getInstance((int) ais
                                .readInteger());
                        break;
                    case _ID_connectedNumberTreatmentInd:
                        this.connectedNumberTreatmentInd = ConnectedNumberTreatmentInd.getInstance((int) ais
                                .readInteger());
                        break;
                    case _ID_nonCUGCall:
                        ais.readNull();
                        this.nonCUGCall = true;
                        break;

                    // these members are defined as OCTET STRING(SIZE(1)) and all values are contained in the 2 least
                    // significant bits of the single octet. The resulting encoding is equivalent to a one octet
                    // integer, where the getInstance() methods take care of discarding the higher bits (if present).

                    case _ID_holdTreatmentIndicator:
                        this.holdTreatmentIndicator = HoldTreatmentIndicator.getInstance((int) ais.readInteger());
                        break;
                    case _ID_cwTreatmentIndicator:
                        this.cwTreatmentIndicator = CwTreatmentIndicator.getInstance((int) ais.readInteger());
                        break;
                    case _ID_ectTreatmentIndicator:
                        this.ectTreatmentIndicator = EctTreatmentIndicator.getInstance((int) ais.readInteger());
                        break;

                    default:
                        ais.advanceElement();
                        break;
                }
            } else {
                ais.advanceElement();
            }
        }
    }

    @Override
    public void encodeData(AsnOutputStream aos) throws CAPException {

        try {

            if (this.forwardServiceInteractionInd != null) {
                ((ForwardServiceInteractionIndImpl) this.forwardServiceInteractionInd).encodeAll(aos,
                        Tag.CLASS_CONTEXT_SPECIFIC, _ID_forwardServiceInteractionInd);
            }
            if (this.backwardServiceInteractionInd != null) {
                ((BackwardServiceInteractionIndImpl) this.backwardServiceInteractionInd).encodeAll(aos,
                        Tag.CLASS_CONTEXT_SPECIFIC, _ID_backwardServiceInteractionInd);
            }
            if (this.bothwayThroughConnectionInd != null) {
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_bothwayThroughConnectionInd,
                        this.bothwayThroughConnectionInd.getCode());
            }
            if (this.connectedNumberTreatmentInd != null) {
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_connectedNumberTreatmentInd,
                        this.connectedNumberTreatmentInd.getCode());
            }
            if (this.nonCUGCall) {
                aos.writeNull(Tag.CLASS_CONTEXT_SPECIFIC, _ID_nonCUGCall);
            }
            if (this.holdTreatmentIndicator != null) {
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_holdTreatmentIndicator,
                        this.holdTreatmentIndicator.getCode());
            }
            if (this.cwTreatmentIndicator != null) {
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_cwTreatmentIndicator,
                        this.cwTreatmentIndicator.getCode());
            }
            if (this.ectTreatmentIndicator != null) {
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_ectTreatmentIndicator,
                        this.ectTreatmentIndicator.getCode());
            }
        } catch (IOException e) {
            throw new CAPException("IOException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<ServiceInteractionIndicatorsTwoImpl> SERVICE_INTERACTION_INDICATORS_TWO_XML = new XMLFormat<ServiceInteractionIndicatorsTwoImpl>(
            ServiceInteractionIndicatorsTwoImpl.class) {

        public void read(javolution.xml.XMLFormat.InputElement xml,
                ServiceInteractionIndicatorsTwoImpl serviceInteractionIndicatorsTwo) throws XMLStreamException {

            serviceInteractionIndicatorsTwo.forwardServiceInteractionInd = xml.get(FORWARD_SERVICE_INTERACTION_IND,
                    ForwardServiceInteractionIndImpl.class);
            serviceInteractionIndicatorsTwo.backwardServiceInteractionInd = xml.get(BACKWARD_SERVICE_INTERACTION_IND,
                    BackwardServiceInteractionIndImpl.class);
            serviceInteractionIndicatorsTwo.bothwayThroughConnectionInd = xml.get(BOTHWAY_THROUGH_CONNECTION_IND,
                    BothwayThroughConnectionInd.class);
            serviceInteractionIndicatorsTwo.connectedNumberTreatmentInd = xml.get(CONNECTED_NUMBER_TREATMENT_IND,
                    ConnectedNumberTreatmentInd.class);
            serviceInteractionIndicatorsTwo.nonCUGCall = Boolean.TRUE.equals(xml.get(NON_CUG_CALL, Boolean.class));
            serviceInteractionIndicatorsTwo.holdTreatmentIndicator = xml.get(HOLD_TREATMENT_INDICATOR,
                    HoldTreatmentIndicator.class);
            serviceInteractionIndicatorsTwo.cwTreatmentIndicator = xml.get(CW_TREATMENT_INDICATOR,
                    CwTreatmentIndicator.class);
            serviceInteractionIndicatorsTwo.ectTreatmentIndicator = xml.get(ECT_TREATMENT_INDICATOR,
                    EctTreatmentIndicator.class);
        }

        public void write(ServiceInteractionIndicatorsTwoImpl serviceInteractionIndicatorsTwo,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {

            xml.add((ForwardServiceInteractionIndImpl) serviceInteractionIndicatorsTwo.forwardServiceInteractionInd,
                    FORWARD_SERVICE_INTERACTION_IND, ForwardServiceInteractionIndImpl.class);
            xml.add((BackwardServiceInteractionIndImpl) serviceInteractionIndicatorsTwo.backwardServiceInteractionInd,
                    BACKWARD_SERVICE_INTERACTION_IND, BackwardServiceInteractionIndImpl.class);
            xml.add(serviceInteractionIndicatorsTwo.bothwayThroughConnectionInd, BOTHWAY_THROUGH_CONNECTION_IND,
                    BothwayThroughConnectionInd.class);
            xml.add(serviceInteractionIndicatorsTwo.connectedNumberTreatmentInd, CONNECTED_NUMBER_TREATMENT_IND,
                    ConnectedNumberTreatmentInd.class);
            if (serviceInteractionIndicatorsTwo.nonCUGCall) { // only add if true
                xml.add(serviceInteractionIndicatorsTwo.nonCUGCall, NON_CUG_CALL, Boolean.class);
            }
            xml.add(serviceInteractionIndicatorsTwo.holdTreatmentIndicator, HOLD_TREATMENT_INDICATOR,
                    HoldTreatmentIndicator.class);
            xml.add(serviceInteractionIndicatorsTwo.cwTreatmentIndicator, CW_TREATMENT_INDICATOR,
                    CwTreatmentIndicator.class);
            xml.add(serviceInteractionIndicatorsTwo.ectTreatmentIndicator, ECT_TREATMENT_INDICATOR,
                    EctTreatmentIndicator.class);
        }
    };

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.forwardServiceInteractionInd != null) {
            sb.append("forwardServiceInteractionInd=");
            sb.append(forwardServiceInteractionInd.toString());
        }
        if (this.backwardServiceInteractionInd != null) {
            sb.append(", backwardServiceInteractionInd=");
            sb.append(backwardServiceInteractionInd.toString());
        }
        if (this.bothwayThroughConnectionInd != null) {
            sb.append(", bothwayThroughConnectionInd=");
            sb.append(bothwayThroughConnectionInd.toString());
        }
        if (this.connectedNumberTreatmentInd != null) {
            sb.append(", connectedNumberTreatmentInd=");
            sb.append(connectedNumberTreatmentInd.toString());
        }
        if (this.nonCUGCall) {
            sb.append(", nonCUGCall");
        }
        if (this.holdTreatmentIndicator != null) {
            sb.append(", holdTreatmentIndicator=");
            sb.append(holdTreatmentIndicator.toString());
        }
        if (this.cwTreatmentIndicator != null) {
            sb.append(", cwTreatmentIndicator=");
            sb.append(cwTreatmentIndicator.toString());
        }
        if (this.ectTreatmentIndicator != null) {
            sb.append(", ectTreatmentIndicator=");
            sb.append(ectTreatmentIndicator.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((backwardServiceInteractionInd == null) ? 0 : backwardServiceInteractionInd.hashCode());
        result = prime * result + ((bothwayThroughConnectionInd == null) ? 0 : bothwayThroughConnectionInd.hashCode());
        result = prime * result + ((connectedNumberTreatmentInd == null) ? 0 : connectedNumberTreatmentInd.hashCode());
        result = prime * result + ((cwTreatmentIndicator == null) ? 0 : cwTreatmentIndicator.hashCode());
        result = prime * result + ((ectTreatmentIndicator == null) ? 0 : ectTreatmentIndicator.hashCode());
        result = prime * result
                + ((forwardServiceInteractionInd == null) ? 0 : forwardServiceInteractionInd.hashCode());
        result = prime * result + ((holdTreatmentIndicator == null) ? 0 : holdTreatmentIndicator.hashCode());
        result = prime * result + (nonCUGCall ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceInteractionIndicatorsTwoImpl other = (ServiceInteractionIndicatorsTwoImpl) obj;
        if (backwardServiceInteractionInd == null) {
            if (other.backwardServiceInteractionInd != null)
                return false;
        } else if (!backwardServiceInteractionInd.equals(other.backwardServiceInteractionInd))
            return false;
        if (bothwayThroughConnectionInd != other.bothwayThroughConnectionInd)
            return false;
        if (connectedNumberTreatmentInd != other.connectedNumberTreatmentInd)
            return false;
        if (cwTreatmentIndicator != other.cwTreatmentIndicator)
            return false;
        if (ectTreatmentIndicator != other.ectTreatmentIndicator)
            return false;
        if (forwardServiceInteractionInd == null) {
            if (other.forwardServiceInteractionInd != null)
                return false;
        } else if (!forwardServiceInteractionInd.equals(other.forwardServiceInteractionInd))
            return false;
        if (holdTreatmentIndicator != other.holdTreatmentIndicator)
            return false;
        if (nonCUGCall != other.nonCUGCall)
            return false;
        return true;
    }

}
