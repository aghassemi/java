// This file was auto-generated by the veyron vdl tool.
// Source(s):  advanced.vdl arith.vdl
package com.veyron2.vdl.test_arith;

import com.google.common.reflect.TypeToken;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.vdl.Stream;
import com.veyron2.vdl.test_arith.exp.ExpService;
import com.veyron2.vdl.test_base.Args;
import com.veyron2.vdl.test_base.NestedArgs;

public class Server { 
	/* Server stub creation methods for interfaces in file: advanced.vdl. */
	public static Object newTrigonometry(TrigonometryService service) { 
		return new TrigonometryStub(service);
	}
	public static Object newAdvancedMath(AdvancedMathService service) { 
		final Server.TrigonometryStub trigonometryService = (Server.TrigonometryStub)Server.newTrigonometry(service);
		final com.veyron2.vdl.test_arith.exp.Server.ExpStub expService = (com.veyron2.vdl.test_arith.exp.Server.ExpStub)com.veyron2.vdl.test_arith.exp.Server.newExp(service);
		return new AdvancedMathStub(service, trigonometryService, expService);
	}
	/* Server stub creation methods for interfaces in file: arith.vdl. */
	public static Object newArith(ArithService service) { 
		return new ArithStub(service);
	}
	public static Object newCalculator(CalculatorService service) { 
		final Server.ArithStub arithService = (Server.ArithStub)Server.newArith(service);
		final Server.AdvancedMathStub advancedMathService = (Server.AdvancedMathStub)Server.newAdvancedMath(service);
		return new CalculatorStub(service, arithService, advancedMathService);
	}
	
	/* Server stubs for interfaces in file: advanced.vdl. */
	public static class TrigonometryStub {
		private final TrigonometryService service;

		TrigonometryStub(TrigonometryService service) {
			this.service = service;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			if (method == "Sine") {
				return new Object[]{  };
			}
			if (method == "Cosine") {
				return new Object[]{  };
			}
			return null;
		}
		// Methods from interface Trigonometry.
		public double sine(ServerCall call, double angle) throws VeyronException { 
			return this.service.sine(call, angle);
		}
		public double cosine(ServerCall call, double angle) throws VeyronException { 
			return this.service.cosine(call, angle);
		}
	}
	public static class AdvancedMathStub {
		private final AdvancedMathService service;
		private final Server.TrigonometryStub trigonometryService;
		private final com.veyron2.vdl.test_arith.exp.Server.ExpStub expService;

