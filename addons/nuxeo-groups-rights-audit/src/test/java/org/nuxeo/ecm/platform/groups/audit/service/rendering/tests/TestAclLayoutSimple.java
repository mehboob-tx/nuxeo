/*
 * (C) Copyright 2006-2013 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Vladimir Pasquier <vpasquier@nuxeo.com>
 */

package org.nuxeo.ecm.platform.groups.audit.service.rendering.tests;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.security.SecurityConstants;
import org.nuxeo.ecm.core.storage.sql.ra.PoolingRepositoryFactory;
import org.nuxeo.ecm.core.test.TransactionalFeature;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.platform.groups.audit.service.acl.AclExcelLayoutBuilder;
import org.nuxeo.ecm.platform.groups.audit.service.acl.IAclExcelLayoutBuilder;
import org.nuxeo.ecm.platform.groups.audit.service.acl.excel.AclNameShortner;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Test excel export of groups
 *
 */
@RunWith(FeaturesRunner.class)
@Features({ TransactionalFeature.class, PlatformFeature.class })
@RepositoryConfig(cleanup = Granularity.METHOD, repositoryFactoryClass = PoolingRepositoryFactory.class)
@Deploy({ "org.nuxeo.ecm.platform.query.api", "nuxeo-groups-rights-audit", "org.nuxeo.ecm.platform.web.common" })
@LocalDeploy({ "nuxeo-groups-rights-audit:OSGI-INF/directory-config.xml",
        "nuxeo-groups-rights-audit:OSGI-INF/schemas-config.xml" })
public class TestAclLayoutSimple extends AbstractAclLayoutTest {
    @Inject
    CoreSession session;

    @Inject
    UserManager userManager;

    protected static File testFile = new File(folder
            + TestAclLayoutSimple.class.getSimpleName() + ".xls");

    @Test
    public void testSimpleExcelExport() throws Exception {
        // groups
        DocumentModel g1 = makeGroup(userManager, "test_g1");
        DocumentModel g2 = makeGroup(userManager, "test_g2");
        List<String> g2Groups = Arrays.asList("test_g1");
        g2.setProperty("group", "subGroups", g2Groups);
        DocumentModel u1 = makeUser(userManager, "test_u1");

        // Set user properties
        u1.setProperty("user", "username", "test_u1");
        u1.setProperty("user", "firstName", "test");
        u1.setProperty("user", "lastName", "_u1");
        u1.setProperty("user", "email", "test@u1");
        // Set user/subgroup/group bindings
        u1.setProperty("user", "groups", Arrays.asList("test_g1"));
        userManager.createUser(u1);
        userManager.createGroup(g1);
        userManager.createGroup(g2);

        // doc tree
        makeFolder(session, "/", "folder1");
        makeFolder(session, "/folder1", "folder1.1");
        makeFolder(session, "/folder1", "folder1.2");
        makeFolder(session, "/folder1/folder1.2", "folder1.2.1");
        makeFolder(session, "/folder1/folder1.2", "folder1.2.2");
        makeFolder(session, "/folder1/folder1.2/folder1.2.1", "folder1.2.1.1");
        makeDoc(session, "/folder1/folder1.2/folder1.2.1", "doc1");
        makeDoc(session, "/folder1/folder1.2/folder1.2.1", "doc2");
        DocumentModel doc121 = makeDoc(session, "/folder1/folder1.2", "doc1");

        // add all possible acl
        addAcl(session, doc121, "test_g1", SecurityConstants.ADD_CHILDREN, true);
        addAcl(session, doc121, "test_g2", SecurityConstants.ADD_CHILDREN,
                false);

        addAcl(session, doc121, "test_u1", SecurityConstants.MANAGE_WORKFLOWS,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ_LIFE_CYCLE,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ_CHILDREN,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ_PROPERTIES,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ_SECURITY,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.READ_VERSION, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.REMOVE, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.REMOVE_CHILDREN,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.RESTRICTED_READ,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.UNLOCK, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.VERSION, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.VIEW_WORKLFOW,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.WRITE, true);
        addAcl(session, doc121, "test_u1", SecurityConstants.WRITE_LIFE_CYCLE,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.WRITE_SECURITY,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.WRITE_VERSION,
                true);
        addAcl(session, doc121, "test_u1", SecurityConstants.WRITE_PROPERTIES,
                true);
        addAcl(session, doc121, "test_u2", SecurityConstants.READ_WRITE, true);
        addAcl(session, doc121, "test_u3", SecurityConstants.EVERYTHING, true);

        // generate XLS report
        IAclExcelLayoutBuilder v = new AclExcelLayoutBuilder();
        v.renderAudit(session);
        v.getExcel().save(testFile);
    }

    @Test
    public void testAclNameShortner() {
        AclNameShortner ans = new AclNameShortner();
        assertEquals("SU", ans.getShortName("Something Unknown"));
        assertEquals("SU1", ans.getShortName("Something Unknowndsds"));
        assertEquals("SU2", ans.getShortName("Somedsasdsathing Unknowndsds"));
        assertEquals("SU", ans.getShortName("Something Unknown"));
        assertEquals("SU2", ans.getShortName("Somedsasdsathing Unknowndsds"));
        assertEquals("R", ans.getShortName(SecurityConstants.READ));
    }
}