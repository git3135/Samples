import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gwt.dev.asm.ClassReader;
import com.google.gwt.dev.asm.Opcodes;
import com.google.gwt.dev.asm.Type;
import com.google.gwt.dev.asm.tree.ClassNode;
import com.google.gwt.dev.asm.tree.FieldNode;
import com.google.gwt.dev.asm.tree.MethodNode;

public class ClassFinder {

	public static boolean searchJarFile(String jarFilePath, String classFilePath) {
		return searchJarFile(new File(jarFilePath), classFilePath);
	}

	public static String describeMethod(MethodNode methodNode) {
	    StringBuilder methodDescription = new StringBuilder();

	    Type returnType = Type.getReturnType(methodNode.desc);
	    Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);

	    @SuppressWarnings("unchecked")
	    List<String> thrownInternalClassNames = methodNode.exceptions;

	    if ((methodNode.access & Opcodes.ACC_PUBLIC) != 0) {
	        methodDescription.append("public ");
	    }

	    if ((methodNode.access & Opcodes.ACC_PRIVATE) != 0) {
	        methodDescription.append("private ");
	    }

	    if ((methodNode.access & Opcodes.ACC_PROTECTED) != 0) {
	        methodDescription.append("protected ");
	    }

	    if ((methodNode.access & Opcodes.ACC_STATIC) != 0) {
	        methodDescription.append("static ");
	    }

	    if ((methodNode.access & Opcodes.ACC_ABSTRACT) != 0) {
	        methodDescription.append("abstract ");
	    }

	    if ((methodNode.access & Opcodes.ACC_SYNCHRONIZED) != 0) {
	        methodDescription.append("synchronized ");
	    }

	    methodDescription.append(returnType.getClassName());
	    methodDescription.append(" ");
	    methodDescription.append(methodNode.name);

	    methodDescription.append("(");
	    for (int i = 0; i < argumentTypes.length; i++) {
	        Type argumentType = argumentTypes[i];
	        if (i > 0) {
	            methodDescription.append(", ");
	        }
	        methodDescription.append(argumentType.getClassName());
	    }
	    methodDescription.append(")");

	    if (!thrownInternalClassNames.isEmpty()) {
	        methodDescription.append(" throws ");
	        int i = 0;
	        for (String thrownInternalClassName : thrownInternalClassNames) {
	            if (i > 0) {
	                methodDescription.append(", ");
	            }
	            methodDescription.append(Type.getObjectType(thrownInternalClassName).getClassName());
	            i++;
	        }
	    }

	    return methodDescription.toString();
	}
	
	public static String describeField(FieldNode methodNode) {
	    StringBuilder methodDescription = new StringBuilder();

	    Type returnType = Type.getReturnType(methodNode.desc);
	    Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);

	    //@SuppressWarnings("unchecked")
	    //List<String> thrownInternalClassNames = methodNode.exceptions;

	    if ((methodNode.access & Opcodes.ACC_PUBLIC) != 0) {
	        methodDescription.append("public ");
	    }

	    if ((methodNode.access & Opcodes.ACC_PRIVATE) != 0) {
	        methodDescription.append("private ");
	    }

	    if ((methodNode.access & Opcodes.ACC_PROTECTED) != 0) {
	        methodDescription.append("protected ");
	    }

	    if ((methodNode.access & Opcodes.ACC_STATIC) != 0) {
	        methodDescription.append("static ");
	    }

	    if ((methodNode.access & Opcodes.ACC_ABSTRACT) != 0) {
	        methodDescription.append("abstract ");
	    }

	    if ((methodNode.access & Opcodes.ACC_SYNCHRONIZED) != 0) {
	        methodDescription.append("synchronized ");
	    }

	    methodDescription.append(returnType.getClassName());
	    methodDescription.append(" ");
	    methodDescription.append(methodNode.name);

	    methodDescription.append("(");
	    for (int i = 0; i < argumentTypes.length; i++) {
	        Type argumentType = argumentTypes[i];
	        if (i > 0) {
	            methodDescription.append(", ");
	        }
	        methodDescription.append(argumentType.getClassName());
	    }
	    methodDescription.append(")");
