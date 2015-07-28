package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import java.io.IOException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallDiversionTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallingPartyRestrictionIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ConferenceTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ForwardServiceInteractionInd;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;

/**
 * Implementation class for {@link ForwardServiceInteractionInd}.
 * @author tamas gyorgyey
 */
@SuppressWarnings("serial")
public class ForwardServiceInteractionIndImpl extends SequenceBase implements ForwardServiceInteractionInd,
        CAPAsnPrimitive {

    public static final int _ID_conferenceTreatmentIndicator = 1;
    public static final int _ID_callDiversionTreatmentIndicator = 2;
    public static final int _ID_callingPartyRestrictionIndicator = 4;

    private static final String CONFERENCE_TREATMENT_INDICATOR = "conferenceTreatmentIndicator";
    private static final String CALL_DIVERSION_TREATMENT_INDICATOR = "callDiversionTreatmentIndicator";
    private static final String CALLING_PARTY_RESTRICTION_INDICATOR = "callingPartyRestrictionIndicator";

    public static final String _PrimitiveName = "ForwardServiceInteractionInd";

    private ConferenceTreatmentIndicator conferenceTreatmentIndicator;
    private CallDiversionTreatmentIndicator callDiversionTreatmentIndicator;
    private CallingPartyRestrictionIndicator callingPartyRestrictionIndicator;

    public ForwardServiceInteractionIndImpl() {
        super(_PrimitiveName);
    }

    public ForwardServiceInteractionIndImpl(ConferenceTreatmentIndicator conferenceTreatmentIndicator,
            CallDiversionTreatmentIndicator callDiversionTreatmentIndicator,
            CallingPartyRestrictionIndicator callingPartyRestrictionIndicator) {
        super(_PrimitiveName);
        this.conferenceTreatmentIndicator = conferenceTreatmentIndicator;
        this.callDiversionTreatmentIndicator = callDiversionTreatmentIndicator;
        this.callingPartyRestrictionIndicator = callingPartyRestrictionIndicator;
    }

    @Override
    public ConferenceTreatmentIndicator getConferenceTreatmentIndicator() {
        return conferenceTreatmentIndicator;
    }

    @Override
    public CallDiversionTreatmentIndicator getCallDiversionTreatmentIndicator() {
        return callDiversionTreatmentIndicator;
    }

    @Override
    public CallingPartyRestrictionIndicator getCallingPartyRestrictionIndicator() {
        return callingPartyRestrictionIndicator;
    }

    @Override
    public void _decode(AsnInputStream ansIS, int length) throws AsnException, IOException {

        this.conferenceTreatmentIndicator = null;
        this.callDiversionTreatmentIndicator = null;
        this.callingPartyRestrictionIndicator = null;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {

                // these members are defined as OCTET STRING(SIZE(1)) and all values are contained in the 2 least
                // significant bits of the single octet. The resulting encoding is equivalent to a one octet integer,
                // where the getInstance() methods take care of discarding the higher bits (if present).

                switch (tag) {
                    case _ID_conferenceTreatmentIndicator:
                        this.conferenceTreatmentIndicator = ConferenceTreatmentIndicator.getInstance((int) ais
                                .readInteger());
                        break;
                    case _ID_callDiversionTreatmentIndicator:
                        this.callDiversionTreatmentIndicator = CallDiversionTreatmentIndicator.getInstance((int) ais
                                .readInteger());
                        break;
                    case _ID_callingPartyRestrictionIndicator:
                        this.callingPartyRestrictionIndicator = CallingPartyRestrictionIndicator.getInstance((int) ais
                                .readInteger());
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
    public void encodeData(AsnOutputStream asnOs) throws CAPException {
        try {
            if (conferenceTreatmentIndicator != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_conferenceTreatmentIndicator,
                        conferenceTreatmentIndicator.getCode());
            }
            if (callDiversionTreatmentIndicator != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_callDiversionTreatmentIndicator,
                        callDiversionTreatmentIndicator.getCode());
            }
            if (callingPartyRestrictionIndicator != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_callingPartyRestrictionIndicator,
                        callingPartyRestrictionIndicator.getCode());
            }

        } catch (Exception e) {
            throw new CAPException("Error while encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    protected static final XMLFormat<ForwardServiceInteractionIndImpl> XML = new XMLFormat<ForwardServiceInteractionIndImpl>(
            ForwardServiceInteractionIndImpl.class) {
        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, ForwardServiceInteractionIndImpl obj)
                throws XMLStreamException {
            obj.conferenceTreatmentIndicator = xml.get(CONFERENCE_TREATMENT_INDICATOR,
                    ConferenceTreatmentIndicator.class);
            obj.callDiversionTreatmentIndicator = xml.get(CALL_DIVERSION_TREATMENT_INDICATOR,
                    CallDiversionTreatmentIndicator.class);
            obj.callingPartyRestrictionIndicator = xml.get(CALLING_PARTY_RESTRICTION_INDICATOR,
                    CallingPartyRestrictionIndicator.class);
        }

        @Override
        public void write(ForwardServiceInteractionIndImpl obj, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.add(obj.conferenceTreatmentIndicator, CONFERENCE_TREATMENT_INDICATOR,
                    ConferenceTreatmentIndicator.class);
            xml.add(obj.callDiversionTreatmentIndicator, CALL_DIVERSION_TREATMENT_INDICATOR,
                    CallDiversionTreatmentIndicator.class);
            xml.add(obj.callingPartyRestrictionIndicator, CALLING_PARTY_RESTRICTION_INDICATOR,
                    CallingPartyRestrictionIndicator.class);
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((callDiversionTreatmentIndicator == null) ? 0 : callDiversionTreatmentIndicator.hashCode());
        result = prime * result
                + ((callingPartyRestrictionIndicator == null) ? 0 : callingPartyRestrictionIndicator.hashCode());
        result = prime * result
                + ((conferenceTreatmentIndicator == null) ? 0 : conferenceTreatmentIndicator.hashCode());
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
        ForwardServiceInteractionIndImpl other = (ForwardServiceInteractionIndImpl) obj;
        if (callDiversionTreatmentIndicator != other.callDiversionTreatmentIndicator)
            return false;
        if (callingPartyRestrictionIndicator != other.callingPartyRestrictionIndicator)
            return false;
        if (conferenceTreatmentIndicator != other.conferenceTreatmentIndicator)
            return false;
        return true;
    }

}
