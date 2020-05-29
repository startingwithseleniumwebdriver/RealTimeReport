package report.realtime.filehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.testng.ISuite;

import report.realtime.datahandler.DataMap;
import report.realtime.datahandler.DataPreparator;
import report.realtime.datahandler.DataSuite;

public class CreateFiles {
	
	private static String REPORT_DIR;
	private static String HTML_RESOURCE_DIR;
	
	static{
		REPORT_DIR = FileNameConstants.PROJECT_DIR + File.separator + FileNameConstants.TARGET_FOLDER + 
				File.separator + FileNameConstants.ROOT_FOLDER + File.separator;
		HTML_RESOURCE_DIR = FileNameConstants.RESOURCE_FOLDER + File.separator;
	}
	
	synchronized public static void createRequiredFolders(ISuite iSuite) {
		/*
		 * STEP 1: If RootResult folder, i.e - 'RealtimeReport', does not exist,
		 * create it
		 */
		;
		if (!new File(REPORT_DIR).exists()) {
			new File(REPORT_DIR).mkdir();
		}
		/*
		 * STEP 2: create css, fonts, image, js folders under RootResult folder,
		 * i.e - 'RealtimeReport'
		 */
		createFolderUnder(REPORT_DIR + FileNameConstants.CSS_FOLDER);
		createFolderUnder(REPORT_DIR + FileNameConstants.FONT_FOLDER);
		createFolderUnder(REPORT_DIR + FileNameConstants.IMAGE_FOLDER);
		createFolderUnder(REPORT_DIR + FileNameConstants.JS_FOLDER);
		createFolderUnder(REPORT_DIR + FileNameConstants.XSL_FOLDER);

		/*
		 * STEP 3: get all the resource files from 'html-rsc' folder
		 */

		String[] css_files = getFilesUnder(HTML_RESOURCE_DIR + FileNameConstants.CSS_FOLDER);
		String[] font_files = getFilesUnder(HTML_RESOURCE_DIR + FileNameConstants.FONT_FOLDER);
		String[] img_files = getFilesUnder(HTML_RESOURCE_DIR + FileNameConstants.IMAGE_FOLDER);
		String[] js_files = getFilesUnder(HTML_RESOURCE_DIR + FileNameConstants.JS_FOLDER);
		String[] xsl = getFilesUnder(HTML_RESOURCE_DIR + FileNameConstants.XSL_FOLDER);
		/*
		 * STEP 4: put all the resource files to the respective folders under
		 * RootResult folder, i.e - 'RealtimeReport' which is already created at
		 * step 2.
		 */

		copyFilesToDestination(css_files, REPORT_DIR + FileNameConstants.CSS_FOLDER);
		copyFilesToDestination(font_files, REPORT_DIR + FileNameConstants.FONT_FOLDER);
		copyFilesToDestination(img_files, REPORT_DIR + FileNameConstants.IMAGE_FOLDER);
		copyFilesToDestination(js_files, REPORT_DIR + FileNameConstants.JS_FOLDER);
		copyFilesToDestination(xsl, REPORT_DIR + FileNameConstants.XSL_FOLDER);
		/*
		 * STEP 5: Put values related to current suite at a set, 'suiteSet'
		 */
		if (DataMap.suiteMap.containsKey(iSuite)) {
			int suiteIndex = DataMap.suiteMap.get(iSuite);
			String suiteName = DataPreparator.prepareSuiteName(iSuite);
			DataSuite ds = new DataSuite(suiteIndex, suiteName, FileNameConstants.DASHBOARD_HTML + "-" + suiteIndex + ".html");
			DataMap.suiteSet.add(ds);
		}

		/*
		 * STEP 6: Finally create index.html page
		 */
		createIndexPage();

	}

	synchronized private static void copyFilesToDestination(String[] files, String destDirPath) {
		if (files.length > 0) {
			ClassLoader classLoader = CreateFiles.class.getClassLoader();
			if (classLoader.getResource(HTML_RESOURCE_DIR).getProtocol().contains("jar")) {
				URL jar = CreateFiles.class.getProtectionDomain().getCodeSource().getLocation();
				try(JarFile jarFile = new JarFile(new File(jar.toURI()))){
					final Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						final JarEntry entry = entries.nextElement();
						for (String file : files) {
							if (entry.getName().startsWith(file) && !entry.isDirectory()) {
								final File dest = new File(REPORT_DIR, entry.getName().substring(file.indexOf(File.separator)+1, file.length()));
								final File parent = dest.getParentFile();
								if (parent != null) {
									parent.mkdirs();
								}
								writeToFile(jarFile.getInputStream(entry), dest);
							}
						}
					}
				} catch (URISyntaxException | IOException e) {
					e.printStackTrace();
				}
			} else {
				for (int i = 0; i < files.length; i++) {
					try {
						Files.copy(Paths.get(files[i]), 
								Paths.get(destDirPath + files[i].substring(files[i].lastIndexOf(File.separator))),
								StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}
		}
	}
	
	synchronized private static void createFolderUnder(String dirPath) {
		if (!new File(dirPath).exists()) {
			new File(dirPath).mkdir();
		}
	}

	synchronized private static String[] getFilesUnder(String dirPath) {
		String[] files = null;
		ClassLoader classLoader = CreateFiles.class.getClassLoader();
		List<String> fileList = new ArrayList<String>();
		if (classLoader.getResource(dirPath).getProtocol().contains("jar")) {
			URL jar = CreateFiles.class.getProtectionDomain().getCodeSource().getLocation();
			try(JarFile jarFile = new JarFile(new File(jar.toURI()))) {	
				final Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					final JarEntry entry = entries.nextElement();
					if (entry.getName().startsWith(dirPath) && !entry.isDirectory()) {
						fileList.add(entry.getName());
					}
				}
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try(BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(CreateFiles.class.getClassLoader().getResourceAsStream(dirPath), StandardCharsets.UTF_8))) {
				String line = bufferedReader.readLine();
				while(line != null) {
					String filePath = dirPath + File.separator + line;
					URL url = CreateFiles.class.getClassLoader().getResource(filePath);
					File file = Paths.get(url.toURI()).toFile();
					fileList.add(file.toString());
					line = bufferedReader.readLine();
				}

			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			} 
		}
		files = fileList.toArray(new String[0]);
		return files;
	}

	synchronized private static void createIndexPage() {
		String index_HTML = REPORT_DIR + FileNameConstants.INDEX_HTML;
		if (new File(index_HTML).exists()) {
			new File(index_HTML).delete();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File(index_HTML), false));
		} catch (FileNotFoundException e) {
		}
		if (pw != null) {
			pw.write(FileNameConstants.INDEX_HEADER);
			pw.write(FileNameConstants.INDEX_BODY_PRE);
			for (DataSuite ds : DataMap.suiteSet) {
				pw.write("<a class='btn btn-link' href='" + ds.getSuiteHTMLPath()
				+ "' style='font-size:24px;'><i class='fa fa-dashboard'></i> " + ds.getSuiteName()
				+ "</a><br/>");
			}
			pw.write(FileNameConstants.INDEX_BODY_POST);
			pw.flush();
			pw.close();
		}
	}
	
	synchronized private static void writeToFile(final InputStream input, final File target){
		try(OutputStream output = Files.newOutputStream(target.toPath())){
			byte[] buffer = new byte[8 * 1024];
			int length = input.read(buffer);
			while (length > 0) {
				output.write(buffer, 0, length);
				length = input.read(buffer);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}