package soot.dexpler.instructions;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2018 Manuel Benz
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import soot.ModulePathSourceLocator;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.SootMethodRef;
import soot.UnitPatchingChain;
import soot.dexpler.tags.SpecialInvokeTypeTag;
import soot.jimple.*;
import soot.options.Options;
import soot.testing.framework.AbstractTestingFramework;
import soot.jimple.SpecialInvokeExpr;
import soot.options.Options;
import soot.testing.framework.AbstractTestingFramework;
import soot.dexpler.tags.SpecialInvokeTypeTag;
/**
 * @author Manuel Benz created on 22.10.18
 */

public class DexByteCodeInstrutionsTest extends AbstractTestingFramework {

  private static final String METHOD_HANDLE_CLASS = "java.lang.invoke.MethodHandle";
//  private static final String TARGET_CLASS = "soot.dexpler.instructions.DexBytecodeTarget";
  private static final String TARGET_CLASS = "com.hihonor.systemmanager.pluginsdk.wifisecure.WifiSecurePluginHelper";
  private static final String METHOD_HANDLE_INVOKE_SUBSIG = "java.lang.Object invoke(java.lang.Object[])";
  private static final String SUPPLIER_GET_SUBSIG = "java.util.function.Supplier get()";

  @Override
  protected void setupSoot() {
    super.setupSoot();
    Options.v().set_src_prec(Options.src_prec_apk);
    // to get the basic classes; java.lang.Object, java.lang.Throwable, ... we add the rt.jar to the classpath
    String rtJar = "";
    if (Scene.isJavaGEQ9(System.getProperty("java.version"))) {
      rtJar = ModulePathSourceLocator.DUMMY_CLASSPATH_JDK9_FS;
    } else {
      rtJar = System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar";

    }

    Options.v().set_process_dir(Arrays.asList(targetDexPath(), rtJar));
    Options.v().set_force_android_jar(androidJarPath());
    Options.v().set_android_api_version(26);
  }

  @Override
  protected void runSoot() {
    // we do not want to have a call graph for this test
  }

  private String androidJarPath() {
    // this is not the nicest thing. Make sure to keep the version in sync with the pom
    // also .m2 repository could fail
//    return System.getProperty("user.home") + "/.m2/repository/" + "com/google/android/android/4.1.1.4/android-4.1.1.4.jar";
    return System.getProperty("user.home") + "/workspace/androidinfotool/sdk/android34.jar";
  }

