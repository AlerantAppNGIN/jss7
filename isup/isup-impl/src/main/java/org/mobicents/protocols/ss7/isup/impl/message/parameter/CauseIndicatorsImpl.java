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

package org.mobicents.protocols.ss7.isup.impl.message.parameter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.mobicents.protocols.ss7.isup.ParameterException;
import org.mobicents.protocols.ss7.isup.message.parameter.CauseIndicators;
import org.mobicents.protocols.ss7.isup.util.ISUPUtility;

/**
 * Start time:15:14:32 2009-03-30<br>
 * Project: mobicents-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author sergey vetyutnev
 */
public class CauseIndicatorsImpl extends AbstractISUPParameter implements CauseIndicators {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private static final String LOCATION = "location";
    private static final String CAUSE_VALUE = "causeValue";
    private static final String CODING_STANDARD = "codingStandard";
    private static final String RECOMMENDATION = "recommendation";
    private static final String DIAGNOSTICS = "diagnostics";

    private static final int DEFAULT_VALUE = -1; // 0 is a valid value for some of the attributes

    private int location = 0;
    private int causeValue = 0;
    private int codingStandard = 0;
    private int recommendation = _RECOMMENDATION_Q763;
    private byte[] diagnostics = null;

    public CauseIndicatorsImpl() {
        super();

    }

    public CauseIndicatorsImpl(int codingStandard, int location, int recommendation, int causeValue, byte[] diagnostics) {
        super();
        this.setCodingStandard(codingStandard);
        this.setLocation(location);
        this.setRecommendation(recommendation);
        this.setCauseValue(causeValue);
        this.diagnostics = diagnostics;
    }

    public int decode(byte[] b) throws ParameterException {

        // Q.850:
        //
        //    NOTE 1 – If the default applies for the Recommendation field, octet including this field shall be omitted.
        //    NOTE 2 – The Recommendation field is not supported by the ISUP. The default interpretation for ISUP is Q.763.

        // This means that on ISUP, the octet containing the Recommendation field will not be present (ext bit of first octet must be 1),
        // and the interpretation is always Q.763.
        // The meaning of the diagnostics field is dependent on the cause value and may or may not be present, we don't interpret it, just record it.

        if (b == null || b.length < 2) {
            throw new ParameterException("byte[] must not be null or has size less than 2");
        }
        // Used because of Q.850 - we must ignore recommendation if present, but it shouldn't be
        int index = 0;
        // first two bytes are mandatory
        int v = 0;
        // remove ext
        v = b[index] & 0x7F;
        this.location = v & 0x0F;
        this.codingStandard = v >> 5;
        if (((b[index++] & 0x80) >> 7) == 0) {
            logger.warn("ISUP Cause Indicator octet "+index+" extension bit indicates that Recommendation field is present, but it shouldn't be, as it is not supported by ISUP.");
            this.recommendation = b[index++] & 0x7F; // parse to skip the octet and record the value, but don't interpret it
            logger.warn("ISUP Cause Indicator ignoring parsed value of Recommendation field: " + this.recommendation);
        } else {
            this.recommendation = _RECOMMENDATION_Q763;
        }
        // cause
        if(index >= b.length)
            throw new ParameterException("Octet " + (index + 1) + " missing from ISUP Cause Indicator!");
        v = b[index++] & 0xFF;
        if((v & 0x80) == 0)
            logger.warn("ISUP Cause Indicator octet "+index+" (cause) extension bit is 0, but it must be 1!");
        this.causeValue = v & 0x7F;
        // diagnostics
        if(index < b.length) {
            this.diagnostics = Arrays.copyOfRange(b, index, b.length);
        }
        return b.length;
    }

    public byte[] encode() throws ParameterException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int v = this.location & 0x0F;
        v |= ((this.codingStandard & 0x03) << 5);
        if(this.recommendation != _RECOMMENDATION_Q763) {
            logger.warn("ISUP Cause Indicator Recommendation field set to " + this.recommendation + ". This is not supported on ISUP, but encoding it anyway.");
            v &= 0x7F; // extension bit cleared to 0 for continuation
            bos.write(v);
            v = 0x80 /* no extension */ | (this.recommendation & 0x7F);
            bos.write(v);
        } else {
            v |= 0x80; // no extension set on first octet: no recommendation
            bos.write(v);
        }
        bos.write(0x80 | this.causeValue );
        if (this.diagnostics != null) {
            bos.write(this.diagnostics, 0, this.diagnostics.length);
        }
        byte[] b = bos.toByteArray();

