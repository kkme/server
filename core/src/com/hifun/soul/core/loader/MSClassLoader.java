package com.hifun.soul.core.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.hifun.soul.core.encrypt.XorDecryptedInputStream;

/**
 * MagicStone类加载器;
 * 
 * @author crazyjohn
 * 
 */
public class MSClassLoader extends ClassLoader {

	private final Map<String, byte[]> bytesMap;
	private final Map<String, Class<?>> clazzMap;
	private final File encryptJarFile;
	private final ClassLoader parent;

	public MSClassLoader(ClassLoader parent, File encryptJarFile) {
		super(parent);
		bytesMap = new HashMap<String, byte[]>(2000);
		clazzMap = new HashMap<String, Class<?>>(2000);
		this.encryptJarFile = encryptJarFile;
		this.parent = parent;
		XorDecryptedInputStream fileInputStream = null;
		try {
			fileInputStream = new XorDecryptedInputStream(
					this.encryptJarFile.getAbsolutePath());
			System.out.println("MSClassLoader: "
					+ this.encryptJarFile.getAbsolutePath());
			byte[] bytes = new byte[fileInputStream.available()];
			System.out.println("MSClassLoader, jar size: " + bytes.length);
			fileInputStream.read(bytes);
			setJar(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private byte[] getBytes(String name) {
		return bytesMap.get(name);
	}

	private void setJar(byte[] bytes) throws IOException {
		ZipInputStream zipIs = new ZipInputStream(new ByteArrayInputStream(
				bytes));
		ZipEntry zipEntry = zipIs.getNextEntry();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		for (; zipEntry != null; zipEntry = zipIs.getNextEntry()) {
			String s = zipEntry.getName();
			if (s.indexOf(".class") > 0) {
				// 将class文件由路径符替换成为java标准的以.分隔的括号
				s = s.substring(0, s.lastIndexOf('.'));
				if (s.indexOf('\\') > 0) {
					s = s.replace('\\', '.');
				} else if (s.indexOf('/') > 0) {
					s = s.replace('/', '.');
				}
			}
			zipEntry.getSize();
			bout.reset();
			do {
				int i = zipIs.read(buffer, 0, buffer.length);
				if (i <= 0) {
					break;
				}
				bout.write(buffer, 0, i);
			} while (true);
			byte[] fileData = bout.toByteArray();
			bytesMap.put(s, fileData);
			zipIs.closeEntry();
		}
		zipIs.close();
	}

	// private native Class<?> makeClass(String name, byte[] bytes);

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> clazz = null;
		clazz = clazzMap.get(name);
		if (clazz == null) {
			byte[] bytes = getBytes(name);
			if (bytes != null) {
				clazz = this.defineClass(name, bytes, 0, bytes.length);
			}
			if (clazz != null) {
				clazzMap.put(name, clazz);
			}
		}
		if (clazz == null) {
			try {
				clazz = this.getParent().loadClass(name);
				if (clazz == null) {
					clazz = this.findSystemClass(name);
				}
			} catch (Exception e) {
				throw new ClassNotFoundException(name, e);
			}
		}
		if (clazz != null && resolve) {
			this.resolveClass(clazz);
		}
		return clazz;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		if (bytesMap.get(name) != null) {
			return new ByteArrayInputStream(bytesMap.get(name));
		}
		return parent.getResourceAsStream(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	@Override
	protected URL findResource(String name) {
		if (this.bytesMap.get(name) != null) {
			try {
				return new URL("jar:" + this.encryptJarFile.toURI().toURL()
						+ "!/" + name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.ClassLoader#findResources(java.lang.String)
	 */
	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Vector<URL> _urls = new Vector<URL>();
		URL resource = this.findResource(name);
		if (resource != null) {
			_urls.add(resource);
		}
		return _urls.elements();
	}
}
