/*
 * (C) Copyright 2006-2010 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     bstefanescu
 */
package org.nuxeo.runtime.start;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.eclipse.equinox.http.jetty.JettyConfigurator;
import org.nuxeo.common.jndi.NamingContextFactory;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.api.ServiceLocatorFactory;
import org.nuxeo.runtime.jtajca.NuxeoContainer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

/**
 * This bundle should be put in a startlevel superior than the one used to start nuxeo bundles.
 * When the bundle is started it will send an application started notification.
 *
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 *
 */
public class StartApplication implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        startRuntime();
        startContainer();
        startWebServices();
        //((OSGiRuntimeService)Framework.getRuntime()).fireApplicationStarted();
    }

    private void startRuntime() throws BundleException {
        FrameworkUtil.getBundle(Framework.class).start();
    }

    private void startContainer() throws NamingException {
        NamingContextFactory.install();
        NuxeoContainer.install();
    }

    private void startWebServices() throws BundleException {
        FrameworkUtil.getBundle(JettyConfigurator.class).start();
    }


    public void stop(BundleContext context) throws Exception {
        
    }
}