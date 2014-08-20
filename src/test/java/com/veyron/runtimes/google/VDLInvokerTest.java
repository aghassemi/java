
package com.veyron.runtimes.google;

import java.util.Arrays;

import junit.framework.TestCase;

import com.veyron.testing.TestUtil;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.ServerContext;
import com.veyron2.ipc.VeyronException;
import com.veyron2.security.Label;
import com.veyron2.vdl.Stream;
import com.veyron2.vdl.test_base.CompComp;
import com.veyron2.vdl.test_base.Composites;
import com.veyron2.vdl.test_base.Scalars;
import com.veyron2.vdl.test_base.ServiceAService;
import com.veyron2.vdl.test_base.ServiceBService;

public class VDLInvokerTest extends TestCase {

    private static class TestServiceImpl implements ServiceAService, ServiceBService {

        @Override
        public void methodA1(ServerContext context) throws VeyronException {

        }

        @Override
        public String methodA2(ServerContext context, int a, String b) throws VeyronException {
            return "Args: " + a + ", " + b;
        }

        @Override
        public String methodA3(ServerContext context, int a, Stream<Void, Scalars> stream)
                throws VeyronException {
            return "a3String";
        }

        @Override
        public void methodA4(ServerContext context, int a, Stream<Integer, String> stream)
                throws VeyronException {

        }

        @Override
        public CompComp methodB1(ServerContext context, Scalars a, Composites b)
                throws VeyronException {
            return null;
        }

    }

    public void testGetImplementedServices() throws IllegalArgumentException, VeyronException {
        String[] expectedImplementedServices = new String[] {
                "veyron2/vdl/test_base/ServiceAService",
                "veyron2/vdl/test_base/ServiceBService"
        };

        VDLInvoker invoker = new VDLInvoker(new TestServiceImpl());
        String[] implementedServices = invoker.getImplementedServices();
        Arrays.sort(implementedServices);
        TestUtil.assertArrayEquals(expectedImplementedServices, implementedServices);
    }

    public void testGetSecurityLabel() throws IllegalArgumentException, VeyronException {
        VDLInvoker invoker = new VDLInvoker(new TestServiceImpl());
        Label label = invoker.getSecurityLabel("methodA1");
        assertEquals(com.veyron2.security.SecurityConstants.ADMIN_LABEL, label);
    }

    public void testInvoke() throws VeyronException, IllegalArgumentException,
            IllegalAccessException {
        VDLInvoker invoker = new VDLInvoker(new TestServiceImpl());
        String[] args = new String[] {
                "4", "Test"
        };
        ServerCall call = null;
        com.veyron.runtimes.google.VDLInvoker.InvokeReply reply = invoker.invoke("methodA2", call, args);
        assertEquals(1, reply.results.length);
        assertEquals("\"Args: 4, Test\"", reply.results[0]);
        assertEquals(false, reply.hasApplicationError);
        assertEquals(null, reply.errorID);
        assertEquals(null, reply.errorMsg);
    }

}