        return b;
    }

    public int encode(ByteArrayOutputStream bos) throws ParameterException {
        byte[] b = this.encode();
        try {
            bos.write(b);
        } catch (IOException e) {
            throw new ParameterException(e);
        }
        return b.length;
    }

    public int getCodingStandard() {
        return codingStandard;
    }

    public void setCodingStandard(int codingStandard) {
        if(codingStandard != (codingStandard & 0x03))
            throw new IllegalArgumentException("Invalid codingStandard value for ISUP cause indicator: " + codingStandard + ". Only 2 bit values allowed");
        this.codingStandard = codingStandard;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        if(location != (location & 0x0F))
            throw new IllegalArgumentException("Invalid location value for ISUP cause indicator: " + location + ". Only 4 bit values allowed");
        this.location = location;
    }

    public int getCauseValue() {
        return causeValue;
    }

    public int getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(int recommendation) {
        if (recommendation == _RECOMMENDATION_Q763) { // unset value
            this.recommendation = recommendation;
        } else if (recommendation != (recommendation & 0x7F)) {
            throw new IllegalArgumentException("Invalid recommendation value for ISUP cause indicator: "
                    + recommendation + ". Only 7 bit values allowed");
        } else {
            logger.warn("ISUP cause indicator: setting unsupported recommendation value " + recommendation);
            this.recommendation = recommendation;
        }
    }

    public void setCauseValue(int causeValue) {
        if(causeValue != (causeValue & 0x7F))
            throw new IllegalArgumentException("Invalid causeValue for ISUP cause indicator: " + causeValue + ". Only 7 bit values allowed");
        this.causeValue = causeValue;
    }

    public byte[] getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(byte[] diagnostics) {
        this.diagnostics = diagnostics;
    }

    public int getCode() {

        return _PARAMETER_CODE;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CauseIndicators [");

        sb.append("codingStandard=");
        sb.append(codingStandard);
        sb.append(", location=");
        sb.append(location);
        if(recommendation != _RECOMMENDATION_Q763) {
            sb.append(", recommendation=");
            sb.append(recommendation);
        }
        sb.append(", causeValue=");
        sb.append(causeValue);

        if (this.diagnostics != null) {
            sb.append(", diagnostics=[hex ");
            ISUPUtility.appendHexStream(sb, diagnostics, " ", false, false);
            sb.append(']');
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<CauseIndicatorsImpl> ISUP_CAUSE_INDICATORS_XML = new XMLFormat<CauseIndicatorsImpl>(
            CauseIndicatorsImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, CauseIndicatorsImpl causeIndicators)
                throws XMLStreamException {
            // use setters to perform validity checks
            causeIndicators.setLocation(xml.getAttribute(LOCATION, DEFAULT_VALUE));
            causeIndicators.setCauseValue(xml.getAttribute(CAUSE_VALUE, DEFAULT_VALUE));
            causeIndicators.setCodingStandard(xml.getAttribute(CODING_STANDARD, DEFAULT_VALUE));
            causeIndicators.setRecommendation(xml.getAttribute(RECOMMENDATION, _RECOMMENDATION_Q763)); // should not be present

            ByteArrayContainer bc = xml.get(DIAGNOSTICS, ByteArrayContainer.class);
            if (bc != null) {
                causeIndicators.setDiagnostics(bc.getData());
            }
        }

        @Override
        public void write(CauseIndicatorsImpl causeIndicators, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(LOCATION, causeIndicators.location);
            xml.setAttribute(CAUSE_VALUE, causeIndicators.causeValue);
            xml.setAttribute(CODING_STANDARD, causeIndicators.codingStandard);
            if (causeIndicators.recommendation != _RECOMMENDATION_Q763) {
                Logger.getLogger(CauseIndicatorsImpl.class).warn(
                        "ISUP Cause Indicator Recommendation field set to " + causeIndicators.recommendation
                                + ". This is not supported on ISUP, but encoding it anyway.");
                xml.setAttribute(RECOMMENDATION, causeIndicators.recommendation);
            }

            if (causeIndicators.diagnostics != null) {
                ByteArrayContainer bac = new ByteArrayContainer(causeIndicators.diagnostics);
                xml.add(bac, DIAGNOSTICS, ByteArrayContainer.class);
            }
        }
    };
}