  private String targetDexPath() {
    final URL targetDex = getClass().getResource("dexBytecodeTarget.dex");
//    final URL targetDex = getClass().getResource("classes2.dex");
    try {
      return targetDex.toURI().getPath();
    } catch (URISyntaxException e) {
      throw new RuntimeException("Exception loading test resources", e);
    }
  }
//  private String targetDexPath() {
//      String resourcePath = "/soot/dexpler/instructions/dexBytecodeTarget.dex";
//      URL targetDex = getClass().getResource(resourcePath);
//      if (targetDex == null) {
//          targetDex = getClass().getClassLoader().getResource(resourcePath);
//      }
//      if (targetDex == null) {
//          throw new RuntimeException("Could not find dexBytecodeTarget.dex");
//      }
//      System.out.println("Found dex file at: " + targetDex);
//      try {
//          return targetDex.toURI().getPath();
//      } catch (URISyntaxException e) {
//          throw new RuntimeException("Exception loading test resources", e);
//      }
//  }
//  @Test
//  public void InvokePolymorphic1() {
//    final SootMethod testTarget = prepareTarget(
//        methodSigFromComponents(TARGET_CLASS, "void invokePolymorphicTarget(java.lang.invoke.MethodHandle)"), TARGET_CLASS);
//
//    // We model invokePolymorphic as invokeVirtual
//    final List<InvokeExpr> invokes = invokesFromMethod(testTarget);
//    Assert.assertEquals(1, invokes.size());
//    final InvokeExpr invokePoly = invokes.get(0);
//    Assert.assertTrue(invokePoly instanceof VirtualInvokeExpr);
//    final SootMethodRef targetMethodRef = invokePoly.getMethodRef();
//    Assert.assertEquals(methodSigFromComponents(METHOD_HANDLE_CLASS, METHOD_HANDLE_INVOKE_SUBSIG),
//        targetMethodRef.getSignature());
//  }

//  @Test
//  public void InvokeCustom1() {
//    final SootMethod testTarget
//        = prepareTarget(methodSigFromComponents(TARGET_CLASS, "void invokeCustomTarget()"), TARGET_CLASS);
//
//    // We model invokeCustom as invokeDynamic
//    final List<InvokeExpr> invokes = invokesFromMethod(testTarget);
//    Assert.assertEquals(1, invokes.size());
//    final InvokeExpr invokeCustom = invokes.get(0);
//    Assert.assertTrue(invokeCustom instanceof DynamicInvokeExpr);
//    final SootMethodRef targetMethodRef = invokeCustom.getMethodRef();
//    Assert.assertEquals(methodSigFromComponents(SootClass.INVOKEDYNAMIC_DUMMY_CLASS_NAME, SUPPLIER_GET_SUBSIG),
//        targetMethodRef.getSignature());
//    final String callToLambdaMethaFactory
//        = "dynamicinvoke \"get\" <java.util.function.Supplier ()>() <java.lang.invoke.LambdaMetafactory: java.lang.invoke.CallSite metafactory(java.lang.invoke.MethodHandles$Lookup,java.lang.String,java.lang.invoke.MethodType,java.lang.invoke.MethodType,java.lang.invoke.MethodHandle,java.lang.invoke.MethodType)>(methodtype: java.lang.Object __METHODTYPE__(), methodhandle: \"REF_INVOKE_STATIC\" <soot.dexpler.instructions.DexBytecodeTarget: java.lang.String lambda$invokeCustomTarget$0()>, methodtype: java.lang.String __METHODTYPE__())";
//    Assert.assertEquals(callToLambdaMethaFactory, invokeCustom.toString());
//  }
//
  private List<InvokeExpr> invokesFromMethod(SootMethod testTarget) {
    final UnitPatchingChain units = testTarget.retrieveActiveBody().getUnits();
    return units.stream().filter(u -> ((Stmt) u).containsInvokeExpr()).map(u -> ((Stmt) u).getInvokeExpr())
        .collect(Collectors.toList());
  }
  @Test
  public void InvokeSuperQuick1() {
    final SootMethod testTarget
        = prepareTarget(methodSigFromComponents(TARGET_CLASS, "java.util.List getAllEngine()"), TARGET_CLASS);

    // We model invoke-super-quick as invokeSpecial with SUPER tag
    final List<InvokeExpr> invokes = invokesFromMethod(testTarget);
    Assert.assertEquals(1, invokes.size());
    final InvokeExpr invokeSuperQuick = invokes.get(0);
    Assert.assertTrue(invokeSuperQuick instanceof SpecialInvokeExpr);

    // Verify the instruction has the SUPER tag
Stmt stmt = invokes.stream()
    .map(expr -> testTarget.retrieveActiveBody().getUnits().stream()
        .filter(u -> u instanceof Stmt)
        .map(u -> (Stmt) u)
        .filter(s -> s.containsInvokeExpr() && s.getInvokeExpr() == expr)
        .findFirst().orElse(null))
    .filter(s -> s != null)
    .findFirst().orElseThrow(() -> new AssertionError("No matching Stmt found"));

    Assert.assertTrue(stmt.hasTag("SpecialInvokeTypeTag"));
    SpecialInvokeTypeTag tag = (SpecialInvokeTypeTag) stmt.getTag("SpecialInvokeTypeTag");
    Assert.assertEquals(SpecialInvokeTypeTag.Type.SUPER, tag.getType());
  }
}