/*
	    if (!thrownInternalClassNames.isEmpty()) {
	        methodDescription.append(" throws ");
	        int i = 0;
	        for (String thrownInternalClassName : thrownInternalClassNames) {
	            if (i > 0) {
	                methodDescription.append(", ");
	            }
	            methodDescription.append(Type.getObjectType(thrownInternalClassName).getClassName());
	            i++;
	        }
	    }
*/
	    return methodDescription.toString();
	}
	
	public static String describeClass(ClassNode classNode) {
	    StringBuilder classDescription = new StringBuilder();

	    Type classType = Type.getObjectType(classNode.name);

	    // The class signature (e.g. - "public class Foo")
	    if ((classNode.access & Opcodes.ACC_PUBLIC) != 0) {
	        classDescription.append("public ");
	    }

	    if ((classNode.access & Opcodes.ACC_PRIVATE) != 0) {
	        classDescription.append("private ");
	    }

	    if ((classNode.access & Opcodes.ACC_PROTECTED) != 0) {
	        classDescription.append("protected ");
	    }

	    if ((classNode.access & Opcodes.ACC_ABSTRACT) != 0) {
	        classDescription.append("abstract ");
	    }

	    if ((classNode.access & Opcodes.ACC_INTERFACE) != 0) {
	        classDescription.append("interface ");
	    } else {
	        classDescription.append("class ");
	    }

	    classDescription.append(classType.getClassName()).append("\n");
	    classDescription.append("{\n");
	    
	    String className=classType.getClassName();
	    int pos=className.lastIndexOf(".");
	    if(pos>0) className=className.substring(pos+1);

	    // The method signatures (e.g. - "public static void main(String[]) throws Exception")
	    @SuppressWarnings("unchecked")
	    List<FieldNode> fieldNodes = classNode.fields;
	    //if(fieldNodes.size()>0) {
		    for (FieldNode fieldNode : fieldNodes) {
		    	String fieldDesc = fieldNode.name; //describeField(fieldNode);
		    	Type returnType = Type.getReturnType(fieldNode.desc);
		    	if(returnType!=null) {
		    		String typeName=returnType.getClassName();
		    	    pos=typeName.lastIndexOf(".");
		    	    if(pos>0) typeName=typeName.substring(pos+1);
		    		fieldDesc+=" : "+typeName;
		    	}
		    	//String className = classNode.name;
		    	if(returnType!=null) {
		    		fieldDesc+=" - "+className;
		    	}
		        classDescription.append("\t").append(fieldDesc).append("\n");
		    }
	    //} else {
		    List<MethodNode> methodNodes = classNode.methods;
		    for (MethodNode methodNode : methodNodes) {
		        String methodDesc = describeMethod(methodNode);
		        methodDesc = methodNode.name; //describeField(fieldNode);
		    	Type returnType = Type.getReturnType(methodNode.desc);
		    	if(returnType!=null) {
		    		String typeName=returnType.getClassName();
		    	    pos=typeName.lastIndexOf(".");
		    	    if(pos>0) typeName=typeName.substring(pos+1);
		    		methodDesc+=" : "+typeName;
		    	}
		    	//String className = classNode.name;
		    	if(returnType!=null) {
		    		methodDesc+=" - "+className;
		    	}
		        classDescription.append("\t").append(methodDesc).append("\n");
		    }
	    //}
	    classDescription.append("}\n");
	    return classDescription.toString();
	}	

	private static void process(Object obj) {
		ZipEntry entry = (ZipEntry) obj;
		String name = entry.getName();
		long size = entry.getSize();
		long compressedSize = entry.getCompressedSize();
		System.out.println(name + "\t" + size + "\t" + compressedSize);
	}
	
	public static boolean searchJarFile(File file, String classFilePath) {
		try {
			if (!file.exists()) return false;
			ZipFile jarFile = new ZipFile(file);
			Enumeration enu=jarFile.entries();
	    	while (enu.hasMoreElements()) {
	    		//System.out.println(enu.nextElement().`)
	    		//process(enu.nextElement());
		        ZipEntry entry = (ZipEntry) enu.nextElement();

		        String entryName = entry.getName();
		        if (entryName.endsWith(".class")) {
			        //if (entryName.endsWith("System.class")||entryName.endsWith("System.static")) {
		            ClassNode classNode = new ClassNode();

		            InputStream classFileInputStream = jarFile.getInputStream(entry);
		            try {
		                ClassReader classReader = new ClassReader(classFileInputStream);
		                classReader.accept(classNode, 0);
		            } finally {
		                classFileInputStream.close();
		            }

		            System.out.println(describeClass(classNode));
		        }
	    	}
			if (jarFile.getEntry(classFilePath) != null) {
				jarFile.close();
				return true;
			} else {
				jarFile.close();
				return false;
			}
		} catch (IOException ex) {
			System.out.println(ex.toString());
			return false;
		}
	}

	static class ArchiveFilter implements FileFilter {
		public boolean accept(File pathName) {
			String upcase = pathName.getName().toUpperCase();
			if (upcase.endsWith(".ZIP") || upcase.endsWith(".JAR"))
				return true;
			return false;
		}
	}

	public static void main(String[] args) {
		args = new String[] { "java.lang.String" };
		args = new String[] { "java.lang.System" };
		if (args.length == 0) {
			System.out.println("usage: java ClassFinder <class name>\n\n"
					+ "example: java ClassFinder java.lang.String\n");
			System.exit(0);
		}

		//File cwd = new File("war/WEB-INF/lib/");
		File cwd = new File("lib/");
		System.out.println(cwd.getAbsolutePath());
		//System.
		//System.exit(0);
		File[] archives = cwd.listFiles(new ArchiveFilter());

		String classFileName = args[0].replace('.', '/');
		if (classFileName.endsWith(".class") == false) {
			classFileName += ".class";
		}

		System.out.println("Searching for " + classFileName + " ...");
		for (int j = 0; j < archives.length; j++) {
			System.out.println("Searching " + archives[j].getName());
			if (searchJarFile(archives[j], classFileName)) {
				System.out.println("FOUND IN " + archives[j].getName());
			}
		}
	}
}
