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
 * Start time:14:56:41 2009-04-20<br>
 * Project: mobicents-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski
 *         </a>
 *
 */
package org.mobicents.protocols.ss7.isup.impl.message.parameter;

import java.io.ByteArrayOutputStream;

import org.mobicents.protocols.ss7.isup.ParameterException;
import org.mobicents.protocols.ss7.isup.message.parameter.MessageType;

/**
 * Start time:14:56:41 2009-04-20<br>
 * Project: mobicents-isup-stack<br>
 *
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 *
 */
public class MessageTypeImpl extends AbstractISUPParameter implements MessageType {

    // we even cant use -1, since it may be avlid value, ech, those binary protocols.
    private int code;

    public MessageTypeImpl(byte[] code) throws ParameterException {
        super();
        this.decode(code);
    }

    public MessageTypeImpl(int code) {
        super();
        this.code = code;
    }

    public int decode(byte[] b) throws ParameterException {
        if (b == null || b.length != 1)
            throw new ParameterException();
        return 1;
    }

    public byte[] encode() throws ParameterException {
        return new byte[] { (byte) this.code };
    }

    public int encode(ByteArrayOutputStream bos) throws ParameterException {
        bos.write(this.code);
        return 1;
    }

    public int getCode() {

        return code;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    public String toString() {

        return super.toString() + "-" + this.code;
    }

}
