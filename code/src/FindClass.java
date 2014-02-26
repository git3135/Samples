
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.zip.ZipFile;

public class FindClass {

	public static boolean searchJarFile(String jarFilePath, String classFilePath) {
		return searchJarFile(new File(jarFilePath), classFilePath);
	}

	public static boolean searchJarFile(File file, String classFilePath) {
		try {
			if (!file.exists()) return false;
			ZipFile jarFile = new ZipFile(file);
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
		if (args.length == 0) {
			System.out.println("usage: java ClassFinder <class name>\n\nexample: java ClassFinder java.lang.String\n");
			// System.exit(0);
		}
		File cwd = new File("war/WEB-INF/lib");
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
