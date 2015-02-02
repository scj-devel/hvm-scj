# hvm-scj
The HVM SCJ implementation

Contains the following folders:

- icecapSDK: Includes the pacakge javax which contains the HVM SCJ implementation (Level 0, 1 and 2)
- icecaptools: The HVM Eclipse plugin. This is a compiler that translates java class files into self contained C code
- icecaptoolstest: Contains all the regression tests for the HVM compiler. All tests starting with TestSCJ* tests the SCJ implementation
- icecapvm: This is the HVM runtime system. Contains the HVM Java interpreter and other runtime utilities