		AdvancedMathStub(AdvancedMathService service, Server.TrigonometryStub trigonometryService, com.veyron2.vdl.test_arith.exp.Server.ExpStub expService) {
			this.service = service;
			this.trigonometryService = trigonometryService;
			this.expService = expService;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			{
				final Object[] tags = this.trigonometryService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			{
				final Object[] tags = this.expService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			return null;
		}
		// Methods from interface AdvancedMath.
		// Methods from sub-interface Trigonometry.
		public double sine(ServerCall call, double angle) throws VeyronException {
			return this.trigonometryService.sine(call, angle);
		}
		public double cosine(ServerCall call, double angle) throws VeyronException {
			return this.trigonometryService.cosine(call, angle);
		}
		// Methods from sub-interface Exp.
		public double exp(ServerCall call, double x) throws VeyronException {
			return this.expService.exp(call, x);
		}
	}
	/* Server stubs for interfaces in file: arith.vdl. */
	public static class ArithStub {
		private final ArithService service;

		ArithStub(ArithService service) {
			this.service = service;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			if (method == "Add") {
				return new Object[]{  };
			}
			if (method == "DivMod") {
				return new Object[]{  };
			}
			if (method == "Sub") {
				return new Object[]{  };
			}
			if (method == "Mul") {
				return new Object[]{  };
			}
			if (method == "GenError") {
				return new Object[]{ "foo", "barz", "hello", 129, 36 };
			}
			if (method == "Count") {
				return new Object[]{  };
			}
			if (method == "StreamingAdd") {
				return new Object[]{  };
			}
			if (method == "QuoteAny") {
				return new Object[]{  };
			}
			return null;
		}
		// Methods from interface Arith.
		public int add(ServerCall call, int a, int b) throws VeyronException { 
			return this.service.add(call, a, b);
		}
		public ArithService.DivModOut divMod(ServerCall call, int a, int b) throws VeyronException { 
			return this.service.divMod(call, a, b);
		}
		public int sub(ServerCall call, Args args) throws VeyronException { 
			return this.service.sub(call, args);
		}
		public int mul(ServerCall call, NestedArgs nested) throws VeyronException { 
			return this.service.mul(call, nested);
		}
		public void genError(ServerCall call) throws VeyronException { 
			this.service.genError(call);
		}
		public void count(ServerCall call, int Start) throws VeyronException { 
			final ServerCall serverCall = call;
			final Stream<Integer,Void> stream = new Stream<Integer,Void>() {
				@Override
				public void send(Integer item) throws VeyronException {
					serverCall.send(item);
				}
				@Override
				public Void recv() throws java.io.EOFException, VeyronException {
					final TypeToken<?> type = new TypeToken<Void>() {};
					final Object result = serverCall.recv(type);
					try {
						return (Void)result;
					} catch (java.lang.ClassCastException e) {
						throw new VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
					}
				}
			};
			this.service.count(call, Start, stream);
		}
		public int streamingAdd(ServerCall call) throws VeyronException { 
			final ServerCall serverCall = call;
			final Stream<Integer,Integer> stream = new Stream<Integer,Integer>() {
				@Override
				public void send(Integer item) throws VeyronException {
					serverCall.send(item);
				}
				@Override
				public Integer recv() throws java.io.EOFException, VeyronException {
					final TypeToken<?> type = new TypeToken<Integer>() {};
					final Object result = serverCall.recv(type);
					try {
						return (Integer)result;
					} catch (java.lang.ClassCastException e) {
						throw new VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
					}
				}
			};
			return this.service.streamingAdd(call, stream);
		}
		public Object quoteAny(ServerCall call, Object a) throws VeyronException { 
			return this.service.quoteAny(call, a);
		}
	}
	public static class CalculatorStub {
		private final CalculatorService service;
		private final Server.ArithStub arithService;
		private final Server.AdvancedMathStub advancedMathService;

		CalculatorStub(CalculatorService service, Server.ArithStub arithService, Server.AdvancedMathStub advancedMathService) {
			this.service = service;
			this.arithService = arithService;
			this.advancedMathService = advancedMathService;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			{
				final Object[] tags = this.arithService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			{
				final Object[] tags = this.advancedMathService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			if (method == "On") {
				return new Object[]{  };
			}
			if (method == "Off") {
				return new Object[]{ "offtag" };
			}
			return null;
		}
		// Methods from interface Calculator.
		public void on(ServerCall call) throws VeyronException { 
			this.service.on(call);
		}
		public void off(ServerCall call) throws VeyronException { 
			this.service.off(call);
		}
		// Methods from sub-interface Arith.
		public int add(ServerCall call, int a, int b) throws VeyronException {
			return this.arithService.add(call, a, b);
		}
		public ArithService.DivModOut divMod(ServerCall call, int a, int b) throws VeyronException {
			return this.arithService.divMod(call, a, b);
		}
		public int sub(ServerCall call, Args args) throws VeyronException {
			return this.arithService.sub(call, args);
		}
		public int mul(ServerCall call, NestedArgs nested) throws VeyronException {
			return this.arithService.mul(call, nested);
		}
		public void genError(ServerCall call) throws VeyronException {
			this.arithService.genError(call);
		}
		public void count(ServerCall call, int Start) throws VeyronException {
			this.arithService.count(call, Start);
		}
		public int streamingAdd(ServerCall call) throws VeyronException {
			return this.arithService.streamingAdd(call);
		}
		public Object quoteAny(ServerCall call, Object a) throws VeyronException {
			return this.arithService.quoteAny(call, a);
		}
		// Methods from sub-interface AdvancedMath.
		public double sine(ServerCall call, double angle) throws VeyronException {
			return this.advancedMathService.sine(call, angle);
		}
		public double cosine(ServerCall call, double angle) throws VeyronException {
			return this.advancedMathService.cosine(call, angle);
		}
		public double exp(ServerCall call, double x) throws VeyronException {
			return this.advancedMathService.exp(call, x);
		}
	}
}
