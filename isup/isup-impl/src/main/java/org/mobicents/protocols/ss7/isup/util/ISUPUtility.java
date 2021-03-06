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

/**
 * Start time:16:56:29 2009-07-17<br>
 * Project: mobicents-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com">Bartosz Baranowski </a>
 *
 */
package org.mobicents.protocols.ss7.isup.util;

import org.mobicents.protocols.ss7.isup.impl.message.ISUPMessageFactoryImpl;
import org.mobicents.protocols.ss7.isup.impl.message.parameter.ISUPParameterFactoryImpl;

/**
 * Start time:16:56:29 2009-07-17<br>
 * Project: mobicents-isup-stack<br>
 * Small class with some utility methods to work on raw without stack.
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class ISUPUtility {

    private static final ISUPParameterFactoryImpl parameterFactory = new ISUPParameterFactoryImpl();
    private static final ISUPMessageFactoryImpl messageFactory = new ISUPMessageFactoryImpl(parameterFactory);

    public static String toHex(byte[] b) {

        String out = "";

        for (int index = 0; index < b.length; index++) {

            // FIXME: is this really the intended behavior? This outputs lines like: b[0][fffffffd]
            // For output like b[0][fd] with only 2 hex digits per byte, b[index] & 0xFF should be used
            // (though values < 0x10 will only be one digit then).
            // Also, a StringBuilder would be nice
            out += "b[" + index + "][" + Integer.toHexString(b[index]) + "]\n";

            // out+="\n";
        }

        return out;

    }

    /** Produces a hex stream where bytes are separated by <code>delimiter</code>, and optionally prefixed with "0x" if <code>prefixEachValue</code> is <code>true</code>.*/
    public static String toHexStream(byte[] bytes, String delimiter, boolean prefixEachValue, boolean upperCase) {
        StringBuilder sb = new StringBuilder();
        appendHexStream(sb, bytes, delimiter, prefixEachValue, upperCase);
        return sb.toString();
    }

    /** Same as {@link #toHexStream(byte[], String, boolean, boolean)}, but appends to an existing StringBuilder. */
    public static void appendHexStream(StringBuilder sb, byte[] bytes, String delimiter, boolean prefixEachValue, boolean upperCase) {
        sb.ensureCapacity(bytes.length* (prefixEachValue? 4:2) + (bytes.length-1)*(delimiter==null?0: delimiter.length()));
        for(int i=0; i<bytes.length;i++) {
            if(prefixEachValue)
                sb.append("0x");
            char c1 = Character.forDigit((bytes[i] & 0xF0) >> 4, 16);
            char c2 = Character.forDigit((bytes[i] & 0x0F), 16);
            sb.append(upperCase? Character.toUpperCase(c1) : c1);
            sb.append(upperCase? Character.toUpperCase(c2) : c2);
            if (delimiter != null && i < bytes.length - 1)
                sb.append(delimiter);
        }
    }

    //
    // /**
    // * Offset where ISUP data should start in MSU
    // */
    // public static final int MSU_SHIFT = 5;
    // /**
    // * Offset where ISUP parameters should start in MSU
    // */
    // public static final int ISUP_SHIFT = MSU_SHIFT + 3;
    //
    // public static void setCIC(byte[] msu, int cic) {
    // msu[MSU_SHIFT] = (byte) cic;
    // msu[MSU_SHIFT + 1] = (byte) ((cic >> 8) & 0x0F);
    // }
    //
    // public static int getCIC(byte[] msu) {
    // int cic = (msu[MSU_SHIFT] & 0xFF);
    // cic |= ((msu[MSU_SHIFT + 1] & 0x0F) << 8);
    // return cic;
    // }
    //
    // public static void setMessageCode(byte[] msu, int code) {
    // msu[MSU_SHIFT + 2] = (byte) code;
    // }
    //
    // public static int getMessageCode(byte[] msu) {
    // return msu[MSU_SHIFT + 2] & 0xFF;
    // }
    //
    // private static byte[] getRawMessage(byte[] msu) throws IOException {
    // ByteArrayInputStream bis = new ByteArrayInputStream(msu);
    // bis.skip(MSU_SHIFT);
    // byte[] b = new byte[bis.available()];
    // bis.read(b);
    // return b;
    // }
    //
    // public static CircuitGroupBlockingMessage getCircuitGroupBlocking(byte[] msu) throws ParameterException, IOException {
    // byte[] b = getRawMessage(msu);
    // int cic = getCIC(msu);
    // CircuitGroupBlockingMessage msg = messageFactory.createCGB(cic);
    // msg.decode(b);
    // return msg;
    // }
    //
    // public static CircuitGroupBlockingAckMessage getCircuitGroupBlockingAck(byte[] msu) throws ParameterException,
    // IOException {
    // byte[] b = getRawMessage(msu);
    // CircuitGroupBlockingAckMessage msg = messageFactory.createCGBA();
    // msg.decode(b);
    // return msg;
    // }
    //
    // public static CircuitGroupUnblockingMessage getCircuitGroupUnBlocking(byte[] msu) throws ParameterException, IOException
    // {
    // byte[] b = getRawMessage(msu);
    // int cic = getCIC(msu);
    // CircuitGroupUnblockingMessage msg = messageFactory.createCGU(cic);
    // msg.decode(b);
    // return msg;
    // }
    //
    // public static CircuitGroupUnblockingAckMessage getCircuitGroupUnBlockingAck(byte[] msu) throws ParameterException,
    // IOException {
    // byte[] b = getRawMessage(msu);
    // CircuitGroupUnblockingAckMessage msg = messageFactory.createCGUA();
    // msg.decode(b);
    // return msg;
    // }
    //
    // public static CircuitGroupResetMessage getCircuitGroupResetMessage(byte[] msu) throws ParameterException, IOException {
    // byte[] b = getRawMessage(msu);
    // int cic = getCIC(msu);
    // CircuitGroupResetMessage msg = messageFactory.createGRS(cic);
    // msg.decode(b);
    // return msg;
    // }
    //
    // public static CircuitGroupResetAckMessage getCircuitGroupResetAckMessage(byte[] msu) throws ParameterException,
    // IOException {
    // byte[] b = getRawMessage(msu);
    // CircuitGroupResetAckMessage msg = messageFactory.createGRA();
    // msg.decode(b);
    // return msg;
    // }
    //
    // private static final byte[] EMPTY = new byte[MSU_SHIFT];
    //
    // /**
    // * Encodes message to MSU format, first bytes should be over writen with
    // * back routing label.
    // *
    // * @param msg
    // * @return
    // * @throws IOException
    // */
    // public static byte[] encodeToMsuFormat(ISUPMessage msg) throws IOException {
    // ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // bos.write(EMPTY);
    // msg.encode(bos);
    // return bos.toByteArray();
    // }

}
