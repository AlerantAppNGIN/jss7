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

import java.util.Arrays;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.ss7.isup.ParameterException;
import org.mobicents.protocols.ss7.isup.message.parameter.UserServiceInformationBase;

/**
 * Start time:12:36:18 2009-04-04<br>
 * Project: mobicents-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski</a>
 * @author <a href="mailto:oifa.yulian@gmail.com"> Yulian Oifa</a>
 * @author sergey vetyutnev
 * @author tamas gyorgyey
 */
public abstract class UserServiceInformationBaseImpl extends AbstractISUPParameter implements
        UserServiceInformationBase {

    private static final String CODING_STANDARD = "codingStandard";
    private static final String INFORMATION_TRANSFER_CAPABILITY = "informationTransferCapability";
    private static final String TRANSFER_MODE = "transferMode";
    private static final String RATE_MULTIPLIER = "rateMultiplier";
    private static final String INFORMATION_TRANSFER_RATE = "informationTransferRate";
    private static final String L1_USER_INFORMATION = "l1UserInformation";
    private static final String L2_USER_INFORMATION = "l2UserInformation";
    private static final String L3_USER_INFORMATION = "l3UserInformation";
    private static final String SYNC_MODE = "syncMode";
    private static final String NEGOTIATION = "negotiation";
    private static final String USER_RATE = "userRate";
    private static final String INTERMEDIATE_RATE = "intermediateRate";
    private static final String NIC_ON_TX = "nicOnTx";
    private static final String NIC_ON_RX = "nicOnRx";
    private static final String FC_ON_TX = "fcOnTx";
    private static final String FC_ON_RX = "fcOnRx";
    private static final String HDR = "hdr";
    private static final String MULTIFRAME = "multiframe";
    private static final String MODE = "mode";
    private static final String LLI = "lli";
    private static final String ASSIGNOR = "assignor";
    private static final String IN_BAND_NEGOTIATION = "inBandNegotiation";
    private static final String STOP_BITS = "stopBits";
    private static final String DATA_BITS = "dataBits";
    private static final String PARITY = "parity";
    private static final String DUPLEX_MODE = "duplexMode";
    private static final String MODEM_TYPE = "modemType";
    private static final String L3_PROTOCOL = "l3Protocol";

    /**
     * Stores the original octets if the object was created using {@link #decode(byte[])}. This is necessary to be able
     * to re{@link #encode()} the same value even if some parts could not be interpreted. Otherwise, it stores the
     * result of {@link #encode()} to speed up the following encode operations.
     */
    private byte[] octets;

    // octets 1 and 2 contain tag and length
    // octet 3: |'1' |.. coding standard|..... information transfer capability|
    private int codingStandard = 0;
    private int informationTransferCapability = 0;

    // octet 4: |'1' |.. transfer mode |..... information transfer rate |
    private int transferMode = 0;
    private int informationTransferRate = 0;

    // octet 4.1: |'1' |....... rate multiplier |
    // NOTE 1 – This octet is required if octet 4 indicates multirate (64 kbit/s base rate). Otherwise, it shall not be
    // present.
    private Integer rateMultiplier; // should be called rateMultiplier?

    // octet 5: |0/1 |'01' layer 1 id. |..... user information layer 1 proto |
    private Integer l1UserInformation;

    // octet 5a: |0/1 |. syncMode |. nego|..... user rate |
    // NOTE 2 – This octet may be present if octet 3 indicates unrestricted digital information and octet 5 indicates
    // either
    // of the ITU-T standardized rate adaptions V.110, I.460 and X.30 or V.120 [9]. It may also be present if octet 3
    // indicates 3.1 kHz audio and octet 5 indicates G.711.
    private Integer syncMode;
    private Integer negotiation;
    private Integer userRate;

    // octet 5b: |0/1 |.. intermed. rate |.nicOnTx|.nicOnRx|.fcOnTx|.fcOnRx|'0'|
    // NOTE 3 – This structure of octet 5b only applies if octet 5 indicates ITU-T standardized rate adaption (see
    // Recommendations V.110 [7], I.460 [15] and X.30 [8]).
    private Integer intermediateRate;
    private Integer nicOnTx;
    private Integer nicOnRx;
    private Integer fcOnTx;
    private Integer fcOnRx;

    // octet 5b: |0/1 |.hdr|.multiframe |.mode|.lli|.assignor|. inband neg|'0'|
    // NOTE 4 – This structure of octet 5b only applies if octet 5 indicates ITU-T standardized rate adaption (see
    // Recommendation V.120 [9]).
    private Integer hdr;
    private Integer multiframe;
    private Integer mode;
    private Integer lli;
    private Integer assignor;
    private Integer inBandNegotiation;

    // The standard does not define the structure of octet 5b for either G.711 or non-ITU-T rate adaption, even though
    // the octet may be present. In these cases, we don't interpret that octet.

    // octet 5c: |0/1 |.. stop bits |.. data bits|... parity |
    // NOTE 2 – This octet may be present if octet 3 indicates unrestricted digital information and octet 5 indicates
    // either
    // of the ITU-T standardized rate adaptions V.110, I.460 and X.30 or V.120 [9]. It may also be present if octet 3
    // indicates 3.1 kHz audio and octet 5 indicates G.711.
    //
    // For non-ITU-T rate adaption, octets 5c and 5d are user defined and not interpreted.
    private Integer stopBits;
    private Integer dataBits;
    private Integer parity;

    // octet 5d: |'1' |. duplexMode |...... modem type |
    // NOTE 2 – This octet may be present if octet 3 indicates unrestricted digital information and octet 5 indicates
    // either
    // of the ITU-T standardized rate adaptions V.110, I.460 and X.30 or V.120 [9]. It may also be present if octet 3
    // indicates 3.1 kHz audio and octet 5 indicates G.711.
    //
    // For non-ITU-T rate adaption, octets 5c and 5d are user defined and not interpreted.
    private Integer duplexMode;
    private Integer modemType;

    // octet 6: |'1' |'10' layer 2 id. |..... user information layer 2 proto |
    private Integer l2UserInformation;
    // octet 7: |'0' |'11' layer 3 id. |..... user information layer 3 proto |
    private Integer l3UserInformation;
    // octet 7a: |'0' |'000' spare |.... additional layer 3 proto info |
    // NOTE 5 – This octet may be included if octet 7 indicates ISO/IEC TR 9577 (Protocol Identification in the network
    // layer).
    // octet 7b: |'1' |'000' spare |.... additional layer 3 proto info |
    // NOTE 5 – This octet may be included if octet 7 indicates ISO/IEC TR 9577 (Protocol Identification in the network
    // layer).
    private Integer l3Protocol; // additional L3 protocol info

    private void checkRepresentableInBitCount(int value, int bitCount) {
        if (Integer.SIZE - Integer.numberOfLeadingZeros(value) > bitCount)
            throw new IllegalArgumentException("Parameter must fit into a " + bitCount + " bit value!");
    }

    private boolean isOctetDataPresent_L1_5a() {
        return syncMode != null && negotiation != null && userRate != null;
    }

    private boolean isOctetDataPresent_L1_5b_V110() {
        return intermediateRate != null && nicOnTx != null && nicOnRx != null && fcOnTx != null && fcOnRx != null;
    }

    private boolean isOctetDataPresent_L1_5b_V120() {
        return hdr != null && multiframe != null && mode != null && lli != null && assignor != null
                && inBandNegotiation != null;
    }

    private boolean isOctetDataPresent_L1_5b() {
        return isOctetDataPresent_L1_5b_V110() || isOctetDataPresent_L1_5b_V120();
    }

    private boolean isOctetDataPresent_L1_5c() {
        return stopBits != null && dataBits != null && parity != null;
    }

    private boolean isOctetDataPresent_L1_5d() {
        return duplexMode != null && modemType != null;
    }

    public int decode(byte[] b) throws ParameterException {
        octets = Arrays.copyOf(b, b.length);

        // The maximum length of this information element content is 12 octets.
        // The first two octets (3 and 4) are mandatory.
        if (b == null || b.length < 2 || b.length > 12) {
            throw new ParameterException("byte[] must not be null and should be between 2 and 12 bytes in length");
        }

        int v = 0;
        int index = 0;

        // octet 3
        // byte 0 bit 1-5 information transfer capability , 6-7 coding standard
        v = b[index++];
        this.informationTransferCapability = v & 0x1F;
        this.codingStandard = (v >> 5) & 0x03;

        // octet 4
        // byte 1 bit 1-5 information transfer rate , 6-7 transfer mode
        v = b[index++];
        this.informationTransferRate = v & 0x1F;
        this.transferMode = (v >> 5) & 0x03;

        // octet 4.1
        if (this.informationTransferRate == _ITR_MULTIRATE) {
            if (index == b.length)
                throw new ParameterException(
                        "Missing octet 4.1: rate multiplier should be present if information transfer rate is multirate");

            v = b[index++];
            this.rateMultiplier = v & 0x7F;
        }

        // octet 5, optional
        if (index < b.length && _LAYER1_IDENTIFIER == ((b[index] >> 5) & 0x03)) {
            // byte 3-5 l1-l3 user information
            v = b[index++];

            this.l1UserInformation = v & 0x1F;
            // check for bytes 5a to 5d depending on l1 information

            // 0 0 0 0 1
            // ITU-T standardized rate adaption V.110, I.460 and X.30. This implies the presence of
            // octet 5a and optionally octets 5b, 5c and 5d as defined below

            // 0 0 1 1 1
            // Non-ITU-T standardized rate adaption. This implies the presence of octet 5a and,
            // optionally, octets 5b, 5c and 5d. The use of this codepoint indicates that the user rate
            // specified in octet 5a is defined by the user. Additionally, octets 5b, 5c and 5d, if
            // present, are defined in accordance with the user specified rate adaption
            //
            // In this case, we don't interpret 5b, 5c and 5d

            // 0 1 0 0 0
            // ITU-T standardized rate adaption V.120 [9]. This implies the presence of octets 5a and
            // 5b as defined below, and optionally octets 5c and 5d

            boolean shouldHave5a = this.l1UserInformation == _L1_ITUT_110
                                || this.l1UserInformation == _L1_ITUT_120
                                || this.l1UserInformation == _L1_NON_ITUT;

            boolean shouldHave5b = this.l1UserInformation == _L1_ITUT_120;
            boolean skipInterpretationOf5b5c5d = this.l1UserInformation == _L1_NON_ITUT;

            // octet 5,5a,...,5d is an octet group:
            //
            // c) An octet group is formed by using some extension mechanism. The preferred extension
            // mechanism is to extend an octet (N) through the next octet(s) (Na, Nb, etc.) by using bit 8 in
            // each octet as an extension bit. The bit value "0" indicates that the octet continues through the
            // next octet. The bit value "1" indicates that this octet is the last octet. If one octet (Nb) is
            // present, also the preceding octets (N and Na) must be present.
            // In the format descriptions appearing in 4.5.5 etc., bit 8 is marked "0/1 ext." if another octet
            // follows. Bit 8 is marked "1 ext." if this is the last octet in the extension domain.
            // Additional octets may be defined later ("1 ext." changed to "0/1 ext.") and equipments shall
            // be prepared to receive such additional octets although the equipment need not be able to
            // interpret or act upon the content of these octets.

            // TODO: currently we are not able to parse later defined extensions, instead we throw ParseException

            // 5a
            if ((v & 0x80) == 0) {
                v = b[index++];
                this.syncMode = (v >> 6) & 0x01;
                this.negotiation = (v >> 5) & 0x01;
                this.userRate = v & 0x1F;
            } else if (shouldHave5a) {
                throw new ParameterException("Octet 5a should be present for L1 protocol 0x"
                        + Integer.toHexString(this.l1UserInformation));
            }

            // 5b
            if ((v & 0x80) == 0) {
                v = b[index++];
                if (!skipInterpretationOf5b5c5d) {
                    switch (this.l1UserInformation) {
                        case _L1_ITUT_110:
                            this.intermediateRate = (v >> 5) & 0x3;
                            this.nicOnTx = (v >> 4) & 0x1;
                            this.nicOnRx = (v >> 3) & 0x1;
                            this.fcOnTx = (v >> 2) & 0x1;
                            this.fcOnRx = (v >> 1) & 0x1;
                            break;
                        case _L1_ITUT_120:
                            this.hdr = (v >> 6) & 0x01;
                            this.multiframe = (v >> 5) & 0x01;
                            this.mode = (v >> 4) & 0x01;
                            this.lli = (v >> 3) & 0x01;
                            this.assignor = (v >> 2) & 0x01;
                            this.inBandNegotiation = (v >> 1) & 0x01;
                            break;
                        default:
                            // No explicit skip, but the contents are not defined by the standard...
                            break;
                    }
                }
            } else if (shouldHave5b) {
                throw new ParameterException("Octet 5b should be present for L1 protocol 0x"
                        + Integer.toHexString(this.l1UserInformation));
            }

            // 5c
            if ((v & 0x80) == 0) {
                v = b[index++];
                if (!skipInterpretationOf5b5c5d) {
                    this.stopBits = (v >> 5) & 0x03;
                    this.dataBits = (v >> 3) & 0x03;
                    this.parity = v & 0x07;
                }
            }

            // 5d
            if ((v & 0x80) == 0) {
                v = b[index++];
                if (!skipInterpretationOf5b5c5d) {
                    this.duplexMode = (v >> 6) & 0x1;
                    this.modemType = v & 0x3F;
                }
            }

            if ((v & 0x80) == 0) { // there mustn't be any more extension to L1 info
                throw new ParameterException("Extension bit must be 1 at the last octet of group 5 (index " + index
                        + ", end of L1 information)");
            }

        }

        // octet 6, optional
        if (index < b.length && _LAYER2_IDENTIFIER == ((b[index] >> 5) & 0x03)) {
            v = b[index++];
            this.l2UserInformation = v & 0x1F;

            if ((v & 0x80) == 0) { // there mustn't be any more extension to L2 info
                throw new ParameterException("Extension bit must be 1 at octet 6 (end of L2 information)");
            }
        }

        // octet 7, optional
        if (index < b.length && _LAYER3_IDENTIFIER == ((b[index] >> 5) & 0x03)) {
            v = b[index++];
            this.l3UserInformation = v & 0x1F;
            // NOTE 5 This octet may be included if octet 7 indicates ISO/IEC TR 9577 (Protocol Identification in the
            // network layer).
            if ((v & 0x80) == 0) {
                if (this.l3UserInformation == _L3_ISO_9577) {
                    if (index != b.length - 2) {
                        throw new ParameterException(
                                "Expected 2 more octets (7a and 7b), but remaining octet count is "
                                        + (b.length - index));
                    }
                    // check 2 next bytes
                    v = b[index++];
                    if ((v & 0x80) != 0) { // extension bit must be 0
                        throw new ParameterException("Extension bit must be 0 in octet 7a");
                    }
                    this.l3Protocol = (v & 0x0F) << 4;

                    v = b[index++];
                    if ((v & 0x80) == 0) { // extension bit must be 1
                        throw new ParameterException("Extension bit must be 1 in octet 7b");
                    }
                    this.l3Protocol |= v & 0x0F;

                } else {
                    throw new ParameterException(
                            "Extension indication in octet 7 only allowed if L3 user information indicates ISO/IEC TR 9577");
                }
            }
        }

        if (index < b.length) {
            throw new ParameterException("Invalid trailing octets found at index " + index);
        }

        return 0;
    }

    public byte[] encode() throws ParameterException {
        if (octets != null) {
            return Arrays.copyOf(octets, octets.length);
        }

        int byteLength = 2;
        if (this.informationTransferRate == _ITR_MULTIRATE)
            byteLength++; // octet 4.1

        // octets 5,5a-5d
        if (this.l1UserInformation != null) {
            // previous octets from a group cannot be missing,
            // e.g. if 5c is present, then 5, 5a and 5b must also be present.
            byteLength +=
                    isOctetDataPresent_L1_5d() ? 5 :
                    isOctetDataPresent_L1_5c() ? 4 :
                    isOctetDataPresent_L1_5b() ? 3 :
                    isOctetDataPresent_L1_5a() ? 2 : 1;
        }

        // octet 6
        if (this.l2UserInformation != null)
            byteLength++;

        // octets 7,7a-7b
        if (this.l3UserInformation != null) {
            byteLength++;
            if (this.l3UserInformation == _L3_ISO_9577 && this.l3Protocol != null)
                byteLength += 2;
        }

        byte[] b = new byte[byteLength];

        b[0] |= 0x80;
        b[0] |= (this.codingStandard & 0x3) << 5;
        b[0] |= (informationTransferCapability & 0x1f);

        b[1] |= 0x80;
        b[1] |= (this.transferMode & 0x3) << 5;
        b[1] |= (informationTransferRate & 0x1f);

        byteLength = 2;
        if (this.informationTransferRate == _ITR_MULTIRATE) {
            b[byteLength] |= 0x80;
            b[byteLength++] |= rateMultiplier;
        }

        if (this.l1UserInformation != null) {
            b[byteLength] |= _LAYER1_IDENTIFIER << 5;
            b[byteLength++] |= l1UserInformation & 0x1f;

            if (isOctetDataPresent_L1_5a()) {
                b[byteLength] |= this.syncMode << 6;
                b[byteLength] |= this.negotiation << 5;
                b[byteLength++] |= this.userRate;
            } else if (isOctetDataPresent_L1_5b() || isOctetDataPresent_L1_5c() || isOctetDataPresent_L1_5d()) {
                // leave octet empty, but advance for next octets
                byteLength++;
            }

            if (isOctetDataPresent_L1_5b_V110()) {
                b[byteLength] |= this.intermediateRate << 5;
                b[byteLength] |= this.nicOnTx << 4;
                b[byteLength] |= this.nicOnRx << 3;
                b[byteLength] |= this.fcOnTx << 2;
                b[byteLength++] |= this.fcOnRx << 1;
            } else if (isOctetDataPresent_L1_5b_V120()) {
                b[byteLength] |= this.hdr << 6;
                b[byteLength] |= this.multiframe << 5;
                b[byteLength] |= this.mode << 4;
                b[byteLength] |= this.lli << 3;
                b[byteLength] |= this.assignor << 3;
                b[byteLength++] |= this.inBandNegotiation << 1;
            } else if (isOctetDataPresent_L1_5c() || isOctetDataPresent_L1_5d()) {
                // leave octet empty, but advance for next octets
                byteLength++;
            }

            if (isOctetDataPresent_L1_5c()) {
                b[byteLength] |= this.stopBits << 5;
                b[byteLength] |= this.dataBits << 3;
                b[byteLength++] |= this.parity;
            } else if (isOctetDataPresent_L1_5d()) {
                // leave octet empty, but advance for next octets
                byteLength++;
            }

            if (isOctetDataPresent_L1_5d()) {
                b[byteLength] |= this.duplexMode << 6;
                b[byteLength++] |= this.modemType;
            }

            b[byteLength - 1] |= 0x80; // end of extensions
        }

        if (this.l2UserInformation != null) {
            b[byteLength] |= 0x80;
            b[byteLength] |= _LAYER2_IDENTIFIER << 5;
            b[byteLength++] |= l2UserInformation & 0x1f;
        }

        if (this.l3UserInformation != null) {
            b[byteLength] |= _LAYER3_IDENTIFIER << 5;
            b[byteLength++] |= l3UserInformation & 0x1f;

            if (this.l3UserInformation == _L3_ISO_9577 && this.l3Protocol != null) {
                b[byteLength++] |= (this.l3Protocol >> 4) & 0x0F;

                b[byteLength++] |= this.l3Protocol & 0x0F;
            }

            b[byteLength - 1] |= 0x80; // end of extensions
        }

        this.octets = b;
        return Arrays.copyOf(octets, octets.length);
    }

    public UserServiceInformationBaseImpl() {
        super();

    }

    public UserServiceInformationBaseImpl(byte[] b) throws ParameterException {
        super();
        this.decode(b);
    }

    public int getCodingStandard() {
        return this.codingStandard;
    }

    public void setCodingStandard(int codingStandard) {
        checkRepresentableInBitCount(codingStandard, 2);
        this.codingStandard = codingStandard;
        this.octets = null;
    }

    public int getInformationTransferCapability() {
        return this.informationTransferCapability;
    }

    public void setInformationTransferCapability(int informationTransferCapability) {
        checkRepresentableInBitCount(informationTransferCapability, 5);
        this.informationTransferCapability = informationTransferCapability;
        this.octets = null;
    }

    public int getTransferMode() {
        return this.transferMode;
    }

    public void setTransferMode(int transferMode) {
        checkRepresentableInBitCount(transferMode, 2);
        this.transferMode = transferMode;
        this.octets = null;
    }

    public int getInformationTransferRate() {
        return this.informationTransferRate;
    }

    public void setInformationTransferRate(int informationTransferRate) {
        checkRepresentableInBitCount(informationTransferRate, 5);
        this.informationTransferRate = informationTransferRate;
        this.octets = null;
    }

    public int getRateMultiplier() {
        return this.rateMultiplier;
    }

    public void setRateMultiplier(int rateMultiplier) {
        checkRepresentableInBitCount(rateMultiplier, 8);
        this.rateMultiplier = rateMultiplier;
        this.octets = null;
    }

    public int getL1UserInformation() {
        return this.l1UserInformation;
    }

    public void setL1UserInformation(int l1UserInformation) {
        checkRepresentableInBitCount(l1UserInformation, 5);
        this.l1UserInformation = l1UserInformation;
        this.octets = null;
    }

    public int getL2UserInformation() {
        return this.l2UserInformation;
    }

    public void setL2UserInformation(int l2UserInformation) {
        checkRepresentableInBitCount(l2UserInformation, 5);
        this.l2UserInformation = l2UserInformation;
        this.octets = null;
    }

    public int getL3UserInformation() {
        return this.l3UserInformation;
    }

    public void setL3UserInformation(int l3UserInformation) {
        checkRepresentableInBitCount(l3UserInformation, 5);
        this.l3UserInformation = l3UserInformation;
        this.octets = null;
    }

    public int getSyncMode() {
        return this.syncMode;
    }

    public void setSyncMode(int syncMode) {
        checkRepresentableInBitCount(syncMode, 1);
        this.syncMode = syncMode;
        this.octets = null;
    }

    public int getNegotiation() {
        return this.negotiation;
    }

    public void setNegotiation(int negotiation) {
        checkRepresentableInBitCount(negotiation, 1);
        this.negotiation = negotiation;
        this.octets = null;
    }

    public int getUserRate() {
        return this.userRate;
    }

    public void setUserRate(int userRate) {
        checkRepresentableInBitCount(userRate, 5);
        this.userRate = userRate;
        this.octets = null;
    }

    public int getIntermediateRate() {
        return this.intermediateRate;
    }

    public void setIntermediateRate(int intermediateRate) {
        checkRepresentableInBitCount(intermediateRate, 2);
        this.intermediateRate = intermediateRate;
        this.octets = null;
    }

    public int getNicOnTx() {
        return this.nicOnTx;
    }

    public void setNicOnTx(int nicOnTx) {
        checkRepresentableInBitCount(nicOnTx, 1);
        this.nicOnTx = nicOnTx;
        this.octets = null;
    }

    public int getNicOnRx() {
        return this.nicOnRx;
    }

    public void setNicOnRx(int nicOnRx) {
        checkRepresentableInBitCount(nicOnRx, 1);
        this.nicOnRx = nicOnRx;
        this.octets = null;
    }

    public int getFlowControlOnTx() {
        return this.fcOnTx;
    }

    public void setFlowControlOnTx(int fcOnTx) {
        checkRepresentableInBitCount(fcOnTx, 1);
        this.fcOnTx = fcOnTx;
        this.octets = null;
    }

    public int getFlowControlOnRx() {
        return this.fcOnRx;
    }

    public void setFlowControlOnRx(int fcOnRx) {
        checkRepresentableInBitCount(fcOnRx, 1);
        this.fcOnRx = fcOnRx;
        this.octets = null;
    }

    public int getHDR() {
        return this.hdr;
    }

    public void setHDR(int hdr) {
        checkRepresentableInBitCount(hdr, 1);
        this.hdr = hdr;
        this.octets = null;
    }

    public int getMultiframe() {
        return this.multiframe;
    }

    public void setMultiframe(int multiframe) {
        checkRepresentableInBitCount(multiframe, 1);
        this.multiframe = multiframe;
        this.octets = null;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        checkRepresentableInBitCount(mode, 1);
        this.mode = mode;
        this.octets = null;
    }

    public int getLLINegotiation() {
        return this.lli;
    }

    public void setLLINegotiation(int lli) {
        checkRepresentableInBitCount(lli, 1);
        this.lli = lli;
        this.octets = null;
    }

    public int getAssignor() {
        return this.assignor;
    }

    public void setAssignor(int assignor) {
        checkRepresentableInBitCount(assignor, 1);
        this.assignor = assignor;
        this.octets = null;
    }

    public int getInBandNegotiation() {
        return this.inBandNegotiation;
    }

    public void setInBandNegotiation(int inBandNegotiation) {
        checkRepresentableInBitCount(inBandNegotiation, 1);
        this.inBandNegotiation = inBandNegotiation;
        this.octets = null;
    }

    public int getStopBits() {
        return this.stopBits;
    }

    public void setStopBits(int stopBits) {
        checkRepresentableInBitCount(stopBits, 2);
        this.stopBits = stopBits;
        this.octets = null;
    }

    public int getDataBits() {
        return this.dataBits;
    }

    public void setDataBits(int dataBits) {
        checkRepresentableInBitCount(dataBits, 2);
        this.dataBits = dataBits;
        this.octets = null;
    }

    public int getParity() {
        return this.parity;
    }

    public void setParity(int parity) {
        checkRepresentableInBitCount(parity, 3);
        this.parity = parity;
        this.octets = null;
    }

    public int getDuplexMode() {
        return this.duplexMode;
    }

    public void setDuplexMode(int duplexMode) {
        checkRepresentableInBitCount(duplexMode, 1);
        this.duplexMode = duplexMode;
        this.octets = null;
    }

    public int getModemType() {
        return this.modemType;
    }

    public void setModemType(int modemType) {
        checkRepresentableInBitCount(modemType, 6);
        this.modemType = modemType;
        this.octets = null;
    }

    public int getL3Protocol() {
        return this.l3Protocol;
    }

    public void setL3Protocol(int l3Protocol) {
        checkRepresentableInBitCount(l3Protocol, 8);
        this.l3Protocol = l3Protocol;
        this.octets = null;
    }

    protected abstract String getPrimitiveName();

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getPrimitiveName());
        sb.append(" [codingStandard=");
        sb.append(codingStandard);
        sb.append(", informationTransferCapability=");
        sb.append(informationTransferCapability);
        sb.append(", transferMode=");
        sb.append(transferMode);
        sb.append(", informationTransferRate=");
        sb.append(informationTransferRate);
        if (rateMultiplier != null) {
            sb.append(", rateMultiplier=");
            sb.append(rateMultiplier);
        }

        if (l1UserInformation != null) {
            sb.append(",\nl1UserInformation=");
            sb.append(l1UserInformation);

            if (isOctetDataPresent_L1_5a()) {
                sb.append(", syncMode=");
                sb.append(syncMode);
                sb.append(", negotiation=");
                sb.append(negotiation);
                sb.append(", userRate=");
                sb.append(userRate);
            }
            if (isOctetDataPresent_L1_5b_V110()) {
                sb.append(", intermediateRate=");
                sb.append(intermediateRate);
                sb.append(", nicOnTx=");
                sb.append(nicOnTx);
                sb.append(", fcOnTx=");
                sb.append(fcOnTx);
            } else if (isOctetDataPresent_L1_5b_V120()) {
                sb.append(", hdr=");
                sb.append(hdr);
                sb.append(", multiframe=");
                sb.append(multiframe);
                sb.append(", mode=");
                sb.append(mode);
                sb.append(", lli=");
                sb.append(lli);
                sb.append(", assignor=");
                sb.append(assignor);
                sb.append(", inBandNegotiation=");
                sb.append(inBandNegotiation);
            }
            if (isOctetDataPresent_L1_5c()) {
                sb.append(", stopBits=");
                sb.append(stopBits);
                sb.append(", dataBits=");
                sb.append(dataBits);
                sb.append(", parity=");
                sb.append(parity);
            }
            if (isOctetDataPresent_L1_5d()) {
                sb.append(", duplexMode=");
                sb.append(duplexMode);
                sb.append(", modemType=");
                sb.append(modemType);
            }
        }

        if (l2UserInformation != null) {
            sb.append(",\nl2UserInformation=");
            sb.append(l2UserInformation);
        }

        if (l3UserInformation != null) {
            sb.append(",\nl3UserInformation=");
            sb.append(l3UserInformation);

            if (l3Protocol != null) {
                sb.append(", l3Protocol=");
                sb.append(l3Protocol);
            }
        }

        if (octets != null) {
            sb.append(",\noctets=");
            for (byte b : octets) {
                sb.append((b >= 0 && b < 16 ? '0' : "") + Integer.toHexString(b & 0xFF));
            }
        }

        sb.append("]");

        return sb.toString();
    }

    private static boolean isAllNullOrAllPresent(Object... values) {
        boolean nulls = values[0] == null;
        for (Object o : values)
            if (nulls != (o == null))
                return false;
        return true;
    }

    private static boolean isAllNull(Object... values) {
        return values[0] == null && isAllNullOrAllPresent(values);
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<UserServiceInformationBaseImpl> ISUP_USER_SERVICE_INFORMATION_BASE_XML = new XMLFormat<UserServiceInformationBaseImpl>(
            UserServiceInformationBaseImpl.class) {

        // NOTE: octets field - if present due to a previous decode() - could be written to XML and used in read()

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                UserServiceInformationBaseImpl userServiceInformation) throws XMLStreamException {

            // Not a real default value, but an invalid int for XmlFormat to have type information
            // setX methods will throw an exception for this value
            final Integer invalidInteger = Integer.MIN_VALUE;

            // mandatory parameters
            userServiceInformation.setCodingStandard(xml.getAttribute(CODING_STANDARD, invalidInteger));
            userServiceInformation.setInformationTransferCapability(xml.getAttribute(INFORMATION_TRANSFER_CAPABILITY,
                    invalidInteger));
            userServiceInformation.setTransferMode(xml.getAttribute(TRANSFER_MODE, invalidInteger));
            userServiceInformation.setInformationTransferRate(xml.getAttribute(INFORMATION_TRANSFER_RATE,
                    invalidInteger));
            if (userServiceInformation.informationTransferRate == _ITR_MULTIRATE) {
                // mandatory in this case
                userServiceInformation.setRateMultiplier(xml.getAttribute(RATE_MULTIPLIER, invalidInteger));
            }

            Integer value;
            // optional parameters

            value = xml.getAttribute(L1_USER_INFORMATION, invalidInteger);
            if (value != invalidInteger) {
                userServiceInformation.setL1UserInformation(value);

                value = xml.getAttribute(SYNC_MODE, invalidInteger);
                if (value != invalidInteger)
                    userServiceInformation.setSyncMode(value);

                value = xml.getAttribute(NEGOTIATION, invalidInteger);
                if (value != invalidInteger)
                    userServiceInformation.setNegotiation(value);

                value = xml.getAttribute(USER_RATE, invalidInteger);
                if (value != invalidInteger)
                    userServiceInformation.setUserRate(value);

                if (!isAllNullOrAllPresent(userServiceInformation.syncMode, userServiceInformation.negotiation,
                        userServiceInformation.userRate)) {
                    throw new XMLStreamException("Either all or none of the following attributes must be present: "
                            + SYNC_MODE + ", " + NEGOTIATION + ", " + USER_RATE);
                }

                if (userServiceInformation.l1UserInformation == _L1_ITUT_110) {
                    value = xml.getAttribute(INTERMEDIATE_RATE, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setIntermediateRate(value);

                    value = xml.getAttribute(NIC_ON_TX, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setNicOnTx(value);

                    value = xml.getAttribute(NIC_ON_RX, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setNicOnRx(value);

                    value = xml.getAttribute(FC_ON_TX, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setFlowControlOnTx(value);

                    value = xml.getAttribute(FC_ON_RX, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setFlowControlOnRx(value);

                    if (!isAllNullOrAllPresent(userServiceInformation.intermediateRate, userServiceInformation.nicOnTx,
                            userServiceInformation.nicOnRx, userServiceInformation.fcOnTx,
                            userServiceInformation.fcOnRx)) {
                        throw new XMLStreamException(
                                "Either all or none of the following attributes must be present if L1 protocol is V.110: "
                                        + INTERMEDIATE_RATE + ", " + NIC_ON_TX + ", " + NIC_ON_RX + ", " + FC_ON_TX
                                        + ", " + FC_ON_RX);
                    } else if (!isAllNull(userServiceInformation.hdr, userServiceInformation.multiframe,
                            userServiceInformation.mode, userServiceInformation.lli, userServiceInformation.assignor,
                            userServiceInformation.inBandNegotiation)) {
                        throw new XMLStreamException(
                                "None of the following V.120 attributes must be present if L1 protocol is V.110: "
                                        + HDR + ", " + MULTIFRAME + ", " + MODE + ", " + LLI + ", " + ASSIGNOR + ", "
                                        + IN_BAND_NEGOTIATION);
                    }
                } else if (userServiceInformation.l1UserInformation == _L1_ITUT_120) {
                    value = xml.getAttribute(HDR, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setHDR(value);

                    value = xml.getAttribute(MULTIFRAME, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setMultiframe(value);

                    value = xml.getAttribute(MODE, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setMode(value);

                    value = xml.getAttribute(LLI, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setLLINegotiation(value);

                    value = xml.getAttribute(ASSIGNOR, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setAssignor(value);

                    value = xml.getAttribute(IN_BAND_NEGOTIATION, invalidInteger);
                    if (value != invalidInteger)
                        userServiceInformation.setInBandNegotiation(value);

                    if (!isAllNullOrAllPresent(userServiceInformation.hdr, userServiceInformation.multiframe,
                            userServiceInformation.mode, userServiceInformation.lli, userServiceInformation.assignor,
                            userServiceInformation.inBandNegotiation)) {
                        throw new XMLStreamException(
                                "Either all or none of the following attributes must be present if L1 protocol is V.120: "
                                        + HDR + ", " + MULTIFRAME + ", " + MODE + ", " + LLI + ", " + ASSIGNOR + ", "
                                        + IN_BAND_NEGOTIATION);
                    } else if (!isAllNull(userServiceInformation.intermediateRate, userServiceInformation.nicOnTx,
                            userServiceInformation.nicOnRx, userServiceInformation.fcOnTx,
                            userServiceInformation.fcOnRx)) {
                        throw new XMLStreamException(
                                "None of the following V.110 attributes must be present if L1 protocol is V.120: "
                                        + INTERMEDIATE_RATE + ", " + NIC_ON_TX + ", " + NIC_ON_RX + ", " + FC_ON_TX
                                        + ", " + FC_ON_RX);
                    }
                } else if (!isAllNull(userServiceInformation.intermediateRate, userServiceInformation.nicOnTx,
                        userServiceInformation.nicOnRx, userServiceInformation.fcOnTx, userServiceInformation.fcOnRx,
                        userServiceInformation.hdr, userServiceInformation.multiframe, userServiceInformation.mode,
                        userServiceInformation.lli, userServiceInformation.assignor,
                        userServiceInformation.inBandNegotiation)) {
                    throw new XMLStreamException(
                            "None of the following attributes can be present if L1 protocol is not V.110 or V.120: "
                                    + INTERMEDIATE_RATE + ", " + NIC_ON_TX + ", " + NIC_ON_RX + ", " + FC_ON_TX + ", "
                                    + FC_ON_RX + ", " + HDR + ", " + MULTIFRAME + ", " + MODE + ", " + LLI + ", "
                                    + ASSIGNOR + ", " + IN_BAND_NEGOTIATION);
                }
            }

            value = xml.getAttribute(STOP_BITS, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setStopBits(value);

            value = xml.getAttribute(DATA_BITS, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setDataBits(value);

            value = xml.getAttribute(PARITY, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setParity(value);

            if (!isAllNullOrAllPresent(userServiceInformation.stopBits, userServiceInformation.dataBits,
                    userServiceInformation.parity)) {
                throw new XMLStreamException("Either all or none of the following attributes must be present: "
                        + STOP_BITS + ", " + DATA_BITS + ", " + PARITY);
            }

            value = xml.getAttribute(DUPLEX_MODE, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setDuplexMode(value);

            value = xml.getAttribute(MODEM_TYPE, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setModemType(value);

            if (!isAllNullOrAllPresent(userServiceInformation.duplexMode, userServiceInformation.modemType)) {
                throw new XMLStreamException("Either all or none of the following attributes must be present: "
                        + DUPLEX_MODE + ", " + MODEM_TYPE);
            }

            value = xml.getAttribute(L2_USER_INFORMATION, invalidInteger);
            if (value != invalidInteger)
                userServiceInformation.setL2UserInformation(value);

            value = xml.getAttribute(L3_USER_INFORMATION, invalidInteger);
            if (value != invalidInteger) {
                userServiceInformation.setL3UserInformation(value);

                value = xml.getAttribute(L3_PROTOCOL, invalidInteger);
                if (value != invalidInteger)
                    userServiceInformation.setL3Protocol(value);
            }
        }

        @Override
        public void write(UserServiceInformationBaseImpl userServiceInformation,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            xml.setAttribute(CODING_STANDARD, userServiceInformation.codingStandard);
            xml.setAttribute(INFORMATION_TRANSFER_CAPABILITY, userServiceInformation.informationTransferCapability);
            xml.setAttribute(TRANSFER_MODE, userServiceInformation.transferMode);
            xml.setAttribute(INFORMATION_TRANSFER_RATE, userServiceInformation.informationTransferRate);

            if (userServiceInformation.informationTransferRate == _ITR_MULTIRATE)
                xml.setAttribute(RATE_MULTIPLIER, userServiceInformation.rateMultiplier);

            if (userServiceInformation.l1UserInformation != null) {
                xml.setAttribute(L1_USER_INFORMATION, userServiceInformation.l1UserInformation);
                xml.setAttribute(SYNC_MODE, userServiceInformation.syncMode);
                xml.setAttribute(NEGOTIATION, userServiceInformation.negotiation);
                xml.setAttribute(USER_RATE, userServiceInformation.userRate);
                if (userServiceInformation.l1UserInformation == _L1_ITUT_110) {
                    xml.setAttribute(INTERMEDIATE_RATE, userServiceInformation.intermediateRate);
                    xml.setAttribute(NIC_ON_TX, userServiceInformation.nicOnTx);
                    xml.setAttribute(NIC_ON_RX, userServiceInformation.nicOnRx);
                    xml.setAttribute(FC_ON_TX, userServiceInformation.fcOnTx);
                    xml.setAttribute(FC_ON_RX, userServiceInformation.fcOnRx);
                } else if (userServiceInformation.l1UserInformation == _L1_ITUT_120) {
                    xml.setAttribute(HDR, userServiceInformation.hdr);
                    xml.setAttribute(MULTIFRAME, userServiceInformation.multiframe);
                    xml.setAttribute(MODE, userServiceInformation.mode);
                    xml.setAttribute(LLI, userServiceInformation.lli);
                    xml.setAttribute(ASSIGNOR, userServiceInformation.assignor);
                    xml.setAttribute(IN_BAND_NEGOTIATION, userServiceInformation.inBandNegotiation);
                }
                xml.setAttribute(STOP_BITS, userServiceInformation.stopBits);
                xml.setAttribute(DATA_BITS, userServiceInformation.dataBits);
                xml.setAttribute(PARITY, userServiceInformation.parity);
                xml.setAttribute(DUPLEX_MODE, userServiceInformation.duplexMode);
                xml.setAttribute(MODEM_TYPE, userServiceInformation.modemType);
            }

            if (userServiceInformation.l2UserInformation != null)
                xml.setAttribute(L2_USER_INFORMATION, userServiceInformation.l2UserInformation);

            if (userServiceInformation.l3UserInformation != null) {
                xml.setAttribute(L3_USER_INFORMATION, userServiceInformation.l3UserInformation);
                xml.setAttribute(L3_PROTOCOL, userServiceInformation.l3Protocol);
            }
        }
    };

}
