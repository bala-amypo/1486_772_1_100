Test result

lower bounds: com.example.demo.model.DocumentType
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[355,10] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[363,11] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[371,10] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[380,10] prePersist() has protected access
[INFO] 5 errors
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 15.302 s
[INFO] Finished at: 2025-12-25T14:27:36Z
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:testCompile (default-testCompile) on project demo
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[329,37] incompatible types: inference variable T
[ERROR] equality constraints: com.example.demo.model.VendorDocument
[ERROR] lower bounds: com.example.demo.model.DocumentType
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[355,10] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[363,11] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[371,10] prePersist() has protected access
[ERROR] /home/coder/Workspace/demo/src/test/java/com/example/demo/VendorComplianceApplicationTests.java:[380,10] prePersist() has protected access
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
[INFO]
[INFO]