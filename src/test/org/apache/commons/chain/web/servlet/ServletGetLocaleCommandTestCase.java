/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//chain/src/test/org/apache/commons/chain/web/servlet/ServletGetLocaleCommandTestCase.java,v 1.2 2003/08/12 20:33:25 husted Exp $
 * $Revision: 1.2 $
 * $Date: 2003/08/12 20:33:25 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.chain.web.servlet;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.chain.Context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;


// Test case for org.apache.commons.chain.web.servlet.ServletGetLocaleCommand

public class ServletGetLocaleCommandTestCase extends TestCase {


    // ---------------------------------------------------------- Constructors

    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public ServletGetLocaleCommandTestCase(String name) {
        super(name);
    }


    // ----------------------------------------------------- Instance Variables


    protected Locale locale = null;

    // Servlet API Objects
    protected ServletContext scontext = null;
    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;
    protected HttpSession session = null;

    // Chain API Objects
    protected Context context = null;
    protected ServletGetLocaleCommand command = null;


    // -------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() {

	locale = new Locale("en", "US");

	// Set up Servlet API Objects
        scontext = new MockServletContext();
        session = new MockHttpSession(scontext);
        request = new MockHttpServletRequest("/context", "/servlet",
                                             "/path/info", "a=b&c=d",
                                             session);
	((MockHttpServletRequest) request).setLocale(locale);
        response = new MockHttpServletResponse();

	// Set up Chain API Objects
        context = new ServletWebContext(scontext, request, response);
	command = new ServletGetLocaleCommand();

    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {

        return (new TestSuite(ServletGetLocaleCommandTestCase.class));

    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {

        scontext = null;
        session = null;
        request = null;
        response = null;

        context = null;
	command = null;

    }


    // ------------------------------------------------- Individual Test Methods


    // Test configured behavior
    public void testConfigured() throws Exception {

	command.setLocaleKey("special");
	assertEquals("special", command.getLocaleKey());
	check(context, command);

    }


    // Test default behavior
    public void testDefaut() throws Exception {

	assertEquals("locale", command.getLocaleKey());
	check(context, command);

    }


    // --------------------------------------------------------- Support Methods


    protected void check(Context context, ServletGetLocaleCommand command)
	throws Exception {

	String localeKey = command.getLocaleKey();
	assertNotNull(localeKey);
	Object value = context.getAttributes().get(localeKey);
	assertNull(value);
	boolean result = command.execute(context);
	assertFalse(result);
	value = context.getAttributes().get(localeKey);
	assertNotNull(value);
	assertTrue(value instanceof Locale);
	assertEquals(locale, (Locale) value);

    }


}
